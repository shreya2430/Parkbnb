package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.model.Review;
import edu.neu.csye6200.parkingapp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review reviewDetails) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setDescription(reviewDetails.getDescription());
                    review.setRentee(reviewDetails.getRentee());
                    review.setParkingSpot(reviewDetails.getParkingSpot());
                    return reviewRepository.save(review);
                }).orElseThrow(() -> new RuntimeException("Review not found with id " + id));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
