package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.ParkingLocationDTO;
import edu.neu.csye6200.parkingapp.dto.RenterDTO;
import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import edu.neu.csye6200.parkingapp.model.Renter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.neu.csye6200.parkingapp.repository.ParkingLocationRepository;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public class ParkingLocationService {
    @Autowired
    private  ParkingLocationRepository parkingLocationRepository;

    public Optional<ParkingLocationDTO> getParkingLocationById(Long id) {
        Optional<ParkingLocation> parkingLocation = parkingLocationRepository.findById(id);
        if (parkingLocation.isPresent()) {
            ParkingLocationRepository r = ParkingLocation.get();
            ParkingLocationDTO parkingLocationDTO = new ParkingLocationDTO(r.getId(), r.getStreet(), r.getLastName(), r.getPassword(), r.getEmail(), r.getPhone());
            return Optional.of(renterDTO);
        }
        return Optional.empty();
    }
}
