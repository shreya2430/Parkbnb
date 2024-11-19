package edu.neu.csye6200.parkingapp.service;


import edu.neu.csye6200.parkingapp.dto.ReviewDTO;
import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.model.Review;
import edu.neu.csye6200.parkingapp.repository.ParkingLocationRepository;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import edu.neu.csye6200.parkingapp.repository.ReviewRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RenteeRepository renteeRepository;

    @Autowired
    private ParkingLocationRepository parkingLocationRepository;

    public Optional<ReviewDTO> getReviewById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            Review r = review.get();
            ReviewDTO reviewDTO = new ReviewDTO(r.getId(), r.getRentee().getFirstName() + " " + r.getRentee().getLastName(), r.getComment(), r.getRentee().getId(), r.getParkingLocation().getId());
            return Optional.of(reviewDTO);
        }
        return Optional.empty();
    }


    public ReviewDTO saveReview(@Valid ReviewDTO reviewDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        Rentee rentee = renteeRepository.getById(reviewDTO.getRenteeId());
        ParkingLocation parkingLocation = parkingLocationRepository.getById(reviewDTO.getParkingLocationId());

        Review review = new Review();
        review.setComment(reviewDTO.getComment());
        review.setRentee(rentee);
        review.setParkingLocation(parkingLocation);

        // Save to database
        Review savedReview = reviewRepository.save(review);

        // Return the saved entity as DTO
        return new ReviewDTO(savedReview.getId(), rentee.getFirstName() + " " + rentee.getLastName(), savedReview.getComment(), savedReview.getRentee().getId(), savedReview.getParkingLocation().getId());
    }
}
