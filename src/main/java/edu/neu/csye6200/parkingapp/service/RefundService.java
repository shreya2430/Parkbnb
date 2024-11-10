package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.RefundDTO;
import edu.neu.csye6200.parkingapp.model.Refund;
import edu.neu.csye6200.parkingapp.repository.RefundRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class RefundService {

        @Autowired
        private RefundRepository refundRepository;

        public Optional<RefundDTO> getByrefund_id(Long refund_id) {
            Optional<Refund> refund = refundRepository.findById(refund_id);
            if (refund.isPresent()) {
                Refund r = refund.get();
                RefundDTO refundDTO = new RefundDTO(r.getrefund_id(), r.getrefund_status(), r.getrefund_amount(), r.getstripe_refund_id());
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
            refund.setrefund_id(refundDTO.getrefund_id());
            refund.setrefund_status(refundDTO.getrefund_status());
            refund.setrefund_amount(refundDTO.getrefund_amount());
            refund.setstripe_refund_id(refundDTO.getstripe_refund_id());


            // Save to database
            Refund savedRefund = refundRepository.save(refund);

            // Return the saved entity as DTO
            return new RefundDTO(savedRefund.getrefund_id(), savedRefund.getrefund_status(), savedRefund.getrefund_amount(), savedRefund.getstripe_refund_id());
        }
    }



