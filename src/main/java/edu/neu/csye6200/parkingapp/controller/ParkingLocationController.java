package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.ParkingLocationDTO;
import edu.neu.csye6200.parkingapp.dto.ParkingSpotDTO;
import edu.neu.csye6200.parkingapp.service.ParkingLocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/parkinglocation")
public class ParkingLocationController {

    @Autowired
    private ParkingLocationService parkingLocationService;

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLocationDTO> getParkingLocation(@PathVariable Long id) {
        Optional<ParkingLocationDTO> parkingLocationDTO = parkingLocationService.getParkingLocationById(id);
        if (parkingLocationDTO.isPresent()) {
            return ResponseEntity.ok(parkingLocationDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ParkingLocationDTO> createParkingLocation(@Valid @RequestBody ParkingLocationDTO parkingLocationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest().body(null);
        }

        ParkingLocationDTO savedParkingLocation = parkingLocationService.saveParkingLocation(parkingLocationDTO, bindingResult);
        return ResponseEntity.ok(savedParkingLocation);
    }

    @GetMapping("/{id}/available-spots")
    public ResponseEntity<List<ParkingSpotDTO>> getAvailableParkingSpots(@PathVariable Long id) {
        List<ParkingSpotDTO> availableSpots = parkingLocationService.getAvailableSpots(id);
        return ResponseEntity.ok(availableSpots);
    }
}
