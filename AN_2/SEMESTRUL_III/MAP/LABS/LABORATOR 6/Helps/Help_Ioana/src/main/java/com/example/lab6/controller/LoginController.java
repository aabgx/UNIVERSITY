package com.example.lab6.controller;

import com.example.lab6.HelloApplication;
import com.example.lab6.domain.User;
import com.example.lab6.events.UserChangeEvent;
import com.example.lab6.observer.Observer;
import com.example.lab6.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LoginController implements Observer<UserChangeEvent>
{
    @FXML
    private TableColumn tableColumnPasswordReq;
    @FXML
    private TableColumn tableColumnNameReq;
    @FXML
    private TableView tableFriendReqView;
    Service serv;
    List<User> users;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private User userCurent;
    public void setUserServ(Service serv)
    {
        this.serv = serv;
        serv.addObserver(this);
    }
    @FXML
    public void initialize() {}
    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        users = serv.getUserList();
        String valoareNume = String.valueOf(usernameField.getCharacters());
        String valoareParola = String.valueOf(passwordField.getCharacters());
        User utAux = new User(valoareNume,valoareParola);
        for(User ut:users)
        {
            if(ut.equals(utAux))
            {
                userCurent=ut;
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/UserView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 520, 600);
                Stage stage = new Stage();
                stage.setTitle("New Window");
                stage.setScene(scene);
                stage.show();
                UserController userController = fxmlLoader.getController();
                userController.setUserCurent(userCurent);
                userController.setUserServ(this.serv);
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
    }
    public void handleSignUpButton(ActionEvent actionEvent) throws IOException {
        String valoareNume = usernameField.getText();
        String valoareParola = passwordField.getText();

        try{
            serv.addUserService(valoareNume,valoareParola);
            users = serv.getUserList();
            List<User> lista = serv.getUserList();
            User utAux = new User(valoareNume,valoareParola);
            for(User ut:users)
            {
                if(ut.equals(utAux))
                {
                    userCurent=ut;
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/UserView.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 520, 600);
                    Stage stage = new Stage();
                    stage.setTitle("New Window");
                    stage.setScene(scene);
                    stage.show();
                    UserController userController = fxmlLoader.getController();
                    userController.setUserCurent(userCurent);
                    userController.setUserServ(this.serv);
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                }
            }
        }
        catch(RuntimeException e)
        {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public void update(UserChangeEvent userChangeEvent)
    {
    }
}
