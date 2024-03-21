package com.service;

import com.domain.Message;
import com.domain.Prietenie;
import com.domain.Utilizator;
import com.domain.validators.ValidationException;
import com.domain.validators.ValidatorPrietenie;
import com.domain.validators.ValidatorUtilizator;

import com.repo.MessageDBRepository;
import com.utils.events.ChangeEventType;
import com.utils.events.FriendshipEntityChangeEvent;
import com.utils.observer.Observable;
import com.utils.observer.Observer;
import repo.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Service implements Observable<FriendshipEntityChangeEvent> {
    private final Repository<String,Utilizator> UtilizatorRepository;
    private final Repository<String,Prietenie> PrietenieRepository;
    private final Repository<String,Message> MessageRepository;

    private String utilizatorCurentId = null;

    private List<Observer<FriendshipEntityChangeEvent>> observers= new ArrayList<>();

    public Service(Repository<String,Utilizator> UtilizatorRepository, Repository<String,Prietenie> PrietenieRepository,Repository<String,Message> MessageRepository) {
        this.UtilizatorRepository = UtilizatorRepository;
        this.PrietenieRepository = PrietenieRepository;
        this.MessageRepository = MessageRepository;
    }

    public void printUsers(){
        for(Utilizator u: UtilizatorRepository.getAll()){
            System.out.println(u);
        }
    }
    public void printFriendships(){
        for(Prietenie f: PrietenieRepository.getAll()){
            System.out.println(f);
        }
    }

    public boolean conectareUtilizator(String email, String parola) {
        for (Utilizator utilizator : UtilizatorRepository.getAll()) {
            if (utilizator.getEmail().equals(email)) {
                if (utilizator.getParola().equals(parola)) {
                    utilizatorCurentId = utilizator.getId();
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean creareUtilizator(String nume, String prenume, String email, String parola) {
        for (Utilizator utilizator : UtilizatorRepository.getAll()) {
            if (utilizator.getEmail().equals(email)){
                return false;
            }
        }
        try {
            Utilizator u = new Utilizator(null, email,parola,nume,prenume);
            UtilizatorRepository.adauga(u);
            utilizatorCurentId = u.getId();
        } catch (Error e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public void deconectareUtilizator() {
        utilizatorCurentId = null;
    }

    /**
     * Add a friend to the current user
     *
     * @param Id String
     */
    public void adaugaPrieten(String Id,LocalDateTime data) {
        Utilizator userB = UtilizatorRepository.cautaId(Id);
        Utilizator userA = UtilizatorRepository.cautaId(utilizatorCurentId);
        if (userB == null || userA == null) {
            throw new Error("Utilizatorul nu exista!");
        }
        List<Prietenie> lst=obtinePrietenii();
        for(Prietenie el:lst){
            if((el.getIdPrieten1().equals(userA.getId()) && el.getIdPrieten2().equals(userB.getId()))||(el.getIdPrieten1().equals(userB.getId()) && el.getIdPrieten2().equals(userA.getId()))){
                throw new ValidationException("Prietenia deja exista!");
            }
        }
        PrietenieRepository.adauga(new Prietenie(null, utilizatorCurentId, Id,data));
    }
    public void trimiteCerere(String Id){
        //caut sa vad daca a trimis cererea deja
        Utilizator userA = UtilizatorRepository.cautaId(utilizatorCurentId);
        Utilizator userB = UtilizatorRepository.cautaId(Id);

        List<Prietenie> lst=obtinePrietenii();
        for(Prietenie el:lst){
            if(el.getIdPrieten1().equals(userA.getId()) && el.getIdPrieten2().equals(userB.getId())){
                throw new ValidationException("AI TRIMIS DEJA ACEASTA CERERE!");
            }
        }

        Prietenie el=new Prietenie(null, utilizatorCurentId, Id);
        PrietenieRepository.adauga(el);
        notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD, el));
    }

    public void acceptaCerere(String Id){
        Utilizator user = UtilizatorRepository.cautaId(Id);
        List<Prietenie> lst=obtinePrietenii();
        //for(Prietenie el:lst) System.out.println(el);
        for(Prietenie el: lst){
            if(el.getIdPrieten1().equals(user.getId()) && el.getPending()) {
                //System.out.println("BA L-A GASIT");
                Prietenie noua= new Prietenie(el.getId(),el.getIdPrieten1(),el.getIdPrieten2(),el.getData(),false);
                //el.setPending(false);
                PrietenieRepository.update(el,noua);
                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD, noua));
                break;
            }
        }
    }

    public Prietenie stergePrieten(String prieten_1,String prieten_2) {
        List<Prietenie> lst = PrietenieRepository.getAll();

        for(Prietenie el:lst){
            if((el.getIdPrieten1().equals(prieten_1) && el.getIdPrieten2().equals(prieten_2)) || (el.getIdPrieten1().equals(prieten_2) && el.getIdPrieten2().equals(prieten_1))){
                PrietenieRepository.sterge(el);
                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD, el));
                return el;
            }
        }
        throw new ValidationException("Nu exista o prietenie intre cei doi!");
    }


    public Prietenie stergeCerere(String id) {
        List<Prietenie> lst = PrietenieRepository.getAll();

        for (Prietenie el : lst) {
            if ((el.getIdPrieten1().equals(id) && el.getIdPrieten2().equals(utilizatorCurentId)) && el.getPending()) {
                PrietenieRepository.sterge(el);
                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.DELETE, el));
                return el;
            }
        }
        return null;
    }

    public Prietenie stergeCerereTrimisa(String id) {
        List<Prietenie> lst = PrietenieRepository.getAll();

        for (Prietenie el : lst) {
            if ((el.getIdPrieten2().equals(id) && el.getIdPrieten1().equals(utilizatorCurentId)) && el.getPending()) {
                PrietenieRepository.sterge(el);
                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.DELETE, el));
                return el;
            }
        }
        return null;
    }


    public List<Prietenie> obtinePrietenii() {
        List<Prietenie> prietenii = new ArrayList<>();
        for (Prietenie pr : PrietenieRepository.getAll()) {
            if (pr.getIdPrieten1().equals(utilizatorCurentId) || pr.getIdPrieten2().equals(utilizatorCurentId)) {
                prietenii.add(pr);
            }
        }
        return prietenii;
    }

    public List<Utilizator> obtineUtilizatoriCereri() {
        List<Utilizator> prietenii = new ArrayList<>();
        for (Prietenie pr : PrietenieRepository.getAll()) {
            if (pr.getIdPrieten2().equals(utilizatorCurentId) && pr.getPending()) {
                prietenii.add(UtilizatorRepository.cautaId(pr.getIdPrieten1()));
            }
        }
        return prietenii;
    }

    public List<Utilizator> obtineUtilizatoriCereriTrimise() {
        List<Utilizator> prietenii = new ArrayList<>();
        for (Prietenie pr : PrietenieRepository.getAll()) {
            if (pr.getIdPrieten1().equals(utilizatorCurentId) && pr.getPending()) {
                prietenii.add(UtilizatorRepository.cautaId(pr.getIdPrieten2()));
            }
        }
        return prietenii;
    }

    public List<Utilizator> obtineUtilizatoriPrieteni() {
        List<Utilizator> prietenii = new ArrayList<>();
        for (Prietenie pr : PrietenieRepository.getAll()) {
            if ((pr.getIdPrieten1().equals(utilizatorCurentId) || pr.getIdPrieten2().equals(utilizatorCurentId)) && !pr.getPending()) {
                if(!pr.getIdPrieten1().equals(utilizatorCurentId))  prietenii.add(UtilizatorRepository.cautaId(pr.getIdPrieten1()));
                else prietenii.add(UtilizatorRepository.cautaId(pr.getIdPrieten2()));
            }
        }
        return prietenii;
    }

    public List<Utilizator> obtineUtilizatoriNeprieteni() {
        Boolean ok=true;
        List<Utilizator> neprieteni = new ArrayList<>();
        List<Utilizator> lista=obtineUtilizatoriPrieteni();
        lista.addAll(obtineUtilizatoriCereri());
        lista.addAll(obtineUtilizatoriCereriTrimise());
        for(Utilizator user:UtilizatorRepository.getAll()){
            for (Utilizator u : lista) {
                if (user.getId().equals(u.getId())) ok=false;
            }
            if(ok==true && !user.getId().equals(utilizatorCurentId)) neprieteni.add(user);
            ok=true;
        }
        return neprieteni;
    }

    public void stergeCont() {
        if (utilizatorCurentId == null) {
            return;
        }
        for (Prietenie pr : obtinePrietenii()) {
            PrietenieRepository.sterge(pr);
        }
        UtilizatorRepository.sterge(UtilizatorRepository.cautaId(utilizatorCurentId));
        ;
        utilizatorCurentId = null;
    }

    public String getIdCurent() {
        return utilizatorCurentId;
    }
    public Utilizator getUtilizatorCurent() {
        return UtilizatorRepository.cautaId(utilizatorCurentId);
    }


    /**Metoda de parcurgere in adancime
     * @param start - nodul de la care se porneste parcurgerea
     * @param vizitat - vector cu pozitii pt fiecare camp din lista, care spune daca acesta a fost vizitat anterior sau nu
     */
    private void DFS(int start, boolean[] vizitat) {
        List<Utilizator> userList = UtilizatorRepository.getAll();
        vizitat[start] = true;
        //System.out.println(visited[0]);
        for (Prietenie f : PrietenieRepository.getAll()) {
            //daca il gasim pe primul la un id din lista de prietenii si prietenul sau nu e vizitat
            if (f.getIdPrieten1().equals(userList.get(start).getId()) && !vizitat[userList.indexOf(UtilizatorRepository.cautaId(f.getIdPrieten2()))]) {

                dimComunitate++;

                //continuam cu cautarea prietenilor acelui prieten
                DFS(userList.indexOf(UtilizatorRepository.cautaId(f.getIdPrieten2())), vizitat);
            }
            if (f.getIdPrieten2().equals(userList.get(start).getId()) && !vizitat[userList.indexOf(UtilizatorRepository.cautaId(f.getIdPrieten1()))]) {

                dimComunitate++;

                DFS(userList.indexOf(UtilizatorRepository.cautaId(f.getIdPrieten1())), vizitat);
            }
        }
    }

    /**Metoda care da numarul de comunitati din reteaua noastra
     * @return nr de comunitati gasit*/
    public int nrComunitati() {
        List<Utilizator> lst = UtilizatorRepository.getAll();

        int comunitati = 0;
        boolean[] vizitat = new boolean[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            vizitat[i] = false;
        }

        for (int i = 0; i < lst.size(); i++) {
            if (!vizitat[i]) {
                DFS(i, vizitat);
                comunitati++;
            }
        }

        return comunitati;
    }

    private int dimComunitate;

    /**Metoda care da cea mai sociabila comunitate(numarul ei de membrii)
     * @return nr de membrii ai acelei comunitati*/
    public List<String> ceaMaiSociabilaComunitate() {
        List<Utilizator> utilizatori = UtilizatorRepository.getAll();

        int maxim = 0;
        int index=0;
        dimComunitate = 1;
        boolean[] visited = new boolean[utilizatori.size()];
        for (int i = 0; i < utilizatori.size(); i++) {
            visited[i] = false;
        }

        for (int i = 0; i < utilizatori.size(); i++) {
            if (!visited[i]) {
                DFS(i, visited);
                if(maxim<dimComunitate) {index=i; maxim=dimComunitate;}
                dimComunitate = 1;
            }
        }

        //afisare membrii
        for (int i = 0; i < utilizatori.size(); i++) {
            visited[i] = false;
        }
        DFS(index,visited);
        List<String> lst = new ArrayList<>(1);
        for(int i=0;i<utilizatori.size();i++){
            if(visited[i]){
                lst.add(UtilizatorRepository.getAll().get(i).getEmail());
            }
        }
        return lst;
    }

    public void updateUtilizator(String Id,String Nume,String Prenume,String Email,String Parola){
        Utilizator utilizator = UtilizatorRepository.cautaId(Id);
        if(utilizator==null){
            throw new ValidationException("\nAcest utilizator nu exista!\n");
        }
        Utilizator utilizatorNou=new Utilizator(Id,Email,Parola,Nume,Prenume);
        UtilizatorRepository.update(utilizator,utilizatorNou);
    }


    public Iterable<Utilizator> getAllUsers(){
        return UtilizatorRepository.getAll();
    }

    public void trimiteMesaj(String idTo, String text){
        Message m=new Message(null,utilizatorCurentId,idTo,text);
        MessageRepository.adauga(m);
    }
//
//    public List<Message> obtineConv(String idUser){
//        List<Message> lista=new ArrayList<>();
//        for(Message m : MessageRepository.getAll()){
//            if((m.getIdFromUser().equals(utilizatorCurentId) && m.getIdToUser().equals(idUser)) || (m.getIdFromUser().equals(idUser) && m.getIdToUser().equals(utilizatorCurentId))){
//                lista.add(m);
//            }
//        }
//        return lista;
//    }

        public List<String> obtineConv(String idUser){
        List<String> lista=new ArrayList<>();
        for(Message m : MessageRepository.getAll()){
            if((m.getIdFromUser().equals(utilizatorCurentId) && m.getIdToUser().equals(idUser)) || (m.getIdFromUser().equals(idUser) && m.getIdToUser().equals(utilizatorCurentId))){
                String nume = UtilizatorRepository.cautaId(m.getIdFromUser()).getPrenume();
                lista.add(nume+": "+m.getDataSent());
            }
        }
        return lista;
    }
    @Override
    public void addObserver(Observer<FriendshipEntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<FriendshipEntityChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(FriendshipEntityChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}
