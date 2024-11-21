package edu.neu.csye6200.parkingapp.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;


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

    @NotBlank(message = "Rentee ID is required")
    private Long renteeId;

    @NotBlank(message = "Parking Spot ID is required")
    private Long parkingSpotId;

    @NotBlank(message = "Payment ID is required")
    private Long paymentId;

    public ReservationDTO(Long reservationID, Timestamp startTime, Timestamp endTime, String confirmationCode, String status, Boolean isEmailSent, Long renteeId, Long parkingSpotId, Long paymentId) {
        this.reservationID = reservationID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.confirmationCode = confirmationCode;
        this.status = status;
        this.isEmailSent = isEmailSent;
        this.renteeId = renteeId;
        this.parkingSpotId = parkingSpotId;
        this.paymentId = paymentId;
    }

    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }

    public @NotBlank(message = "Start Time is required") Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(@NotBlank(message = "Start Time is required") Timestamp startTime) {
        this.startTime = startTime;
    }

    public @NotBlank(message = "End Time is required") Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(@NotBlank(message = "End Time is required") Timestamp endTime) {
        this.endTime = endTime;
    }

    public @NotBlank(message = "Confirmation code is required") @Size(max = 10, message = "Confirmation code  must be of 10 characters") String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(@NotBlank(message = "Confirmation code is required") @Size(max = 10, message = "Confirmation code  must be of 10 characters") String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public @NotBlank(message = "Status is required") @Size(max = 15, message = "Status can have max 15 characters") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Status is required") @Size(max = 15, message = "Status can have max 15 characters") String status) {
        this.status = status;
    }

    public Boolean getEmailSent() {
        return isEmailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        isEmailSent = emailSent;
    }

    public @NotBlank(message = "Rentee ID is required") Long getRenteeId() {
        return renteeId;
    }

    public void setRenteeId(@NotBlank(message = "Rentee ID is required") Long renteeId) {
        this.renteeId = renteeId;
    }

    public @NotBlank(message = "Parking Spot ID is required") Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(@NotBlank(message = "Parking Spot ID is required") Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public @NotBlank(message = "Payment ID is required") Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(@NotBlank(message = "Payment ID is required") Long paymentId) {
        this.paymentId = paymentId;
    }
}
