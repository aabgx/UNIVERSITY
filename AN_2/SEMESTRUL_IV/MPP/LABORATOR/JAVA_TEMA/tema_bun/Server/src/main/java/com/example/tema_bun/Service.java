package com.example.tema_bun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Service implements IServices{
    private UtilizatorRepository utilizatorRepository;
    private CopilRepository copilRepository;
    private ProbaRepository probaRepository;
    private InscriereRepository inscriereRepository;

    private static Map<String, IObserver> loggedClients;
    private final int defaultThreadsNo = 5;

    public Service(UtilizatorRepository utilizatorRepository,CopilRepository copilRepository,ProbaRepository probaRepository,InscriereRepository inscriereRepository) {
        this.utilizatorRepository = utilizatorRepository;
        this.copilRepository=copilRepository;
        this.probaRepository=probaRepository;
        this.inscriereRepository=inscriereRepository;

        loggedClients = new ConcurrentHashMap<>();
    }


    public Utilizator getByAccount(String username,String parola)  {
        for(Utilizator entity: utilizatorRepository.getAll())
            if(entity.getUsername().equals(username) && entity.getParola().equals(parola))
                return entity;
        return null;
    }

    public synchronized boolean login(String username,String parola, IObserver client) throws Exception {
        Utilizator u_cautat=getByAccount(username,parola);
        if(u_cautat!=null) {
            if (loggedClients.get(u_cautat.getUsername()) != null)
                throw new Exception("User already logged in.");
            loggedClients.put(username, client);
            return true;
        }
        return false;
    }

    public void logout(String username) throws Exception
    {
        IObserver localClient = loggedClients.remove(username);
        if (localClient == null)
            throw new Exception("User " + username + " is not logged in.");
    }

    public void createAccount(String username,String parola) throws IOException{
        if(username.equals("") || parola.equals(""))
            throw new IOException("USERNAME-UL SI PAROLA NU POT FI NULE!");
        else{
            utilizatorRepository.adauga(new Utilizator(username,parola));
        }
    }

    @Override
    public List<Integer> creareVectorCnt(){
        List<Integer> rezultat = new ArrayList<Integer>(Collections.nCopies(9, 0));
        //System.out.println(inscriereRepository.getAll().size());
        for(Inscriere i: inscriereRepository.getAll()){
            System.out.println("ajunge aici!!!!!!!!!!!!!!!!");
            //System.out.println(i.getId_proba());
            if(i.getId_proba().equals("d_6_8"))
                rezultat.set(0,rezultat.get(0)+1);
            else if(i.getId_proba().equals("d_9_11"))
                rezultat.set(1,rezultat.get(1)+1);
            else if(i.getId_proba().equals("d_12_15"))
                rezultat.set(2,rezultat.get(2)+1);
            else if(i.getId_proba().equals("c_6_8"))
                rezultat.set(3,rezultat.get(3)+1);
            else if(i.getId_proba().equals("c_9_11"))
                rezultat.set(4,rezultat.get(4)+1);
            else if(i.getId_proba().equals("c_12_15"))
                rezultat.set(5,rezultat.get(5)+1);
            else if(i.getId_proba().equals("p_6_8"))
                rezultat.set(6,rezultat.get(6)+1);
            else if(i.getId_proba().equals("p_9_11"))
                rezultat.set(7,rezultat.get(7)+1);
            else if(i.getId_proba().equals("p_12_15"))
                rezultat.set(8,rezultat.get(8)+1);

            System.out.println(rezultat);
        }
        return rezultat;
    }

    public void adaugaCopilInscriere(String nume, Integer varsta, List<String> probe,String usernameCurent){
        Copil copil = new Copil(nume,varsta);
        copilRepository.adauga(copil);
        Integer id_copil = copilRepository.getAll().get(copilRepository.getAll().size()-1).getId();

        String categorie;
        if(varsta>=5 && varsta <=8) categorie="6_8";
        else if (varsta <=11) categorie="9_11";
        else categorie="12_15";

        for(String s: probe){
            String proba=s.charAt(0) + "_" +categorie;
            Inscriere inscriere = new Inscriere(id_copil,proba);
            inscriereRepository.adauga(inscriere);
        }

        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);

        loggedClients.forEach((username, client) -> {
            executor.execute(() -> {
                System.out.println("Notifying [" + username + "] about new participant.");
                client.participantInscris();
            });
        });
        executor.shutdown();
    }

    public List<String> JoinInscrieriCopii(String proba, String categorie){
//        List<String> nameAge = new ArrayList<>();

        String de_cautat=proba.charAt(0) + "_" +categorie;

        List<String> nameAge=copilRepository.JoinCopilInscrieri(de_cautat);
        return nameAge;
    }
}
