package com.example.socialnetwork;

import com.example.socialnetwork.controller.UserController;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.domain.validators.FriendshipValidator;
import com.example.socialnetwork.domain.validators.UserValidator;
import com.example.socialnetwork.domain.validators.Validator;
import com.example.socialnetwork.repository.Repository0;
import com.example.socialnetwork.repository.database.FriendshipDBRepository;
import com.example.socialnetwork.repository.database.UserDBRepository;
import com.example.socialnetwork.service.Service;
import com.example.socialnetwork.service.ServiceNetwork;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    UserDBRepository repoUser;
    FriendshipDBRepository repoFriendship;
    Service service;

    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/academic";
        repoUser = new UserDBRepository(url, "postgres", "postgres");
        repoFriendship = new FriendshipDBRepository(url, "postgres", "postgres");
        service = new Service(repoUser, repoFriendship);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/users.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Select user");
        stage.setScene(scene);
        stage.show();

        UserController userController = fxmlLoader.getController();
        userController.setService(service);
    }

    public static void main(String[] args) {
        launch();
    }
}