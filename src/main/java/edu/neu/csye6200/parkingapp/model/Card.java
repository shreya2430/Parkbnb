package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
public class Card extends BaseEntity {

    @Column(length = 4, nullable = false)
    private String last4; // Last 4 digits of the card number

    @Column(length = 6, nullable = false)
    private String cardType; // e.g., "Visa", "MasterCard"

    @Column(length = 20, nullable = false)
    private String stripeCardId; // ID provided by Stripe for saved card

    @Column(nullable = false)
    private LocalDate expiryDate; // Expiry date of the card

    @ManyToOne
    @JoinColumn(name = "rentee_id", nullable = false)
    private Rentee rentee;

    // Getters and Setters

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getStripeCardId() {
        return stripeCardId;
    }

    public void setStripeCardId(String stripeCardId) {
        this.stripeCardId = stripeCardId;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Rentee getRentee() {
        return rentee;
    }

    public void setRentee(Rentee rentee) {
        this.rentee = rentee;
    }
}
