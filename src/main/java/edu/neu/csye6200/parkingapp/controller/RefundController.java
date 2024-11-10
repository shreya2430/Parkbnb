package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.RefundDTO;
import edu.neu.csye6200.parkingapp.service.RefundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/refund")

public class RefundController {



    @Autowired
    private RefundService refundService;

    @GetMapping("/{refund_id}")
    public ResponseEntity<RefundDTO> getRefund(@PathVariable Long refund_id) {
        Optional<RefundDTO> refundDTO = refundService.getByrefund_id(refund_id);
        if (refundDTO.isPresent()) {
            return ResponseEntity.ok(refundDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RefundDTO> createRefund(@Valid @RequestBody RefundDTO refundDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest().body(null);
        }

        RefundDTO savedRefund = refundService.saveRefund(refundDTO, bindingResult);
        return ResponseEntity.ok(savedRefund);
    }
}
