package com.example.validatorapplication;

import com.example.validatorapplication.service.CardService;
import com.example.validatorapplication.service.CardTapService;
import com.example.validatorapplication.util.AutowiredServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static javafx.application.Application.launch;

@SpringBootApplication
public class ValidatorApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(ValidatorApplication.class);

	@Autowired
	private CardService cardService;
	@Autowired
	private CardTapService cardTapService;

	public static void main(String[] args) {
		SpringApplication.run(ValidatorApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		AutowiredServices.cardService = cardService;
		AutowiredServices.cardTapService = cardTapService;
		launch(FxApp.class, args);
	}
}
