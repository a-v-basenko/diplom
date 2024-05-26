package com.example.machineapplication;

import com.example.machineapplication.service.CardService;
import com.example.machineapplication.service.TopUpService;
import com.example.machineapplication.utils.AutowiredServices;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


@SpringBootTest
public class StressTests {

    @Rollback(false)
    @Test
    @Transactional
    public void testBuyCardStress() {
        int AMOUNT = 100_000;
        CardService cardService = AutowiredServices.cardService;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < AMOUNT; i++) {
            cardService.insertCard(1, 100);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime) / 1000;
        long sec = totalTime % 60;
        long min = totalTime / 60;
        System.out.printf("Total buy time (%d times): %d min %d sec%n", AMOUNT, min, sec);
    }

    @Rollback(false)
    @Test
    @Transactional
    public void testTopUpCardStress() {
        int AMOUNT = 100_000;
        TopUpService topUpService = AutowiredServices.topUpService;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < AMOUNT; i++) {
            topUpService.insertTopUp(1_000_000 + i, 100);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime) / 1000;
        long sec = totalTime % 60;
        long min = totalTime / 60;
        System.out.printf("Total top up (%d times) time: %d min %d sec%n", AMOUNT, min, sec);
    }

    @Rollback(false)
    @Test
    @Transactional
    public void tesBuyAndTopUpStress() {
        int AMOUNT = 100_000;
        CardService cardService = AutowiredServices.cardService;
        TopUpService topUpService = AutowiredServices.topUpService;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < AMOUNT; i++) {
            int cardId = cardService.insertCard(2, 0);
            topUpService.insertTopUp(cardId, 1000);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime) / 1000;
        long sec = totalTime % 60;
        long min = totalTime / 60;
        System.out.printf("Total buy + top_up (%d times) time: %d min %d sec%n", AMOUNT, min, sec);
    }
}
