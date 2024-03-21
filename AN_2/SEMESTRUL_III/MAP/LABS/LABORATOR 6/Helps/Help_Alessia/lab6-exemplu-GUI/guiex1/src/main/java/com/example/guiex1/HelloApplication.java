package com.example.guiex1;

import com.example.guiex1.controller.LoginController;
import com.example.guiex1.controller.UtilizatorController;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.domain.UtilizatorValidator;
import com.example.guiex1.repository.Repository;
import com.example.guiex1.repository.dbrepo.UtilizatorDbRepository;
import com.example.guiex1.services.UtilizatorService;
import com.example.guiex1.services.config.ApplicationContext;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

//public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}

public class HelloApplication extends Application {

    Repository<Long, Utilizator> utilizatorRepository;
    UtilizatorService service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        String fileN = ApplicationContext.getPROPERTIES().getProperty("data.tasks.messageTask");
//        messageTaskRepository = new InFileMessageTaskRepository
//                (fileN, new MessageTaskValidator());
//        messageTaskService = new MessageTaskService(messageTaskRepository);
        //messageTaskService.getAll().forEach(System.out::println);

        System.out.println("Reading data from file");
        String username="postgres";
        String pasword="3.B1d1an";
        String url="jdbc:postgresql://localhost:5432/academic";
        utilizatorRepository = new UtilizatorDbRepository(url,username, pasword, new UtilizatorValidator());

//        utilizatorRepository.findAll().forEach(System.out::println);
        service =new UtilizatorService(utilizatorRepository);
//        initView(primaryStage);
        loginView(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();

    }

    private void loginView(Stage stage) throws IOException {
        /*stage.setTitle("Welcome to JavaFX!");
        GridPane gr=new GridPane();
        gr.setPadding(new Insets(20));
        gr.setAlignment(Pos.CENTER);
        gr.add(new Label("Username:"),0,0);
        gr.add(new Label("Password:"),0,1);
        gr.add(new TextField(),1,0);
        gr.add(new PasswordField(),1,1);

        //gr.add(new Button("LogIn"), 1, 2);
        Button btn = new Button("LogIn!");
        btn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                try {
                    initView(stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        gr.add(btn,1,2);

        Scene scene = new Scene(gr, 300, 200);
        stage.setTitle("Welcome to JavaFX!!");
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/Login.fxml"));
        GridPane Layout = fxmlLoader.load();
        stage.setScene(new Scene(Layout));

        LoginController userController = fxmlLoader.getController();
        userController.setUtilizatorService(service);
    }

    private void initView(Stage primaryStage) throws IOException {

       // FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(getClass().getResource("com/example/guiex1/views/UtilizatorView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/UtilizatorView.fxml"));

        AnchorPane userLayout = fxmlLoader.load();
        primaryStage.setScene(new Scene(userLayout));

        UtilizatorController userController = fxmlLoader.getController();
        userController.setUtilizatorService(service);
    }
}