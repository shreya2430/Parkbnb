package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.ReservationDTO;
import edu.neu.csye6200.parkingapp.model.*;
import edu.neu.csye6200.parkingapp.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class ReservationService{

    @Autowired
    private  ReservationRepository reservationRepository;

    @Autowired
    private RenteeRepository renteeRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public Optional<ReservationDTO> getReservationById(Long id) {
        Optional <Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            Reservation res = reservation.get();
            ReservationDTO resDTO = new ReservationDTO(res.getId(), res.getStartTime(), res.getEndTime(), res.getConfirmationCode(), res.getStatus(), res.getIsEmailSent(), res.getRentee().getId(), res.getParkingSpot().getId(), res.getPayment().getId());
            return Optional.of(resDTO);
        }
        return Optional.empty();
    }

    public ReservationDTO saveReservation(@Valid ReservationDTO reservationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        Rentee rentee = renteeRepository.getById(reservationDTO.getRenteeId());
        ParkingSpot parkingSpot = parkingSpotRepository.getById(reservationDTO.getParkingSpotId());
        Payment payment = paymentRepository.getById(reservationDTO.getPaymentId());

        Reservation res = new Reservation();
        res.setStartTime(reservationDTO.getStartTime());
        res.setEndTime(reservationDTO.getEndTime());
        res.setConfirmationCode(reservationDTO.getConfirmationCode());
        res.setStatus(reservationDTO.getStatus());
        res.setIsEmailSent(reservationDTO.getEmailSent());
        res.setRentee(rentee);
        res.setParkingSpot(parkingSpot);
        res.setPayment(payment);

        // Save to database
        Reservation savedReservation = reservationRepository.save(res);

        // Return the saved entity as DTO
        return new ReservationDTO(savedReservation.getId(), savedReservation.getStartTime(), savedReservation.getEndTime(), savedReservation.getConfirmationCode(), savedReservation.getStatus(), savedReservation.getIsEmailSent(), savedReservation.getRentee().getId(), savedReservation.getParkingSpot().getId(), savedReservation.getPayment().getId());
    }
}
