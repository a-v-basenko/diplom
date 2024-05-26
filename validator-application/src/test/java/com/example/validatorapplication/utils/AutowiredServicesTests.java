package com.example.validatorapplication.utils;

import com.example.validatorapplication.ValidatorApplication;
import com.example.validatorapplication.util.AutowiredServices;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AutowiredServicesTests {

    @BeforeAll
    public static void setup() {
        ValidatorApplication.main(new String[] {});
    }

    @Test
    public void testAutowiredServicesNotNull() {
        assertNotNull(AutowiredServices.cardService, "CardService should not be null");
        assertNotNull(AutowiredServices.cardTapService, "CardTapService should not be null");
    }
}
