package com.example.myfacebook;

import com.example.myfacebook.controller.UserController;
import com.example.myfacebook.domain.validators.UserValidator;
import com.example.myfacebook.repo.db.FriendshipDBRepo;
import com.example.myfacebook.repo.db.MessageDBRepo;
import com.example.myfacebook.repo.db.RequestDBRepo;
import com.example.myfacebook.repo.db.UserDBRepo;
import com.example.myfacebook.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


public class HelloApplication extends Application {
    UserDBRepo userDBRepo;
    FriendshipDBRepo friendshipDBRepo;
    Service service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        String username = "postgres";
        String password = "vivalavega";
        String url = "jdbc:postgresql://localhost:5432/community";
        UserDBRepo userDBRepo = new UserDBRepo(url, username, password);
        FriendshipDBRepo friendshipDBRepo = new FriendshipDBRepo(url, username, password);
        RequestDBRepo requestDBRepo = new RequestDBRepo(url, username, password);
        MessageDBRepo messageDBRepo = new MessageDBRepo(url, username, password);
        UserValidator validator = new UserValidator();


        service = new Service(userDBRepo, friendshipDBRepo, requestDBRepo, messageDBRepo, validator);
        initView(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();


    }

    private void initView(Stage primaryStage) throws IOException {

        // FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(getClass().getResource("com/example/guiex1/views/UtilizatorView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("communityUI.fxml"));

        VBox userLayout = fxmlLoader.load();
        primaryStage.setScene(new Scene(userLayout));

        UserController userController = fxmlLoader.getController();
        userController.setService(service);

    }

}