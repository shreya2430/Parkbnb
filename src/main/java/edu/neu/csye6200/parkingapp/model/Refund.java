package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="refunds")
public class Refund extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "rentee_id", nullable = false)
    private Rentee rentee; // Foreign key relationship with Rentee

    @Column(name = "refund_status", nullable = false, length = 20)
    private String refundStatus;

    @Column(name = "refund_amount", nullable = false)
    private double refundAmount;

    @Column(name = "stripe_refund_id", nullable = false, unique = true, length = 50)
    private Long stripeRefundId;

    // Getters and Setters
    public Long getId() {
        return super.getId(); // Use the inherited ID from BaseEntity
    }

    public void setId(Long id) {
        super.setId(id); // Set the ID in the BaseEntity
    }

    public Rentee getRentee() {
        return rentee;
    }

    public void setRentee(Rentee rentee) {
        this.rentee = rentee;
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
