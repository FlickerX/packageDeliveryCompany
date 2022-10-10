package delivery.kursinis.utils;

import javafx.scene.control.Alert;

import java.time.LocalDate;

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
}
