package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.service.RenterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.neu.csye6200.parkingapp.dto.RenterDTO;

import java.util.Optional;


@RestController
@RequestMapping("/api/renters")
public class RenterController {

    @Autowired
    private RenterService renterService;

    @GetMapping("/{id}")
    public ResponseEntity<RenterDTO> getRenter(@PathVariable Long id) {
        Optional<RenterDTO> renterDTO = renterService.getRenterById(id);
        if (renterDTO.isPresent()) {
            return ResponseEntity.ok(renterDTO.get());
        } else {
            return ResponseEntity.notFound().build();
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
