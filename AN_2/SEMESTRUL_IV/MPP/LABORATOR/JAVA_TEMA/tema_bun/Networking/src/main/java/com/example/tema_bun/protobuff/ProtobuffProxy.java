package com.example.tema_bun.protobuff;

import com.example.tema_bun.IObserver;
import com.example.tema_bun.IServices;
import com.example.tema_bun.Utilizator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProtobuffProxy implements IServices {
    private String host;
    private int port;
    private InputStream input;  //
    private OutputStream output; //
    private Socket connection;
    private BlockingQueue<AppProtobuffs.ResponseP> qresponses;
    private volatile boolean finished;
    private IObserver client = null;

    public ProtobuffProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingDeque<AppProtobuffs.ResponseP>();
    }

    @Override
    public boolean login(String username, String parola, IObserver client) throws Exception {
        initializeConnection();
        Utilizator u=new Utilizator(username,parola);
        //Request request = new Request.Builder().type(RequestType.LOGIN).data(angajat).build();
        AppProtobuffs.RequestP request=ProtoUtils.createLoginRequest(u);
        sendRequest(request);
        AppProtobuffs.ResponseP response = readResponse();
        if (response.getType()==AppProtobuffs.ResponseP.Type.OK) {
            if(response.getBoolean())
            {
                this.client = client;
                return true;
            }
            else
                return false;
        } else if (response.getType() ==  AppProtobuffs.ResponseP.Type.ERROR) {
            String err = response.getMessage();
            closeConnection();
            throw new Exception(err);
        }
        return false;
    }

    @Override
    public void logout(String email) throws Exception {
        Utilizator u=new Utilizator(email,"password");
        //Request req = new Request.Builder().type(RequestType.LOGOUT).data(angajat).build();
        AppProtobuffs.RequestP request=ProtoUtils.createLogoutRequest(u);
        sendRequest(request);
        AppProtobuffs.ResponseP response = readResponse();
        closeConnection();
        client = null;
        if (response.getType() == AppProtobuffs.ResponseP.Type.ERROR) {
            String err = response.getMessage();
            throw new Exception(err);
        }
    }

    @Override
    public Utilizator getByAccount(String username, String parola) {
        return null;
    }

    @Override
    public void createAccount(String username, String parola) throws IOException, Exception {

    }

    @Override
    public List<Integer> creareVectorCnt() throws Exception {
        //Request req = new Request.Builder().type(RequestType.GET_NUMBER_OF_PARTICIPANTS).build();
        AppProtobuffs.RequestP request=ProtoUtils.createGetNumberOfParticipantsRequest();
        sendRequest(request);
        System.out.println("am trimis request");
        AppProtobuffs.ResponseP response=readResponse();
        System.out.println("am primit raspuns");
        if (response.getType() == AppProtobuffs.ResponseP.Type.ERROR) {
            String err = response.getMessage();
            throw new Exception(err);
        }
        return ProtoUtils.getVectorCntFromResponse(response);
    }

/*    @Override
    public void adaugaCopilInscriere(String nume, Integer varsta, List<String> probe,String usernameCurent) throws Exception {
        *//*ObjectMapper objectMapper = new ObjectMapper();
        String[] inscriereData = new String[]{objectMapper.writeValueAsString(probe)};*//*

        Gson gson = new Gson();
        String inscriereData = gson.toJson(probe);

        //Request req = new Request.Builder().type(RequestType.ADD_PARTICIPANT).data(participantDTO).build();
        AppProtobuffs.RequestP request=ProtoUtils.createInscriereRequest(nume,varsta,inscriereData,usernameCurent);
        sendRequest(request);
        AppProtobuffs.ResponseP response=readResponse();
        if (response.getType() == AppProtobuffs.ResponseP.Type.ERROR) {
            String err = response.getMessage();
            throw new Exception(err);
        }
    }*/
@Override
public void adaugaCopilInscriere(String nume, Integer varsta, List<String> probe,String usernameCurent) throws Exception {
    Gson gson = new Gson();
    String inscriereData = gson.toJson(probe);
    String[] inscriereDataArray = new String[] {nume, varsta.toString(), inscriereData, usernameCurent};
    AppProtobuffs.RequestP request=ProtoUtils.createInscriereRequest(inscriereDataArray);
    sendRequest(request);
    AppProtobuffs.ResponseP response=readResponse();
    if (response.getType() == AppProtobuffs.ResponseP.Type.ERROR) {
        String err = response.getMessage();
        throw new Exception(err);
    }
}

    @Override
    public List<String> JoinInscrieriCopii(String proba, String categorie) throws Exception {
        //Request req=new Request.Builder().type(RequestType.GET_CONTEST_PARTICIPANTS).data(stil+" "+distanta).build();
        AppProtobuffs.RequestP request=ProtoUtils.createGetProbaCategorieParticipantsRequest(proba,categorie);
 sendRequest(request);
        AppProtobuffs.ResponseP response=readResponse();
        if (response.getType() == AppProtobuffs.ResponseP.Type.ERROR) {
            String err = response.getMessage();
            throw new Exception(err);
        }
        List<String> participanti=ProtoUtils.getSearchedFromResponse(response);
        return participanti;
    }




    ///PARTEA NESPECIFICA
    private void sendRequest(AppProtobuffs.RequestP request)throws Exception {
        try {
            request.writeDelimitedTo(output);
            output.flush();
        } catch (IOException e) {
            throw new Exception("Error sending object "+e);
        }
    }

    private AppProtobuffs.ResponseP readResponse() throws Exception {
        AppProtobuffs.ResponseP response=null;
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
            output=connection.getOutputStream(); //
            input=connection.getInputStream(); //
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
        Thread tw=new Thread(new ProtobuffProxy.ReaderThread());
        tw.start();
    }



    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    AppProtobuffs.ResponseP response= AppProtobuffs.ResponseP.parseDelimitedFrom(input);
                    System.out.println("response received " + response);

                    if (response.getType()== AppProtobuffs.ResponseP.Type.UPDATE)
                        client.participantInscris();
                    else {
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
