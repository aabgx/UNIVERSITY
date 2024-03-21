package com;

import com.controller.LoginController;
import com.controller.StartController;
import com.domain.Message;
import com.domain.Prietenie;
import com.domain.Utilizator;
import com.repo.MessageDBRepository;
import com.repo.PrietenieDBRepository;
import com.repo.UtilizatorDBRepository;
import com.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    Service service;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        repo.Repository<String, Utilizator> utilizatorRepository = new UtilizatorDBRepository("jdbc:postgresql://localhost:5432/UP","postgres","ubb22");
        repo.Repository<String, Prietenie> prietenieRepository = new PrietenieDBRepository("jdbc:postgresql://localhost:5432/UP","postgres","ubb22");
        repo.Repository<String, Message> messageRepository = new MessageDBRepository("jdbc:postgresql://localhost:5432/UP","postgres","ubb22");
        this.service = new Service(utilizatorRepository, prietenieRepository,messageRepository);

        primaryStage.setTitle("START PAGE");
        startView(primaryStage);
        primaryStage.show();
    }

    private void startView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/startView.fxml"));
        AnchorPane Layout = fxmlLoader.load();
        stage.setScene(new Scene(Layout));

        StartController startController = fxmlLoader.getController();
        startController.setService(service);
    }
}
