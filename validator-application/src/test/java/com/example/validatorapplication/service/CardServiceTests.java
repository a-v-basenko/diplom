package com.example.validatorapplication.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class CardServiceTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCardExists() {
        int cardId = 123;
        String sql = "SELECT EXISTS(SELECT 1 FROM card WHERE id = ?)";
        Mockito.when(entityManager.createNativeQuery(sql)).thenReturn(query);
        Mockito.when(query.setParameter(1, cardId)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(true);

        boolean exists = cardService.cardExists(cardId);

        Assertions.assertTrue(exists);
        Mockito.verify(entityManager, Mockito.times(1)).createNativeQuery(sql);
        Mockito.verify(query, Mockito.times(1)).setParameter(1, cardId);
        Mockito.verify(query, Mockito.times(1)).getSingleResult();
    }

    @Test
    public void testGetCardBalance() {
        int cardId = 123;
        BigDecimal balance = new BigDecimal("100.00");
        String sql = "SELECT balance FROM card WHERE id = ?";
        Mockito.when(entityManager.createNativeQuery(sql)).thenReturn(query);
        Mockito.when(query.setParameter(1, cardId)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(balance);

        BigDecimal retrievedBalance = cardService.getCardBalance(cardId);

        Assertions.assertEquals(balance, retrievedBalance);
        Mockito.verify(entityManager, Mockito.times(1)).createNativeQuery(sql);
        Mockito.verify(query, Mockito.times(1)).setParameter(1, cardId);
        Mockito.verify(query, Mockito.times(1)).getSingleResult();
    }
}
