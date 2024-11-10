package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.CardDTO;
import edu.neu.csye6200.parkingapp.model.Card;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.repository.CardRepository;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CardService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    RenteeRepository renteeRepository;

    public CardDTO addCard(CardDTO cardDTO) {
        // Add card to the database
        Card card = new Card();
        card.setLast4(cardDTO.getLast4());
        card.setCardType(cardDTO.getCardType());
        card.setStripeCardId(cardDTO.getStripeCardId());
        card.setExpiryDate(cardDTO.getExpiryDate());

        //retireve rentee from the database
        Rentee rentee = renteeRepository.findById(cardDTO.getRenteeId())
                .orElseThrow(() -> new IllegalArgumentException("Rentee not found"));

        card.setRentee(rentee);
        card = cardRepository.save(card);
        return convertToDTO(card);
    }

    public List<CardDTO> getCardsByRenteeId(Long renteeId) {
        // Retrieve cards from the database
        List<Card> cards = cardRepository.findByRenteeId(renteeId);
        return cards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CardDTO convertToDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setLast4(card.getLast4());
        cardDTO.setCardType(card.getCardType());
        cardDTO.setStripeCardId(card.getStripeCardId());
        cardDTO.setExpiryDate(card.getExpiryDate());
        cardDTO.setRenteeId(card.getRentee().getId());
        return cardDTO;
    }
}
