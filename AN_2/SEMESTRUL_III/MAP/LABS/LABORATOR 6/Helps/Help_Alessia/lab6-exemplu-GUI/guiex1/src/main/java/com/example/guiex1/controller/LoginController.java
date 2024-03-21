package com.example.guiex1.controller;

import com.example.guiex1.HelloApplication;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.services.UtilizatorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginController {
    UtilizatorService service;

    @FXML
    private Text textResponse;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void setUtilizatorService(UtilizatorService service) {
        this.service = service;
        //service.addObserver(this);
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent actionEvent) {
//        textResponse.setText("Login button was pressed!");
        Utilizator u = new Utilizator(usernameField.getText(),passwordField.getText());
        if(service.findUtilizator(u.getFirstName(),u.getLastName())!=null) {
            textResponse.setText("Login succesful!");
            try{
                //URL fxmlLocation = HelloApplication.class.getResource("views/UtilizatorView.fxml");
                /*if(fxmlLocation == null)
                    textResponse.setText("NULL URL!");*/
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/UtilizatorView.fxml"));
                //fxmlLoader.setLocation();
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                UtilizatorController userController = fxmlLoader.getController();
                userController.setUtilizatorService(service);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            } catch(Exception e) {
                e.printStackTrace();
            }

        }
        else
            textResponse.setText("Login failed!");

    }
}
