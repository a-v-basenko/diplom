package com.example.adminapplication;

import com.example.adminapplication.model.*;
import com.example.adminapplication.service.*;
import com.example.adminapplication.utils.AutowiredServices;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.crypto.Mac;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public class FxApp extends Application {
    private TransportTypeService transportTypeService;
    private VehicleService vehicleService;
    private ValidatorService validatorService;
    private FareTypeService fareTypeService;
    private FareService fareService;
    private MachineService machineService;
    private CardService cardService;
    private TopUpService topUpService;
    private CardTapService cardTapService;

    private Stage primaryStage;
    private StackPane root;
    private ImageView logo;

    private final int BUTTON_WIDTH = 120;

    @Override
    public void start(Stage primaryStage) {
        transportTypeService = AutowiredServices.transportTypeService;
        vehicleService = AutowiredServices.vehicleService;
        validatorService = AutowiredServices.validatorService;
        fareTypeService = AutowiredServices.fareTypeService;
        fareService = AutowiredServices.fareService;
        machineService = AutowiredServices.machineService;
        cardService = AutowiredServices.cardService;
        topUpService = AutowiredServices.topUpService;
        cardTapService = AutowiredServices.cardTapService;

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Admin Application");
        this.root = new StackPane();
        this.root.setPrefSize(800, 600);

        logo = new ImageView(new Image("/logo.png"));
        logo.setFitHeight(48);
        logo.setFitWidth(48);

        initMainMenu();

        // Scene setup
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
        root.getChildren().clear();

        Label header = new Label("Tables");
        header.setFont(new Font(20));

        Button buttonTransportType = new Button("Transport Type");
        buttonTransportType.setOnAction(e -> initTransportType());
        buttonTransportType.setPrefWidth(BUTTON_WIDTH);

        Button buttonVehicle = new Button("Vehicle");
        buttonVehicle.setOnAction(e -> initVehicle());
        buttonVehicle.setPrefWidth(BUTTON_WIDTH);

        Button buttonValidator = new Button("Validator");
        buttonValidator.setOnAction(e -> initValidator());
        buttonValidator.setPrefWidth(BUTTON_WIDTH);

        Button buttonFareType = new Button("Fare Type");
        buttonFareType.setOnAction(e -> initFareType());
        buttonFareType.setPrefWidth(BUTTON_WIDTH);

        Button buttonFare = new Button("Fare");
        buttonFare.setOnAction(e -> initFare());
        buttonFare.setPrefWidth(BUTTON_WIDTH);

        Button buttonMachine = new Button("Machine");
        buttonMachine.setOnAction(e -> initMachine());
        buttonMachine.setPrefWidth(BUTTON_WIDTH);

        Button buttonCard = new Button("Card");
        buttonCard.setOnAction(e -> initCard());
        buttonCard.setPrefWidth(BUTTON_WIDTH);

        Button buttonTopUp = new Button("Top Up");
        buttonTopUp.setOnAction(e -> initTopUp());
        buttonTopUp.setPrefWidth(BUTTON_WIDTH);

        Button buttonCardTap = new Button("Card Tap");
        buttonCardTap.setOnAction(e -> initCardTap());
        buttonCardTap.setPrefWidth(BUTTON_WIDTH);

        VBox initialMainMenuView = new VBox();
        initialMainMenuView.setAlignment(Pos.CENTER);
        initialMainMenuView.setSpacing(20);

        HBox buttonRow1 = new HBox();
        buttonRow1.setAlignment(Pos.CENTER);
        buttonRow1.setSpacing(20);
        buttonRow1.getChildren().addAll(buttonTransportType, buttonVehicle, buttonValidator);

        HBox buttonRow2 = new HBox();
        buttonRow2.setAlignment(Pos.CENTER);
        buttonRow2.setSpacing(20);
        buttonRow2.getChildren().addAll(buttonFareType, buttonFare, buttonMachine);

        HBox buttonRow3 = new HBox();
        buttonRow3.setAlignment(Pos.CENTER);
        buttonRow3.setSpacing(20);
        buttonRow3.getChildren().addAll(buttonCard, buttonTopUp, buttonCardTap);

        initialMainMenuView.getChildren().addAll(
                logo,
                header,
                buttonRow1,
                buttonRow2,
                buttonRow3
        );
        root.getChildren().add(initialMainMenuView);
    }

    private void initTransportType() {
        root.getChildren().clear();

        // Table
        TableView<TransportType> table = new TableView<>();

        TableColumn<TransportType, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());

        TableColumn<TransportType, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        table.getColumns().addAll(idColumn, nameColumn);

        ObservableList<TransportType> transportTypes = FXCollections.observableArrayList();
        for (TransportType transportType : transportTypeService.getAllTransportTypes()) {
            transportTypes.add(transportType);
        }

        table.setItems(transportTypes);

        // View
        Label header = new Label("Transport Type Table");
        header.setFont(new Font(20));

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMainMenu());
        Button buttonCreate = new Button("Create");
        buttonCreate.setOnAction(e -> initCreateTransportType());
        Button buttonUpdate = new Button("Update");
        buttonUpdate.setOnAction(e -> initUpdateTransportType());

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonCreate, buttonUpdate);

        VBox initTransportTypeView = new VBox();
        initTransportTypeView.setAlignment(Pos.CENTER);
        initTransportTypeView.setSpacing(10);
        initTransportTypeView.getChildren().addAll(logo, header, table, buttonRow);

        root.getChildren().add(initTransportTypeView);
    }

    private void initCreateTransportType() {
        root.getChildren().clear();

        Label header = new Label("Adding Transport Type");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the name: ");
        TextField fieldName = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            String newName = fieldName.getText();
            if (newName.isBlank()) {
                showQuickMessage("Error: the name cannot be blank",
                        5000, 16, Color.RED, this::initCreateTransportType);
                return;
            } else if (newName.length() > 16) {
                showQuickMessage("Error: the name cannot contain more than 16 symbols",
                        5000, 16, Color.RED, this::initCreateVehicle);
                return;
            }
            try {
                TransportType transportType = new TransportType();
                transportType.setName(newName);
                transportTypeService.createTransportType(transportType);
                showQuickMessage("Transport Type has been successfully created",
                        3000, 16, Color.GREEN, this::initTransportType);
            } catch (Exception exception) {
                showMessage("Error: Could not create Transport Type\n" + exception.getMessage(),
                        16, Color.RED, this::initCreateTransportType
                );
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initTransportType());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(label1, fieldName, buttonSubmit);

        HBox hBoxGoBack = new HBox();
        hBoxGoBack.setAlignment(Pos.CENTER);
        hBoxGoBack.getChildren().add(buttonGoBack);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox, hBoxGoBack);

        root.getChildren().add(vBox);
    }

    private void initUpdateTransportType() {
        root.getChildren().clear();

        Label header = new Label("Updating Transport Type");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter id: ");
        TextField fieldID = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                int id = Integer.parseInt(fieldID.getText());
                initSubmitUpdateTransportType(id);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the id is not an integer",
                        3000, 16, Color.RED, this::initUpdateTransportType);
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initTransportType());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(label1, fieldID, buttonSubmit);

        HBox hBoxGoBack = new HBox();
        hBoxGoBack.setAlignment(Pos.CENTER);
        hBoxGoBack.getChildren().add(buttonGoBack);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox, hBoxGoBack);

        root.getChildren().add(vBox);
    }

    private void initSubmitUpdateTransportType(int id) {
        root.getChildren().clear();

        Label header = new Label("Updating Transport Type");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter new name: ");
        TextField fieldNewName = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                TransportType transportType = transportTypeService.getTransportTypeById(id);
                String newName = fieldNewName.getText();
                if (newName.isBlank()) {
                    showQuickMessage("Error: the name cannot be blank",
                            5000, 16, Color.RED, this::initUpdateTransportType);
                    return;
                } else if (newName.length() > 16) {
                    showQuickMessage("Error: the name cannot contain more than 16 symbols",
                            5000, 16, Color.RED, this::initUpdateTransportType);
                    return;
                }
                transportType.setName(newName);
                transportTypeService.updateTransportType(transportType);
                showQuickMessage("Transport Type has been successfully updated",
                        3000, 16, Color.GREEN, this::initTransportType);
            } catch (NullPointerException exception) {
                showQuickMessage("Error: the Transport Type with this id does not exist",
                        3000, 16, Color.RED, this::initUpdateTransportType);
            } catch (Exception exception) {
                showMessage("Error: Could not update Transport Type\n" + exception.getMessage(),
                        16, Color.RED, this::initUpdateTransportType);
            }
        });
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> initTransportType());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(label1, fieldNewName, buttonSubmit);

        HBox hBoxCancel = new HBox();
        hBoxCancel.setAlignment(Pos.CENTER);
        hBoxCancel.getChildren().add(buttonCancel);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox, hBoxCancel);

        root.getChildren().add(vBox);
    }

    private void initVehicle() {
        root.getChildren().clear();

        // Table
        TableView<Vehicle> table = new TableView<>();

        TableColumn<Vehicle, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());

        TableColumn<Vehicle, Integer> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty().asObject());

        TableColumn<Vehicle, String> licensePlaceColumn = new TableColumn<>("License Plate");
        licensePlaceColumn.setCellValueFactory(cellData -> cellData.getValue().getLicensePlateProperty());

        table.getColumns().addAll(idColumn, typeColumn, licensePlaceColumn);

        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        for (Vehicle vehicle : vehicleService.getAllVehicles()) {
            vehicles.add(vehicle);
        }

        table.setItems(vehicles);

        // View
        Label header = new Label("Vehicle Table");
        header.setFont(new Font(20));

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMainMenu());
        Button buttonCreate = new Button("Create");
        buttonCreate.setOnAction(e -> initCreateVehicle());
        Button buttonUpdate = new Button("Update");
        buttonUpdate.setOnAction(e -> initUpdateVehicle());

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonCreate, buttonUpdate);

        VBox initVehicleView = new VBox();
        initVehicleView.setAlignment(Pos.CENTER);
        initVehicleView.setSpacing(10);
        initVehicleView.getChildren().addAll(logo, header, table, buttonRow);

        root.getChildren().add(initVehicleView);
    }

    private void initCreateVehicle() {
        root.getChildren().clear();

        Label header = new Label("Adding Vehicle");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the type:");
        TextField fieldType = new TextField();
        Label label2 = new Label("Enter the license plate:");
        TextField fieldLicensePlate = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            String licensePlate = fieldLicensePlate.getText().trim();
            if (licensePlate.isBlank()) {
                showQuickMessage("Error: the license plate cannot be blank",
                        5000, 16, Color.RED, this::initCreateVehicle);
                return;
            } else if (licensePlate.length() > 10) {
                showQuickMessage("Error: the license plate cannot contain more than 10 symbols",
                        5000, 16, Color.RED, this::initCreateVehicle);
                return;
            }
            try {
                int type = Integer.parseInt(fieldType.getText());
                transportTypeService.getTransportTypeById(type).getId();
                Vehicle vehicle = new Vehicle(type, licensePlate);
                vehicleService.createVehicle(vehicle);
                showQuickMessage("Vehicle has been successfully created",
                        3000, 16, Color.GREEN, this::initVehicle);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the type is not an integer",
                        5000, 16, Color.RED, this::initCreateVehicle
                );
            } catch (NullPointerException exception) {
                showQuickMessage("Error: the type does not exist",
                        5000, 16, Color.RED, this::initCreateVehicle
                );
            } catch (Exception exception) {
                showMessage("Error: Could not create Vehicle\n" + exception.getMessage(),
                        16, Color.RED, this::initCreateVehicle
                );
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initVehicle());

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(10);
        row1.getChildren().addAll(label1, fieldType);

        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER);
        row2.setSpacing(10);
        row2.getChildren().addAll(label2, fieldLicensePlate);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, row1, row2, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initUpdateVehicle() {
        root.getChildren().clear();

        Label header = new Label("Updating Vehicle");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter id: ");
        TextField fieldID = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                int id = Integer.parseInt(fieldID.getText());
                initSubmitUpdateVehicle(id);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the id is not an integer",
                        3000, 16, Color.RED, this::initUpdateVehicle);
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initVehicle());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(label1, fieldID, buttonSubmit);

        HBox hBoxGoBack = new HBox();
        hBoxGoBack.setAlignment(Pos.CENTER);
        hBoxGoBack.getChildren().add(buttonGoBack);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox, hBoxGoBack);

        root.getChildren().add(vBox);
    }

    private void initSubmitUpdateVehicle(int id) {
        root.getChildren().clear();

        Label header = new Label("Updating Vehicle");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the new type:");
        TextField fieldNewType = new TextField();
        Label label2 = new Label("Enter the new license plate:");
        TextField fieldNewLicensePlate = new TextField("");
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                Vehicle vehicle = vehicleService.getVehicleById(id);
                if (vehicle == null) {
                    showQuickMessage("Error: the Vehicle with this id does not exist",
                            5000, 16, Color.RED, this::initUpdateVehicle
                    );
                    return;
                }
                String newTypeString = fieldNewType.getText();
                String newLicensePlate = fieldNewLicensePlate.getText();
                if (newTypeString.isBlank() && newLicensePlate.isBlank()) {
                    showQuickMessage("Error: both fields are blank",
                            5000, 16, Color.RED, this::initUpdateVehicle);
                    return;
                }
                if (!newTypeString.isBlank()) {
                    int newType = Integer.parseInt(newTypeString);
                    transportTypeService.getTransportTypeById(newType).getId();
                    vehicle.setType(newType);
                }
                if (!newLicensePlate.isBlank()) {
                    if (newLicensePlate.length() > 10) {
                        showQuickMessage("Error: the license plate cannot contain more than 10 symbols",
                                5000, 16, Color.RED, this::initUpdateVehicle);
                        return;
                    }
                    vehicle.setLicensePlate(newLicensePlate);
                }
                vehicleService.updateVehicle(vehicle);
                showQuickMessage("Vehicle has been successfully updated",
                        3000, 16, Color.GREEN, this::initVehicle);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the type is not an integer",
                        5000, 16, Color.RED, this::initUpdateVehicle
                );
            } catch (NullPointerException exception) {
                showQuickMessage("Error: the type does not exist",
                        5000, 16, Color.RED, this::initUpdateVehicle
                );
            } catch (Exception exception) {
                showMessage("Error: Could not update Vehicle\n" + exception.getMessage(),
                        16, Color.RED, this::initUpdateVehicle
                );
            }
        });
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> initVehicle());

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(label1, fieldNewType);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(label2, fieldNewLicensePlate);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonCancel, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox1, hBox2, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initValidator() {
        root.getChildren().clear();

        // Table
        TableView<Validator> table = new TableView<>();

        TableColumn<Validator, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());

        TableColumn<Validator, Integer> vehicleIdColumn = new TableColumn<>("Vehicle");
        vehicleIdColumn.setCellValueFactory(cellData -> cellData.getValue().getVehicleIdProperty().asObject());


        table.getColumns().addAll(idColumn, vehicleIdColumn);

        ObservableList<Validator> validators = FXCollections.observableArrayList();
        for (Validator validator : validatorService.getAllValidators()) {
            validators.add(validator);
        }

        table.setItems(validators);

        // View
        Label header = new Label("Validator Table");
        header.setFont(new Font(20));

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMainMenu());
        Button buttonCreate = new Button("Create");
        buttonCreate.setOnAction(e -> initCreateValidator());

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonCreate);

        VBox initValidatorView = new VBox();
        initValidatorView.setAlignment(Pos.CENTER);
        initValidatorView.setSpacing(10);
        initValidatorView.getChildren().addAll(logo, header, table, buttonRow);

        root.getChildren().add(initValidatorView);
    }

    private void initCreateValidator() {
        root.getChildren().clear();

        Label header = new Label("Adding Validator");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the vehicle:");
        TextField fieldVehicle = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                int vehicle = Integer.parseInt(fieldVehicle.getText());
                vehicleService.getVehicleById(vehicle).getId();
                Validator validator = new Validator(vehicle);
                validatorService.createValidator(validator);
                showQuickMessage("Validator has been successfully created",
                        3000, 16, Color.GREEN, this::initValidator);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the vehicle is not an integer",
                        5000, 16, Color.RED, this::initCreateValidator
                );
            } catch (NullPointerException exception) {
                showQuickMessage("Error: the vehicle does not exist",
                        5000, 16, Color.RED, this::initCreateValidator
                );
            } catch (Exception exception) {
                showMessage("Error: Could not create Validator\n" + exception.getMessage(),
                        16, Color.RED, this::initCreateValidator
                );
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initValidator());

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(10);
        row1.getChildren().addAll(label1, fieldVehicle);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, row1, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initFareType() {
        root.getChildren().clear();

        // Table
        TableView<FareType> table = new TableView<>();

        TableColumn<FareType, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());

        TableColumn<FareType, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        TableColumn<FareType, Double> dailyLimitColumn = new TableColumn<>("Daily limit");
        dailyLimitColumn.setCellValueFactory(cellData -> cellData.getValue().getDailyLimitProperty().asObject());

        TableColumn<FareType, Double> weeklyLimitColumn = new TableColumn<>("Weekly limit");
        weeklyLimitColumn.setCellValueFactory(cellData -> cellData.getValue().getWeeklyLimitProperty().asObject());

        table.getColumns().addAll(idColumn, nameColumn, dailyLimitColumn, weeklyLimitColumn);

        ObservableList<FareType> fareTypes = FXCollections.observableArrayList();
        for (FareType fareType : fareTypeService.getAllFareTypes()) {
            fareTypes.add(fareType);
        }

        table.setItems(fareTypes);

        // View
        Label header = new Label("Fare Type Table");
        header.setFont(new Font(20));

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMainMenu());
        Button buttonCreate = new Button("Create");
        buttonCreate.setOnAction(e -> initCreateFareType());
        Button buttonUpdate = new Button("Update");
        buttonUpdate.setOnAction(e -> initUpdateFareType());

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonCreate, buttonUpdate);

        VBox initFareTypeView = new VBox();
        initFareTypeView.setAlignment(Pos.CENTER);
        initFareTypeView.setSpacing(10);
        initFareTypeView.getChildren().addAll(logo, header, table, buttonRow);

        root.getChildren().add(initFareTypeView);
    }

    private void initCreateFareType() {
        root.getChildren().clear();

        Label header = new Label("Adding Fare Type");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the name:");
        TextField fieldName = new TextField();
        Label label2 = new Label("Enter the daily limit:");
        TextField fieldDailyLimit = new TextField();
        Label label3 = new Label("Enter the weekly limit:");
        TextField fieldWeeklyLimit = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            String name = fieldName.getText();
            if (name.isBlank()) {
                showQuickMessage("Error: the name cannot be blank",
                        5000, 16, Color.RED, this::initCreateFareType);
                return;
            } else if (name.length() > 16) {
                showQuickMessage("Error: the name cannot contain more than 16 symbols",
                        5000, 16, Color.RED, this::initCreateFareType);
                return;
            }
            try {
                BigDecimal dailyLimit = new BigDecimal(fieldDailyLimit.getText());
                BigDecimal weeklyLimit = new BigDecimal(fieldWeeklyLimit.getText());
                FareType fareType = new FareType(name, dailyLimit, weeklyLimit);
                fareTypeService.createFareType(fareType);
                showQuickMessage("Fare Type has been successfully created",
                        3000, 16, Color.GREEN, this::initFareType);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the value is not a real number",
                        5000, 16, Color.RED, this::initCreateFareType
                );
            } catch (Exception exception) {
                showMessage("Error: Could not create Fare Type\n" + exception.getMessage(),
                        16, Color.RED, this::initCreateFareType
                );
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initFareType());

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(10);
        row1.getChildren().addAll(label1, fieldName);

        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER);
        row2.setSpacing(10);
        row2.getChildren().addAll(label2, fieldDailyLimit);

        HBox row3 = new HBox();
        row3.setAlignment(Pos.CENTER);
        row3.setSpacing(10);
        row3.getChildren().addAll(label3, fieldWeeklyLimit);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, row1, row2, row3, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initUpdateFareType() {
        root.getChildren().clear();

        Label header = new Label("Updating Fare Type");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter id: ");
        TextField fieldID = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                int id = Integer.parseInt(fieldID.getText());
                initSubmitUpdateFareType(id);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the id is not an integer",
                        3000, 16, Color.RED, this::initUpdateVehicle);
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initFareType());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(label1, fieldID, buttonSubmit);

        HBox hBoxGoBack = new HBox();
        hBoxGoBack.setAlignment(Pos.CENTER);
        hBoxGoBack.getChildren().add(buttonGoBack);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox, hBoxGoBack);

        root.getChildren().add(vBox);
    }

    private void initSubmitUpdateFareType(int id) {
        root.getChildren().clear();

        Label header = new Label("Updating Fare Type");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the new name:");
        TextField fieldNewName = new TextField();
        Label label2 = new Label("Enter the new daily limit:");
        TextField fieldNewDailyLimit = new TextField();
        Label label3 = new Label("Enter the new weekly limit:");
        TextField fieldNewWeeklyLimit = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                FareType fareType = fareTypeService.getFareTypeById(id);
                if (fareType == null) {
                    showQuickMessage("Error: the Fare Type with this id does not exist",
                            5000, 16, Color.RED, this::initUpdateFareType
                    );
                    return;
                }
                String newName = fieldNewName.getText();
                String newDailyLimitString = fieldNewDailyLimit.getText();
                String newWeeklyLimitString = fieldNewWeeklyLimit.getText();
                if (newName.isBlank() && newDailyLimitString.isBlank() && newWeeklyLimitString.isBlank()) {
                    showQuickMessage("Error: all fields are blank",
                            5000, 16, Color.RED, this::initUpdateFareType);
                    return;
                }
                if (!newName.isBlank()) {
                    if (newName.length() > 16) {
                        showQuickMessage("Error: the name cannot contain more than 16 symbols",
                                5000, 16, Color.RED, this::initUpdateFareType);
                        return;
                    }
                    fareType.setName(newName);
                }
                if (!newDailyLimitString.isBlank()) {
                    BigDecimal newDailyLimit = new BigDecimal(newDailyLimitString);
                    if (newDailyLimit.compareTo(BigDecimal.ZERO) <= 0) {
                        showQuickMessage("Error: the daily limit must be greater than 0",
                                5000, 16, Color.RED, this::initUpdateFareType
                        );
                        return;
                    }
                    fareType.setDailyLimit(newDailyLimit);
                }
                if (!newWeeklyLimitString.isBlank()) {
                    BigDecimal newWeeklyLimit = new BigDecimal(newWeeklyLimitString);
                    if (newWeeklyLimit.compareTo(BigDecimal.ZERO) <= 0) {
                        showQuickMessage("Error: the weekly limit must be greater than 0",
                                5000, 16, Color.RED, this::initUpdateFareType
                        );
                        return;
                    }
                    fareType.setWeeklyLimit(newWeeklyLimit);
                }
                fareTypeService.updateFareType(fareType);
                showQuickMessage("Fare Type has been successfully updated",
                        3000, 16, Color.GREEN, this::initFareType);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the limit is not a real number",
                        5000, 16, Color.RED, this::initUpdateFareType
                );
            } catch (Exception exception) {
                showMessage("Error: Could not update Fare Type\n" + exception.getMessage(),
                        16, Color.RED, this::initUpdateFareType
                );
            }
        });
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> initVehicle());

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(label1, fieldNewName);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(label2, fieldNewDailyLimit);

        HBox hBox3 = new HBox();
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setSpacing(10);
        hBox3.getChildren().addAll(label3, fieldNewWeeklyLimit);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonCancel, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox1, hBox2, hBox3, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initFare() {
        root.getChildren().clear();

        // Table
        TableView<Fare> table = new TableView<>();

        TableColumn<Fare, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());

        TableColumn<Fare, Integer> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty().asObject());

        TableColumn<Fare, Integer> transportTypeColumn = new TableColumn<>("Transport Type");
        transportTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getTransportTypeProperty().asObject());

        TableColumn<Fare, Double> fareColumn = new TableColumn<>("Fare");
        fareColumn.setCellValueFactory(cellData -> cellData.getValue().getFareProperty().asObject());

        table.getColumns().addAll(idColumn, typeColumn, transportTypeColumn, fareColumn);

        ObservableList<Fare> fares = FXCollections.observableArrayList();
        for (Fare fare : fareService.getAllFares()) {
            fares.add(fare);
        }

        table.setItems(fares);

        // View
        Label header = new Label("Fare Table");
        header.setFont(new Font(20));

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMainMenu());
        Button buttonCreate = new Button("Create");
        buttonCreate.setOnAction(e -> initCreateFare());
        Button buttonUpdate = new Button("Update");
        buttonUpdate.setOnAction(e -> initUpdateFare());

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonCreate, buttonUpdate);

        VBox initFareView = new VBox();
        initFareView.setAlignment(Pos.CENTER);
        initFareView.setSpacing(10);
        initFareView.getChildren().addAll(logo, header, table, buttonRow);

        root.getChildren().add(initFareView);
    }

    private void initCreateFare() {
        root.getChildren().clear();

        Label header = new Label("Adding Fare");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the fare type:");
        TextField fieldType = new TextField();
        Label label2 = new Label("Enter the transport type:");
        TextField fieldTransportType = new TextField();
        Label label3 = new Label("Enter the fare:");
        TextField fieldFare = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                int type = Integer.parseInt(fieldType.getText());
                int transportType = Integer.parseInt(fieldTransportType.getText());
                BigDecimal fare = new BigDecimal(fieldFare.getText());
                fareTypeService.getFareTypeById(type).getId();
                transportTypeService.getTransportTypeById(transportType).getId();
                Fare newFare = new Fare(type, transportType, fare);
                fareService.createFare(newFare);
                showQuickMessage("Fare has been successfully created",
                        3000, 16, Color.GREEN, this::initFare);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the wrong number format",
                        5000, 16, Color.RED, this::initCreateFare
                );
            } catch (NullPointerException exception) {
                showQuickMessage("Error: the fare type or transport type does not exist",
                        5000, 16, Color.RED, this::initCreateFare
                );
            } catch (Exception exception) {
                showMessage("Error: Could not create Fare\n" + exception.getMessage(),
                        16, Color.RED, this::initCreateFare
                );
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initFare());

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(10);
        row1.getChildren().addAll(label1, fieldType);

        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER);
        row2.setSpacing(10);
        row2.getChildren().addAll(label2, fieldTransportType);

        HBox row3 = new HBox();
        row3.setAlignment(Pos.CENTER);
        row3.setSpacing(10);
        row3.getChildren().addAll(label3, fieldFare);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, row1, row2, row3, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initUpdateFare() {
        root.getChildren().clear();

        Label header = new Label("Updating Fare");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter id: ");
        TextField fieldID = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                int id = Integer.parseInt(fieldID.getText());
                initSubmitUpdateFare(id);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the id is not an integer",
                        3000, 16, Color.RED, this::initUpdateFare);
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initFare());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(label1, fieldID, buttonSubmit);

        HBox hBoxGoBack = new HBox();
        hBoxGoBack.setAlignment(Pos.CENTER);
        hBoxGoBack.getChildren().add(buttonGoBack);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox, hBoxGoBack);

        root.getChildren().add(vBox);
    }

    private void initSubmitUpdateFare(int id) {
        root.getChildren().clear();

        Label header = new Label("Updating Fare");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the new fare:");
        TextField fieldNewFare = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                Fare fare = fareService.getFareById(id);
                if (fare == null) {
                    showQuickMessage("Error: the Fare with this id does not exist",
                            5000, 16, Color.RED, this::initUpdateFare
                    );
                    return;
                }
                String newFareString = fieldNewFare.getText();
                if (newFareString.isBlank()) {
                    showQuickMessage("Error: fare field cannot be blank",
                            5000, 16, Color.RED, this::initUpdateFare);
                    return;
                }
                BigDecimal newFare = new BigDecimal(newFareString);
                if (newFare.compareTo(BigDecimal.ZERO) <= 0) {
                    showQuickMessage("Error: the fare must be greater than 0",
                            5000, 16, Color.RED, this::initUpdateFare);
                    return;
                }
                fare.setAmount(newFare);
                fareService.updateFare(fare);
                showQuickMessage("Fare has been successfully updated",
                        3000, 16, Color.GREEN, this::initFare);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the fare is not a real number",
                        5000, 16, Color.RED, this::initUpdateFare
                );
            } catch (Exception exception) {
                showMessage("Error: Could not update Fare\n" + exception.getMessage(),
                        16, Color.RED, this::initUpdateFare
                );
            }
        });
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> initFare());

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(label1, fieldNewFare);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonCancel, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox1, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initMachine() {
        root.getChildren().clear();

        // Table
        TableView<Machine> table = new TableView<>();

        TableColumn<Machine, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());

        TableColumn<Machine, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());

        table.getColumns().addAll(idColumn, addressColumn);

        ObservableList<Machine> machines = FXCollections.observableArrayList();
        for (Machine machine : machineService.getAllMachines()) {
            machines.add(machine);
        }

        table.setItems(machines);

        // View
        Label header = new Label("Machine Table");
        header.setFont(new Font(20));

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMainMenu());
        Button buttonCreate = new Button("Create");
        buttonCreate.setOnAction(e -> initCreateMachine());
        Button buttonUpdate = new Button("Update");
        buttonUpdate.setOnAction(e -> initUpdateMachine());

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonCreate, buttonUpdate);

        VBox initMachineView = new VBox();
        initMachineView.setAlignment(Pos.CENTER);
        initMachineView.setSpacing(10);
        initMachineView.getChildren().addAll(logo, header, table, buttonRow);

        root.getChildren().add(initMachineView);
    }

    private void initCreateMachine() {
        root.getChildren().clear();

        Label header = new Label("Adding Machine");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the address:");
        TextField fieldAddress = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            String address = fieldAddress.getText();
            if (address.isBlank()) {
                showQuickMessage("Error: the address cannot be blank",
                        5000, 16, Color.RED, this::initCreateMachine);
                return;
            } else if (address.length() > 100) {
                showQuickMessage("Error: the address cannot contain more than 100 symbols",
                        5000, 16, Color.RED, this::initCreateMachine);
                return;
            }
            try {
                Machine machine = new Machine(address);
                machineService.createMachine(machine);
                showQuickMessage("Machine has been successfully created",
                        3000, 16, Color.GREEN, this::initMachine);
            } catch (Exception exception) {
                showMessage("Error: Could not create Machine\n" + exception.getMessage(),
                        16, Color.RED, this::initCreateMachine
                );
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMachine());

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(10);
        row1.getChildren().addAll(label1, fieldAddress);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, row1, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initUpdateMachine() {
        root.getChildren().clear();

        Label header = new Label("Updating Machine");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter id: ");
        TextField fieldID = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                int id = Integer.parseInt(fieldID.getText());
                initSubmitUpdateMachine(id);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the id is not an integer",
                        3000, 16, Color.RED, this::initUpdateMachine);
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMachine());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(label1, fieldID, buttonSubmit);

        HBox hBoxGoBack = new HBox();
        hBoxGoBack.setAlignment(Pos.CENTER);
        hBoxGoBack.getChildren().add(buttonGoBack);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox, hBoxGoBack);

        root.getChildren().add(vBox);
    }

    private void initSubmitUpdateMachine(int id) {
        root.getChildren().clear();

        Label header = new Label("Updating Machine");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the new address:");
        TextField fieldNewAddress = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                Machine machine = machineService.getMachineById(id);
                if (machine == null) {
                    showQuickMessage("Error: the Machine with this id does not exist",
                            5000, 16, Color.RED, this::initUpdateMachine
                    );
                    return;
                }
                String newAddress = fieldNewAddress.getText();
                if (newAddress.isBlank()) {
                    showQuickMessage("Error: address cannot be blank",
                            5000, 16, Color.RED, this::initUpdateMachine);
                    return;
                } else if (newAddress.length() > 100) {
                    showQuickMessage("Error: the address cannot contain more than 100 symbols",
                            5000, 16, Color.RED, this::initUpdateMachine);
                    return;
                }
                machine.setAddress(newAddress);
                machineService.updateMachine(machine);
                showQuickMessage("Fare has been successfully updated",
                        3000, 16, Color.GREEN, this::initMachine);
            } catch (Exception exception) {
                showMessage("Error: Could not update Machine\n" + exception.getMessage(),
                        16, Color.RED, this::initUpdateMachine
                );
            }
        });
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> initMachine());

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(label1, fieldNewAddress);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonCancel, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, hBox1, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initCard() {
        root.getChildren().clear();

        // Table
        TableView<Card> table = new TableView<>();

        TableColumn<Card, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());

        TableColumn<Card, Integer> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty().asObject());

        TableColumn<Card, Double> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(cellData -> cellData.getValue().getBalanceProperty().asObject());

        table.getColumns().addAll(idColumn, typeColumn, balanceColumn);

        ObservableList<Card> cards = FXCollections.observableArrayList();
        for (Card card : cardService.getAllCards()) {
            cards.add(card);
        }

        table.setItems(cards);

        // View
        Label header = new Label("Card Table");
        header.setFont(new Font(20));

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMainMenu());
        Button buttonCreate = new Button("Create");
        buttonCreate.setOnAction(e -> initCreateCard());

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonCreate);

        VBox initCardView = new VBox();
        initCardView.setAlignment(Pos.CENTER);
        initCardView.setSpacing(10);
        initCardView.getChildren().addAll(logo, header, table, buttonRow);

        root.getChildren().add(initCardView);
    }

    private void initCreateCard() {
        root.getChildren().clear();

        Label header = new Label("Adding Card");
        header.setFont(new Font(20));
        Label label1 = new Label("Enter the fare type:");
        TextField fieldType = new TextField();
        Label label2 = new Label("Enter the balance:");
        TextField fieldBalance = new TextField();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            try {
                int type = Integer.parseInt(fieldType.getText());
                BigDecimal balance = new BigDecimal(fieldBalance.getText());
                fareTypeService.getFareTypeById(type).getId();
                if (balance.compareTo(BigDecimal.ZERO) < 0) {
                    showQuickMessage("Error: the balance cannot be less than 0",
                            5000, 16, Color.RED, this::initCreateCard);
                    return;
                }
                Card card = new Card(type, balance);
                cardService.createCard(card);
                showQuickMessage("Card has been successfully created",
                        3000, 16, Color.GREEN, this::initCard);
            } catch (NumberFormatException exception) {
                showQuickMessage("Error: the wrong number format",
                        5000, 16, Color.RED, this::initCreateCard
                );
            } catch (NullPointerException exception) {
                showQuickMessage("Error: the fare type does not exist",
                        5000, 16, Color.RED, this::initCreateCard
                );
            } catch (Exception exception) {
                showMessage("Error: Could not create Card\n" + exception.getMessage(),
                        16, Color.RED, this::initCreateCard
                );
            }
        });
        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initCard());

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(10);
        row1.getChildren().addAll(label1, fieldType);

        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER);
        row2.setSpacing(10);
        row2.getChildren().addAll(label2, fieldBalance);

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack, buttonSubmit);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(logo, header, row1, row2, buttonRow);

        root.getChildren().add(vBox);
    }

    private void initTopUp() {
        root.getChildren().clear();

        // Table
        TableView<TopUp> table = new TableView<>();

        TableColumn<TopUp, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());

        TableColumn<TopUp, Integer> machineColumn = new TableColumn<>("Machine");
        machineColumn.setCellValueFactory(cellData -> cellData.getValue().getMachineProperty().asObject());

        TableColumn<TopUp, Integer> cardColumn = new TableColumn<>("Card");
        cardColumn.setCellValueFactory(cellData -> cellData.getValue().getCardProperty().asObject());

        TableColumn<TopUp, String> dateTimeColumn = new TableColumn<>("Date and Time");
        dateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());

        TableColumn<TopUp, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().getAmountProperty().asObject());

        table.getColumns().addAll(idColumn, machineColumn, cardColumn, dateTimeColumn, amountColumn);

        ObservableList<TopUp> topUps = FXCollections.observableArrayList();
        for (TopUp topUp : topUpService.getAllTopUps()) {
            topUps.add(topUp);
        }

        table.setItems(topUps);

        // View
        Label header = new Label("Top Up Table");
        header.setFont(new Font(20));

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMainMenu());

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack);

        VBox initTopUpView = new VBox();
        initTopUpView.setAlignment(Pos.CENTER);
        initTopUpView.setSpacing(10);
        initTopUpView.getChildren().addAll(logo, header, table, buttonRow);

        root.getChildren().add(initTopUpView);
    }

    private void initCardTap() {
        root.getChildren().clear();

        // Table
        TableView<CardTap> table = new TableView<>();

        TableColumn<CardTap, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());

        TableColumn<CardTap, Integer> cardColumn = new TableColumn<>("Card");
        cardColumn.setCellValueFactory(cellData -> cellData.getValue().getCardProperty().asObject());

        TableColumn<CardTap, Integer> validatorColumn = new TableColumn<>("Validator");
        validatorColumn.setCellValueFactory(cellData -> cellData.getValue().getValidatorProperty().asObject());

        TableColumn<CardTap, String> dateTimeColumn = new TableColumn<>("Date and Time");
        dateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());

        TableColumn<CardTap, Double> fareColumn = new TableColumn<>("Fare");
        fareColumn.setCellValueFactory(cellData -> cellData.getValue().getFareProperty().asObject());

        TableColumn<CardTap, Double> withdrawAmountColumn = new TableColumn<>("Withdraw Amount");
        withdrawAmountColumn.setCellValueFactory(cellData -> cellData.getValue().getWithdrawAmountProperty().asObject());

        TableColumn<CardTap, Boolean> successfulColumn = new TableColumn<>("Is Successful");
        successfulColumn.setCellValueFactory(cellData -> cellData.getValue().getIsSuccessfulProperty().asObject());

        TableColumn<CardTap, String> noteColumn = new TableColumn<>("Note");
        noteColumn.setCellValueFactory(cellData -> cellData.getValue().getNoteProperty());

        table.getColumns().addAll(idColumn, cardColumn, validatorColumn, dateTimeColumn, fareColumn, withdrawAmountColumn, successfulColumn, noteColumn);

        ObservableList<CardTap> cardTaps = FXCollections.observableArrayList();
        for (CardTap cardTap : cardTapService.getAllCardTaps()) {
            cardTaps.add(cardTap);
        }

        table.setItems(cardTaps);

        // View
        Label header = new Label("Card Tap Table");
        header.setFont(new Font(20));

        Button buttonGoBack = new Button("Go Back");
        buttonGoBack.setOnAction(e -> initMainMenu());

        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(100);
        buttonRow.getChildren().addAll(buttonGoBack);

        VBox initCardTapView = new VBox();
        initCardTapView.setAlignment(Pos.CENTER);
        initCardTapView.setSpacing(10);
        initCardTapView.getChildren().addAll(logo, header, table, buttonRow);

        root.getChildren().add(initCardTapView);
    }
}
