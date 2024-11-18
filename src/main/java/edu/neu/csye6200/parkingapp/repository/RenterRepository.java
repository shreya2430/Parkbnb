package edu.neu.csye6200.parkingapp.repository;

import edu.neu.csye6200.parkingapp.dto.RenterDTO;
import edu.neu.csye6200.parkingapp.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Long> {
    Optional<RenterDTO> findByEmailAndPassword(String email, String password);
}
