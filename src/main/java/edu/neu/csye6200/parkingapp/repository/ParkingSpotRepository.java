package edu.neu.csye6200.parkingapp.repository;

import edu.neu.csye6200.parkingapp.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot,Long>
{

}
