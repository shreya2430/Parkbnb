package edu.neu.csye6200.parkingapp.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal amount;

    @Column(length = 15)
    private String paymentStatus;

    @Column(length = 20)
    private String stripePaymentId;

    @ManyToOne // Many payments to one card
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne // Many payments to one rentee
    @JoinColumn(name = "rentee_id", nullable = false)
    private Rentee rentee;

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getStripePaymentId() {
        return stripePaymentId;
    }

    public Card getCard() {
        return card;
    }

    public Rentee getRentee() {
        return rentee;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setStripePaymentId(String stripePaymentId) {
        this.stripePaymentId = stripePaymentId;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setRentee(Rentee rentee) {
        this.rentee = rentee;
    }
}