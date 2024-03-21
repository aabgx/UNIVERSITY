package com.example.tema_bun;

import java.io.IOException;
import java.util.List;

public interface IServices {
    boolean login(String username, String parola , IObserver client) throws Exception;

    void logout(String email) throws Exception;

    Utilizator getByAccount(String username,String parola);

    void createAccount(String username,String parola) throws IOException, Exception;

    List<Integer> creareVectorCnt() throws Exception;

    void adaugaCopilInscriere(String nume, Integer varsta, List<String> probe,String usernameCurent) throws Exception;

    List<String> JoinInscrieriCopii(String proba,String categorie) throws Exception;
}
