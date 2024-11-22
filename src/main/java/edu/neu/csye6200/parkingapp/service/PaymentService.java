package edu.neu.csye6200.parkingapp.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import edu.neu.csye6200.parkingapp.config.StripeConfig;
import edu.neu.csye6200.parkingapp.dto.PaymentDTO;
import edu.neu.csye6200.parkingapp.model.Card;
import edu.neu.csye6200.parkingapp.model.Payment;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.repository.CardRepository;
import edu.neu.csye6200.parkingapp.repository.PaymentRepository;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RenteeRepository renteeRepository;


    @Autowired
    private StripeConfig stripeConfig;

    /**
     * Create a PaymentIntent and save payment details.
     */

    public String createPayment(PaymentDTO paymentDTO) throws StripeException {
        // Validate and fetch related entities
        Stripe.apiKey = stripeConfig.getSecretKey();
        Card card = cardRepository.findById(paymentDTO.getCardId())
                     .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        Rentee rentee = renteeRepository.findById(paymentDTO.getRenteeId())
                .orElseThrow(() -> new IllegalArgumentException("Rentee not found"));

        // Create Stripe PaymentIntent
        Map<String, Object> params = new HashMap<>();
        params.put("amount", convertToCents(paymentDTO.getAmount()));
        params.put("currency", "usd");
        params.put("automatic_payment_methods[enabled]",true);
        params.put("automatic_payment_methods[allow_redirects]","never");

        params.put("payment_method", card.getStripeCardId());

//        CustomerCreateParams customerParams =
//                CustomerCreateParams.builder()
//                        .setName(card.getCardHolderName())
//                        .build();
//        Customer customer = Customer.create(customerParams);
//        PaymentMethod paymentMethod = PaymentMethod.retrieve(card.getStripeCardId());
//
//        paymentMethod.attach(createAttachParams(customer.getId()));
//        params.put("customer",customer.getId());
//        params.put("setup_future_usage","off_session");


        PaymentIntent intent = PaymentIntent.create(params);

        savePayment(paymentDTO, card, rentee, intent);
        confirmPayment(card.getStripeCardId(), intent.getId());
        return intent.getId();
    }

    private Map<String, Object> createAttachParams(String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
        return params;
    }

    private long convertToCents(BigDecimal amount) {
        return amount.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).longValue();
    }

    private void savePayment(PaymentDTO paymentDTO, Card card, Rentee rentee, PaymentIntent intent) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentStatus("PENDING");
        payment.setStripePaymentId(intent.getId());
        payment.setCard(card);
        payment.setRentee(rentee);
        payment.setPaymentMethodId(intent.getPaymentMethod());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    /**
     * Confirm the payment after Stripe processes it.
     */
    private void confirmPayment(String stripePaymentId , String intentId) throws StripeException {
        // Validate PaymentMethodId

        // Retrieve the PaymentIntent from Stripe
        PaymentIntent intent = PaymentIntent.retrieve(intentId);

        // Confirm the PaymentIntent with the PaymentMethod
        Map<String, Object> confirmParams = new HashMap<>();
        confirmParams.put("confirm", true);

       intent = intent.confirm();

        // Handle the PaymentIntent status
        if ("succeeded".equals(intent.getStatus())) {
            Payment payment = paymentRepository.findByStripePaymentId(intentId);
            if(payment == null){
                throw new IllegalStateException("Payment not found");
            }
            payment.setPaymentStatus("COMPLETED");
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(payment);
            System.out.println("Payment successfully confirmed!");
        } else if ("requires_action".equals(intent.getStatus())) {
            throw new IllegalStateException("Payment requires additional authentication.");
        } else {
            throw new IllegalStateException("Payment confirmation failed with status: " + intent.getStatus());
        }
    }

    public Optional<PaymentDTO> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).map(this::convertToDTO);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setStripePaymentId(payment.getStripePaymentId());
        dto.setCardId(payment.getCard().getId());
        dto.setRenteeId(payment.getRentee().getId());
        return dto;
    }
}