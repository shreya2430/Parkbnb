package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.RefundDTO;
import edu.neu.csye6200.parkingapp.model.Refund;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.repository.RefundRepository;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class RefundService {

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private RenteeRepository renteeRepository;

    public Optional<RefundDTO> getById(Long id) {
        Optional<Refund> refund = refundRepository.findById(id);
        if (refund.isPresent()) {
            Refund r = refund.get();
            RefundDTO refundDTO = new RefundDTO(r.getId(), r.getRentee().getId(), r.getRefundStatus(), r.getRefundAmount(), r.getStripeRefundId());
            return Optional.of(refundDTO);
        }
        return Optional.empty();
    }

    public RefundDTO saveRefund(@Valid RefundDTO refundDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        // Convert DTO to entity
        Refund refund = new Refund();
        refund.setRefundStatus(refundDTO.getRefundStatus());
        refund.setRefundAmount(refundDTO.getRefundAmount());
        refund.setStripeRefundId(refundDTO.getStripeRefundId());

        // Handle the Rentee foreign key relationship
        if (refundDTO.getRenteeId() != null) {
            Rentee rentee = renteeRepository.findById(refundDTO.getRenteeId())
                    .orElseThrow(() -> new RuntimeException("Rentee not found with ID: " + refundDTO.getRenteeId()));
            refund.setRentee(rentee);
        }

        // Save to database
        Refund savedRefund = refundRepository.save(refund);

        // Return the saved entity as DTO
        return new RefundDTO(savedRefund.getId(), savedRefund.getRentee().getId(), savedRefund.getRefundStatus(), savedRefund.getRefundAmount(), savedRefund.getStripeRefundId());
    }
}
