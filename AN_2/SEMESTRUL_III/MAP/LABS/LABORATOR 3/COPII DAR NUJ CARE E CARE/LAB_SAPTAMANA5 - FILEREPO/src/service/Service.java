package service;

import domain.Prietenie;
import domain.Utilizator;
import domain.validators.ValidationException;
import domain.validators.ValidatorPrietenie;
import domain.validators.ValidatorUtilizator;
import repo.PrietenieRepository;
import repo.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final Repository<String,Utilizator> UtilizatorRepository;
    private final Repository<String,Prietenie> PrietenieRepository;

    public Service(Repository<String,Utilizator> UtilizatorRepository, Repository<String,Prietenie> PrietenieRepository) {
        this.UtilizatorRepository = UtilizatorRepository;
        this.PrietenieRepository = PrietenieRepository;
    }

    /**
     * Adauga un Utilizator in lista dupa campurile specifice
     * @param email - emailul utilizatorului
     * @param parola - parola contului
     * @param nume - numele utilizatorului
     * @param prenume -prenumele utilizatorului
     * @return true daca s-a reusit adaugarea, false altfel
     * @throws ValidationException daca emailul nu e unic sau unul din campuri e invalid*/
    public boolean adaugaUtilizator(String email, String parola, String nume, String prenume){
        ValidatorUtilizator v=new ValidatorUtilizator();
        Utilizator u=new Utilizator(null,email,parola,nume,prenume);
        v.validate(u);
        List<Utilizator> lst=UtilizatorRepository.getAll();
        for(Utilizator el:lst)
        {
            if(el.getEmail().equals(email))
            {
                throw new ValidationException("\nEmail deja folosit!\n");
            }
        }
        UtilizatorRepository.adauga(u);
        return true;
    }

    /**
     * Sterge un utilizator din lista dupa id
     * @param idUtilizator - id-ul de cautat si sters
     * @return true daca s-a reusit stergerea, false altfel
     * @throws ValidationException daca id-ul nu se afla in lista de Utilizatori*/
    public boolean stergeUtilizator(String idUtilizator) {
        Utilizator utilizator = UtilizatorRepository.cautaId(idUtilizator);
        if (utilizator == null) {
            throw new ValidationException("\nAcest utilizator nu este inregistrat!\n");
        }
        UtilizatorRepository.sterge(utilizator);
        return true;
    }

    /**Adauga o prietenie dupa id-ul a 2 utilizatori
     * @param idPrieten1 - ID-UL PRIMULUI VIITOR PRIETEN
     * @param idPrieten2-id-ul celui de-al doilea viitor prieten
     * @return true daca s-a adaugat cu succes prietenia
     * @throws ValidationException daca unul din id-uri nu reprezinta un utilizator
     *                             daca prietenia deja exista
     * */
    public boolean adaugaPrietenie(String idPrieten1, String idPrieten2, LocalDateTime data) {
        ValidatorPrietenie v = new ValidatorPrietenie();
        Utilizator u1 = UtilizatorRepository.cautaId(idPrieten1);
        Utilizator u2 = UtilizatorRepository.cautaId(idPrieten2);

        //vedem daca exista id-urile date
        if(u1==null || u2 == null)
        {
            throw new ValidationException("\nUnul din cele 2 ID-uri nu exista!\n");
        }

        //vedem daca nu vrem sa adaugam o prietenie deja existenta
        List<Prietenie> lst = PrietenieRepository.getAll();
        for(Prietenie el: lst){
            if((el.getIdPrieten1().equals(u1.getId()) && el.getIdPrieten2().equals(u2.getId())) || (el.getIdPrieten1().equals(u2.getId()) && el.getIdPrieten2().equals(u1.getId()))){
                throw new ValidationException("\nPrietenia deja exista!\n");
            }
        }

        Prietenie p=new Prietenie(null,u1.getId(),u2.getId(),data);
        PrietenieRepository.adauga(p);
        return true;
    }

    /**Sterge o prietenie dupa id
     * @param idPrietenie - id-ul prieteniei de sters
     * @return true daca s-a efectuat cu succes stergerea
     * @throws ValidationException daca nu exista prietenie cu acest id*/
    public boolean stergePrietenie(String idPrietenie) {
        Prietenie prietenie = PrietenieRepository.cautaId(idPrietenie);
        if (prietenie == null) {
            throw new ValidationException("\nAceasta prietenie nu exista!\n");
        }
        PrietenieRepository.sterge(prietenie);
        return true;
    }



    public List<Utilizator> getAllUtilizatori(){
        return UtilizatorRepository.getAll();
    }

    public List<Prietenie> getAllPrietenii(){
        return PrietenieRepository.getAll();
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

    public Utilizator updateUtilizator(String Id,String Nume,String Prenume,String Email,String Parola){
        Utilizator utilizator = UtilizatorRepository.cautaId(Id);
        if(utilizator==null){
            throw new ValidationException("\nAcest utilizator nu exista!\n");
        }
        Utilizator utilizatorNou=new Utilizator(Id,Email,Parola,Nume,Prenume);
        return UtilizatorRepository.update(utilizator,utilizatorNou);
    }

}
