package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.ApiResponse;
import edu.neu.csye6200.parkingapp.dto.ParkingLocationDTO;
import edu.neu.csye6200.parkingapp.dto.ParkingSpotDTO;
import edu.neu.csye6200.parkingapp.dto.ReviewDTO;
import edu.neu.csye6200.parkingapp.service.ParkingLocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import java.io.IOException;

@RestController
@RequestMapping("/api/parkinglocation")
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ParkingLocationDTO>> createParkingLocation(
            @Valid @ModelAttribute ParkingLocationDTO parkingLocationDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Validation failed"));
        }

        try {
            ParkingLocationDTO savedParkingLocation = parkingLocationService.saveParkingLocation(
                    parkingLocationDTO.getUploadImage(),
                    parkingLocationDTO,
                    bindingResult
            );
            return ResponseEntity.ok(new ApiResponse<>(true, savedParkingLocation));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Error uploading file: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}/available-spots")
    public ResponseEntity<List<ParkingSpotDTO>> getAvailableParkingSpots(@PathVariable Long id) {
        List<ParkingSpotDTO> availableSpots = parkingLocationService.getAvailableSpots(id);
        return ResponseEntity.ok(availableSpots);
    }

    @GetMapping("/{locationId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable Long locationId) {
        List<ReviewDTO> reviews = parkingLocationService.getReviewsForParkingLocation(locationId);
        if (reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(reviews);
    }
}
