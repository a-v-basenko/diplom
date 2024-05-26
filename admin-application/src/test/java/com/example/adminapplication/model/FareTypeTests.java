package com.example.adminapplication.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class FareTypeTests {

    @Test
    public void testConstructors() {
        FareType fareType1 = new FareType();
        assertNull(fareType1.getId());
        assertNull(fareType1.getName());
        assertNull(fareType1.getDailyLimit());
        assertNull(fareType1.getWeeklyLimit());

        BigDecimal dailyLimit = new BigDecimal("10.50");
        BigDecimal weeklyLimit = new BigDecimal("50.00");
        FareType fareType2 = new FareType("Standard", dailyLimit, weeklyLimit);
        assertNull(fareType2.getId());
        assertEquals("Standard", fareType2.getName());
        assertEquals(dailyLimit, fareType2.getDailyLimit());
        assertEquals(weeklyLimit, fareType2.getWeeklyLimit());

        FareType fareType3 = new FareType(1, "Premium", new BigDecimal("15.75"), new BigDecimal("75.25"));
        assertEquals(1, fareType3.getId());
        assertEquals("Premium", fareType3.getName());
        assertEquals(new BigDecimal("15.75"), fareType3.getDailyLimit());
        assertEquals(new BigDecimal("75.25"), fareType3.getWeeklyLimit());
    }

    @Test
    public void testGettersAndSetters() {
        FareType fareType = new FareType();

        fareType.setId(5);
        assertEquals(5, fareType.getId());

        fareType.setName("Student");
        assertEquals("Student", fareType.getName());

        BigDecimal dailyLimit = new BigDecimal("8.25");
        fareType.setDailyLimit(dailyLimit);
        assertEquals(dailyLimit, fareType.getDailyLimit());

        BigDecimal weeklyLimit = new BigDecimal("40.00");
        fareType.setWeeklyLimit(weeklyLimit);
        assertEquals(weeklyLimit, fareType.getWeeklyLimit());
    }

    @Test
    public void testPropertyObjectBehavior() {
        FareType fareType = new FareType(4, "Full", new BigDecimal("12.00"), new BigDecimal("60.00"));
        assertEquals(4, fareType.getIdProperty().getValue());
        assertEquals("Full", fareType.getNameProperty().getValue());
        assertEquals(12.00, fareType.getDailyLimitProperty().getValue());
        assertEquals(60.00, fareType.getWeeklyLimitProperty().getValue());
    }
}
