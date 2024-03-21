package com.example.tema_bun;

import com.example.tema_bun.servers.AbstractServer;
import com.example.tema_bun.servers.RpcConcurrentServer;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;

    public static void main(String[] args){
        Properties serverProps=new Properties();
        try {
//            serverProps.load(new FileReader("D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\LAB_3\\mpp-proiect-java-aabgx\\tema_bun\\Server\\src\\main\\resources\\server.properties"));
            serverProps.load(new FileReader("D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\JAVA_CSHARP_TEMA\\mpp-proiect-java-aabgx\\tema_bun\\Server\\src\\main\\resources\\server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        UtilizatorRepository utilizatorRepository=new UtilizatorRepository(serverProps);
        CopilRepository copilRepository=new CopilRepository(serverProps);
        ProbaRepository probaRepository=new ProbaRepository(serverProps);
        InscriereRepository inscriereRepository=new InscriereRepository(serverProps);


        Service service = new Service(utilizatorRepository,copilRepository,probaRepository,inscriereRepository);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new RpcConcurrentServer(serverPort, service);
        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Error starting the server" + e.getMessage());
        } finally {
            try {
                server.stop();
            } catch (Exception e) {
                System.err.println("Error stopping server " + e.getMessage());
            }
        }
    }
}
