package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Column(name = "start_time", nullable = false, length = 30)
    private Timestamp startTime;

    @Column(name = "end_time", nullable = false, length = 30)
    private Timestamp endTime;

    @Column(name = "confirmation_code", nullable = false, length = 10)
    private String confirmationCode;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Column(name = "is_email_sent", nullable = false)
    private Boolean isEmailSent;

    // Foreign key to Rentee
    @ManyToOne
    @JoinColumn(name = "rentee_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVATION_RENTEE"))
    private Rentee rentee;

    // Foreign key to ParkingSpot
    @ManyToOne
    @JoinColumn(name = "parking_spot_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVATION_PARKING_SPOT"))
    private ParkingSpot parkingSpot;

    // Foreign key to Payment
    @ManyToOne
    @JoinColumn(name = "payment_id", foreignKey = @ForeignKey(name = "FK_RESERVATION_PAYMENT"))
    private Payment payment;

    // Getters and Setters
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsEmailSent() {
        return isEmailSent;
    }

    public void setIsEmailSent(Boolean isEmailSent) {
        this.isEmailSent = isEmailSent;
    }

    public Rentee getRentee() {
        return rentee;
    }

    public void setRentee(Rentee rentee) {
        this.rentee = rentee;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
