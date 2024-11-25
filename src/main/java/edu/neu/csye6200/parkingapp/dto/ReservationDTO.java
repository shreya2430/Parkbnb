package edu.neu.csye6200.parkingapp.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;


public class ReservationDTO {
    private Long id;

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

    @NotBlank(message = "Rentee ID is required")
    private Long renteeId;

    @NotBlank(message = "Parking Spot ID is required")
    private Long parkingSpotId;

    @NotBlank(message = "Payment ID is required")
    private Long paymentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getRenteeId() {
        return renteeId;
    }

    public void setRenteeId(Long renteeId) {
        this.renteeId = renteeId;
    }

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
