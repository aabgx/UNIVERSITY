module com.example.sapt10gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com to javafx.fxml;
    exports com;
    exports com.controller;
    exports com.domain;
    opens com.controller to javafx.fxml;
}