package com.example.tema_bun.service;

import com.example.tema_bun.domain.Copil;
import com.example.tema_bun.domain.Inscriere;
import com.example.tema_bun.domain.Proba;
import com.example.tema_bun.domain.Utilizator;
import com.example.tema_bun.repo.CopilRepository;
import com.example.tema_bun.repo.InscriereRepository;
import com.example.tema_bun.repo.ProbaRepository;
import com.example.tema_bun.repo.UtilizatorRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Service {
    private UtilizatorRepository utilizatorRepository;
    private CopilRepository copilRepository;
    private ProbaRepository probaRepository;
    private InscriereRepository inscriereRepository;

    public Service(UtilizatorRepository utilizatorRepository,CopilRepository copilRepository,ProbaRepository probaRepository,InscriereRepository inscriereRepository) {
        this.utilizatorRepository = utilizatorRepository;
        this.copilRepository=copilRepository;
        this.probaRepository=probaRepository;
        this.inscriereRepository=inscriereRepository;
    }

    public Utilizator getByAccount(String username,String parola) throws IOException {
        for(Utilizator entity: utilizatorRepository.getAll())
            if(entity.getUsername().equals(username) && entity.getParola().equals(parola))
                return entity;
        throw new IOException("CONTUL NU EXISTA!");
    }

    public void createAccount(String username,String parola) throws IOException{
        if(username.equals("") || parola.equals(""))
            throw new IOException("USERNAME-UL SI PAROLA NU POT FI NULE!");
        else{
            utilizatorRepository.adauga(new Utilizator(username,parola));
        }
    }
    public List<Integer> creareVectorCnt(){
        List<Integer> rezultat = new ArrayList<Integer>(Collections.nCopies(9, 0));
        //System.out.println(inscriereRepository.getAll().size());
        for(Inscriere i: inscriereRepository.getAll()){
            System.out.println("IN FOR");
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

        }
        System.out.println("OUT OF FOR");
    return rezultat;
    }
//
//    public List<Integer> gasesteParticipanti(String proba, String categorie){
//        String cod = proba.charAt(0) + "_" + categorie;
//
//        //System.out.println(cod);
//
//        List<Integer> listaCoduri = new ArrayList<Integer>();
//        for(Inscriere i: inscriereRepository.getAll()){
//            if(i.getId_proba().equals(cod)) {
//                listaCoduri.add(i.getId_copil());
//                //System.out.println(i.getId_copil());
//            }
//        }
//        return listaCoduri;
//    }
//
//    public String getNumeVarstaById(Integer id){
//        for(Copil c:copilRepository.getAll()){
//            if(c.getId().equals(id))
//                return c.getNume()+" - "+c.getVarsta()+" ani";
//        }
//        return null;
//    }
//
//    public List<String> gasesteNumeVarstaCopii(List<Integer> listCoduri){
//        List<String> listNumeCopii = new ArrayList<String>();
//        for(Integer i: listCoduri){
//            listNumeCopii.add(getNumeVarstaById(i));
//        }
//        return listNumeCopii;
//    }

    public void adaugaCopilInscriere(String nume, Integer varsta, List<String> probe){
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
    }

    public List<String> JoinInscrieriCopii(String proba, String categorie){
            List<String> nameAge = new ArrayList<>();

            String de_cautat=proba.charAt(0) + "_" +categorie;

            nameAge=copilRepository.JoinCopilInscrieri(de_cautat);
            return nameAge;
    }

    public Proba addProba(String id){
        Proba p=new Proba(id);
        probaRepository.adauga(p);
        return p;
    }

    public Proba deleteProba(String id){
        Proba p=new Proba(id);
        try{
        probaRepository.sterge(p);
        }catch(Exception e){

        }
        return p;
    }
}
