package com.example.adminapplication;

import com.example.adminapplication.service.*;
import com.example.adminapplication.utils.AutowiredServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static javafx.application.Application.launch;

@SpringBootApplication
public class AdminApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AdminApplication.class);

    @Autowired
    TransportTypeService transportTypeService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    ValidatorService validatorService;

    @Autowired
    FareTypeService fareTypeService;

    @Autowired
    FareService fareService;

    @Autowired
    MachineService machineService;

    @Autowired
    CardService cardService;

    @Autowired
    TopUpService topUpService;

    @Autowired
    CardTapService cardTapService;

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AutowiredServices.transportTypeService = transportTypeService;
        AutowiredServices.vehicleService = vehicleService;
        AutowiredServices.validatorService = validatorService;
        AutowiredServices.fareTypeService = fareTypeService;
        AutowiredServices.fareService = fareService;
        AutowiredServices.machineService = machineService;
        AutowiredServices.cardService = cardService;
        AutowiredServices.topUpService = topUpService;
        AutowiredServices.cardTapService = cardTapService;

        launch(FxApp.class, args);
    }
}
