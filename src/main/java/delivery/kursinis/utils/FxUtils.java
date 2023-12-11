package delivery.kursinis.utils;

import delivery.kursinis.Enums.OrderStatus;
import delivery.kursinis.model.Cargo;
import delivery.kursinis.model.Destination;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class FxUtils {
    public void alertMessage(Alert.AlertType alertType, String title, String headerText, String alertMessage){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
    public boolean areAllCourierFieldsFilled(String username, String password, String name, String surname, String phoneNo, String salary,
                                             String driverLicence, String medicalCertificate, LocalDate birthday){
        return username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || phoneNo.isEmpty()
                || salary.isEmpty() || driverLicence.isEmpty() || medicalCertificate.isEmpty() || birthday == null;
    }
    public boolean areAllManagerFieldsFilled(String username, String password, String name, String surname, String phoneNo, String salary,
                                             LocalDate birthday){
        return username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || phoneNo.isEmpty()
                || salary.isEmpty() || birthday == null;
    }
    public boolean areAllTruckFieldsFilled(String mark, String model, String horsePower, String engineLiters, String color){ // TODO: Make sure to change String to integerts and double
        return mark.isEmpty() || model.isEmpty() || horsePower.isEmpty() || engineLiters.isEmpty() || color.isEmpty();
    }
    public boolean areCheckpointFieldInputed(String checkpoint, LocalDate checkpointDate){ // TODO: Make sure to change String to integerts and double
        return checkpoint.isEmpty() || checkpointDate == null;
    }
    public boolean areCargoFieldsFilled(String cargoNaming, String weight){
        return cargoNaming.isEmpty() || weight.isEmpty();
    }
    public boolean areDestinationFieldsFilled(String address, LocalDate requestedDeliveryDate, LocalDate currentDay, OrderStatus status){
        return address.isEmpty() || requestedDeliveryDate == null || currentDay == null || status == null;
    }
    public boolean areForumFieldsFilled(String title, String description){
        return title.isEmpty() || description == null || description == null;
    }
    public boolean isPositiveInteger(String text){
        int number;
        try{
            number = Integer.parseInt(text);
            if (number < 0){
                alertMessage(Alert.AlertType.ERROR, "Field Type Warning", "Type Error", "Field can't be negative");
                return false;
            }
        } catch (NumberFormatException e){
            alertMessage(Alert.AlertType.ERROR, "Field Type Warning", "Type Error", "Field is not Integer type");
            return false;
        }
        return true;
    }
    public boolean isPositiveDouble(String text){
        double number;
        try{
            number = Double.parseDouble(text);
            if (number < 0){
                alertMessage(Alert.AlertType.ERROR, "Field Type Warning", "Type Error", "Field can't be negative");
                return false;
            }

        } catch (NumberFormatException e){
            alertMessage(Alert.AlertType.ERROR, "Field Type Warning", "Type Error", "Field is not Double type");
            return false;
        }
        return true;
    }
    public boolean isCargoListEmpty(List<Cargo> cargos){
        return cargos.size() == 0 ? false : true;
    }
}
