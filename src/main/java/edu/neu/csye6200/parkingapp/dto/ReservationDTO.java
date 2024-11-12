package edu.neu.csye6200.parkingapp.dto;

import edu.neu.csye6200.parkingapp.model.ParkingSpot;
import edu.neu.csye6200.parkingapp.model.Payment;
import edu.neu.csye6200.parkingapp.model.Rentee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.security.Timestamp;

public class ReservationDTO {
    private Long reservationID;

    @NotBlank(message = "Start Time is required")
    private Timestamp startTime;

    @NotBlank(message = "End Time is required")
    private Timestamp endTime;

    @NotBlank(message = "Confirmation code is required")
    @Size( max = 10, message = "Confirmation code  must be of 10 characters")
    private String confirmationCode;

    @NotBlank(message = "Status is required")
    @Size( max = 15, message = "Status can have max 15 characters")
    private String status;

    private Boolean isEmailSent;
    private Rentee rentee;
    private ParkingSpot parkingSpot;
    private Payment payment;

    public ReservationDTO(Long reservationID, Timestamp startTime, Timestamp endTime, String confirmationCode, String status, Boolean isEmailSent, Rentee rentee, ParkingSpot parkingSpot, Payment payment) {
        this.reservationID = reservationID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.confirmationCode = confirmationCode;
        this.status = status;
        this.isEmailSent = isEmailSent;
        this.rentee = rentee;
        this.parkingSpot = parkingSpot;
        this.payment = payment;
    }

    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }

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

    public Boolean getEmailSent() {
        return isEmailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        isEmailSent = emailSent;
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
