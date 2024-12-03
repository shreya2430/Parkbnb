package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.ApiResponse;
import edu.neu.csye6200.parkingapp.model.Renter;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import edu.neu.csye6200.parkingapp.service.RenterService;
import edu.neu.csye6200.parkingapp.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import edu.neu.csye6200.parkingapp.dto.RenterDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/renter")
public class RenterController {

    @Autowired
    private RenterService renterService;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private RenteeRepository renteeRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JavaMailSender mailSender;
    // 1. Get Renter Details
    @GetMapping("/{id}")
    public ResponseEntity<RenterDTO> getRenter(@PathVariable Long id) {
        Optional<RenterDTO> renterDTO = renterService.getRenterById(id);
        if (renterDTO.isPresent()) {
            return ResponseEntity.ok(renterDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 2. Login
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody RenterDTO renterDTO) {
        try {
            Optional<RenterDTO> existingUser = renterService.findUserByEmailAndPassword(renterDTO.getEmail(), renterDTO.getPassword());
            if (existingUser.isPresent()) {
                RenterDTO user = existingUser.get();
                String token = jwtUtil.generateToken(user.getEmail());

                Map<String, Object> response = new HashMap<>();
                response.put("id", user.getId());
                response.put("email", user.getEmail());
                response.put("token", token);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body(Collections.singletonMap("message", "Invalid email or password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "Internal server error"));
        }
    }

    // 3. Validate Token
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extractEmail(token);
                return ResponseEntity.ok("Token is valid for user: " + email);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header format");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    // 4. Register Renter
    @PostMapping
    public ResponseEntity<ApiResponse<RenterDTO>> createRenter(@Valid @RequestBody RenterDTO renterDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ApiResponse<RenterDTO> response = new ApiResponse<>(false, "Validation errors occurred", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (renteeRepository.findByEmail(renterDTO.getEmail()).isPresent()) {
            ApiResponse<RenterDTO> response = new ApiResponse<>(false, "Email already registered as a rentee", null);
            return ResponseEntity.status(409).body(response);
        }

        if (renterRepository.findByEmail(renterDTO.getEmail()).isPresent()) {
            ApiResponse<RenterDTO> response = new ApiResponse<>(false, "Email already registered as a renter", null);
            return ResponseEntity.status(409).body(response);
        }

        RenterDTO savedRenter = renterService.saveRenter(renterDTO, bindingResult);
        ApiResponse<RenterDTO> response = new ApiResponse<>(true, "Renter registered successfully", savedRenter);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Check if the email exists in the database
        Optional<Renter> renterOpt = renterRepository.findByEmail(email);
        if (!renterOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Email not registered."));
        }

        // Generate password reset token
        String resetToken = jwtUtil.generateToken(email);

        // Build the reset link
        String resetLink = "http://localhost:3000/reset-password?token=" + resetToken;

        // Send email with reset link
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click the link to reset your password: " + resetLink);
            mailSender.send(message);

            return ResponseEntity.ok(Collections.singletonMap("message", "Password reset link sent to your email."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error sending email."));
        }
    }


    // 6. Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("password");

        try {
            // Extract email from the reset token
            String email = jwtUtil.extractEmail(token);

            // Find the user by email
            Optional<Renter> renterOpt = renterRepository.findByEmail(email);
            if (!renterOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "User not found."));
            }

            // Update the user's password
            Renter renter = renterOpt.get();
            renter.setPassword(newPassword); // Remember to hash the password in a real-world application!
            renterRepository.save(renter);

            return ResponseEntity.ok(Collections.singletonMap("message", "Password has been reset successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Invalid or expired token."));
        }
    }


}
