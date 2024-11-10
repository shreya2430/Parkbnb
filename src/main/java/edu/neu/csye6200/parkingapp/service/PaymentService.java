package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.PaymentDTO;
import edu.neu.csye6200.parkingapp.model.Card;
import edu.neu.csye6200.parkingapp.model.Payment;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.repository.CardRepository;
import edu.neu.csye6200.parkingapp.repository.PaymentRepository;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RenteeRepository renteeRepository;

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setStripePaymentId(paymentDTO.getStripePaymentId());

        // Retrieve Card and Rentee based on IDs from DTO
        Card card = cardRepository.findById(paymentDTO.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        Rentee rentee = renteeRepository.findById(paymentDTO.getRenteeId())
                .orElseThrow(() -> new IllegalArgumentException("Rentee not found"));
        payment.setCard(card);
        payment.setRentee(rentee);

        // Save payment and convert to DTO
        payment = paymentRepository.save(payment);
        return convertToDTO(payment);
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