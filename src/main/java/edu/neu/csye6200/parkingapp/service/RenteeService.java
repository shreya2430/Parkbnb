package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.RenteeDTO;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class RenteeService {

    @Autowired
    private RenteeRepository renteeRepository;

    public Optional<RenteeDTO> getRenteeById(Long id) {
        Optional<Rentee> rentee = renteeRepository.findById(id);
        if (rentee.isPresent()) {
            Rentee r = rentee.get();
            RenteeDTO renteeDTO = new RenteeDTO(r.getId(), r.getFirstName(), r.getLastName(), r.getPassword(), r.getEmail(), r.getPhone());
            return Optional.of(renteeDTO);
        }
        return Optional.empty();
    }

    public Optional<RenteeDTO> findUserByEmailAndPassword(String email, String password) {
        Optional<Rentee> rentee = renteeRepository.findByEmail(email);
        if (rentee.isPresent()) {
            Rentee existingRentee = rentee.get();
            if (BCrypt.checkpw(password, existingRentee.getPassword())) {
                // Password matches, return the user as DTO
                RenteeDTO renteeDTO = new RenteeDTO(existingRentee.getId(), existingRentee.getFirstName(), existingRentee.getLastName(), existingRentee.getPassword(), existingRentee.getEmail(), existingRentee.getPhone());
                return Optional.of(renteeDTO);
            }
        }
        return Optional.empty();
    }

    public RenteeDTO saveRentee(@Valid RenteeDTO renteeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        Rentee rentee = new Rentee();
        rentee.setFirstName(renteeDTO.getFirstName());
        rentee.setLastName(renteeDTO.getLastName());
        rentee.setPassword(renteeDTO.getPassword());
        rentee.setEmail(renteeDTO.getEmail());
        rentee.setPhone(renteeDTO.getPhone());

        Rentee savedRentee = renteeRepository.save(rentee);

        return new RenteeDTO(savedRentee.getId(), savedRentee.getFirstName(), savedRentee.getLastName(), savedRentee.getPassword(), savedRentee.getEmail(), savedRentee.getPhone());
    }
}
