package com.example.adminapplication.utils;

import com.example.adminapplication.AdminApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AutowiredServicesTests {

    @BeforeAll
    public static void setup() {
        AdminApplication.main(new String[] {});
    }

    @Test
    public void testAutowiredServicesNotNull() {
        assertNotNull(AutowiredServices.transportTypeService, "TransportTypeService should not be null");
        assertNotNull(AutowiredServices.vehicleService, "VehicleService should not be null");
        assertNotNull(AutowiredServices.validatorService, "ValidatorService should not be null");
        assertNotNull(AutowiredServices.fareTypeService, "FareTypeService should not be null");
        assertNotNull(AutowiredServices.fareService, "FareService should not be null");
        assertNotNull(AutowiredServices.machineService, "MachineService should not be null");
        assertNotNull(AutowiredServices.cardService, "CardService should not be null");
        assertNotNull(AutowiredServices.topUpService, "TopUpService should not be null");
        assertNotNull(AutowiredServices.cardTapService, "CardTapService should not be null");
    }
}
