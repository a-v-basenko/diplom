package com.example.validatorapplication.service;

import com.example.validatorapplication.dto.CardTapDTO;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CardTapServiceTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private CardTapService cardTapService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertCardTap() {
        int cardId = 123;
        int insertedId = 456;
        int validatorId = 789;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BigDecimal fare = new BigDecimal("4.00");
        BigDecimal withdrawAmount = new BigDecimal("4.00");
        boolean success = true;
        String message = "DAILY_LIMIT";

        Object[] resultArray = new Object[]{insertedId, cardId, validatorId, timestamp, fare, withdrawAmount, success, message};
        List<Object[]> resultList = new ArrayList<>();
        resultList.add(resultArray);

        String insertSql = "SELECT fn_card_tap_insert(?, ?)";
        String selectSql = "SELECT * FROM card_tap WHERE id = ?";

        Mockito.when(entityManager.createNativeQuery(insertSql)).thenReturn(query);
        Mockito.when(entityManager.createNativeQuery(selectSql)).thenReturn(query);
        Mockito.when(query.setParameter(1, cardId)).thenReturn(query);
        Mockito.when(query.setParameter(2, validatorId)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(insertedId);
        Mockito.when(entityManager.createNativeQuery(selectSql)).thenReturn(query);
        Mockito.when(query.setParameter(1, insertedId)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(resultList);

        CardTapDTO inserted = cardTapService.insertCardTap(cardId);

        assertNotNull(inserted);
        assertEquals(insertedId, inserted.getId());
        assertEquals(cardId, inserted.getCard());
        assertEquals(validatorId, inserted.getValidator());
        assertEquals(timestamp, inserted.getDateTime());
        assertEquals(fare, inserted.getFare());
        assertEquals(withdrawAmount, inserted.getWithdrawAmount());
        assertEquals(success, inserted.getIsSuccessful());
        assertEquals(message, inserted.getNote());

        Mockito.verify(entityManager, Mockito.times(2)).createNativeQuery(Mockito.anyString());
        Mockito.verify(query, Mockito.times(3)).setParameter(Mockito.anyInt(), Mockito.any());
        Mockito.verify(query, Mockito.times(1)).getSingleResult();
        Mockito.verify(query, Mockito.times(1)).getResultList();
    }
}
