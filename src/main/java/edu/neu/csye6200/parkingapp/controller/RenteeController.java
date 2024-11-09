package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.RenteeDTO;
import edu.neu.csye6200.parkingapp.service.RenteeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/rentees")
public class RenteeController {

    @Autowired
    private RenteeService renteeService;

    @GetMapping("/{id}")
    public ResponseEntity<RenteeDTO> getRentee(@PathVariable Long id) {
        Optional<RenteeDTO> renteeDTO = renteeService.getRenteeById(id);
        if (renteeDTO.isPresent()) {
            return ResponseEntity.ok(renteeDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RenteeDTO> createRentee(@Valid @RequestBody RenteeDTO renteeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest().body(null);
        }

        RenteeDTO savedRentee = renteeService.saveRentee(renteeDTO, bindingResult);
        return ResponseEntity.ok(savedRentee);
    }
}
