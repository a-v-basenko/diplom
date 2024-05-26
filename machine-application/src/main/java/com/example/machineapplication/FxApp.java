package com.example.machineapplication;

import com.example.machineapplication.service.CardService;
import com.example.machineapplication.service.TopUpService;
import com.example.machineapplication.utils.AutowiredServices;
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
    private TopUpService topUpService;

    private Stage primaryStage;
    private StackPane root;

    private ImageView logo;

    @Override
    public void start(Stage primaryStage) {
        cardService = AutowiredServices.cardService;
        topUpService = AutowiredServices.topUpService;

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Machine Application");
        this.root = new StackPane();
        this.root.setPrefSize(400, 300);
        this.root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        logo = new ImageView(new Image("/logo.png"));
        logo.setFitHeight(48);
        logo.setFitWidth(48);

        initMainMenu();
        Scene scene = new Scene(root);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void showMessage(String message, int fontSize, Color fontColor, Runnable method) {
        root.getChildren().clear();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        Label label = new Label(message);
        label.setFont(new Font(fontSize));
        label.setTextFill(fontColor);
        label.setWrapText(true);

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> Platform.runLater(method));

        vBox.getChildren().addAll(logo, label, buttonGoBack);
        root.getChildren().add(vBox);
    }

    private void showQuickMessage(String message, int duration, int fontSize, Color fontColor, Runnable method) {
        root.getChildren().clear();
        Label label = new Label(message);
        label.setFont(new Font(fontSize));
        label.setTextFill(fontColor);
        label.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, label);
        root.getChildren().addAll(vBox);

        new Thread(() -> {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Platform.runLater(method);
        }).start();
    }

    private void initMainMenu() {
        root.getChildren().clear(); // Clear the main menu

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);

        Button buyButton = new Button("Buy a card");
        buyButton.setOnAction(e -> initBuyCard());

        Button checkBalanceButton = new Button("Check balance");
        checkBalanceButton.setOnAction(e -> initCheckBalance());

        Button topUpButton = new Button("Top up a card");
        topUpButton.setOnAction(e -> initTopUp());

        buttonBox.getChildren().addAll(logo, buyButton, checkBalanceButton, topUpButton);
        root.getChildren().add(buttonBox);
    }

    private void initBuyCard() {
        root.getChildren().clear(); // Clear the main menu

        // Text for card price
        Label cardPriceLabel = new Label("A card costs $5");

        // Variable to track inserted amount
        // The variables used in the lambda expressions should be final or effectively final
        final int[] insertedAmount = {0};

        // Text for inserted amount
        Label insertedAmountLabel = new Label("You have inserted $" + insertedAmount[0]);

        // Horizontal box for cash buttons
        HBox cashButtonBox = new HBox();
        cashButtonBox.setSpacing(10);
        cashButtonBox.setAlignment(Pos.CENTER);

        // Array of cash buttons
        Button[] cashButtons = new Button[]{new Button("$5"), new Button("$10"), new Button("$20"), new Button("$50"), new Button("$100")};

        // Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> initMainMenu());

        // Done button (initially disabled)
        Button doneButton = new Button("Done");
        doneButton.setDisable(true);
        doneButton.setOnAction(e -> {
            // SQL query to create card
            int cardId = cardService.insertCard(1, insertedAmount[0]);

            // Show success message for 5 seconds
            Label successLabel = new Label("Here's your card: " + cardId);
            root.getChildren().setAll(successLabel);
            new Thread(() -> {
                try {
                    Thread.sleep(5000); // Wait for 5 seconds
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Platform.runLater(() -> initMainMenu());
            }).start();
        });

        // Add click event handler for each cash button
        for (Button button : cashButtons) {
            int value = Integer.parseInt(button.getText().substring(1)); // Extract amount from button text
            button.setOnAction(e -> {
                insertedAmount[0] += value;
                insertedAmountLabel.setText("You have inserted $" + insertedAmount[0]);
                // Disable clicked cash button
                cancelButton.setDisable(true);
                doneButton.setDisable(false);
            });
            cashButtonBox.getChildren().add(button);
        }

        // VBox to organize elements
        VBox buyCardView = new VBox();
        buyCardView.setSpacing(10);
        buyCardView.setAlignment(Pos.CENTER);
        buyCardView.getChildren().addAll(logo, cardPriceLabel, insertedAmountLabel, cashButtonBox, cancelButton, doneButton);

        root.getChildren().add(buyCardView);
    }

    private void initCheckBalance() {
        root.getChildren().clear(); // Clear the main menu

        // Text for entering card ID
        Label enterCardLabel = new Label("Enter your card ID:");

        // Text field for entering card ID
        TextField cardIdField = new TextField();

        // Horizontal box for buttons
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        // Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> initMainMenu());

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String cardIdString = cardIdField.getText();
            try {
                int cardId = Integer.parseInt(cardIdString);
                if (cardService.cardExists(cardId)) {
                    BigDecimal balance = cardService.getCardBalance(cardId);
                    showQuickMessage("Your balance: $" + balance,
                            10000, 16, Color.BLACK, this::initMainMenu);
                } else {
                    showQuickMessage("The card with this id doesn't exist",
                            5000, 16, Color.RED, this::initCheckBalance);
                }
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the id is not an integer",
                        5000, 16, Color.RED, this::initCheckBalance);
            } catch (Exception exception) {
                showMessage("Unknown error: " + exception.getMessage(),
                        16, Color.RED, this::initCheckBalance);
            }
        });

        buttonBox.getChildren().addAll(cancelButton, submitButton);

        // VBox to organize elements
        VBox checkBalanceView = new VBox();
        checkBalanceView.setSpacing(10);
        checkBalanceView.setAlignment(Pos.CENTER);
        checkBalanceView.getChildren().addAll(logo, enterCardLabel, cardIdField, buttonBox);

        root.getChildren().add(checkBalanceView);
    }

    private void initTopUp() {
        root.getChildren().clear(); // Clear the main menu

        // Text for entering card ID
        Label enterCardLabel = new Label("Enter your card ID:");

        // Text field for entering card ID
        TextField cardIdField = new TextField();

        // Horizontal box for buttons
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        // Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> initMainMenu());

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String cardIdString = cardIdField.getText();
            // Get card ID
            int cardId;
            try {
                cardId = Integer.parseInt(cardIdString);
                if (!cardService.cardExists(cardId)) {
                    showQuickMessage("The card with this id doesn't exist",
                            5000, 16, Color.RED, this::initTopUp);
                    return;
                }
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the id is not an integer",
                        5000, 16, Color.RED, this::initTopUp);
                return;
            } catch (Exception exception) {
                showMessage("Unknown error: " + exception.getMessage(),
                        16, Color.RED, this::initTopUp);
                return;
            }

            // SQL query to get current balance
            BigDecimal currentBalance = cardService.getCardBalance(cardId);

            // Display current balance
            Label currentBalanceLabel = new Label("Your balance: $" + currentBalance);

            // Text for inserted amount
            final int[] insertedAmount = {0};
            Label insertedAmountLabel = new Label("You have inserted $" + insertedAmount[0]);

            // Horizontal box for cash buttons
            HBox cashButtonBox = new HBox();
            cashButtonBox.setSpacing(10);
            cashButtonBox.setAlignment(Pos.CENTER);

            // Array of cash buttons
            Button[] cashButtons = new Button[]{new Button("$5"), new Button("$10"), new Button("$20"), new Button("$50"), new Button("$100")};

            // Done button (initially disabled)
            Button doneButton = new Button("Done");
            doneButton.setDisable(true);
            doneButton.setOnAction(e2 -> {
                // SQL query to top up card
                topUpService.insertTopUp(cardId, insertedAmount[0]);
                BigDecimal newBalance = cardService.getCardBalance(cardId);

                // Show updated balance for 10 seconds
                showQuickMessage("Your new balance: $" + newBalance,
                        10000, 16, Color.BLACK, this::initMainMenu);
            });

            // Add click event handler for each cash button
            for (Button button : cashButtons) {
                int value = Integer.parseInt(button.getText().substring(1)); // Extract amount from button text
                button.setOnAction(e3 -> {
                    insertedAmount[0] += value;
                    insertedAmountLabel.setText("You have inserted $" + insertedAmount[0]);
                    cancelButton.setDisable(true);
                    doneButton.setDisable(false);
                });
                cashButtonBox.getChildren().add(button);
            }

            // VBox to organize elements for top-up view
            VBox topUpView = new VBox();
            topUpView.setSpacing(10);
            topUpView.setAlignment(Pos.CENTER);
            topUpView.getChildren().addAll(currentBalanceLabel, insertedAmountLabel, cashButtonBox, cancelButton, doneButton);

            // Clear previous elements and display top-up view
            root.getChildren().setAll(topUpView);

        });

        buttonBox.getChildren().addAll(cancelButton, submitButton);

        // VBox to organize elements for initial top-up screen
        VBox initialTopUpView = new VBox();
        initialTopUpView.setSpacing(10);
        initialTopUpView.setAlignment(Pos.CENTER);
        initialTopUpView.getChildren().addAll(logo, enterCardLabel, cardIdField, buttonBox);

        root.getChildren().add(initialTopUpView);
    }
}
