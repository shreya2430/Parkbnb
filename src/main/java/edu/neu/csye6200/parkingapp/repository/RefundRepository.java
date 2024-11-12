package edu.neu.csye6200.parkingapp.repository;

import edu.neu.csye6200.parkingapp.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    //Optional<Refund> findByrefund_id(Long refund_id);
}



