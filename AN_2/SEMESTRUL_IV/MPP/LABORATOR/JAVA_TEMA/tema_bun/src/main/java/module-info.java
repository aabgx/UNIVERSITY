module com.example.tema_bun {
    requires java.sql;

    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires javafx.graphics;


    opens com.example.tema_bun to javafx.fxml;
    exports com.example.tema_bun;
    exports com.example.tema_bun.service;
    opens com.example.tema_bun.service to javafx.fxml;

    exports com.example.tema_bun.controller;
    opens com.example.tema_bun.controller to javafx.fxml;

    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
}