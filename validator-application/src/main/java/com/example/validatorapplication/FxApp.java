package com.example.validatorapplication;

import com.example.validatorapplication.dto.CardTapDTO;
import com.example.validatorapplication.service.CardService;
import com.example.validatorapplication.service.CardTapService;
import com.example.validatorapplication.util.AutowiredServices;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class FxApp extends Application {

    private CardService cardService;
    private CardTapService cardTapService;

    private Stage primaryStage;
    private StackPane root;
    private ImageView logo;
    private TextField cardIdField;
    private Label titleLabel, messageLabel1, messageLabel2;

    @Override
    public void start(Stage primaryStage) {
        cardService = AutowiredServices.cardService;
        cardTapService = AutowiredServices.cardTapService;

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Validator Application");
        this.root = new StackPane();
        this.root.setPrefSize(300, 400);

        logo = new ImageView(new Image("/logo.png"));
        logo.setFitHeight(48);
        logo.setFitWidth(48);

        initMainMenu();

        // Scene setup
        Scene scene = new Scene(root);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void initMainMenu() {
        root.getChildren().clear();

        // Reset background color
        root.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        // Text for tapping card
        Label tapCardLabel = new Label("Tap your card");
        tapCardLabel.setFont(new Font(16));

        // Text field for entering card ID
        cardIdField = new TextField();

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> processCardTap());

        // Initializing labels
        titleLabel = new Label();
        messageLabel1 = new Label();
        messageLabel2 = new Label();

        // Configuring fonts
        titleLabel.setFont(new Font(20));
        messageLabel1.setFont(new Font(20));
        messageLabel2.setFont(new Font(20));

        // Initially display tap card message
        vBox.getChildren().addAll(logo, tapCardLabel, cardIdField, submitButton);
        root.getChildren().add(vBox);
    }

    private void processCardTap() {
        root.getChildren().clear();

        String cardIdString = cardIdField.getText();
        int cardId;
        try {
            cardId = Integer.parseInt(cardIdString);
            if (!cardService.cardExists(cardId)) {
                updateView(Color.TOMATO, "The card with this id doesn't exist");
                return;
            }
        } catch (NumberFormatException exception) {
            updateView(Color.TOMATO, "Error: the id is not an integer");
            return;
        } catch (Exception exception) {
            updateView(Color.TOMATO, "Unknown error");
            return;
        }

        // Call card tap service
        CardTapDTO tapResult = cardTapService.insertCardTap(cardId);

        // SQL query to get card balance
        BigDecimal cardBalance = cardService.getCardBalance(cardId);

        if (tapResult.getIsSuccessful()) {
            String message = tapResult.getNote();

            BigDecimal withdrawAmount = tapResult.getWithdrawAmount();
            if (message == null) {
                // Successful withdrawal, display green (lime green) message
                updateView(Color.LIMEGREEN, "Withdraw amount: $" + withdrawAmount, "Your card balance: $" + cardBalance);
            } else if (message.equals("DAILY_LIMIT")) {
                // Daily limit reached, display blue (cornflower blue) message
                updateView(Color.CORNFLOWERBLUE, "Withdraw amount: $" + withdrawAmount, "Daily limit is reached", "Your card balance: $" + cardBalance);
            } else if (message.equals("WEEKLY_LIMIT")) {
                // Weekly limit reached, display blue (cornflower blue) message
                updateView(Color.CORNFLOWERBLUE, "Withdraw amount: $" + withdrawAmount, "Weekly limit is reached", "Your card balance: $" + cardBalance);
            }
        } else {
            String message = tapResult.getNote();

            BigDecimal withdrawAmount = tapResult.getWithdrawAmount();
            if (message.equals("ALREADY_PAID")) {
                // Already paid, display yellow (golden yellow) message
                updateView(Color.GOLD, "You have already paid", "Withdraw amount: $" + withdrawAmount, "Your card balance: $" + cardBalance);
            } else if (message.equals("INSUFFICIENT_BALANCE")) {
                // Insufficient balance, display red (tomato red) message
                updateView(Color.TOMATO, "Insufficient balance", "Your card balance: $" + cardBalance);
            }
        }
    }

    private void updateView(Color color, String... messages) {
        // Change background color
        root.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        // Set title and messages
        titleLabel.setText("");
        messageLabel1.setText("");
        messageLabel2.setText("");
        for (int i = 0; i < messages.length; i++) {
            if (i == 0) {
                titleLabel.setText(messages[i]);
            } else if (i == 1) {
                messageLabel1.setText(messages[i]);
            } else {
                messageLabel2.setText(messages[i]);
            }
        }

        // Show labels and wait for 3 seconds
        vBox.getChildren().addAll(titleLabel, messageLabel1, messageLabel2);
        // root.getChildren().setAll(titleLabel, messageLabel1, messageLabel2);
        root.getChildren().add(vBox);
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Wait for 3 seconds
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Platform.runLater(() -> initMainMenu());
        }).start();
    }
}
