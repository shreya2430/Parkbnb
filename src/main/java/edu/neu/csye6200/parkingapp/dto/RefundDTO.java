package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RefundDTO {

    private Long id; // Inherited primary key
    private Long renteeId;

    @NotNull(message = "Refund status is required")
    @Size(min = 2, max = 20, message = "Refund status must be between 2 and 20 characters")
    private String refundStatus;

    @NotNull(message = "Refund amount is required")
    private double refundAmount;

    @NotNull(message = "Stripe refund ID is required")
    private Long stripeRefundId;

    // Constructor
    public RefundDTO(Long id, Long renteeId, String refundStatus, double refundAmount, Long stripeRefundId) {
        this.id = id;
        this.renteeId = renteeId;
        this.refundStatus = refundStatus;
        this.refundAmount = refundAmount;
        this.stripeRefundId = stripeRefundId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRenteeId() {
        return renteeId;
    }

    public void setRenteeId(Long renteeId) {
        this.renteeId = renteeId;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Long getStripeRefundId() {
        return stripeRefundId;
    }

    public void setStripeRefundId(Long stripeRefundId) {
        this.stripeRefundId = stripeRefundId;
    }
}
