package com.example.myfacebook.controller;
import com.example.myfacebook.HelloApplication;
import com.example.myfacebook.domain.User;
import com.example.myfacebook.service.Service;
import com.example.myfacebook.utils.events.UserEntityChangeEvent;
import com.example.myfacebook.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.List;

public class UserController implements Observer<UserEntityChangeEvent> {
    Service service;
    ObservableList<User> model = FXCollections.observableArrayList();

    //pagina login
    @FXML
    public TextField userNameField;
    @FXML
    public TextField passwordField;
    @FXML
    private Button LogInButton;



    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
    }

    public Stage showPage(User user) {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("communityUserUI.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(
                    new Scene(loader.load())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserPageController controller = loader.getController();
        controller.initData(user);
        controller.setService(service);
        stage.show();
        return stage;
    }

    @FXML
    protected void onLogInButtonClick() {
        boolean found = false;
        boolean valid = false;
        String username = userNameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            valid = true;
            List<User> users = service.getListUsers();
            for (User u : users) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    //deschid fereastra noua cu user
                    showPage(u);
                    found = true;
                }
            }
        }
        if (!valid || !found) {
            System.out.println(0);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Invalid username or password!");

        }
    }


    @Override
    public void update(UserEntityChangeEvent userEntityChangeEvent) {

    }


}
