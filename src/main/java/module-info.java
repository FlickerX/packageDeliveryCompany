module delivery.kursinis {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires fontawesomefx;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires mysql.connector.java;


    opens delivery.kursinis to javafx.fxml;
    exports delivery.kursinis;
    exports delivery.kursinis.fxContorllers to javafx.fxml;
    opens delivery.kursinis.fxContorllers to javafx.fxml;
    opens delivery.kursinis.model to org.hibernate.orm.core;
}