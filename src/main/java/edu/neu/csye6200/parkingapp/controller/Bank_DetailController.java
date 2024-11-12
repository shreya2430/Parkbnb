package edu.neu.csye6200.parkingapp.controller;


import edu.neu.csye6200.parkingapp.dto.Bank_DetailDTO;
import edu.neu.csye6200.parkingapp.service.Bank_DetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bank_details")

public class Bank_DetailController {


        @Autowired
        private Bank_DetailService bank_detailService;

        @GetMapping("/{bank_detail_id}")
        public ResponseEntity<Bank_DetailDTO> getBank_Detail(@PathVariable Long bank_detail_id) {
            Optional<Bank_DetailDTO> bank_detailDTO = bank_detailService.getBybank_detail_id(bank_detail_id);
            if (bank_detailDTO.isPresent()) {
                return ResponseEntity.ok(bank_detailDTO.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping
        public ResponseEntity<Bank_DetailDTO> createBank_Detail(@Valid @RequestBody Bank_DetailDTO bank_detailDTO, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                // Handle validation errors
                return ResponseEntity.badRequest().body(null);
            }

            Bank_DetailDTO savedBank_Detail = bank_detailService.saveBank_Detail(bank_detailDTO, bindingResult);
            return ResponseEntity.ok(savedBank_Detail);
        }
    }
