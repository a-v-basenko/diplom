package com.example.adminapplication.service;

import com.example.adminapplication.model.Card;
import com.example.adminapplication.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardServiceTests {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCard() {
        Card card = new Card();
        card.setId(1);
        Mockito.when(cardRepository.save(Mockito.any(Card.class))).thenReturn(card);

        Card createdCard = cardService.createCard(card);

        Assertions.assertEquals(card.getId(), createdCard.getId());
        Mockito.verify(cardRepository, Mockito.times(1)).save(card);
    }

    @Test
    public void testGetCardById() {
        Card card = new Card();
        card.setId(1);
        Mockito.when(cardRepository.findById(1)).thenReturn(Optional.of(card));

        Card retrievedCard = cardService.getCardById(1);

        Assertions.assertNotNull(retrievedCard);
        Assertions.assertEquals(card.getId(), retrievedCard.getId());
        Mockito.verify(cardRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetCardById_NotFound() {
        Mockito.when(cardRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Card retrievedCard = cardService.getCardById(1);

        Assertions.assertNull(retrievedCard);
        Mockito.verify(cardRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetAllCards() {
        List<Card> cardList = new ArrayList<>();
        cardList.add(new Card());
        cardList.add(new Card());

        Mockito.when(cardRepository.findAll(Mockito.any(Sort.class))).thenReturn(cardList);

        Iterable<Card> retrievedCards = cardService.getAllCards();

        Assertions.assertEquals(cardList.size(), ((List<Card>) retrievedCards).size());
        Mockito.verify(cardRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }
}
