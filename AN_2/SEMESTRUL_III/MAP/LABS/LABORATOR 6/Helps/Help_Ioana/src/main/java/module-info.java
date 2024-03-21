module com.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.lab6 to javafx.fxml;
    opens com.example.lab6.controller to javafx.fxml;

    exports com.example.lab6;
    exports com.example.lab6.domain;
    exports com.example.lab6.controller;
}