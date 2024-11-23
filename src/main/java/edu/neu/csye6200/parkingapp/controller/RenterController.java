package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.service.RenterService;
import edu.neu.csye6200.parkingapp.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<RenterDTO> getRenter(@PathVariable Long id) {
        Optional<RenterDTO> renterDTO = renterService.getRenterById(id);
        if (renterDTO.isPresent()) {
            return ResponseEntity.ok(renterDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody RenterDTO renterDTO) {
        try {
            // Check if the user exists with the provided email and password (make sure passwords are securely handled)
            Optional<RenterDTO> existingUser = renterService.findUserByEmailAndPassword(renterDTO.getEmail(), renterDTO.getPassword());

            if (existingUser.isPresent()) {
                RenterDTO user = existingUser.get();

                // Generate JWT token
                String token = jwtUtil.generateToken(user.getEmail());

                // Create a response with user email and token
                Map<String, Object> response = new HashMap<>();
                response.put("email", user.getEmail());
                response.put("token", token);

                return ResponseEntity.ok(response);
            } else {
                // Invalid credentials: Return an appropriate error message
                return ResponseEntity.status(401).body(Collections.singletonMap("message", "Invalid email or password"));
            }
        } catch (Exception e) {
            // Log error and return a generic 500 error response
            e.printStackTrace(); // This could be replaced with a logger
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "Internal server error"));
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
    public ResponseEntity<RenterDTO> createRenter(@Valid @RequestBody RenterDTO renterDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest().body(null);
        }

        RenterDTO savedRenter = renterService.saveRenter(renterDTO, bindingResult);
        return ResponseEntity.ok(savedRenter);
    }
}
