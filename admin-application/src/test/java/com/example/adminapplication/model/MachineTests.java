package com.example.adminapplication.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MachineTests {

    @Test
    public void testConstructors() {
        Machine machine1 = new Machine();
        assertNull(machine1.getId());
        assertNull(machine1.getAddress());

        Machine machine2 = new Machine("Main Street 123");
        assertNull(machine2.getId());
        assertEquals("Main Street 123", machine2.getAddress());

        Machine machine3 = new Machine(1, "Elm Street 456");
        assertEquals(1, machine3.getId());
        assertEquals("Elm Street 456", machine3.getAddress());
    }

    @Test
    public void testGettersAndSetters() {
        Machine machine = new Machine();

        machine.setId(5);
        assertEquals(5, machine.getId());

        machine.setAddress("Park Avenue 789");
        assertEquals("Park Avenue 789", machine.getAddress());
    }

    @Test
    public void testPropertyObjectBehavior() {
        Machine machine = new Machine(4, "Market Street 1011");
        assertEquals(4, machine.getIdProperty().getValue());
        assertEquals("Market Street 1011", machine.getAddressProperty().getValue());
    }
}
