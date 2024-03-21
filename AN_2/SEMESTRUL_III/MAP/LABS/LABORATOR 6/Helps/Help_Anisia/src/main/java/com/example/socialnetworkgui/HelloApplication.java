package com.example.socialnetworkgui;

import com.example.socialnetworkgui.controller.LoginController;
import com.example.socialnetworkgui.controller.UserController;
import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.Pair;
import com.example.socialnetworkgui.domain.Request;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validators.FriendshipValidator;
import com.example.socialnetworkgui.domain.validators.RequestValidator;
import com.example.socialnetworkgui.domain.validators.UserValidator;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repo.Repository;
import com.example.socialnetworkgui.repo.db.FriendshipDBRepository;
import com.example.socialnetworkgui.repo.db.RequestDbRepo;
import com.example.socialnetworkgui.repo.db.UserDbRepo;
import com.example.socialnetworkgui.service.ServiceGUI;
import com.example.socialnetworkgui.service.ServiceRequest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    ServiceGUI service;
    ServiceRequest serviceRequest;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Validator<User> validator= new UserValidator();
        UserDbRepo uRepo= new UserDbRepo(validator,"jdbc:postgresql://localhost:5432/laborator", "postgres", "postgres");
        Validator<Friendship> valF= new FriendshipValidator();
        FriendshipDBRepository fRepo= new FriendshipDBRepository("jdbc:postgresql://localhost:5432/laborator", "postgres", "postgres", valF);
        Validator<Request> valR= new RequestValidator();
        RequestDbRepo rRepo= new RequestDbRepo("jdbc:postgresql://localhost:5432/laborator", "postgres", "postgres", valR);

        service= new ServiceGUI(uRepo, fRepo);
        serviceRequest= new ServiceRequest(uRepo, fRepo, rRepo);
        initView(primaryStage);
        primaryStage.setTitle("LogIn page");
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/userView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        primaryStage.setScene(new Scene(userLayout));

        //UserController userController = fxmlLoader.getController();
        LoginController loginController= fxmlLoader.getController();
        //userController.setService(service);
        loginController.setServiceGUI(service, serviceRequest);
    }
}