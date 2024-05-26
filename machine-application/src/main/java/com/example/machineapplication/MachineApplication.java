package com.example.machineapplication;

import com.example.machineapplication.service.CardService;
import com.example.machineapplication.service.TopUpService;
import com.example.machineapplication.utils.AutowiredServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static javafx.application.Application.launch;

@SpringBootApplication
public class MachineApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(MachineApplication.class);
    @Autowired
    private CardService cardService;

    @Autowired
    private TopUpService topUpService;

    public static void main(String[] args) {
        SpringApplication.run(MachineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AutowiredServices.cardService = cardService;
        AutowiredServices.topUpService = topUpService;
        launch(FxApp.class, args);
    }
}
