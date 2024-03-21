package com.example.tema_bun.objectProtocol;

import com.example.tema_bun.IObserver;
import com.example.tema_bun.IServices;
import com.example.tema_bun.Inscriere;
import com.example.tema_bun.Utilizator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientRpcWorker implements Runnable, IObserver {

    private IServices server;

    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientRpcWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();

            input = new ObjectInputStream(connection.getInputStream());
            connected = true;

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (connected){
            try{
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if (response!=null){
                    sendResponse((Response) response);
                }
            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try{
            input.close();
            output.close();
            connection.close();
        }catch (IOException e){
            System.out.println("Error : " + e);
        }

    }


    private Response handleRequest(Request request){
        Response response = null;
        switch (request.type()) {
            case LOGIN: {
                response = solveLogin(request);
                break;
            }
            case LOGOUT: {
                response = solveLogout(request);
                break;
            }
            case CREARE_CONT:
            {
                response=solveAccount(request);
                break;
            }
            case GET_NUMBER_OF_PARTICIPANTS: {
                System.out.println("macar aici intra?");
                response=solveCreareVectorCnt(request);
                break;
            }
            case GET_PROBA_CATEGORIE_PARTICIPANTS:
            {
                response=solveGetProbaCategorie(request);
                break;
            }
            case INSCRIERE:
            {
                response=solveInscriere(request);
                break;
            }
//            case ADD_PARTICIPANT:
//            {
//                response=solveInscriereParticipant(request);
//                break;
//            }
        }
        return response;
    }



    private Response solveLogin(Request request) {
//        System.out.println("Login request ..." + request.type());
        Utilizator utilizator= (Utilizator) request.data();
        String username= utilizator.getUsername();
        String password = utilizator.getParola();

        try {
            Boolean loggedIn = server.login(username, password, this);
            return new Response.Builder().type(ResponseType.OK).data(loggedIn).build();
        } catch (Exception e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response solveAccount(Request request) {
//        System.out.println("Login request ..." + request.type());
        Utilizator utilizator= (Utilizator) request.data();
        String username= utilizator.getUsername();
        String password = utilizator.getParola();

        try {
            server.createAccount(username, password);
            Boolean loggedIn = server.login(username, password, this);
            return new Response.Builder().type(ResponseType.OK).data(loggedIn).build();
        } catch (Exception e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response solveLogout(Request request) {
//        System.out.println("Logout request");
        Utilizator utilizator= (Utilizator) request.data();
        String email= utilizator.getUsername();
        try {
            server.logout(email);
            connected = false;
            return new Response.Builder().type(ResponseType.OK).data(true).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response solveInscriere(Request request) {
        String[] inscriereData = (String[]) request.data();

        String nume = inscriereData[0];
        Integer varsta = Integer.parseInt(inscriereData[1]);

/*        ObjectMapper objectMapper = new ObjectMapper();
        List<String> probe = new ArrayList<>();
        try {
            probe = objectMapper.readValue(inscriereData[2], new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }*/
        Gson gson = new Gson();
        List<String> probe = new ArrayList<>();
        try {
            probe = gson.fromJson(inscriereData[2], new TypeToken<List<String>>() {}.getType());
        } catch (JsonSyntaxException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

        String usernameCurent=inscriereData[3];

        try {
            server.adaugaCopilInscriere(nume, varsta, probe,usernameCurent);
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    private Response solveCreareVectorCnt(Request request)
    {
//        System.out.println("Ger number of participants request");
        try
        {
/*            System.out.println("Pas 2");*/
            List<Integer> result=server.creareVectorCnt();
//            List<Integer> result=new ArrayList<>();
//            result.add(1);
            return new Response.Builder().type(ResponseType.OK).data(result).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response solveGetProbaCategorie(Request request)
    {
//        System.out.println("Get all participants request");

        try {
            String proba_categorie=request.data().toString();
            String[] parts=proba_categorie.split(" ");
            String proba=parts[0],categorie=parts[1];
            List<String> participanti=server.JoinInscrieriCopii(proba,categorie);
            return new Response.Builder().type(ResponseType.OK).data(participanti).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response : " + response);
        synchronized (output){
            output.writeObject(response);
            output.flush();
        }
    }

    @Override
    public void participantInscris() {
        Response resp = new Response.Builder().type(ResponseType.UPDATE).build();
        System.out.println("Participant added");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
