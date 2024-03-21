package com.example.tema_bun;

import com.example.tema_bun.controller.StartController;
import com.example.tema_bun.repo.CopilRepository;
import com.example.tema_bun.repo.InscriereRepository;
import com.example.tema_bun.repo.ProbaRepository;
import com.example.tema_bun.repo.UtilizatorRepository;
import com.example.tema_bun.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    private Service service;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Properties props=new Properties();
        try {
            props.load(new FileReader("D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\LAB_3\\mpp-proiect-java-aabgx\\tema_bun\\bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        this.service = new Service(new UtilizatorRepository(props),
                new CopilRepository(props),
                new ProbaRepository(props),
                new InscriereRepository(props));

        primaryStage.setTitle("START PAGE");
        startView(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
//        System.out.println("DA");
//
//        Properties props=new Properties();
//        try {
//            props.load(new FileReader("D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\LAB_3\\mpp-proiect-java-aabgx\\tema_bun\\bd.config"));
//        } catch (IOException e) {
//            System.out.println("Cannot find bd.config "+e);
//        }

//        Proba p = new Proba("altceva");
//        ProbaRepository probaRepository = new ProbaRepository(props);
//        try {
//            probaRepository.adauga(p);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }

//        Proba p1=new Proba(100,"altceva");
//        //probaRepository.sterge(p1);
//        List<Proba> probe = probaRepository.getAll();
//        Proba p2 = probaRepository.cautaId(p1.getId());
//        System.out.println(p1);
//        System.out.println((new File(".")).getAbsolutePath());
        launch();
    }

    private void startView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/startView.fxml"));
        AnchorPane Layout = fxmlLoader.load();
        stage.setScene(new Scene(Layout));

        StartController startController = fxmlLoader.getController();
        startController.setService(service);
    }
}