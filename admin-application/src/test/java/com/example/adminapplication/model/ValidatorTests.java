package com.example.adminapplication.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTests {

    @Test
    public void testConstructors() {
        Validator validator1 = new Validator();
        assertNull(validator1.getId());
        assertNull(validator1.getVehicleId());

        Validator validator2 = new Validator(2);
        assertNull(validator2.getId());
        assertEquals(2, validator2.getVehicleId());

        Validator validator3 = new Validator(1, 3);
        assertEquals(1, validator3.getId());
        assertEquals(3, validator3.getVehicleId());
    }

    @Test
    public void testGettersAndSetters() {
        Validator validator = new Validator();

        validator.setId(5);
        assertEquals(5, validator.getId());

        validator.setVehicleId(7);
        assertEquals(7, validator.getVehicleId());
    }

    @Test
    public void testPropertyObjectBehavior() {
        Validator validator = new Validator(7, 4);
        assertEquals(7, validator.getIdProperty().getValue());
        assertEquals(4, validator.getVehicleIdProperty().getValue());
    }
}
