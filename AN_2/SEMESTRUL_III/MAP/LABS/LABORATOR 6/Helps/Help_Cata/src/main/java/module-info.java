module socialnetwork.com.reteadesocializare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;

    opens socialnetwork.com.reteadesocializare to javafx.fxml;
    exports socialnetwork.com.reteadesocializare;
}