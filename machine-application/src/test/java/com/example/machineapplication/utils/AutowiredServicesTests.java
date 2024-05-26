package com.example.machineapplication.utils;

import com.example.machineapplication.MachineApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AutowiredServicesTests {
    @BeforeAll
    public static void setup() {
        MachineApplication.main(new String[] {});
    }

    @Test
    public void testAutowiredServicesNotNull() {
        assertNotNull(AutowiredServices.cardService);
        assertNotNull(AutowiredServices.topUpService);
    }
}
