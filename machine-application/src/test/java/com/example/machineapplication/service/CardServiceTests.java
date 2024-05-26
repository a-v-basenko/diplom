package com.example.machineapplication.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertTrue(exists);
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

        assertEquals(balance, retrievedBalance);
        Mockito.verify(entityManager, Mockito.times(1)).createNativeQuery(sql);
        Mockito.verify(query, Mockito.times(1)).setParameter(1, cardId);
        Mockito.verify(query, Mockito.times(1)).getSingleResult();
    }

    @Test
    public void testInsertCard() {
        int cardType = 1;
        int initialBalance = 50;
        int insertedId = 123;
        String sql = "SELECT fn_card_insert(?, ?)";
        Mockito.when(entityManager.createNativeQuery(sql)).thenReturn(query);
        Mockito.when(query.setParameter(1, cardType)).thenReturn(query);
        Mockito.when(query.setParameter(2, initialBalance)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(insertedId);

        int result = cardService.insertCard(cardType, initialBalance);

        assertEquals(insertedId, result);
        Mockito.verify(entityManager, Mockito.times(1)).createNativeQuery(sql);
        Mockito.verify(query, Mockito.times(1)).setParameter(1, cardType);
        Mockito.verify(query, Mockito.times(1)).setParameter(2, initialBalance);
        Mockito.verify(query, Mockito.times(1)).getSingleResult();
    }
}
