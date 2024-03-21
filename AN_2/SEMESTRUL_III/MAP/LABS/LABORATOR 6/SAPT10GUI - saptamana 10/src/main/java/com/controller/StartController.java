package com.controller;

import com.HelloApplication;
import com.domain.Prietenie;
import com.domain.Utilizator;
import com.repo.PrietenieDBRepository;
import com.repo.UtilizatorDBRepository;
import com.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartController {
    Service service;

    public void setService(Service service) {
        this.service = service;
    }
    @FXML
    public void handleLoginButtonAction(ActionEvent actionEvent){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/loginView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("LOGIN PAGE");
            LoginController loginController = fxmlLoader.getController();

            loginController.setService(service);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide(); //ca sa ascunda prima fereastra
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleSinginButtonAction(ActionEvent actionEvent){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/singinView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("SIGN IN PAGE");
            LoginController loginController = fxmlLoader.getController();

            loginController.setService(service);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide(); //ca sa ascunda prima fereastra
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
