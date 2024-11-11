package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.ParkingLocationDTO;
import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import edu.neu.csye6200.parkingapp.model.Renter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.neu.csye6200.parkingapp.repository.ParkingLocationRepository;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import org.springframework.validation.BindingResult;

import java.util.Optional;
@Service
public class ParkingLocationService {
    @Autowired
    private  ParkingLocationRepository parkingLocationRepository;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private GeocodingService geocodingService;

    public Optional<ParkingLocationDTO> getParkingLocationById(Long id) {
        Optional<ParkingLocation> parkingLocation = parkingLocationRepository.findById(id);
        if (parkingLocation.isPresent()) {
            ParkingLocation r = parkingLocation.get();
            Long renterId = (r.getId() != null) ? r.getId() : null; // Retrieve renter ID if available
            ParkingLocationDTO parkingLocationDTO = new ParkingLocationDTO(r.getId(),r.getStreet(),r.getCity(),r.getPostalcode(),r.getState(),r.getCountry(),r.getLatitude(),r.getLongitude(),renterId);
            return Optional.of(parkingLocationDTO);
        }
        return Optional.empty();
    }

    public ParkingLocationDTO saveParkingLocation(@Valid ParkingLocationDTO parkingLocationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        // Convert DTO to entity
        ParkingLocation parkingLocation = new ParkingLocation();
        parkingLocation.setStreet(parkingLocationDTO.getStreet());
        parkingLocation.setCity(parkingLocationDTO.getCity());
        parkingLocation.setPostalcode(parkingLocationDTO.getPostalcode());
        parkingLocation.setState(parkingLocationDTO.getState());
        parkingLocation.setCountry(parkingLocationDTO.getCountry());

        // Set renter if renterId is provided
        if (parkingLocationDTO.getId() != null) {
             Renter r = renterRepository.findById(parkingLocationDTO.getRenterId())
                     .orElseThrow(() -> new RuntimeException("Renter not found with ID: " + parkingLocationDTO.getRenterId()));
            parkingLocation.setRenter(r);
        }


        // Get latitude and longitude using Geocoding Service
        geocodingService.getCoordinates(parkingLocation);

        // Save to database
        ParkingLocation saveParkingLocation = parkingLocationRepository.save(parkingLocation);

        // Return the saved entity as DTO
        return new ParkingLocationDTO(saveParkingLocation.getId(),saveParkingLocation.getStreet(),saveParkingLocation.getCity(),saveParkingLocation.getPostalcode(),saveParkingLocation.getState(),saveParkingLocation.getCountry(),saveParkingLocation.getLatitude(),saveParkingLocation.getLongitude(),saveParkingLocation.getRenter().getId());
    }
}
