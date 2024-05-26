package com.example.adminapplication.service;

import com.example.adminapplication.model.Card;
import com.example.adminapplication.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Card getCardById(Integer id) {
        return cardRepository.findById(id).orElse(null);
    }

    public Iterable<Card> getAllCards() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return cardRepository.findAll(sort);
    }
}
