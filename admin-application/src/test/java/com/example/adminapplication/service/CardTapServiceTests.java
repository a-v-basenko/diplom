package com.example.adminapplication.service;

import com.example.adminapplication.model.CardTap;
import com.example.adminapplication.repository.CardTapRepository;
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

public class CardTapServiceTests {

    @Mock
    private CardTapRepository cardTapRepository;

    @InjectMocks
    private CardTapService cardTapService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCardTapById() {
        CardTap cardTap = new CardTap();
        cardTap.setId(1);
        Mockito.when(cardTapRepository.findById(1)).thenReturn(Optional.of(cardTap));

        CardTap retrievedCardTap = cardTapService.getCardTapById(1);

        Assertions.assertNotNull(retrievedCardTap);
        Assertions.assertEquals(cardTap.getId(), retrievedCardTap.getId());
        Mockito.verify(cardTapRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetCardTapById_NotFound() {
        Mockito.when(cardTapRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        CardTap retrievedCardTap = cardTapService.getCardTapById(1);

        Assertions.assertNull(retrievedCardTap);
        Mockito.verify(cardTapRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetAllCardTaps() {
        List<CardTap> cardTapList = new ArrayList<>();
        cardTapList.add(new CardTap());
        cardTapList.add(new CardTap());

        Mockito.when(cardTapRepository.findAll(Mockito.any(Sort.class))).thenReturn(cardTapList);

        Iterable<CardTap> retrievedCardTaps = cardTapService.getAllCardTaps();

        Assertions.assertEquals(cardTapList.size(), ((List<CardTap>) retrievedCardTaps).size());
        Mockito.verify(cardTapRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }
}
