package com.example.lab6;

import com.example.lab6.controller.LoginController;
import com.example.lab6.controller.UserController;
import com.example.lab6.domain.User;
import com.example.lab6.repo.FriendRepositoryDatabase;
import com.example.lab6.repo.Repository;
import com.example.lab6.repo.UserRepositoryDatabase;
import com.example.lab6.service.Service;
import com.example.lab6.config.ApplicationContext;
import com.example.lab6.validator.Validator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    UserRepositoryDatabase userRepo = new UserRepositoryDatabase("jdbc:postgresql://localhost:5432/Lab4","postgres","postgres");
    FriendRepositoryDatabase friendRepo = new FriendRepositoryDatabase("jdbc:postgresql://localhost:5432/Lab4","postgres","postgres",userRepo);

    Validator validator=new Validator();
    Service serv = new Service(userRepo,validator,friendRepo);

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        initView(stage);
        stage.setWidth(800);
        stage.show();
    }
    private void initView(Stage primaryStage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/UserView.fxml"));
        AnchorPane userLayout = (AnchorPane) fxmlLoader.load();
        primaryStage.setScene(new Scene(userLayout));

        UserController loginController = fxmlLoader.getController();
        User user = new User("Ioana Baciu","qweASD3");
        user.setId(1);
        loginController.setUserCurent(user);
        loginController.setUserServ(serv);
    }
}