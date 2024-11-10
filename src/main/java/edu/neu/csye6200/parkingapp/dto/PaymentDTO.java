package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PaymentDTO {
    private Long id; // Auto-generated, so no validation needed

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotBlank(message = "Payment status is required")
    private String paymentStatus;

    @NotBlank(message = "Stripe payment ID is required")
    private String stripePaymentId;

    @NotNull(message = "Card ID is required")
    private Long cardId;

    @NotNull(message = "Rentee ID is required")
    private Long renteeId;

    public PaymentDTO(Long id, BigDecimal amount, String paymentStatus, String stripePaymentId, Long cardId, Long renteeId) {
        this.id = id;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.stripePaymentId = stripePaymentId;
        this.cardId = cardId;
        this.renteeId = renteeId;
    }

    public Long getId() {
        return id;
    }

    public @NotNull(message = "Amount is required") BigDecimal getAmount() {
        return amount;
    }

    public @NotBlank(message = "Payment status is required") String getPaymentStatus() {
        return paymentStatus;
    }

    public @NotBlank(message = "Stripe payment ID is required") String getStripePaymentId() {
        return stripePaymentId;
    }

    public @NotNull(message = "Card ID is required") Long getCardId() {
        return cardId;
    }

    public @NotNull(message = "Rentee ID is required") Long getRenteeId() {
        return renteeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(@NotNull(message = "Amount is required") BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentStatus(@NotBlank(message = "Payment status is required") String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setStripePaymentId(@NotBlank(message = "Stripe payment ID is required") String stripePaymentId) {
        this.stripePaymentId = stripePaymentId;
    }

    public void setCardId(@NotNull(message = "Card ID is required") Long cardId) {
        this.cardId = cardId;
    }

    public void setRenteeId(@NotNull(message = "Rentee ID is required") Long renteeId) {
        this.renteeId = renteeId;
    }
}
