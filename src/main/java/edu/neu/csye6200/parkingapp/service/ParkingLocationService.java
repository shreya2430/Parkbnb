package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.ParkingLocationDTO;
import edu.neu.csye6200.parkingapp.dto.ParkingSpotDTO;
import edu.neu.csye6200.parkingapp.dto.ReviewDTO;
import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import edu.neu.csye6200.parkingapp.model.ParkingSpot;
import edu.neu.csye6200.parkingapp.model.Renter;
import edu.neu.csye6200.parkingapp.model.Review;
import edu.neu.csye6200.parkingapp.repository.ParkingSpotRepository;
import edu.neu.csye6200.parkingapp.repository.ReviewRepository;
import edu.neu.csye6200.parkingapp.service.thirdparty.GeocodingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import edu.neu.csye6200.parkingapp.repository.ParkingLocationRepository;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    private ReviewRepository reviewRepository;


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

    public List<ParkingLocationDTO> searchByQuery(String query) {
        List<ParkingLocation> locations = parkingLocationRepository.findByCityContainingIgnoreCaseOrStreetContainingIgnoreCase(
                query, query);

        return mapToDTOs(locations);
    }

    public List<ParkingLocation> findNearbyLocations(Double latitude, Double longitude, double radius) {
        return parkingLocationRepository.findNearbyLocations(latitude, longitude, radius);
    }
    public List<ParkingLocationDTO> searchByCoordinates(Double latitude, Double longitude, double radius) {
        List<ParkingLocation> locations = parkingLocationRepository.findNearbyLocations(latitude, longitude, radius);
        return mapToDTOs(locations);
    }

    private List<ParkingLocationDTO> mapToDTOs(List<ParkingLocation> locations) {
        List<ParkingLocationDTO> dtos = new ArrayList<>();
        for (ParkingLocation location : locations) {
            dtos.add(new ParkingLocationDTO(
                    location.getId(),
                    location.getStreet(),
                    location.getCity(),
                    location.getPostalcode(),
                    location.getState(),
                    location.getCountry(),
                    location.getLatitude(),
                    location.getLongitude(),
                    uploadDirForParkingLocations + location.getImageFileName(),
                    location.getRenter().getId()
            ));
        }
        return dtos;
    }


    public ParkingLocationDTO saveParkingLocation(MultipartFile file, @Valid ParkingLocationDTO parkingLocationDTO, BindingResult bindingResult) throws IOException {
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

        Renter r = renterRepository.findById(parkingLocationDTO.getRenterId())
                 .orElseThrow(() -> new RuntimeException("Renter not found with ID: " + parkingLocationDTO.getRenterId()));
        parkingLocation.setRenter(r);

        // Get latitude and longitude using Geocoding Service
        geocodingService.getCoordinates(parkingLocation);

        // Save to database
        ParkingLocation saveParkingLocation = parkingLocationRepository.save(parkingLocation);

        String imagePath = uploadDirForParkingLocations + saveParkingLocation.getImageFileName();

        uploadParkingLocationImage(saveParkingLocation.getImageFileName(), file);

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

    public List<ReviewDTO> getReviewsForParkingLocation(Long locationId) {

        List<Review> reviews = reviewRepository.findByParkingLocationId(locationId);

        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(review.getId());
            dto.setComment(review.getComment());
            dto.setRating(review.getRating());
            dto.setRenteeId(review.getRentee().getId());
            dto.setParkingLocationId(review.getParkingLocation().getId());
            dto.setRenteeName(review.getRentee().getFirstName() + " " + review.getRentee().getLastName());
            reviewDTOs.add(dto);
        }
        return reviewDTOs;
    }

    private void uploadParkingLocationImage(String imageFileName, MultipartFile file) throws IOException {
        // Get the absolute path of the upload directory
        Path uploadPath = Paths.get(uploadDirForParkingLocations).toAbsolutePath();

        // Ensure the directory exists or create it
        File directory = uploadPath.toFile();
        if (!directory.exists()) {
            boolean dirsCreated = directory.mkdirs();
            if (!dirsCreated) {
                throw new IOException("Failed to create directory: " + uploadPath);
            }
        }

        // Combine the directory path with the file name to get the full file path
        File targetFile = uploadPath.resolve(imageFileName).toFile();

        // Transfer the uploaded file to the target location
        file.transferTo(targetFile);
    }
}
