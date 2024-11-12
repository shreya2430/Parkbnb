package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.Bank_DetailDTO;
import edu.neu.csye6200.parkingapp.model.Bank_Detail;
import edu.neu.csye6200.parkingapp.repository.Bank_DetailRepository;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import edu.neu.csye6200.parkingapp.model.Renter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class Bank_DetailService {


        @Autowired
        private RenterRepository renterRepository;

        @Autowired
        private Bank_DetailRepository bank_detailRepository;

        public Optional<Bank_DetailDTO> getBybank_detail_id(Long bank_detail_id) {
            Optional<Bank_Detail> bank_detail = bank_detailRepository.findById(bank_detail_id);
            if (bank_detail.isPresent()) {
                Bank_Detail r = bank_detail.get();
                Long renterId = (r.getId() != null) ? r.getId() : null; // Retrieve renter ID if available
                Bank_DetailDTO bank_detailDTO = new Bank_DetailDTO(r.getbank_detail_id(),renterId, r.getbank_name(), r.getaccount_type(), r.getaccount_number(), r.getrouting_number());
                return Optional.of(bank_detailDTO);
            }
            return Optional.empty();
        }

        public Bank_DetailDTO saveBank_Detail(@Valid Bank_DetailDTO bank_detailDTO, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                // Handle validation errors
                throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
            }

            // Convert DTO to entity
            Bank_Detail bank_detail = new Bank_Detail();
            bank_detail.setbank_detail_id(bank_detailDTO.getbank_detail_id());
            bank_detail.setbank_name(bank_detailDTO.getbank_name());
            bank_detail.setaccount_type(bank_detailDTO.getaccount_type());
            bank_detail.setaccount_number(bank_detailDTO.getaccount_number());
            bank_detail.setrouting_number(bank_detailDTO.getrouting_number());

            // Set renter if renterId is provided
            if (bank_detailDTO.getRenterId() != null) {
                Renter renter = renterRepository.findById(bank_detailDTO.getRenterId())
                        .orElseThrow(() -> new RuntimeException("Renter not found with ID: " + bank_detailDTO.getRenterId()));
                bank_detail.setRenter(renter);
            }


            // Save to database
            Bank_Detail savedBank_Detail = bank_detailRepository.save(bank_detail);

            // Return the saved entity as DTO
            return new Bank_DetailDTO(savedBank_Detail.getbank_detail_id(), savedBank_Detail.getRenter().getId(), savedBank_Detail.getbank_name(), savedBank_Detail.getaccount_type(), savedBank_Detail.getaccount_number(),savedBank_Detail.getrouting_number());
        }
    }




