module com.example.socialnetworkfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.socialnetwork to javafx.fxml;
    opens com.example.socialnetwork.controller to javafx.fxml;
    opens com.example.socialnetwork.domain to javafx.base;

    exports com.example.socialnetwork;
}