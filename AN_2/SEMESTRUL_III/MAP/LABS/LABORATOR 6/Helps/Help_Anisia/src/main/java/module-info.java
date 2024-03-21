module com.example.socialnetworkgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.socialnetworkgui to javafx.fxml;
    exports com.example.socialnetworkgui;
    exports com.example.socialnetworkgui.controller;
    exports com.example.socialnetworkgui.domain;
    opens com.example.socialnetworkgui.controller to javafx.fxml;
}