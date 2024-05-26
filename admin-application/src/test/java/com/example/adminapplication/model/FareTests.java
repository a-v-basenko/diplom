package com.example.adminapplication.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class FareTests {

    @Test
    public void testConstructors() {
        Fare fare1 = new Fare();
        assertNull(fare1.getId());
        assertNull(fare1.getTypeId());
        assertNull(fare1.getTransportTypeId());
        assertNull(fare1.getAmount());

        BigDecimal fareAmount = new BigDecimal("2.50");
        Fare fare2 = new Fare(2, 1, fareAmount);
        assertNull(fare2.getId());
        assertEquals(2, fare2.getTypeId());
        assertEquals(1, fare2.getTransportTypeId());
        assertEquals(fareAmount, fare2.getAmount());

        Fare fare3 = new Fare(3, 3, 2, new BigDecimal("3.75"));
        assertEquals(3, fare3.getId());
        assertEquals(3, fare3.getTypeId());
        assertEquals(2, fare3.getTransportTypeId());
        assertEquals(new BigDecimal("3.75"), fare3.getAmount());
    }

    @Test
    public void testGettersAndSetters() {
        Fare fare = new Fare();

        fare.setId(5);
        assertEquals(5, fare.getId());

        fare.setTypeId(4);
        assertEquals(4, fare.getTypeId());

        fare.setTransportTypeId(1);
        assertEquals(1, fare.getTransportTypeId());

        BigDecimal fareAmount = new BigDecimal("1.75");
        fare.setAmount(fareAmount);
        assertEquals(fareAmount, fare.getAmount());
    }

    @Test
    public void testPropertyObjectBehavior() {
        Fare fare = new Fare(1, 2, 3, new BigDecimal("4.25"));
        assertEquals(1, fare.getIdProperty().getValue());
        assertEquals(2, fare.getTypeProperty().getValue());
        assertEquals(3, fare.getTransportTypeProperty().getValue());
        assertEquals(4.25, fare.getFareProperty().getValue());
    }
}
