package com.example.tema_bun.controller;

import com.example.tema_bun.HelloApplication;
import com.example.tema_bun.domain.Utilizator;
import com.example.tema_bun.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginCreareController {
    private Service service;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField parolaField;

    public void setService(Service service) {
        this.service = service;
        //initModel();
    }

    public void handleLogin(ActionEvent actionEvent){
        try{
            String username = usernameField.getText();
            String parola = parolaField.getText();
            Utilizator entity = service.getByAccount(username,parola);

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/utilizatorView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            UtilizatorController UtilizatorController = fxmlLoader.getController();
            UtilizatorController.setService(service);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch(Exception e) {
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public void handleCreare(ActionEvent actionEvent){
        try{
            String username = usernameField.getText();
            String parola = parolaField.getText();
            service.createAccount(username,parola);

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/utilizatorView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            UtilizatorController UtilizatorController = fxmlLoader.getController();
            UtilizatorController.setService(service);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch(Exception e) {
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }
}