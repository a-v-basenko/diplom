package com.example.adminapplication.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransportTypeTests {
    @Test
    public void testConstructors() {
        TransportType transportType1 = new TransportType();
        assertNull(transportType1.getId());
        assertNull(transportType1.getName());

        TransportType transportType2 = new TransportType("Trolleybus");
        assertNull(transportType2.getId());
        assertEquals("Trolleybus", transportType2.getName());

        TransportType transportType3 = new TransportType(6, "Horsecar");
        assertEquals(6, transportType3.getId());
        assertEquals("Horsecar", transportType3.getName());
    }
    @Test
    public void testGettersAndSetters() {
        TransportType transportType = new TransportType();

        transportType.setId(1);
        assertEquals(1, transportType.getId());

        transportType.setName("Car");
        assertEquals("Car", transportType.getName());
    }

    @Test
    public void testPropertyObjectBehavior() {
        TransportType transportType = new TransportType(2, "Bus");
        assertEquals(2, transportType.getIdProperty().getValue());
        assertEquals("Bus", transportType.getNameProperty().getValue());
    }
}
