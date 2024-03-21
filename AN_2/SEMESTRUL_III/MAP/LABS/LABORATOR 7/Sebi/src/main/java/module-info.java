module com.example.myfacebook {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.myfacebook to javafx.fxml;
    opens com.example.myfacebook.domain to javafx.base;
    exports com.example.myfacebook;
    exports com.example.myfacebook.controller;
    opens com.example.myfacebook.controller to javafx.fxml;
}