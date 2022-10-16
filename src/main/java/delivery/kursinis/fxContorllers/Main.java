package delivery.kursinis.fxContorllers;

import delivery.kursinis.Enums.OrderStatus;
import delivery.kursinis.HelloApplication;
import delivery.kursinis.hibernate.*;
import delivery.kursinis.model.*;
import delivery.kursinis.utils.DatabaseOperations;
import delivery.kursinis.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
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

    //TODO: Cargos
    @FXML
    public Tab cargoManagementTab;
    @FXML
    public TextField cargoNaming;
    @FXML
    public TextField cargoWeight;
    @FXML
    public ListView<Cargo> cargoList;

    public Button cargoActionButton;
    CargoHib cargoHib;
    //--------------------------

    //TODO: Orders
    public ListView<Manager> managersOrderList;
    public ListView<Cargo> cargosOrderList;
    public TextField destinationAddress;
    public DatePicker destinationRequestedDeliveryDate;
    @FXML
    public ChoiceBox trucksChoiceBox;
    public ChoiceBox couriersChoiceBox;


    public Tab ordersTab;

    public ListView assignedOrdersList;

    public ListView allOrdersList;

    @FXML
    public ChoiceBox trucksChoiceBoxOrders;
    @FXML
    public TextField checkpointField;
    @FXML
    public Button addCheckpointButton;
    @FXML
    public DatePicker checkpointDate;
    @FXML
    public ChoiceBox managerChoiceBoxOrders;
    //-----------------

    // TODO: Checkpoints
    public TextField checkpointAddress;
    public DatePicker checkpointDateTab;
    public ListView<Checkpoint> checkpointsList;
    public ChoiceBox checkpointsChoiceBoxOrders;
    public Button checkpointActionButton;


    //TODO: Profile
    public TextField profileUsername;
    public TextField profileName;
    public TextField profileSurname;
    public TextField profilePhoneNo;
    public TextField profileDriverLicense;
    public TextField profileMedicalCertificate;
    public PasswordField profilePassword;

    public Button profileActionButton;
    public DatePicker profileBirthday;
    public Button profileActionButtonApplyChanges;


    private EntityManagerFactory entityManagerFactory;
    private User user;
    private UserHib userHib;
    private TruckHib truckHib;
    private DestinationHib destinationHib;

    private CheckpointHib checkpointHib;
    private String[] checkBoxValues = {"Courier", "Manager", "Admin Manager"};

    private FxUtils fxUtils = new FxUtils();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userTypeChoiceBox.getItems().addAll(checkBoxValues);
        userTypeChoiceBox.setOnAction(actionEvent -> hideFields(userTypeChoiceBox.getValue()));
    }

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
        this.userHib = new UserHib(this.entityManagerFactory);
        this.truckHib = new TruckHib(this.entityManagerFactory);
        this.destinationHib = new DestinationHib(this.entityManagerFactory);
        this.cargoHib = new CargoHib(this.entityManagerFactory);
        this.checkpointHib = new CheckpointHib(this.entityManagerFactory);


        List<Truck> allTrucks = truckHib.getAllTrucks();
        List<Courier> allCouriers = userHib.getAllCouriers();
        List<Manager> allManagers = userHib.getAllManagers();
        for (Truck truck : allTrucks) {
            trucksChoiceBoxOrders.getItems().add(truck);
            trucksChoiceBox.getItems().add(truck);
        }

        for (Courier courier : allCouriers)
            couriersChoiceBox.getItems().add(courier);

        for (Manager manager : allManagers)
            managerChoiceBoxOrders.getItems().add(manager);

        fillAllLists();
        disableData();
        setProfileData();
    }

    private void disableData() {
        if (user.getClass() == Courier.class) {
            allTabs.getTabs().remove(truckManagementTab);
            allTabs.getTabs().remove(registerUserTab);
            allTabs.getTabs().remove(orderManagementTab);
            allTabs.getTabs().remove(cargoManagementTab);
        }
    }

    private void fillAllLists() {
        fillTruckLists();
        fillCourierLists();
        fillManagersLists();
        fillCargosLists();
        fillAllDestinations();
        fillAllCheckpoints();

        setSelectionModes();
    }

    public void createUserByAdmin() {
        if (userTypeChoiceBox.getValue() == null)
            fxUtils.alertMessage(Alert.AlertType.ERROR, "User creating warning", "Validation error", "You have to specify user type!");
        switch (userTypeChoiceBox.getValue()) {
            case "Courier":
                Courier courier = null;
                if (fxUtils.areAllCourierFieldsFilled(username.getText(), password.getText(), name.getText(), surname.getText(), phoneNumber.getText(), salary.getText(),
                        driverLicense.getText(), medicalCertificate.getText(), birthday.getValue()))
                    fxUtils.alertMessage(Alert.AlertType.ERROR, "User creating warning", "Validation error", "All fields has to be filled");

                courier = new Courier(username.getText(), password.getText(), name.getText(), surname.getText(), birthday.getValue(), phoneNumber.getText(),
                        Double.parseDouble(salary.getText()),
                        driverLicense.getText(), medicalCertificate.getText());
                userHib.createUser(courier);
                fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Courier Creation Status", "", "Courier was created");
                fillCourierLists();
                break;

            case "Manager":
            case "Admin Manager":
                Manager manager = null;
                if (fxUtils.areAllManagerFieldsFilled(username.getText(), password.getText(), name.getText(), surname.getText(), phoneNumber.getText(), salary.getText(),
                        birthday.getValue()))
                    fxUtils.alertMessage(Alert.AlertType.ERROR, "User creating warning", "Validation error", "All fields has to be filled");
                if (userTypeChoiceBox.getValue().equals("Manager")) {
                    manager = new Manager(username.getText(), password.getText(), name.getText(), surname.getText(), birthday.getValue(),
                            phoneNumber.getText(), Double.parseDouble(salary.getText()), false); // TODO: Make double validation
                } else {
                    manager = new Manager(username.getText(), password.getText(), name.getText(), surname.getText(), birthday.getValue(),
                            phoneNumber.getText(), Double.parseDouble(salary.getText()), true); // TODO: Make double validation
                }
                userHib.createUser(manager);
                fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Manager Creation Status", "", "Manager was created");
                fillManagersLists();
                break;
        }
    }

    public void updateUserData() {
        accountActionButton.setText("Update User");
        fillUserFields();
    }

    private void fillUserFields() { // TODO: I need to refactor this
        if (managerList.getSelectionModel().getSelectedItem() != null) {
            Manager manager = managerList.getSelectionModel().getSelectedItem();
            if (manager.isAdmin()) {
                userTypeChoiceBox.setValue("Admin Manager");
            } else {
                userTypeChoiceBox.setValue("Manager");
            }
            username.setText(manager.getLogin());
            password.setText(manager.getPassword());
            name.setText(manager.getName());
            surname.setText(manager.getSurname());
            phoneNumber.setText(manager.getPhoneNo());
            salary.setText(Double.toString(manager.getSalary()));
            birthday.setValue(manager.getBirthday());
            accountActionButton.setOnAction(actionEvent -> {
                updateManager(manager);
            });
        } else {
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
            accountActionButton.setOnAction(actionEvent -> {
                updateCourier(courier);
            });
        }
    }

    private void updateCourier(Courier courier) {
        courier.setLogin(username.getText());
        courier.setPassword(password.getText());
        courier.setName(name.getText());
        courier.setSurname(surname.getText());
        courier.setPhoneNo(phoneNumber.getText());
        courier.setSalary(Double.parseDouble(salary.getText()));
        courier.setDriverLicense(driverLicense.getText());
        courier.setHealthCertificate(medicalCertificate.getText());
        courier.setBirthday(birthday.getValue());
        userHib.updateUser(courier);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Courier Update completed", "", "Courier data were successfully updated");
        accountActionButton.setText("Create User");
        fillCourierLists();
        resetUserTab();
    }

    private void updateManager(Manager manager) {
        manager.setLogin(username.getText());
        manager.setPassword(password.getText());
        manager.setName(name.getText());
        manager.setSurname(surname.getText());
        manager.setPhoneNo(phoneNumber.getText());
        manager.setSalary(Double.parseDouble(salary.getText()));
        manager.setBirthday(birthday.getValue());
        userHib.updateUser(manager);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Manager Update completed", "", "Manager data were successfully updated");
        accountActionButton.setText("Create User");
        fillManagersLists();
        resetUserTab();
    }

    public void removeUser() {
        User user1 = courierList.getSelectionModel().getSelectedItem();
        userHib.removeUser(user1);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Delete completed", "", "User was successfully deleted");
    }

    public void createTruck() {
        if (fxUtils.isPositiveInteger(horsePower.getText()) && fxUtils.isPositiveDouble(engineLiters.getText()) &&
                !fxUtils.areAllTruckFieldsFilled(mark.getText(), model.getText(), horsePower.getText(), engineLiters.getText(), color.getText())) {
            Truck truck = new Truck(mark.getText(), model.getText(), Double.parseDouble(engineLiters.getText()), Integer.parseInt(horsePower.getText()),
                    color.getText());
            truckHib.createTruck(truck);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Truck Creation Status", "", "Truck was created");
            fillTruckLists();
        } else {
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Truck creating warning", "Validation error", "All fields has to be filled");
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
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Truck Update completed", "", "Truck was successfully updated");
        truckActionButton.setText("Create Truck");
        fillTruckLists();
        resetTruckTab();
    }

    public void deleteTruck() {
        Truck truck = truckList.getSelectionModel().getSelectedItem();
        truckHib.removeTruck(truck);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Delete completed", "", "Truck was successfully deleted");
    }

    public void createCargo() {
        if (fxUtils.isPositiveDouble(cargoWeight.getText()) && !fxUtils.areCargoFieldsFilled(cargoNaming.getText(), cargoWeight.getText())) {
            Cargo cargo = new Cargo(cargoNaming.getText(), Double.parseDouble(cargoWeight.getText()));
            cargoHib.createCargo(cargo);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Cargo Creation Status", "", "Cargo was created");
            fillCargosLists();
        } else {
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Field Input Error", "Typing Error", "All fields has to be filled");
        }
    }

    public void updateCargoData() { // TODO: Make functionlity
        cargoActionButton.setText("Update Cargo");
        fillCargoFields();
    }

    private void fillCargoFields() {
        Cargo cargo = cargoList.getSelectionModel().getSelectedItem();
        cargoNaming.setText(cargo.getNaming());
        cargoWeight.setText(Double.toString(cargo.getWeight()));

        cargoActionButton.setOnAction(actionEvent -> updateCargo(cargo));
    }

    private void updateCargo(Cargo cargo) {
        cargo.setNaming(cargoNaming.getText());
        cargo.setWeight(Double.parseDouble(cargoWeight.getText()));
        cargoHib.updateCargo(cargo);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Cargo Update completed", "", "Cargo was successfully updated");
        cargoActionButton.setText("Create Cargo");
        fillCargosLists();
        resetCargoTab();
    }

    public void deleteCargo(ActionEvent actionEvent) {
        Cargo cargo = cargoList.getSelectionModel().getSelectedItem();
        cargoHib.removeCargo(cargo);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Delete completed", "", "Cargo was successfully deleted");
    }

    public void createOrder() {
        Manager currentManager = userHib.getManagerByID(user.getId());
        Truck truck = null;
        managersOrderList.getItems().add(currentManager);
        List<Manager> selectedManagers = managersOrderList.getSelectionModel().getSelectedItems();
        List<Cargo> selectedCargos = cargosOrderList.getSelectionModel().getSelectedItems();
        Truck truckFromChoiceBox = (Truck) trucksChoiceBox.getValue();


        if (!fxUtils.areDestinationFieldsFilled(destinationAddress.getText(), destinationRequestedDeliveryDate.getValue(), LocalDate.now(), OrderStatus.PENDING) &&
                fxUtils.isCargoListEmpty(selectedCargos)) {
            if (truckFromChoiceBox != null)
                truck = truckHib.getTruckByID(truckFromChoiceBox.getId());

            Destination destination = new Destination(destinationAddress.getText(), destinationRequestedDeliveryDate.getValue(), LocalDate.now(), OrderStatus.PENDING, selectedManagers, selectedCargos, truck);
            destinationHib.createDestination(destination);
            allOrdersList.getItems().add(destination);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Order Creation Status", "", "Order was created");
            fillAllDestinations();
        } else {
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Field Input Error", "Typing Error", "All fields has to be filled");
        }
    }

    public void setOrderToCurrentUser() {
        int id = ((Destination) allOrdersList.getSelectionModel().getSelectedItem()).getId();
        Destination destination = destinationHib.getDestinationByID(id);
        if (destination.getCourier() != null && user.getClass() == Courier.class)
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Setting completed", "Error", "There is already assigned Courier to order");

        else if (user.getClass() == Courier.class && destination.getCourier() == null) {
            Courier courier = userHib.getCourierByID(user.getId());
            destination.setCourier(courier);
            destinationHib.updateDestination(destination);
        } else {
            Manager manager = userHib.getManagerByID(user.getId());
            List<Manager> destinationManagers = destination.getManagers();

            boolean insertValues = true;
            for (Manager man : destinationManagers) // With contains doesnt work
                if (man.getId() == manager.getId()) {
                    fxUtils.alertMessage(Alert.AlertType.ERROR, "Manager assign error", "Assigning error", "This Manager has been already assigned to this order");
                    insertValues = false;
                    break;
                }
            if (insertValues) {
                destination.getManagers().add(manager);
                manager.getDestinations().add(destination);
                destinationHib.updateDestination(destination); //TODO: Maybe it work with managers array
            }
        }
    }

    public void assignTruckToOrder() {
        //TODO: Check if value from choiceBox is selected
        Destination destination = (Destination) allOrdersList.getSelectionModel().getSelectedItem(); //TODO: Warning if truck is filleed
        destination.setTruck((Truck) trucksChoiceBoxOrders.getValue());
        destinationHib.updateDestination(destination);
    }

    public void addCheckpointToOrder() {
        Checkpoint checkpoint = null;
        int id = ((Destination) allOrdersList.getSelectionModel().getSelectedItem()).getId();
        Destination destination = destinationHib.getDestinationByID(id);

        Checkpoint checkpointFromCheckbox = (Checkpoint) checkpointsChoiceBoxOrders.getValue();
        checkpoint = checkpointHib.getCheckpointByID(checkpointFromCheckbox.getId());

        destination.getCheckpoints().add(checkpoint);
        destinationHib.updateDestination(destination);
    }

    public void assignManagerToOrder() {
        if (allOrdersList.getSelectionModel().getSelectedItems().size() == 0) {
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Order assign error", "Assigning error", "Orders has to chosen from list");
        }
        int id = ((Destination) allOrdersList.getSelectionModel().getSelectedItem()).getId();
        Destination destination = destinationHib.getDestinationByID(id);
        Manager managerFromChoiceBox = (Manager) managerChoiceBoxOrders.getValue();
        Manager manager = userHib.getManagerByID(managerFromChoiceBox.getId());

        List<Manager> destinationManagers = destination.getManagers();
        boolean insertValues = true;
        for (Manager man : destinationManagers) // With contains doesnt work
            if (man.getId() == manager.getId()) {
                fxUtils.alertMessage(Alert.AlertType.ERROR, "Manager assign error", "Assigning error", "Manager has been already assigned to this order");
                insertValues = false;
                break;
            }
        if (insertValues) {
            destination.getManagers().add(manager);
            manager.getDestinations().add(destination);
            destinationHib.updateDestination(destination);
        }
    }

    public void createCheckpoint() {
        if (!fxUtils.areCheckpointFieldInputed(checkpointAddress.getText(), checkpointDateTab.getValue())) {
            Checkpoint checkpoint = new Checkpoint(checkpointAddress.getText(), checkpointDateTab.getValue());
            checkpointHib.createCheckpoint(checkpoint);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Checkpoint Creation Status", "", "Checkpoint was created");
            fillAllCheckpoints();
        } else {
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Checkpoint creating warning", "Validation error", "All fields has to be filled");
        }
    }

    public void deleteCheckpoint() {
        Checkpoint checkpoint = checkpointsList.getSelectionModel().getSelectedItem();
        checkpointHib.removeCheckpoint(checkpoint);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Delete completed", "", "Cargo was successfully deleted");
    }

    public void updateCheckpointData() {
        checkpointActionButton.setText("Update Checkpoint");
        fillCheckpointFields();
    }

    private void fillCheckpointFields() {
        Checkpoint checkpoint = checkpointsList.getSelectionModel().getSelectedItem();
        checkpointAddress.setText(checkpoint.getAddress());
        checkpointDateTab.setValue(checkpoint.getCheckpointDate());
        checkpointActionButton.setOnAction(actionEvent -> {
            updateCheckpoint(checkpoint);
        });
    }

    private void updateCheckpoint(Checkpoint checkpoint) {
        checkpoint.setAddress(checkpointAddress.getText());
        checkpoint.setCheckpointDate(checkpointDateTab.getValue());

        checkpointHib.updateCheckpoint(checkpoint);
        fillAllCheckpoints();
        resetCheckpointTab();
    }

    private void resetCheckpointTab() { // TODO: Reset all tabs
        checkpointAddress.setText("");
        checkpointDateTab.setValue(null);
        checkpointActionButton.setText("Create Checkpoint");
    }

    private void resetUserTab(){
        username.setText("");
        password.setText("");
        name.setText("");
        surname.setText("");
        phoneNumber.setText("+370");
        salary.setText("");
        birthday.setValue(null);
        driverLicense.setText("");
        medicalCertificate.setText("");
    }

    private void resetTruckTab(){
        mark.setText("");
        model.setText("");
        horsePower.setText("");
        engineLiters.setText("");
        color.setText("");
    }
    private void resetCargoTab(){
        cargoNaming.setText("");
        cargoWeight.setText("");
    }
    private void resetProfileTab(){
        profileUsername.setText("");
        profilePassword.setText("");
        profileName.setText("");
        profileSurname.setText("");
        profilePhoneNo.setText("+370");
        profileDriverLicense.setText("");
        profileMedicalCertificate.setText("");
        profileBirthday.setValue(null);
    }



    public void updateProfileData() {
        setProfileFieldsNotEditable(true);
        profileActionButton.setVisible(false);
        profileActionButtonApplyChanges.setVisible(true);
    }

    public void applyProfileChanges() {
        if (user.getClass() == Courier.class) {
            Courier courier = userHib.getCourierByID(user.getId());
            courier.setLogin(profileUsername.getText());
            courier.setPassword(profilePassword.getText());
            courier.setName(profileName.getText());
            courier.setSurname(profileSurname.getText());
            courier.setPhoneNo(profilePhoneNo.getText());
            courier.setBirthday(profileBirthday.getValue());
            courier.setDriverLicense(profileDriverLicense.getText());
            courier.setHealthCertificate(profileMedicalCertificate.getText());
            userHib.updateCourier(courier);
        } else if (user.getClass() == Manager.class) {
            user.setLogin(profileUsername.getText());
            user.setPassword(profilePassword.getText());
            user.setName(profileName.getText());
            user.setSurname(profileSurname.getText());
            user.setPhoneNo(profilePhoneNo.getText());
            user.setBirthday(profileBirthday.getValue());
            userHib.updateUser(user);
        }
        profileActionButton.setVisible(true);
        profileActionButtonApplyChanges.setVisible(false);
        resetProfileTab();
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "User data update completed", "", "User information was successfully updated");
    }

    private void setProfileData() {
        profileUsername.setText(user.getLogin());
        profilePassword.setText(user.getPassword());
        profileName.setText(user.getName());
        profileSurname.setText(user.getSurname());
        profilePhoneNo.setText(user.getPhoneNo());
        profileBirthday.setValue(user.getBirthday());
        if (user.getClass() == Manager.class) {
            profileDriverLicense.setVisible(false);
            profileMedicalCertificate.setVisible(false);
        } else {
            Courier courier = userHib.getCourierByID(user.getId());
            profileDriverLicense.setText(courier.getDriverLicense());
            profileMedicalCertificate.setText(courier.getHealthCertificate());
        }
        setProfileFieldsNotEditable(false);
    }

    private void setProfileFieldsNotEditable(boolean type) {
        profileUsername.setEditable(type);
        profilePassword.setEditable(type);
        profileName.setEditable(type);
        profileSurname.setEditable(type);
        profilePhoneNo.setEditable(type);
        profileDriverLicense.setEditable(type);
        profileMedicalCertificate.setEditable(type);
        profileActionButtonApplyChanges.setVisible(false);
    }

    private void setSelectionModes() {
        cargosOrderList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        managersOrderList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void fillAllCheckpoints() {
        checkpointsList.getItems().clear();
        List<Checkpoint> allCheckpoints = checkpointHib.getAllCheckpoints();
        for (Checkpoint checkpoint : allCheckpoints) {
            checkpointsList.getItems().add(checkpoint);
            checkpointsChoiceBoxOrders.getItems().add(checkpoint);
        }

    }

    private void fillAllDestinations() {
        allOrdersList.getItems().clear();
        assignedOrdersList.getItems().clear();
        List<Destination> allDestinations = destinationHib.getAllDestinations();
        for (Destination destination : allDestinations) {
            allOrdersList.getItems().add(destination);
            if (destination.getCourier() != null && destination.getCourier().getId() == user.getId()) {
                assignedOrdersList.getItems().add(destination);
            }
        }
    }

    private void fillCargosLists() {
        cargoList.getItems().clear();
        cargosOrderList.getItems().clear();
        List<Cargo> allCargos = cargoHib.getAllCargos();
        for (Cargo cargo : allCargos) {
            cargoList.getItems().add(cargo);
            cargosOrderList.getItems().add(cargo);
        }
    }

    private void fillManagersLists() {
        managerList.getItems().clear();
        managersOrderList.getItems().clear();
        List<Manager> allManagers = userHib.getAllManagers();
        for (Manager manager : allManagers) {
            managerList.getItems().add(manager);
            if (manager.getId() != user.getId())
                managersOrderList.getItems().add(manager);
        }
    }

    private void fillCourierLists() {
        courierList.getItems().clear();
        List<Courier> allCouriers = userHib.getAllCouriers();
        for (Courier courier : allCouriers)
            courierList.getItems().add(courier);
    }

    private void fillTruckLists() {
        truckList.getItems().clear();
        List<Truck> allTrucks = truckHib.getAllTrucks();
        for (Truck truck : allTrucks)
            truckList.getItems().add(truck);
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
}
