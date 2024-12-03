package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.ApiResponse;
import edu.neu.csye6200.parkingapp.dto.RenteeDTO;
import edu.neu.csye6200.parkingapp.dto.RenterDTO;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import edu.neu.csye6200.parkingapp.service.RenteeService;
import edu.neu.csye6200.parkingapp.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentees")
public class RenteeController {

    @Autowired
    private RenteeService renteeService;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private RenteeRepository renteeRepository;


    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<RenteeDTO> getRentee(@PathVariable Long id) {
        Optional<RenteeDTO> renteeDTO = renteeService.getRenteeById(id);
        if (renteeDTO.isPresent()) {
            return ResponseEntity.ok(renteeDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody RenteeDTO renteeDTO) {
        Optional<RenteeDTO> existingUser = renteeService.findUserByEmailAndPassword(renteeDTO.getEmail(), renteeDTO.getPassword());

        if (existingUser.isPresent()) {
            RenteeDTO user = existingUser.get();

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail());

            // Build response
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("email", user.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        System.out.println("Received Authorization Header: " + authHeader);
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Remove "Bearer " prefix
                String email = jwtUtil.extractEmail(token); // Extract email from token
                return ResponseEntity.ok("Token is valid for user: " + email);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header format");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RenteeDTO>> createRentee(@Valid @RequestBody RenteeDTO renteeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            ApiResponse<RenteeDTO> response = new ApiResponse<>(false, "Validation errors occurred", null);
            return ResponseEntity.badRequest().body(response);
        }

        // Check if the email is already registered as a rentee
        if (renteeRepository.findByEmail(renteeDTO.getEmail()).isPresent()) {
            ApiResponse<RenteeDTO> response = new ApiResponse<>(false, "Email already registered as a rentee", null);
            return ResponseEntity.status(409).body(response);
        }

        // Check if the email is already registered as a renter
        if (renterRepository.findByEmail(renteeDTO.getEmail()).isPresent()) {
            ApiResponse<RenteeDTO> response = new ApiResponse<>(false, "Email already registered as a renter", null);
            return ResponseEntity.status(409).body(response);
        }

        // Save the renter
        RenteeDTO savedRenter = renteeService.saveRentee(renteeDTO, bindingResult);
        ApiResponse<RenteeDTO> response = new ApiResponse<>(true, "Rentee registered successfully", savedRenter);
        return ResponseEntity.status(201).body(response);
    }
}
