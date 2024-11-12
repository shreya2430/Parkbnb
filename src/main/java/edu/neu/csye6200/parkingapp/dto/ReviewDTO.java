package edu.neu.csye6200.parkingapp.dto;

import edu.neu.csye6200.parkingapp.model.ParkingSpot;
import edu.neu.csye6200.parkingapp.model.Rentee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReviewDTO {

    private Long reviewID;

    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 100, message = "Description must be between 5 and 100 characters")
    private String description;

    private Rentee rentee;
    private ParkingSpot parkingSpot;

    public ReviewDTO(Long id, String description, Rentee rentee, ParkingSpot parkingSpot) {
        this.reviewID = id;
        this.description = description;
        this.rentee = rentee;
        this.parkingSpot = parkingSpot;
    }

    public Long getId() {
        return reviewID;
    }

    public void setId(Long id) {
        this.reviewID = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}

