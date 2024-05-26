package com.example.adminapplication.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CardTapTests {

    @Test
    public void testConstructors() {
        CardTap cardTap1 = new CardTap();
        assertNull(cardTap1.getId());
        assertNull(cardTap1.getCardId());
        assertNull(cardTap1.getValidatorId());
        assertNull(cardTap1.getDateTime());
        assertNull(cardTap1.getFare());
        assertNull(cardTap1.getWithdrawAmount());
        assertNull(cardTap1.getSuccessful());
        assertNull(cardTap1.getNote());

        BigDecimal fareAmount = new BigDecimal("2.75");
        BigDecimal withdrawAmount = new BigDecimal("0.00");
        LocalDateTime specificDateTime = LocalDateTime.of(2024, 4, 21, 12, 0);
        CardTap cardTap2 = new CardTap(1, 2, specificDateTime, fareAmount, withdrawAmount, true, NoteType.INSUFFICIENT_BALANCE);
        assertNull(cardTap2.getId());
        assertEquals(1, cardTap2.getCardId());
        assertEquals(2, cardTap2.getValidatorId());
        assertEquals(specificDateTime, cardTap2.getDateTime());
        assertEquals(fareAmount, cardTap2.getFare());
        assertEquals(withdrawAmount, cardTap2.getWithdrawAmount());
        assertTrue(cardTap2.getSuccessful());
        assertEquals(NoteType.INSUFFICIENT_BALANCE, cardTap2.getNote());

        CardTap cardTap3 = new CardTap(3, 3, 1, LocalDateTime.now(), new BigDecimal("3.50"), new BigDecimal("2.50"), true, null);
        assertEquals(3, cardTap3.getId());
        assertEquals(3, cardTap3.getCardId());
        assertEquals(1, cardTap3.getValidatorId());
        assertNotNull(cardTap3.getDateTime());
        assertEquals(new BigDecimal("3.50"), cardTap3.getFare());
        assertEquals(new BigDecimal("2.50"), cardTap3.getWithdrawAmount());
        assertTrue(cardTap3.getSuccessful());
        assertNull(cardTap3.getNote());
    }

    @Test
    public void testGettersAndSetters() {
        CardTap cardTap = new CardTap();

        cardTap.setId(5);
        assertEquals(5, cardTap.getId());

        cardTap.setCardId(4);
        assertEquals(4, cardTap.getCardId());

        cardTap.setValidatorId(2);
        assertEquals(2, cardTap.getValidatorId());

        LocalDateTime newDateTime = LocalDateTime.now();
        cardTap.setDateTime(newDateTime);
        assertEquals(newDateTime, cardTap.getDateTime());

        BigDecimal newFare = new BigDecimal("1.75");
        cardTap.setFare(newFare);
        assertEquals(newFare, cardTap.getFare());

        BigDecimal newWithdrawAmount = new BigDecimal("5.00");
        cardTap.setWithdrawAmount(newWithdrawAmount);
        assertEquals(newWithdrawAmount, cardTap.getWithdrawAmount());

        cardTap.setSuccessful(true);
        assertTrue(cardTap.getSuccessful());

        cardTap.setNote(NoteType.DAILY_LIMIT);
        assertEquals(NoteType.DAILY_LIMIT, cardTap.getNote());
    }

    @Test
    public void testPropertyObjectBehavior() {
        LocalDateTime specificDateTime = LocalDateTime.of(2024, 4, 27, 15, 15, 5);
        CardTap cardTap = new CardTap(55, 20, 78, specificDateTime, new BigDecimal("5.0"), new BigDecimal("4.5"), true, NoteType.WEEKLY_LIMIT);
        assertEquals(55, cardTap.getIdProperty().getValue());
        assertEquals(20, cardTap.getCardProperty().getValue());
        assertEquals(78, cardTap.getValidatorProperty().getValue());
        assertEquals("2024-04-27T15:15:05", cardTap.getDateTimeProperty().getValue());
        assertEquals(5.0, cardTap.getFareProperty().getValue());
        assertEquals(4.5, cardTap.getWithdrawAmountProperty().getValue());
        assertEquals(true, cardTap.getIsSuccessfulProperty().getValue());
        assertEquals("WEEKLY_LIMIT", cardTap.getNoteProperty().getValue());
    }
}
