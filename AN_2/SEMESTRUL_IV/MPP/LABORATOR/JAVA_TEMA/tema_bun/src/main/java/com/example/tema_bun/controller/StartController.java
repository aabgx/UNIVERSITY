package com.example.tema_bun.controller;

import com.example.tema_bun.HelloApplication;
import com.example.tema_bun.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartController {
    private Service service;
    public void setService(Service service) {
        this.service = service;
    }

    public void handleLogin(ActionEvent actionEvent){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/loginView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            LoginCreareController loginCreareController = fxmlLoader.getController();
            loginCreareController.setService(service);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void handleCreare(ActionEvent actionEvent){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/creareView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            LoginCreareController loginCreareController = fxmlLoader.getController();
            loginCreareController.setService(service);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
