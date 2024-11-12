package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.model.Reservation;
import edu.neu.csye6200.parkingapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService{

    private  ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }


    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }


    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setStartTime(reservationDetails.getStartTime());
            reservation.setEndTime(reservationDetails.getEndTime());
            reservation.setConfirmationCode(reservationDetails.getConfirmationCode());
            reservation.setStatus(reservationDetails.getStatus());
            reservation.setIsEmailSent(reservationDetails.getIsEmailSent());
            reservation.setRentee(reservationDetails.getRentee());
            reservation.setParkingSpot(reservationDetails.getParkingSpot());
            reservation.setPayment(reservationDetails.getPayment());
            return reservationRepository.save(reservation);
        }).orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
    }


    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
