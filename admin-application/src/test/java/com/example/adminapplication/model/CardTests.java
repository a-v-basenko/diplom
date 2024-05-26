package com.example.adminapplication.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CardTests {

    @Test
    public void testConstructors() {
        Card card1 = new Card();
        assertNull(card1.getId());
        assertNull(card1.getTypeId());
        assertNull(card1.getBalance());

        BigDecimal initialBalance = new BigDecimal("100.00");
        Card card2 = new Card(2, initialBalance);
        assertNull(card2.getId());
        assertEquals(2, card2.getTypeId());
        assertEquals(initialBalance, card2.getBalance());

        Card card3 = new Card(3, 1, new BigDecimal("50.25"));
        assertEquals(3, card3.getId());
        assertEquals(1, card3.getTypeId());
        assertEquals(new BigDecimal("50.25"), card3.getBalance());
    }

    @Test
    public void testGettersAndSetters() {
        Card card = new Card();

        card.setId(5);
        assertEquals(5, card.getId());

        card.setTypeId(4);
        assertEquals(4, card.getTypeId());

        BigDecimal newBalance = new BigDecimal("75.50");
        card.setBalance(newBalance);
        assertEquals(newBalance, card.getBalance());
    }

    @Test
    public void testPropertyObjectBehavior() {
        Card card = new Card(5,1, new BigDecimal("25.00"));
        assertEquals(5, card.getIdProperty().getValue());
        assertEquals(1, card.getTypeProperty().getValue());
        assertEquals(25.00, card.getBalanceProperty().getValue());
    }
}
