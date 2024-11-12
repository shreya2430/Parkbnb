package edu.neu.csye6200.parkingapp.repository;

import edu.neu.csye6200.parkingapp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
