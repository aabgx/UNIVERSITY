package com.example.tema_bun;

import com.example.tema_bun.controller.StartController;
import com.example.tema_bun.objectProtocol.ServicesRpcProxy;
import com.example.tema_bun.protobuff.ProtobuffProxy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartRpcClientFX extends Application {

    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("IN CLIENT START");
        Properties clientProps = new Properties();
        try{
            clientProps.load(new FileReader("D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\JAVA_CSHARP_TEMA\\mpp-proiect-java-aabgx\\tema_bun\\ClientFX\\src\\main\\resources\\appclient.properties"));
            System.out.println("Client props set.");
            clientProps.list(System.out);
        }catch (IOException e){
            System.err.println("CANNOT FIND appclient.properties" + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;

        try{
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        }catch (NumberFormatException ex){
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        //IServices server = new ServicesRpcProxy(serverIP, serverPort);
        IServices server = new ProtobuffProxy(serverIP, serverPort);

//        FXMLLoader loader=new FXMLLoader();
//        loader.setLocation(getClass().getResource("/loginView.fxml"));
//        AnchorPane root=loader.load();
//
//        LoginCreareController ctrl = loader.getController();
//        ctrl.setService(server);
//
//        primaryStage.setScene(new Scene(root));
//        primaryStage.setTitle("Login");
//        primaryStage.show();
        primaryStage.setTitle("START PAGE");

        FXMLLoader fxmlLoader = new FXMLLoader(StartRpcClientFX.class.getResource("/startView.fxml"));
        AnchorPane Layout = fxmlLoader.load();
        primaryStage.setScene(new Scene(Layout));

        StartController startController = fxmlLoader.getController();
        startController.setService(server);

        primaryStage.show();
    }
}

