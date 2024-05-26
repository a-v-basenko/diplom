package com.example.machineapplication.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

public class TopUpServiceTests {

    @Value("${machine.id}")
    private int machineId;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private TopUpService topUpService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertTopUp() {
        int cardId = 123;
        int amount = 50;
        String sql = "INSERT INTO top_up (machine, card, amount) VALUES (?, ?, ?)";
        Mockito.when(entityManager.createNativeQuery(sql)).thenReturn(query);
        Mockito.when(query.setParameter(1, machineId)).thenReturn(query);
        Mockito.when(query.setParameter(2, cardId)).thenReturn(query);
        Mockito.when(query.setParameter(3, amount)).thenReturn(query);

        topUpService.insertTopUp(cardId, amount);

        Mockito.verify(entityManager, Mockito.times(1)).createNativeQuery(sql);
        Mockito.verify(query, Mockito.times(1)).setParameter(1, machineId);
        Mockito.verify(query, Mockito.times(1)).setParameter(2, cardId);
        Mockito.verify(query, Mockito.times(1)).setParameter(3, amount);
        Mockito.verify(query, Mockito.times(1)).executeUpdate();
    }
}
