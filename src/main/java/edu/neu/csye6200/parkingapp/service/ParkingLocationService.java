package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.ParkingLocationDTO;
import edu.neu.csye6200.parkingapp.dto.ParkingSpotDTO;
import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import edu.neu.csye6200.parkingapp.model.ParkingSpot;
import edu.neu.csye6200.parkingapp.model.Renter;
import edu.neu.csye6200.parkingapp.repository.ParkingSpotRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import edu.neu.csye6200.parkingapp.repository.ParkingLocationRepository;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLocationService {
    @Autowired
    private  ParkingLocationRepository parkingLocationRepository;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Value("${upload.dir.parking_locations}")
    private String uploadDirForParkingLocations;


    public Optional<ParkingLocationDTO> getParkingLocationById(Long id) {
        Optional<ParkingLocation> parkingLocation = parkingLocationRepository.findById(id);
        if (parkingLocation.isPresent()) {
            ParkingLocation p = parkingLocation.get();
            Long renterId = (p.getRenter() != null) ? p.getRenter().getId() : null; // Retrieve renter ID if available
            String imagePath = uploadDirForParkingLocations + p.getImageFileName();
            ParkingLocationDTO parkingLocationDTO = new ParkingLocationDTO(p.getId(),p.getStreet(),p.getCity(),p.getPostalcode(),p.getState(),p.getCountry(),p.getLatitude(),p.getLongitude(), imagePath, renterId);
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

        String imagePath = uploadDirForParkingLocations + saveParkingLocation.getImageFileName();

        // Return the saved entity as DTO
        return new ParkingLocationDTO(saveParkingLocation.getId(),saveParkingLocation.getStreet(),saveParkingLocation.getCity(),saveParkingLocation.getPostalcode(),saveParkingLocation.getState(),saveParkingLocation.getCountry(),saveParkingLocation.getLatitude(),saveParkingLocation.getLongitude(), imagePath, saveParkingLocation.getRenter().getId());
    }

    public List<ParkingSpotDTO> getAvailableSpots(Long locationId) {
        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByParkingLocationIdAndIsAvailable(locationId, true);
        List<ParkingSpotDTO> parkingSpotDTOList = new ArrayList<>();

        for (ParkingSpot ps : parkingSpots) {
            Long parkingLocationId = ps.getParkingLocation().getId();
            ParkingSpotDTO parkingSpotDTO = new ParkingSpotDTO(
                    ps.getId(),
                    ps.getSpotNumber(),
                    ps.getSpotType(),
                    ps.isAvailable(),
                    ps.getPricePerHour(),
                    parkingLocationId
            );
            parkingSpotDTOList.add(parkingSpotDTO);
        }

        return parkingSpotDTOList;
    }
}
