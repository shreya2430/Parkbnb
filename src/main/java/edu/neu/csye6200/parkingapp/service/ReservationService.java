package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.ReservationDTO;
import edu.neu.csye6200.parkingapp.model.*;
import edu.neu.csye6200.parkingapp.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            ReservationDTO dto = new ReservationDTO();
            dto.setId(res.getId());
            dto.setRenteeId(res.getRentee().getId());
            dto.setParkingSpotId(res.getParkingSpot().getId());
            dto.setStartTime(res.getStartTime());
            dto.setEndTime(res.getEndTime());
            dto.setConfirmationCode(res.getConfirmationCode());
            dto.setStatus(res.getStatus());
            dto.setPaymentId(res.getPayment().getId());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public List<ReservationDTO> getReservationsByRenteeId(Long renteeId) {
        List<Reservation> reservations = reservationRepository.findByRenteeId(renteeId);

        return reservations.stream().map(reservation -> {
            ReservationDTO dto = new ReservationDTO();
            dto.setId(reservation.getId());
            dto.setRenteeId(reservation.getRentee().getId());
            dto.setParkingSpotId(reservation.getParkingSpot().getId());
            dto.setStartTime(reservation.getStartTime());
            dto.setEndTime(reservation.getEndTime());
            dto.setConfirmationCode(reservation.getConfirmationCode());
            dto.setStatus(reservation.getStatus());
            dto.setPaymentId(reservation.getPayment().getId());
            return dto;
        }).collect(Collectors.toList());
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

        ReservationDTO dto = new ReservationDTO();
        dto.setId(savedReservation.getId());
        dto.setRenteeId(savedReservation.getRentee().getId());
        dto.setParkingSpotId(savedReservation.getParkingSpot().getId());
        dto.setStartTime(savedReservation.getStartTime());
        dto.setEndTime(savedReservation.getEndTime());
        dto.setConfirmationCode(savedReservation.getConfirmationCode());
        dto.setStatus(savedReservation.getStatus());
        dto.setPaymentId(savedReservation.getPayment().getId());
        return dto;
    }
}
