package com.example.adminapplication.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TopUpTests {

    @Test
    public void testConstructors() {
        TopUp topUp1 = new TopUp();
        assertNull(topUp1.getId());
        assertNull(topUp1.getMachineId());
        assertNull(topUp1.getCardId());
        assertNull(topUp1.getDateTime());
        assertNull(topUp1.getAmount());

        BigDecimal topUpAmount = new BigDecimal("20.00");
        TopUp topUp2 = new TopUp(2, 1, topUpAmount);
        assertNull(topUp2.getId());
        assertEquals(2, topUp2.getMachineId());
        assertEquals(1, topUp2.getCardId());
        assertNotNull(topUp2.getDateTime()); // Should be set to current date and time
        assertEquals(topUpAmount, topUp2.getAmount());

        LocalDateTime specificDateTime = LocalDateTime.of(2024, 4, 25, 10, 30);
        TopUp topUp3 = new TopUp(3, 3, 2, specificDateTime, new BigDecimal("35.75"));
        assertEquals(3, topUp3.getId());
        assertEquals(3, topUp3.getMachineId());
        assertEquals(2, topUp3.getCardId());
        assertEquals(specificDateTime, topUp3.getDateTime());
        assertEquals(new BigDecimal("35.75"), topUp3.getAmount());
    }

    @Test
    public void testGettersAndSetters() {
        TopUp topUp = new TopUp();

        topUp.setId(5);
        assertEquals(5, topUp.getId());

        topUp.setMachineId(4);
        assertEquals(4, topUp.getMachineId());

        topUp.setCardId(1);
        assertEquals(1, topUp.getCardId());

        LocalDateTime newDateTime = LocalDateTime.now();
        topUp.setDateTime(newDateTime);
        assertEquals(newDateTime, topUp.getDateTime());

        BigDecimal newAmount = new BigDecimal("15.50");
        topUp.setAmount(newAmount);
        assertEquals(newAmount, topUp.getAmount());
    }

    @Test
    public void testPropertyObjectBehavior() {
        LocalDateTime specificDateTime = LocalDateTime.of(2024, 4, 27, 15, 15);
        TopUp topUp = new TopUp(1, 2, 3, specificDateTime, new BigDecimal("10.25"));
        assertEquals(1, topUp.getIdProperty().getValue());
        assertEquals(2, topUp.getMachineProperty().getValue());
        assertEquals(3, topUp.getCardProperty().getValue());
        assertEquals(specificDateTime.toString(), topUp.getDateTimeProperty().getValue());
        assertEquals(10.25, topUp.getAmountProperty().getValue());
    }
}
