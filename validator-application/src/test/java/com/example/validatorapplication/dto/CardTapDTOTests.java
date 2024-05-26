package com.example.validatorapplication.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class CardTapDTOTests {
    @Test
    public void testConstructors() {
        CardTapDTO cardTap1 = new CardTapDTO();
        assertNull(cardTap1.getId());
        assertNull(cardTap1.getCard());
        assertNull(cardTap1.getValidator());
        assertNull(cardTap1.getDateTime());
        assertNull(cardTap1.getFare());
        assertNull(cardTap1.getWithdrawAmount());
        assertNull(cardTap1.getIsSuccessful());
        assertNull(cardTap1.getNote());

        Timestamp specificTimestamp = Timestamp.valueOf("2024-04-14 09:00:00");
        CardTapDTO cardTap2 = new CardTapDTO(1, 5, 18, specificTimestamp, new BigDecimal("4.0"),
                new BigDecimal("3.5"), true, "WEEKLY_LIMIT");
        assertEquals(1, cardTap2.getId());
        assertEquals(5, cardTap2.getCard());
        assertEquals(18, cardTap2.getValidator());
        assertEquals(specificTimestamp, cardTap2.getDateTime());
        assertEquals(new BigDecimal("4.0"), cardTap2.getFare());
        assertEquals(new BigDecimal("3.5"), cardTap2.getWithdrawAmount());
        assertEquals(true, cardTap2.getIsSuccessful());
        assertEquals("WEEKLY_LIMIT", cardTap2.getNote());
    }

    @Test
    public void testGettersAndSetters() {
        CardTapDTO cardTap = new CardTapDTO();

        cardTap.setId(195);
        assertEquals(195, cardTap.getId());

        cardTap.setCard(37);
        assertEquals(37, cardTap.getCard());

        cardTap.setValidator(28);
        assertEquals(28, cardTap.getValidator());

        cardTap.setDateTime(Timestamp.valueOf("2024-04-17 09:00:00"));
        assertEquals(Timestamp.valueOf("2024-04-17 09:00:00"), cardTap.getDateTime());

        cardTap.setFare(new BigDecimal("1.75"));
        assertEquals(new BigDecimal("1.75"), cardTap.getFare());

        cardTap.setWithdrawAmount(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, cardTap.getWithdrawAmount());

        cardTap.setIsSuccessful(false);
        assertFalse( cardTap.getIsSuccessful());

        cardTap.setNote("ALREADY_PAID");
        assertEquals("ALREADY_PAID", cardTap.getNote());
    }
}
