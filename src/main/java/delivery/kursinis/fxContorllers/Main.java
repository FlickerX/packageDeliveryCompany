package delivery.kursinis.fxContorllers;

import delivery.kursinis.Enums.OrderStatus;
import delivery.kursinis.hibernate.*;
import delivery.kursinis.model.*;
import delivery.kursinis.utils.DatabaseOperations;
import delivery.kursinis.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Main implements Initializable {
    @FXML
    public TabPane allTabs;
    @FXML
    public ListView<Courier> courierList;
    @FXML
    public ListView<Manager> managerList;
    @FXML
    public Tab registerUserTab;
    @FXML
    public Button updateUserButton;
    @FXML
    public Button removeUserButton;
    @FXML
    public Button accountActionButton;
    @FXML
    ChoiceBox<String> userTypeChoiceBox;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public TextField name;
    @FXML
    public TextField surname;
    @FXML
    public TextField phoneNumber;
    @FXML
    public TextField salary;
    @FXML
    public TextField driverLicense;
    @FXML
    public DatePicker birthday;
    @FXML
    public TextField medicalCertificate;
    @FXML
    public Tab truckManagementTab;
    @FXML
    public Button truckActionButton;
    @FXML
    public TextField mark;
    @FXML
    public TextField model;
    @FXML
    public TextField horsePower;
    @FXML
    public TextField engineLiters;
    @FXML
    public TextField color;
    @FXML
    public ListView<Truck> truckList;
    @FXML
    public Tab orderManagementTab;

    //TODO: Checkpoints
    @FXML
    public Tab checkpointManagementTab;
    @FXML
    public TextField checkpointAddress;
    @FXML
    public DatePicker checkpointDate;
    @FXML
    public ListView<Checkpoint> checkpointList;
    @FXML
    public Button checkpointActionButton;
    CheckpointHib checkpointHib;
    //

    //TODO: Cargos
    @FXML
    public Tab cargoManagementTab;
    @FXML
    public TextField cargoNaming;
    @FXML
    public TextField cargoWeight;
    @FXML
    public ListView<Cargo> cargoList;

    CargoHib cargoHib;
    //--------------------------

    //TODO: Orders
    public ListView<Manager> managersOrderList;
    public ListView<Cargo> cargosOrderList;
    public TextField destinationAddress;
    public DatePicker destinationRequestedDeliveryDate;
    public DatePicker destinationDeliveryStartDate;
    @FXML
    public ChoiceBox trucksChoiceBox;
    public ChoiceBox couriersChoiceBox;



    public Tab ordersTab;

    public ListView assignedOrdersList;

    public ListView allOrdersList;

    //-----------------
    private EntityManagerFactory entityManagerFactory;
    private User user;
    private UserHib userHib;
    private TruckHib truckHib;
    private DestinationHib destinationHib;
    private String[] checkBoxValues = {"Courier", "Manager", "Admin Manager"};

    private FxUtils fxUtils = new FxUtils();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userTypeChoiceBox.getItems().addAll(checkBoxValues);
//        trucksChoiceBox.getItems().addAll(trucksChoiceBoxValues);
        userTypeChoiceBox.setOnAction(actionEvent -> hideFields(userTypeChoiceBox.getValue()));
    }

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
        this.userHib = new UserHib(this.entityManagerFactory);
        this.truckHib = new TruckHib(this.entityManagerFactory);
        this.checkpointHib = new CheckpointHib(this.entityManagerFactory);
        this.destinationHib = new DestinationHib(this.entityManagerFactory);
        this.cargoHib = new CargoHib(this.entityManagerFactory);


        List<Truck> allTrucks = truckHib.getAllTrucks();
        List<Courier> allCouriers = userHib.getAllCouriers();
        for (Truck truck : allTrucks)
            trucksChoiceBox.getItems().add(truck);

        for (Courier courier : allCouriers)
            couriersChoiceBox.getItems().add(courier);

        fillAllLists();
        disableData();
    }

    private void disableData() {
        if (user.getClass() == Courier.class) {
            allTabs.getTabs().remove(truckManagementTab);
            allTabs.getTabs().remove(registerUserTab);
        }
    }

    private void fillAllLists() {
        List<Truck> allTrucks = truckHib.getAllTrucks();
        List<Courier> allCouriers = userHib.getAllCouriers();
        List<Manager> allManagers = userHib.getAllManagers();
        List<Checkpoint> allCheckpoints = checkpointHib.getAllCheckpoints();
        List<Cargo> allCargos = cargoHib.getAllCargos();
        List<Destination> allDestinations = destinationHib.getAllDestinations();


        for (Truck truck : allTrucks) // TODO: Maybe refactor this shit
            truckList.getItems().add(truck);

        for (Courier courier : allCouriers)
            courierList.getItems().add(courier);

        for (Manager manager : allManagers){
            managerList.getItems().add(manager);
            managersOrderList.getItems().add(manager);
        }
        for (Checkpoint checkpoint : allCheckpoints)
            checkpointList.getItems().add(checkpoint);

        for (Cargo cargo : allCargos){
            cargoList.getItems().add(cargo);
            cargosOrderList.getItems().add(cargo);
        }
        for (Destination destination : allDestinations)
            allOrdersList.getItems().add(destination);
    }

    public void createUserByAdmin() {
        switch (userTypeChoiceBox.getValue()) {
            case "Courier":
                Courier courier = null;
                if (fxUtils.areAllCourierFieldsFilled(username.getText(), password.getText(), name.getText(), surname.getText(), phoneNumber.getText(), salary.getText(),
                        driverLicense.getText(), medicalCertificate.getText(), birthday.getValue()))
                    fxUtils.alertMessage(Alert.AlertType.WARNING, "User creating warning", "Validation error", "All fields has to be filled");

                courier = new Courier(username.getText(), password.getText(), name.getText(), surname.getText(), birthday.getValue(), phoneNumber.getText(),
                        Double.parseDouble(salary.getText()),
                        driverLicense.getText(), medicalCertificate.getText());
                userHib.createUser(courier);
                break;

            case "Manager":
            case "Admin Manager":
                Manager manager = null;
                if (fxUtils.areAllManagerFieldsFilled(username.getText(), password.getText(), name.getText(), surname.getText(), phoneNumber.getText(), salary.getText(),
                        birthday.getValue()))
                    fxUtils.alertMessage(Alert.AlertType.WARNING, "User creating warning", "Validation error", "All fields has to be filled");
                if (userTypeChoiceBox.getValue().equals("Manager")) {
                    manager = new Manager(username.getText(), password.getText(), name.getText(), surname.getText(), birthday.getValue(),
                            phoneNumber.getText(), Double.parseDouble(salary.getText()), false); // TODO: Make double validation
                } else {
                    manager = new Manager(username.getText(), password.getText(), name.getText(), surname.getText(), birthday.getValue(),
                            phoneNumber.getText(), Double.parseDouble(salary.getText()), true); // TODO: Make double validation
                }
                userHib.createUser(manager);
                break;
        }
    }

    public void hideFields(String choiceBoxValue) {
        switch (choiceBoxValue) {
            case "Courier":
                driverLicense.setDisable(false);
                medicalCertificate.setDisable(false);
                break;
            case "Manager":
            case "Admin Manager":
                driverLicense.setDisable(true);
                medicalCertificate.setDisable(true);
                break;
        }
    }

    public void createTruck() {
        if (!fxUtils.areAllTruckFieldsFilled(mark.getText(), model.getText(), horsePower.getText(), engineLiters.getText(), color.getText())) {
            Truck truck = new Truck(mark.getText(), model.getText(), Double.parseDouble(engineLiters.getText()), Integer.parseInt(horsePower.getText()),
                    color.getText());
            truckHib.createTruck(truck);
        } else {
            fxUtils.alertMessage(Alert.AlertType.WARNING, "Truck creating warning", "Validation error", "All fields has to be filled");
        }
    }

    public void updateTruckData() {
        truckActionButton.setText("Update Truck");
        fillTruckFields();
    }

    private void fillTruckFields() {
        Truck truck = truckList.getSelectionModel().getSelectedItem();
        mark.setText(truck.getMark());
        model.setText(truck.getModel());
        horsePower.setText(Integer.toString(truck.getHorsePower()));
        engineLiters.setText(Double.toString(truck.getEngineLiters()));
        color.setText(truck.getColor());

        truckActionButton.setOnAction(actionEvent -> {
            updateTruck(truck);
        });

    }

    private void updateTruck(Truck truck) {
        truck.setMark(mark.getText());
        truck.setModel(model.getText());
        truck.setHorsePower(Integer.parseInt(horsePower.getText()));
        truck.setEngineLiters(Double.parseDouble(engineLiters.getText()));
        truck.setColor(color.getText());
        truckHib.updateTruck(truck);
    }

    public void deleteTruck() {
    }

    public void viewTruckDetails(ActionEvent actionEvent) {
    }

    public void removeUser() {
    }

    public void updateUserData() {
        accountActionButton.setText("Update User");
        fillUserFields();
    }

    private void fillUserFields() { // TODO: I need to refactor this
        if (managerList.getSelectionModel().getSelectedItem() != null) {
            Manager manager = managerList.getSelectionModel().getSelectedItem();
            if (manager.isAdmin()){
                userTypeChoiceBox.setValue("Admin Manager");
            }else{
                userTypeChoiceBox.setValue("Manager");
            }
            username.setText(manager.getLogin());
            password.setText(manager.getPassword());
            name.setText(manager.getName());
            surname.setText(manager.getSurname());
            phoneNumber.setText(manager.getPhoneNo());
            salary.setText(Double.toString(manager.getSalary()));
            userHib.updateUser(manager);
//            birthday = manager.getBirthday(); //TODO: Fix problem LocalDate to DatePicker

        }else{
            Courier courier = courierList.getSelectionModel().getSelectedItem();
            userTypeChoiceBox.setValue("Courier");
            username.setText(courier.getLogin());
            password.setText(courier.getPassword());
            name.setText(courier.getName());
            surname.setText(courier.getSurname());
            phoneNumber.setText(courier.getPhoneNo());
            salary.setText(Double.toString(courier.getSalary()));
            driverLicense.setText(courier.getDriverLicense());
            medicalCertificate.setText(courier.getHealthCertificate());
            userHib.updateUser(courier);
        }
    }

    //Checkpoints
    public void createCheckpoint(ActionEvent actionEvent) {
        //TODO: Make here check if all fields are filled
        Checkpoint checkpoint = new Checkpoint(checkpointAddress.getText(), checkpointDate.getValue());
        checkpointHib.createCheckpoint(checkpoint);
    }

    public void viewCheckpointDetails() {
    }

    public void updateCheckpointData() {
        checkpointActionButton.setText("Update Checkpoint");
        fillCheckpointFields();
    }

    private void fillCheckpointFields() {
        Checkpoint checkpoint = checkpointList.getSelectionModel().getSelectedItem();
        checkpointAddress.setText(checkpoint.getAddress());
        checkpointDate.setValue(checkpoint.getCheckpointDate());
        checkpointActionButton.setOnAction(actionEvent -> {
            updateCheckpoint(checkpoint);
        });
    }
    private void updateCheckpoint(Checkpoint checkpoint){
        checkpoint.setAddress(checkpointAddress.getText());
        checkpoint.setCheckpointDate(checkpointDate.getValue());
        checkpointHib.updateCheckpoint(checkpoint);
    }

    public void deleteCheckpoint() {
    }

    public void createCargo() {
        //TODO: Make sure all fields are filled
        Cargo cargo = new Cargo(cargoNaming.getText(), Double.parseDouble(cargoWeight.getText()));
        cargoHib.createCargo(cargo);
    }

    public void createOrder() {
        List<Manager> selectedManagers = managersOrderList.getSelectionModel().getSelectedItems(); // TODO: Make here validation
        List<Cargo> selectedCargos = cargosOrderList.getSelectionModel().getSelectedItems();
        Destination destination = new Destination(destinationAddress.getText(), destinationRequestedDeliveryDate.getValue(), LocalDate.now(), OrderStatus.PENDING, selectedManagers, selectedCargos);
        destinationHib.createDestination(destination);
        allOrdersList.getItems().add(destination);
    }

    public void setOrderToCurrentUser() {
        Destination destination = (Destination) allOrdersList.getSelectionModel().getSelectedItem();
//        destination.setCourier(user);
    }
}
