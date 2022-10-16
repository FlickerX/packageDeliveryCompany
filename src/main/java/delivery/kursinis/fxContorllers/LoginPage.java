package delivery.kursinis.fxContorllers;

import delivery.kursinis.HelloApplication;
import delivery.kursinis.hibernate.UserHib;
import delivery.kursinis.model.User;
import delivery.kursinis.utils.DatabaseOperations;
import delivery.kursinis.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.dialect.Database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginPage {
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField loginField;
    @FXML
    public Button login_btn;
    @FXML
    public CheckBox isManagerCheckbox;
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PackageDeliveryCompany");
    private UserHib userHib = new UserHib(entityManagerFactory);
    private FxUtils fxUtils = new FxUtils();

    public void login() throws IOException {
        User user = userHib.getUserByLoginData(loginField.getText(), passwordField.getText(), isManagerCheckbox.isSelected());
        if (user != null){
            openMainWindow(user);
        }else{
            fxUtils.alertMessage(Alert.AlertType.ERROR, "Log-in error", "Database Error", "There's no user with such username");
        }

    }

    private void openMainWindow(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        Parent parent = fxmlLoader.load();
        Main mainPage = fxmlLoader.getController();
        mainPage.setData(entityManagerFactory, user);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("Package Delivery Company");
        stage.setScene(scene);
        stage.show();
    }
}
