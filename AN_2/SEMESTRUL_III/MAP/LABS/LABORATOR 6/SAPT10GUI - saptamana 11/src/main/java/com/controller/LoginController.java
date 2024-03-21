package com.controller;

import com.HelloApplication;
import com.domain.Utilizator;
import com.domain.validators.ValidationException;
import com.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
    Service service;
    @FXML
    private TextField emailField;

    @FXML
    private TextField parolaField;
    @FXML
    private TextField numeField;
    @FXML
    private TextField prenumeField;


    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    public void handleLoginButtonAction(ActionEvent actionEvent) {
        if(service.conectareUtilizator(emailField.getText(),parolaField.getText())!=false) {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/utilizatorView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                UtilizatorController utilizatorController = fxmlLoader.getController();
                utilizatorController.setService(service);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        else{
            MessageAlert.showErrorMessage(null, "NU EXISTA UN UTILIZATOR CU ACEST EMAIL SI PAROLA!");
        }
    }

    @FXML
    public void handleSinginButtonAction(ActionEvent actionEvent) {
        if(service.creareUtilizator(numeField.getText(),prenumeField.getText(),emailField.getText(),parolaField.getText())==true) {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/utilizatorView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                UtilizatorController utilizatorController = fxmlLoader.getController();
                utilizatorController.setService(service);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            } catch(Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else{
            MessageAlert.showErrorMessage(null, "EXISTA DEJA UN UTILIZATOR CU ACEST EMAIL!");
        }

    }
}
