package com.example.adminapplication.service;

import com.example.adminapplication.model.CardTap;
import com.example.adminapplication.repository.CardTapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CardTapService {

    @Autowired
    private CardTapRepository cardTapRepository;

    public CardTap getCardTapById(Integer id) {
        return cardTapRepository.findById(id).orElse(null);
    }

    public Iterable<CardTap> getAllCardTaps() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return cardTapRepository.findAll(sort);
    }
}
