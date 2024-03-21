package com.example.tema_bun.objectProtocol;

import com.example.tema_bun.IObserver;
import com.example.tema_bun.IServices;
import com.example.tema_bun.Utilizator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ServicesRpcProxy implements IServices {
    private String host;
    private int port;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    private String username = null;
    private IObserver client = null;

    public ServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingDeque<Response>();
    }


    public boolean login(String username, String parola, IObserver client) throws Exception {
        initializeConnection();
        Utilizator utilizator=new Utilizator(username,parola);
        Request request = new Request.Builder().type(RequestType.LOGIN).data(utilizator).build();
        sendRequest(request);
        Response response = readResponse();

        Boolean loggedIn = false;

        if (response.type() == ResponseType.OK) {
            loggedIn = (Boolean) response.data();
            if (loggedIn) {
                this.client = client;
                return true;
            }

        } else if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            throw new Exception(err);
        }
        return loggedIn;
    }

    public void logout(String notused) throws Exception {
//        initializeConnection();
        Utilizator utilizator=new Utilizator(notused,null);
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(utilizator).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        username = null;
        client = null;
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
    }

    public Utilizator getByAccount(String username, String parola){
        return null;
    }

    @Override
    public void createAccount(String usernamee, String parola) throws Exception{
        initializeConnection();
        Utilizator u=new Utilizator(usernamee,parola);
        Request req = new Request.Builder().type(RequestType.CREARE_CONT).data(u).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
    }

    @Override
    public List<Integer> creareVectorCnt() throws Exception{
        initializeConnection();

        Request req = new Request.Builder().type(RequestType.GET_NUMBER_OF_PARTICIPANTS).build();
        sendRequest(req);
        Response response = readResponse();
            if (response.type() == ResponseType.ERROR) {
                String err = response.data().toString();
                throw new Exception(err);
            }

            return (List<Integer>) response.data();

    }

    public List<String> JoinInscrieriCopii(String proba,String categorie) throws Exception{
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.GET_PROBA_CATEGORIE_PARTICIPANTS).data(proba+" "+categorie).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
        List<String> rez=(List<String>)response.data();
        return (List<String>)response.data();
    }

    public void adaugaCopilInscriere(String nume, Integer varsta, List<String> probe,String usernameCurent) throws Exception
    {
//        initializeConnection();
        ObjectMapper objectMapper = new ObjectMapper();
        String[] inscriereData = new String[]{nume, varsta.toString(), objectMapper.writeValueAsString(probe),usernameCurent};

//        System.out.println(inscriereData[2]);

//        String data = objectMapper.writeValueAsString(inscriereData);

        Request req = new Request.Builder().type(RequestType.INSCRIERE).data(inscriereData).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
    }


    private void sendRequest(Request request) throws Exception {
        try {
            output.writeObject(request);
            output.flush();
        } catch (Exception e) {
            System.out.println("intra pe eroare");
            throw new Exception("Error sending object "+e);
        }
    }

    private Response readResponse() throws Exception {
        Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }



    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);

                    if (((Response)response).type() == ResponseType.UPDATE)
                        client.participantInscris();
                    else {
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

}
