package delivery.kursinis.fxContorllers;

import delivery.kursinis.Enums.OrderStatus;
import delivery.kursinis.hibernate.*;
import delivery.kursinis.model.*;
import delivery.kursinis.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.persistence.EntityManagerFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    public Button accountActionButton;
    @FXML
    public TextField commentTitle;
    @FXML
    public TextField commentText;
    @FXML
    public Button addManagerButton;
    @FXML
    public ChoiceBox courierChoiceBoxOrders;
    @FXML
    public Button addCourierButton;
    @FXML
    public MenuItem replyItemInForum;
    @FXML
    public DatePicker departureDateFilter;
    @FXML
    public DatePicker arrivalDateFilter;
    @FXML
    public ChoiceBox statusFilter;
    @FXML
    public ChoiceBox courierChoiceBoxOrdersFilter;
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
    //Cargos
    @FXML
    public Tab cargoManagementTab;
    @FXML
    public TextField cargoNaming;
    @FXML
    public TextField cargoWeight;
    @FXML
    public ListView<Cargo> cargoList;
    @FXML
    public Button cargoActionButton;
    @FXML
    CargoHib cargoHib;
    @FXML
    public ListView<Manager> managersOrderList;
    @FXML
    public ListView<Cargo> cargosOrderList;
    @FXML
    public TextField destinationAddress;
    @FXML
    public DatePicker destinationRequestedDeliveryDate;
    @FXML
    public ChoiceBox trucksChoiceBox;
    @FXML
    public ChoiceBox couriersChoiceBox;
    @FXML
    public Button assignOrderButton;
    @FXML
    public Tab ordersTab;
    @FXML
    public ListView<Destination> assignedOrdersList;
    @FXML
    public ListView<Destination> allOrdersList;
    @FXML
    public ChoiceBox trucksChoiceBoxOrders;
    @FXML
    public ChoiceBox managerChoiceBoxOrders;
    @FXML
    public TextField orderAddress;
    @FXML
    public DatePicker orderDeliveryDate;
    @FXML
    public MenuItem updateOrderMenuItem;
    @FXML
    public MenuItem deleteOrderMenuItem;
    @FXML
    public Button orderActionButton;
    @FXML
    public TextField checkpointAddress;
    @FXML
    public DatePicker checkpointDateTab;
    @FXML
    public ListView<Checkpoint> checkpointsList;
    @FXML
    public ChoiceBox checkpointsChoiceBoxOrders;
    @FXML
    public Button checkpointActionButton;
    //Profile
    @FXML
    public TextField profileUsername;
    @FXML
    public TextField profileName;
    @FXML
    public TextField profileSurname;
    @FXML
    public TextField profilePhoneNo;
    @FXML
    public TextField profileDriverLicense;
    @FXML
    public TextField profileMedicalCertificate;
    @FXML
    public PasswordField profilePassword;
    @FXML
    public Button profileActionButton;
    @FXML
    public DatePicker profileBirthday;
    @FXML
    public Button profileActionButtonApplyChanges;
    //Forum
    @FXML
    public MenuItem updateItemInForum;
    @FXML
    public TreeView<Comment> commentTree = new TreeView<Comment>();
    @FXML
    public TextField forumTitle;
    @FXML
    public TextField forumDescription;
    @FXML
    public Button forumActionButton;
    @FXML
    public ListView<Forum> allForumsList;
    @FXML
    public Button updateCommentButton;
    @FXML
    public Tab forumTab;
    private EntityManagerFactory entityManagerFactory;
    private User user;
    private UserHib userHib;
    private TruckHib truckHib;
    private DestinationHib destinationHib;
    private CommentHib commentHib;
    private ForumHib forumHib;
    private CheckpointHib checkpointHib;
    private String[] checkBoxValues = {"Courier", "Manager", "Admin Manager"};
    private FxUtils fxUtils = new FxUtils();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userTypeChoiceBox.getItems().addAll(checkBoxValues);
        userTypeChoiceBox.setOnAction(actionEvent -> hideFields(userTypeChoiceBox.getValue()));
        statusFilter.getItems().addAll(OrderStatus.getStatuses());
    }

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
        this.userHib = new UserHib(this.entityManagerFactory);
        this.truckHib = new TruckHib(this.entityManagerFactory);
        this.destinationHib = new DestinationHib(this.entityManagerFactory);
        this.cargoHib = new CargoHib(this.entityManagerFactory);
        this.checkpointHib = new CheckpointHib(this.entityManagerFactory);
        this.commentHib = new CommentHib(this.entityManagerFactory);
        this.forumHib = new ForumHib(this.entityManagerFactory);


        List<Truck> allTrucks = truckHib.getAllTrucks();
        List<Courier> allCouriers = userHib.getAllCouriers();
        List<Manager> allManagers = userHib.getAllManagers();
        for (Truck truck : allTrucks) {
            trucksChoiceBoxOrders.getItems().add(truck);
            trucksChoiceBox.getItems().add(truck);
        }

        for (Courier courier : allCouriers) {
            couriersChoiceBox.getItems().add(courier);
            courierChoiceBoxOrders.getItems().add(courier);
            courierChoiceBoxOrdersFilter.getItems().add(courier);
        }


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
            deleteOrderMenuItem.setVisible(false);
            updateOrderMenuItem.setVisible(false);
            managerChoiceBoxOrders.setVisible(false); // TODO: Make it when Manager visible
            addManagerButton.setVisible(false);
            allOrdersList.setEditable(false);
            courierChoiceBoxOrders.setVisible(false);
            addCourierButton.setVisible(false);
        }
        orderActionButton.setVisible(false);
        updateCommentButton.setVisible(false);
        orderAddress.setVisible(false);
        orderDeliveryDate.setVisible(false);
    }

    private void fillAllLists() {
        fillTruckLists();
        fillCourierLists();
        fillManagersLists();
        fillCargosLists();
        fillAllDestinations();
        fillAllCheckpoints();
        fillForumLists();
        setSelectionModes();
    }

    public void createUserByAdmin() {
        if (userTypeChoiceBox.getValue() == null)
            fxUtils.alertMessage(Alert.AlertType.ERROR, "User creating warning", "Validation error", "You have to specify user type!");
        else
            switch (userTypeChoiceBox.getValue()) {
                case "Courier":
                    Courier courier = null;
                    if (fxUtils.areAllCourierFieldsFilled(username.getText(), password.getText(), name.getText(), surname.getText(), phoneNumber.getText(), salary.getText(),
                            driverLicense.getText(), medicalCertificate.getText(), birthday.getValue())) {
                        fxUtils.alertMessage(Alert.AlertType.ERROR, "User creating warning", "Validation error", "All fields has to be filled");
                        break;
                    } else if (!fxUtils.isPositiveDouble(salary.getText())) {
                        fxUtils.alertMessage(Alert.AlertType.ERROR, "User creating warning", "Validation error", "Wrong type, type has to be double");
                        break;
                    } else {
                        courier = new Courier(username.getText(), password.getText(), name.getText(), surname.getText(), birthday.getValue(), phoneNumber.getText(),
                                Double.parseDouble(salary.getText()),
                                driverLicense.getText(), medicalCertificate.getText());
                        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Courier Creation Status", "", "Courier was created");
                    }
                    userHib.createUser(courier);
                    fillCourierLists();
                    break;

                case "Manager":
                case "Admin Manager":
                    Manager manager = null;
                    if (fxUtils.areAllManagerFieldsFilled(username.getText(), password.getText(), name.getText(), surname.getText(), phoneNumber.getText(), salary.getText(),
                            birthday.getValue())) {
                        fxUtils.alertMessage(Alert.AlertType.ERROR, "User creating warning", "Validation error", "All fields has to be filled");
                        break;
                    } else if (!fxUtils.isPositiveDouble(salary.getText())) {
                        fxUtils.alertMessage(Alert.AlertType.ERROR, "User creating warning", "Validation error", "Wrong type, type has to be double");
                        break;
                    } else if (userTypeChoiceBox.getValue().equals("Manager")) {
                        manager = new Manager(username.getText(), password.getText(), name.getText(), surname.getText(), birthday.getValue(),
                                phoneNumber.getText(), Double.parseDouble(salary.getText()), false);
                    } else {
                        manager = new Manager(username.getText(), password.getText(), name.getText(), surname.getText(), birthday.getValue(),
                                phoneNumber.getText(), Double.parseDouble(salary.getText()), true);
                        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Manager Creation Status", "", "Manager was created");
                    }
                    userHib.createUser(manager);
                    fillManagersLists();
                    break;
            }
    }

    public void updateUserData() {
        accountActionButton.setText("Update User");
        fillUserFields();
    }

    private void fillUserFields() {
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
        fillManagersLists();
        fillCourierLists();
    }

    public void createTruck() {
        if (!fxUtils.isPositiveInteger(horsePower.getText()) || !fxUtils.isPositiveDouble(engineLiters.getText()) ||
                fxUtils.areAllTruckFieldsFilled(mark.getText(), model.getText(), horsePower.getText(), engineLiters.getText(), color.getText())) {
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Truck creating warning", "Validation error", "All fields has to be filled");
        } else {
            Truck truck = new Truck(mark.getText(), model.getText(), Double.parseDouble(engineLiters.getText()), Integer.parseInt(horsePower.getText()),
                    color.getText());
            truckHib.createTruck(truck);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Truck Creation Status", "", "Truck was created");
            fillTruckLists();
            resetTruckTab();
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
        fillTruckLists();
    }

    public void createCargo() {
        if (!fxUtils.isPositiveDouble(cargoWeight.getText()) || fxUtils.areCargoFieldsFilled(cargoNaming.getText(), cargoWeight.getText())) {
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Field Input Error", "Typing Error", "All fields has to be filled");
        } else {
            Cargo cargo = new Cargo(cargoNaming.getText(), Double.parseDouble(cargoWeight.getText()));
            cargoHib.createCargo(cargo);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Cargo Creation Status", "", "Cargo was created");
            fillCargosLists();
        }
    }

    public void updateCargoData() {
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
        fillCargosLists();
    }

    public void createOrder() {
        Manager currentManager = userHib.getManagerByID(user.getId());
        Truck truck = null;
        Courier courier = null;
        List<Manager> selectedManagers = new ArrayList<>();
        selectedManagers.add(currentManager);
        for (Manager man : managersOrderList.getSelectionModel().getSelectedItems()) {
            selectedManagers.add(man);
        }
        List<Cargo> selectedCargos = cargosOrderList.getSelectionModel().getSelectedItems();
        Truck truckFromChoiceBox = (Truck) trucksChoiceBox.getValue();
        Courier courierFromChoiceBox = (Courier) couriersChoiceBox.getValue();
        if (!fxUtils.areDestinationFieldsFilled(destinationAddress.getText(), destinationRequestedDeliveryDate.getValue(), LocalDate.now(), OrderStatus.PENDING) &&
                fxUtils.isCargoListEmpty(selectedCargos)) {
            if (truckFromChoiceBox != null)
                truck = truckHib.getTruckByID(truckFromChoiceBox.getId());
            if (courierFromChoiceBox != null)
                courier = userHib.getCourierByID(courierFromChoiceBox.getId());

            Destination destination = new Destination(destinationAddress.getText(), destinationRequestedDeliveryDate.getValue(), LocalDate.now(), OrderStatus.PENDING, selectedManagers, selectedCargos, courier, truck);
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
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Type Error", "Error", "There is already assigned Courier to order");

        else if (user.getClass() == Courier.class && destination.getCourier() == null) {
            Courier courier = userHib.getCourierByID(user.getId());
            destination.setCourier(courier);
            destinationHib.updateDestination(destination);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Updated", "", "You assigned yourself to order");
        } else {
            Manager manager = userHib.getManagerByID(user.getId());
            List<Manager> destinationManagers = destination.getManagers();

            boolean insertValues = true;
            for (Manager man : destinationManagers)
                if (man.getId() == manager.getId()) {
                    fxUtils.alertMessage(Alert.AlertType.ERROR, "Manager assign error", "Assigning error", "This Manager has been already assigned to this order");
                    insertValues = false;
                    break;
                }
            if (insertValues) {
                destination.getManagers().add(manager);
                manager.getDestinations().add(destination);
                destinationHib.updateDestination(destination);
            }
        }
        fillAssignedOrders();
    }

    private void fillAssignedOrders() {
        assignedOrdersList.getItems().clear();
        List<Destination> allDestinations = destinationHib.getAllDestinations();
        if (user.getClass() == Manager.class) {
            List<Manager> managers = null;
            for (Destination destination : allDestinations) {
                managers = userHib.getAllManagers();
                for (Manager man : managers) {
                    if (man.getId() == user.getId()) {
                        assignedOrdersList.getItems().add(destination);
                    }
                }
            }
        } else {
            for (Destination destination : allDestinations) {
                if (destination.getCourier() != null && destination.getCourier().getId() == user.getId())
                    assignedOrdersList.getItems().add(destination);
            }
        }
    }

    public void assignTruckToOrder() {
        //TODO: Check if value from choiceBox is selected
        Destination destination = (Destination) allOrdersList.getSelectionModel().getSelectedItem();
        if (couriersChoiceBox.getSelectionModel().getSelectedItem() != null && destination.getTruck() != null) {
            destination.setTruck((Truck) trucksChoiceBoxOrders.getValue());
            destinationHib.updateDestination(destination);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Assign Status", "", "Truck was successfully assigned to order");
        } else {
            fxUtils.alertMessage(Alert.AlertType.WARNING, "Assign Status", "", "Truck was already assigned by manager");
        }
    }

    public void addCheckpointToOrder() {
        try {
            Checkpoint checkpoint = null;
            int id = ((Destination) assignedOrdersList.getSelectionModel().getSelectedItem()).getId();
//        int id = ((Destination) allOrdersList.getSelectionModel().getSelectedItem()).getId();
            Destination destination = destinationHib.getDestinationByID(id);

            Checkpoint checkpointFromCheckbox = (Checkpoint) checkpointsChoiceBoxOrders.getValue();
            checkpoint = checkpointHib.getCheckpointByID(checkpointFromCheckbox.getId());

            destination.getCheckpoints().add(checkpoint);
            destinationHib.updateDestination(destination);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Assign Status", "", "Checkpoint was successfully added to order");
        } catch (Exception e) {
            fxUtils.alertMessage(Alert.AlertType.WARNING, "WRONG", "", "You can't edit not assigned orders");
        }
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
        for (Manager man : destinationManagers)
            if (man.getId() == manager.getId()) {
                fxUtils.alertMessage(Alert.AlertType.ERROR, "Manager assign error", "Assigning error", "Manager has been already assigned to this order");
                insertValues = false;
                break;
            }
        if (insertValues) {
            destination.getManagers().add(manager);
            manager.getDestinations().add(destination);
            destinationHib.updateDestination(destination);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Assign Status", "", "Manager was successfully added to order");
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
        fillAllCheckpoints();
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

    private void resetCheckpointTab() {
        checkpointAddress.setText("");
        checkpointDateTab.setValue(null);
        checkpointActionButton.setText("Create Checkpoint");
    }

    private void resetUserTab() {
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

    private void resetTruckTab() {
        mark.setText("");
        model.setText("");
        horsePower.setText("");
        engineLiters.setText("");
        color.setText("");
    }

    private void resetCargoTab() {
        cargoNaming.setText("");
        cargoWeight.setText("");
    }

    private void resetProfileTab() {
        profileUsername.setText(user.getLogin());
        profilePassword.setText(user.getPassword());
        profileName.setText(user.getName());
        profileSurname.setText(user.getPassword());
        profilePhoneNo.setText(user.getPhoneNo());
        profileDriverLicense.setText("");
        profileMedicalCertificate.setText("");
        profileBirthday.setValue(user.getBirthday());
    }

    private void resetForumTab() {
        forumTitle.setText("");
        forumDescription.setText("");
        commentTitle.setText("");
        commentText.setText("");
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
        List<Destination> allDestinations = destinationHib.getAllDestinations();
        for (Destination destination : allDestinations) {
            allOrdersList.getItems().add(destination);
        }
        fillAssignedOrders();
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
        couriersChoiceBox.getItems().clear();
        List<Courier> allCouriers = userHib.getAllCouriers();
        for (Courier courier : allCouriers) {
            courierList.getItems().add(courier);
            couriersChoiceBox.getItems().add(courier);
        }

    }

    private void fillTruckLists() {
        truckList.getItems().clear();
        trucksChoiceBox.getItems().clear();
        List<Truck> allTrucks = truckHib.getAllTrucks();
        for (Truck truck : allTrucks) {
            truckList.getItems().add(truck);
            trucksChoiceBox.getItems().add(truck);
        }
    }

    private void fillForumLists() {
        allForumsList.getItems().clear();
        List<Forum> allForums = forumHib.getAllForums();
        for (Forum forum : allForums)
            allForumsList.getItems().add(forum);
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

    public void updateForumData() {
        Forum forum = (Forum) allForumsList.getSelectionModel().getSelectedItem();
        forumTitle.setText(forum.getForumTitle());
        forumDescription.setText(forum.getForumDescription());
        updateCommentButton.setText("Update Forum");
        updateCommentButton.setVisible(true);
        updateCommentButton.setOnAction(actionEvent -> updateForum(forum));
    }

    private void updateForum(Forum forum) {
        forum.setForumTitle(forumTitle.getText());
        forum.setForumDescription(forumDescription.getText());
        forumHib.updateForum(forum);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Forum Update completed", "", "Forum was successfully updated");
        updateCommentButton.setVisible(false);
        fillForumLists();
        resetForumTab();
    }

    public void deleteForum() {
        Forum forum = (Forum) allForumsList.getSelectionModel().getSelectedItem();
        List<Comment> forumComments = getCommentsByParentId(forum.getId());
        for (Comment comment : forumComments) {
            deleteComment(comment);
        }
        forumHib.deleteForum(forum);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Delete completed", "", "Forum was successfully deleted");
        fillForumLists();
        loadComments();
    }

    public void deleteComment() {
        Comment comment = (Comment) commentTree.getSelectionModel().getSelectedItem().getValue();
        commentHib.deleteComment(comment);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Delete completed", "", "Comment was successfully deleted");
        loadComments();
    }

    public void deleteComment(Comment comment) {
        commentHib.deleteComment(comment);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Delete completed", "", "Comment was successfully deleted");
    }

    public void updateCommentData() {
        Comment comment = (Comment) commentTree.getSelectionModel().getSelectedItem().getValue();
        commentTitle.setText(comment.getTitle());
        commentText.setText(comment.getCommentText());
        updateCommentButton.setText("Update Comment");
        updateCommentButton.setVisible(true);
        updateCommentButton.setOnAction(actionEvent -> updateComment(comment));
    }

    public void updateComment(Comment comment) {
        comment.setTitle(commentTitle.getText());
        comment.setCommentText(commentText.getText());
        commentHib.updateComment(comment);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Comment Update completed", "", "Comment was successfully updated");
        updateCommentButton.setVisible(false);
        loadComments();
        resetForumTab();
    }

    public void createComment() {
        Forum forum = allForumsList.getSelectionModel().getSelectedItem();
        Comment comment = new Comment(commentTitle.getText(), commentText.getText(), forum);
        commentHib.createComment(comment);
        loadComments();
    }

    public void loadComments() {
        Forum forumFromList = allForumsList.getSelectionModel().getSelectedItem();
        Forum forum = forumHib.getForumByID(forumFromList.getId());
        List<Comment> comments = getCommentsByParentId(forum.getId());
        TreeItem mainRoot = new TreeItem<>("Open Comments");
        TreeItem<Comment> commentTreeItem = new TreeItem<Comment>();
        for (Comment comment : comments) {
            if (comment.getParentForum() != null) {
                mainRoot.getChildren().add(new TreeItem<>(comment));
            }
        }
        List<Comment> allComments = commentHib.getAllComments();
        for (Comment comment : allComments) {
            if (comment.getParentComment() != null) {
                Comment parentComment = commentHib.getCommentByID(comment.getParentComment().getId());
                TreeItem<Comment> tree = new TreeItem<>(parentComment);
                tree.getChildren().add(new TreeItem<>(comment));
                mainRoot.getChildren().add(tree);
            }
        }
        commentTree.setRoot(mainRoot);
    }

    public List<Comment> getCommentsByParentId(int id) {
        List<Comment> comments = commentHib.getAllComments();
        List<Comment> forumComments = new ArrayList<Comment>();
        for (Comment comment : comments) {
            if (comment.getParentForum() != null && comment.getParentForum().getId() == id) {
                forumComments.add(comment);
            }
        }
        return forumComments;
    }

    private void addTreeItem(Comment comment, TreeItem parent) {
        TreeItem<Comment> treeItem = new TreeItem<Comment>(comment);
        parent.getChildren().add(treeItem);
        comment.getReplies().forEach(r -> addTreeItem(r, treeItem));
    }

    public void createForum() {
        if (fxUtils.areForumFieldsFilled(forumTitle.getId(), forumDescription.getText()))
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Creation Error", "", "All fields must be filled");
        else {
            Forum forum = new Forum(forumTitle.getText(), forumDescription.getText());
            forumHib.createForum(forum);
            fillForumLists();
        }
    }

    public void updateOrderData() {
        orderActionButton.setVisible(true);
        assignOrderButton.setVisible(false);
        orderAddress.setVisible(true);
        orderDeliveryDate.setVisible(true);
        fillOrderFields();
    }

    private void fillOrderFields() {
        Destination destination = allOrdersList.getSelectionModel().getSelectedItem();
        orderAddress.setText(destination.getAddress());
        orderDeliveryDate.setValue(destination.getRequestedDeliveryDate());
        trucksChoiceBoxOrders.setValue(destination.getTruck());

        orderActionButton.setOnAction(actionEvent -> updateOrder(destination));
    }

    private void updateOrder(Destination destination) {
        destination.setAddress(orderAddress.getText());
        destination.setRequestedDeliveryDate(orderDeliveryDate.getValue());
        destination.setTruck((Truck) trucksChoiceBoxOrders.getSelectionModel().getSelectedItem());
        destinationHib.updateDestination(destination);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Truck Update completed", "", "Order was successfully updated");
        fillAllOrders();
        resetDestinationTab();
        orderActionButton.setVisible(false);
        assignOrderButton.setVisible(true);
    }

    private void fillAllOrders() {
        allOrdersList.getItems().clear();
        List<Destination> allDestinations = destinationHib.getAllDestinations();
        for (Destination destination : allDestinations) {
            allOrdersList.getItems().add(destination);
        }
    }

    private void resetDestinationTab() {
        orderAddress.setVisible(false);
        orderDeliveryDate.setVisible(false);
        orderAddress.setText("");
        orderDeliveryDate.setValue(null);
        trucksChoiceBoxOrders.setValue("");
    }

    public void deleteOrder() {
        Destination destination = (Destination) allOrdersList.getSelectionModel().getSelectedItem();
        destinationHib.removeDestination(destination);
        fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Truck Delete completed", "", "Order was successfully deleted");
        fillAllOrders();
    }

    public void assignCourierToOrder() {
        int id = ((Destination) allOrdersList.getSelectionModel().getSelectedItem()).getId();
        if (allOrdersList.getSelectionModel().getSelectedItem().getCourier() == null) {
            Destination destination = destinationHib.getDestinationByID(id);
            Courier courierFromChoiceBox = (Courier) courierChoiceBoxOrders.getValue();
            Courier courier = userHib.getCourierByID(courierFromChoiceBox.getId());
            destination.setCourier(courier);
            destinationHib.updateDestination(destination);
            fxUtils.alertMessage(Alert.AlertType.INFORMATION, "Courier Assigned Successfully", "", "Courier was successfully assigned to order");
        } else {
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Assign Error", "", "There's already assigned Courier to order");
        }
    }

    public void replyCommentData() {
        TreeItem<Comment> parentCommentTree = commentTree.getSelectionModel().getSelectedItem();
        Comment parentComment = commentHib.getCommentByID(commentTree.getSelectionModel().getSelectedItem().getValue().getId());
        Comment comment = new Comment(commentTitle.getText(), commentText.getText(), parentComment);
        commentHib.createComment(comment);
        parentCommentTree.getChildren().add(new TreeItem<>(comment));
    }

    public void setNextOrderStatus() {
        Destination destination = assignedOrdersList.getSelectionModel().getSelectedItem();
        switch (destination.getStatus()) {
            case PENDING:
                destination.setStatus(OrderStatus.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                destination.setStatus(OrderStatus.COMPLETED);
                destination.setDeliveryEndDate(LocalDate.now());
                break;
        }
        destinationHib.updateDestination(destination);
        fillAllDestinations();
    }

    public void setPreviousOrderStatus() {
        Destination destination = assignedOrdersList.getSelectionModel().getSelectedItem();
        switch (destination.getStatus()) {
            case IN_PROGRESS:
                destination.setStatus(OrderStatus.PENDING);
                break;
            case COMPLETED:
                destination.setStatus(OrderStatus.IN_PROGRESS);
                break;
        }
        destinationHib.updateDestination(destination);
        fillAllDestinations();
    }

    public void cancelOrder() {
        Destination destination = assignedOrdersList.getSelectionModel().getSelectedItem();
        destination.setStatus(OrderStatus.CANCELED);
        destinationHib.updateDestination(destination);
        fillAllDestinations();
    }

    public void filterByDeparture() {
        if (departureDateFilter.getValue() != null) {
            allOrdersList.getItems().clear();
            List<Destination> allDestinations = destinationHib.getAllDestinations();
            for (Destination destination : allDestinations) {
                if (destination.getDeliveryStartDate().isEqual(departureDateFilter.getValue())) {
                    allOrdersList.getItems().add(destination);
                }
            }
            assignedOrdersList.getItems().clear();
            if (user.getClass() == Manager.class) {
                List<Manager> managers = null;
                for (Destination destination : allDestinations) {
                    managers = userHib.getAllManagers();
                    for (Manager man : managers) {
                        if (man.getId() == user.getId() && destination.getDeliveryStartDate().isEqual(departureDateFilter.getValue())) {
                            assignedOrdersList.getItems().add(destination);
                        }
                    }
                }
            } else {
                for (Destination destination : allDestinations) {
                    if (destination.getCourier() != null && destination.getCourier().getId() == user.getId() && destination.getDeliveryStartDate().isEqual(departureDateFilter.getValue()))
                        assignedOrdersList.getItems().add(destination);
                }
            }
        }else{
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "", "Please choose departure filter value");
        }
    }

    public void filterByArrival() {
        if (arrivalDateFilter.getValue() != null) {
            allOrdersList.getItems().clear();
            List<Destination> allDestinations = destinationHib.getAllDestinations();
            for (Destination destination : allDestinations) {
                if (destination.getDeliveryStartDate().isEqual(arrivalDateFilter.getValue())) {
                    allOrdersList.getItems().add(destination);
                }
            }
            assignedOrdersList.getItems().clear();
            if (user.getClass() == Manager.class) {
                List<Manager> managers = null;
                for (Destination destination : allDestinations) {
                    managers = userHib.getAllManagers();
                    for (Manager man : managers) {
                        if (man.getId() == user.getId() && destination.getDeliveryStartDate().isEqual(arrivalDateFilter.getValue())) {
                            assignedOrdersList.getItems().add(destination);
                        }
                    }
                }
            } else {
                for (Destination destination : allDestinations) {
                    if (destination.getCourier() != null && destination.getCourier().getId() == user.getId() && destination.getDeliveryStartDate().isEqual(arrivalDateFilter.getValue()))
                        assignedOrdersList.getItems().add(destination);
                }
            }
        }else{
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "", "Please choose arrival filter value");
        }
    }

    public void filterByStatus() {
        if (statusFilter.getValue() != null) {
            allOrdersList.getItems().clear();
            List<Destination> allDestinations = destinationHib.getAllDestinations();
            for (Destination destination : allDestinations) {
                if (destination.getStatus().equals(statusFilter.getValue())) {
                    allOrdersList.getItems().add(destination);
                }
            }
            assignedOrdersList.getItems().clear();
            if (user.getClass() == Manager.class) {
                List<Manager> managers = null;
                for (Destination destination : allDestinations) {
                    managers = userHib.getAllManagers();
                    for (Manager man : managers) {
                        if (man.getId() == user.getId() && destination.getStatus().equals(statusFilter.getValue())) {
                            assignedOrdersList.getItems().add(destination);
                        }
                    }
                }
            } else {
                for (Destination destination : allDestinations) {
                    if (destination.getCourier() != null && destination.getCourier().getId() == user.getId() && destination.getStatus().equals(statusFilter.getValue()))
                        assignedOrdersList.getItems().add(destination);
                }
            }
        }else{
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "", "Please choose status filter value");
        }
    }

    public void filterByCourier() {
        if (courierChoiceBoxOrdersFilter.getSelectionModel().getSelectedItem() != null) {
            Courier courier = (Courier) courierChoiceBoxOrdersFilter.getSelectionModel().getSelectedItem();
            allOrdersList.getItems().clear();
            List<Destination> allDestinations = destinationHib.getAllDestinations();
            for (Destination destination : allDestinations) {
                if (destination.getCourier() != null && destination.getCourier().getId() == courier.getId()) {
                    allOrdersList.getItems().add(destination);
                }
            }
            assignedOrdersList.getItems().clear();
            if (user.getClass() == Manager.class) {
                List<Manager> managers = null;
                for (Destination destination : allDestinations) {
                    managers = userHib.getAllManagers();
                    for (Manager man : managers) {
                        if (destination.getCourier() != null && man.getId() == user.getId() && destination.getCourier().getId() == courier.getId()) {
                            assignedOrdersList.getItems().add(destination);
                        }
                    }
                }
            } else {
                for (Destination destination : allDestinations) {
                    if (destination.getCourier() != null && destination.getCourier().getId() == user.getId() && destination.getCourier().getId() == courier.getId())
                        assignedOrdersList.getItems().add(destination);
                }
            }
        }else{
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "", "Please choose courier filter value");
        }
    }

    public void setDefaultLists() {
        fillAllDestinations();
    }
}
