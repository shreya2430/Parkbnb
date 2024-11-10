package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
public class CardDTO {

    private Long id;

    @NotBlank(message = "Last 4 digits of the card number is required")
    @Size(min = 4, max = 4, message = "Last 4 digits of the card number must be 4 characters")
    private String last4; // Last 4 digits of the card number

    @NotBlank(message = "Card type is required")
    private String cardType; // e.g., "Visa", "MasterCard"

    @NotBlank(message = "Stripe card ID is required")
    private String stripeCardId; // Stripe's unique ID for the card

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    @NotNull(message = "Rentee ID is required")
    private Long renteeId; // Reference to the Rentee entity

    public CardDTO(Long id, String last4, String cardType, String stripeCardId, LocalDate expiryDate, Long renteeId) {
        this.id = id;
        this.last4 = last4;
        this.cardType = cardType;
        this.stripeCardId = stripeCardId;
        this.expiryDate = expiryDate;
        this.renteeId = renteeId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getRenteeId() {
        return renteeId;
    }

    public void setRenteeId(Long renteeId) {
        this.renteeId = renteeId;
    }
}
