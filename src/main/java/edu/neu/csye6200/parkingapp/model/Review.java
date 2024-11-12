package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name="review")
public class Review extends BaseEntity {

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    // Foreign key to Rentee
    @ManyToOne
    @JoinColumn(name = "rentee_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVATION_RENTEE"))
    private Rentee rentee;

    // Foreign key to ParkingSpot
    @ManyToOne
    @JoinColumn(name = "parking_spot_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVATION_PARKING_SPOT"))
    private ParkingSpot parkingSpot;

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
