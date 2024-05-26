package com.example.validatorapplication;

import com.example.validatorapplication.service.CardTapService;
import com.example.validatorapplication.util.AutowiredServices;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class StressTests {
    @Rollback(false)
    @Test
    @Transactional
    public void testCardTapStress() {
        int AMOUNT = 1_000;
        CardTapService cardTapService = AutowiredServices.cardTapService;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < AMOUNT; i++) {
            cardTapService.insertCardTap(86_000 + i);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime) / 1000;
        long sec = totalTime % 60;
        long min = totalTime / 60;
        System.out.printf("Total card tap (%d times) time: %d min %d sec%n", AMOUNT, min, sec);
    }
}
