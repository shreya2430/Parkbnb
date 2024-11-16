package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.BankDetailDTO;
import edu.neu.csye6200.parkingapp.model.BankDetail;
import edu.neu.csye6200.parkingapp.model.Renter;
import edu.neu.csye6200.parkingapp.repository.BankDetailRepository;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class BankDetailService {

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private BankDetailRepository bankDetailRepository;

    public Optional<BankDetailDTO> getById(Long id) {
        Optional<BankDetail> bankDetail = bankDetailRepository.findById(id);
        if (bankDetail.isPresent()) {
            BankDetail detail = bankDetail.get();
            Long renterId = (detail.getRenter() != null) ? detail.getRenter().getId() : null;
            BankDetailDTO bankDetailDTO = new BankDetailDTO(
                    detail.getId(),
                    renterId,
                    detail.getBankName(),
                    detail.getAccountType(),
                    detail.getAccountNumber(),
                    detail.getRoutingNumber()
            );
            return Optional.of(bankDetailDTO);
        }
        return Optional.empty();
    }

    public BankDetailDTO saveBankDetail(@Valid BankDetailDTO bankDetailDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        // Convert DTO to entity
        BankDetail bankDetail = new BankDetail();
        bankDetail.setId(bankDetailDTO.getId());
        bankDetail.setBankName(bankDetailDTO.getBankName());
        bankDetail.setAccountType(bankDetailDTO.getAccountType());
        bankDetail.setAccountNumber(bankDetailDTO.getAccountNumber());
        bankDetail.setRoutingNumber(bankDetailDTO.getRoutingNumber());


        if (bankDetailDTO.getRenterId() != null) {
            Renter renter = renterRepository.findById(bankDetailDTO.getRenterId())
                    .orElseThrow(() -> new RuntimeException("Renter not found with ID: " + bankDetailDTO.getRenterId()));
            bankDetail.setRenter(renter);
        }


        BankDetail savedBankDetail = bankDetailRepository.save(bankDetail);


        return new BankDetailDTO(
                savedBankDetail.getId(),
                savedBankDetail.getRenter() != null ? savedBankDetail.getRenter().getId() : null,
                savedBankDetail.getBankName(),
                savedBankDetail.getAccountType(),
                savedBankDetail.getAccountNumber(),
                savedBankDetail.getRoutingNumber()
        );
    }
}
