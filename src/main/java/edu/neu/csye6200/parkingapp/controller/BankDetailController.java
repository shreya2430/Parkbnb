package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.BankDetailDTO;
import edu.neu.csye6200.parkingapp.service.BankDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/bankdetails")
public class BankDetailController {

    @Autowired
    private BankDetailService bankDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<BankDetailDTO> getBankDetail(@PathVariable Long id) {
        Optional<BankDetailDTO> bankDetailDTO = bankDetailService.getById(id);
        if (bankDetailDTO.isPresent()) {
            return ResponseEntity.ok(bankDetailDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BankDetailDTO> createBankDetail(@Valid @RequestBody BankDetailDTO bankDetailDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest().body(null);
        }

        BankDetailDTO savedBankDetail = bankDetailService.saveBankDetail(bankDetailDTO, bindingResult);
        return ResponseEntity.ok(savedBankDetail);
    }
}
