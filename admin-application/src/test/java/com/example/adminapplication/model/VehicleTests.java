package com.example.adminapplication.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTests {

    @Test
    public void testConstructors() {
        Vehicle vehicle1 = new Vehicle();
        assertNull(vehicle1.getId());
        assertNull(vehicle1.getType());
        assertNull(vehicle1.getLicensePlate());

        Vehicle vehicle2 = new Vehicle(2, "ABC123");
        assertNull(vehicle2.getId());
        assertEquals(2, vehicle2.getType());
        assertEquals("ABC123", vehicle2.getLicensePlate());

        Vehicle vehicle3 = new Vehicle(1, 1, "DEF456");
        assertEquals(1, vehicle3.getId());
        assertEquals(1, vehicle3.getType());
        assertEquals("DEF456", vehicle3.getLicensePlate());
    }

    @Test
    public void testGettersAndSetters() {
        Vehicle vehicle = new Vehicle();

        vehicle.setId(5);
        assertEquals(5, vehicle.getId());

        vehicle.setType(3);
        assertEquals(3, vehicle.getType());

        vehicle.setLicensePlate("GHI789");
        assertEquals("GHI789", vehicle.getLicensePlate());
    }

    @Test
    public void testPropertyObjectBehavior() {
        Vehicle vehicle = new Vehicle(3, 4, "JKL012");
        assertEquals(3, vehicle.getIdProperty().getValue());
        assertEquals(4, vehicle.getTypeProperty().getValue());
        assertEquals("JKL012", vehicle.getLicensePlateProperty().getValue());
    }
}
