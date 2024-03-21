package service;

import domain.Prietenie;
import domain.Utilizator;
import validators.ValidationException;
import validators.ValidatorPrietenie;
import validators.ValidatorUtilizator;
import repository.Repository;

import java.lang.reflect.Array;
import java.util.*;


public class Service {
    private final Repository<String,Utilizator> utilizatori;
    private final Repository<String,Prietenie> prietenii;

    public Service(Repository<String,Utilizator> utilizatori, Repository<String,Prietenie> prietenii) {
        this.utilizatori = utilizatori;
        this.prietenii = prietenii;
    }

    public String adaugaUtilizator(String nume,String prenume, String email,String parola){
        ValidatorUtilizator v = new ValidatorUtilizator();
        Utilizator u = new Utilizator(null,prenume,nume,email,parola);
        v.validate(u);
        List<Utilizator> lst=utilizatori.get_all();
        for(Utilizator el:lst){
            if(el.get_email().equals(email))
            {
                throw new ValidationException("Emailul deja exista!");
            }
        }
        utilizatori.adauga(u);
        return u.toString();
    }

    public String stergeUtilizator(String email,String parola){
        List<Utilizator> lst = utilizatori.get_all();
        for(Utilizator u:lst){
            if(u.get_email().equals(email) && u.get_parola().equals(parola)){
                utilizatori.sterge(u);
                return u.toString();
            }
        }
        throw new ValidationException("Nu s-a gasit nici un utilizator cu acest email si parola!");
    }

    public String stergeUtilizator_dupa_id(String id){
        Utilizator u = utilizatori.cauta_dupa_id(id);
        utilizatori.sterge(u);
        if(u==null)
            throw new ValidationException("Nu s-a gasit nici un utilizator cu acest id!");
        return u.toString();
    }

    private String prietenie_string(String prieten_1,String prieten_2){
        return utilizatori.cauta_dupa_id(prieten_1).get_firstname()+", "+utilizatori.cauta_dupa_id(prieten_2).get_firstname();
    }
    public String adaugaPrietenie_dupa_id(String prieten_1,String prieten_2){
        ValidatorPrietenie v = new ValidatorPrietenie();
        List<Prietenie> lst = prietenii.get_all();
        for(Prietenie el:lst){
            if(el.get_prieten_1().equals(prieten_1) && el.get_prieten_2().equals(prieten_2)){
                throw new ValidationException("Prietenia deja exista!");
            }
        }
        Prietenie p = new Prietenie(null,prieten_1,prieten_2);
        prietenii.adauga(p);
        return prietenie_string(prieten_1,prieten_2);
    }

    private String get_id_from_email(String email){
        List<Utilizator> lst =utilizatori.get_all();
        for(Utilizator el:lst){
            if(el.get_email().equals(email))
                return el.get_id();
        }
        return "";
    }

    public String adaugaPrietenie(String email_1,String email_2){
        return adaugaPrietenie_dupa_id(get_id_from_email(email_1),get_id_from_email(email_2));
    }

    public String stergePrietenie_dupa_id(String prieten_1,String prieten_2){
        List<Prietenie> lst = prietenii.get_all();
        for(Prietenie el:lst){
            if(el.get_prieten_1().equals(prieten_1) && el.get_prieten_2().equals(prieten_2)){
                prietenii.sterge(el);
                return prietenie_string(prieten_1,prieten_2);
            }
        }
        throw new ValidationException("Nu exista o prietenie intre cei doi!");
    }
    public String stergePrietenie(String email_1,String email_2){
        return stergePrietenie_dupa_id(get_id_from_email(email_1),get_id_from_email(email_2));
    }

    public List<Utilizator> get_all_Utilizatori(){
        return utilizatori.get_all();
    }

    public List<Prietenie> get_all_Prietenii(){
        return prietenii.get_all();
    }

    private Map<String,List<String>> get_map(){
        List<Prietenie> lst = prietenii.get_all();
        Map<String, List<String>> m = new HashMap<>();
        for(Prietenie p:lst){
            if(m.get(p.get_prieten_1())==null){
                m.put(p.get_prieten_1(),new ArrayList<>(1));
            }
            m.get(p.get_prieten_1()).add(p.get_prieten_2());

            if(m.get(p.get_prieten_2())==null){
                m.put(p.get_prieten_2(),new ArrayList<>(1));
            }
            m.get(p.get_prieten_2()).add(p.get_prieten_1());
        }

        return m;
    }

    public Integer get_numar_componente_conexe(){
        List<Prietenie> lst = prietenii.get_all();
        Map<String, List<String>> m = get_map();
        Map<String,Boolean> ok = new HashMap<>();
        Integer cnt=0;
        for(Prietenie p :lst){
            ok.put(p.get_prieten_1(),false);
            ok.put(p.get_prieten_2(),false);
        }
        while(true){
            Boolean gata=true;
            for(Map.Entry<String,Boolean>pereche:ok.entrySet()){
                if(pereche.getValue()==false){
                    cnt=cnt+1;
                    gata=false;
                    //BFS
                    Queue<String> coada = new PriorityQueue<>();
                    coada.add(pereche.getKey());
                    pereche.setValue(true);
                    while(!coada.isEmpty()){
                        String top = coada.remove();
                        // Mergem la toti prietenii lui top
                        for(String prieten: m.get(top)){
                            if(ok.get(prieten).equals(false)){ // Nu a fost vizitat
                                ok.put(prieten,true);
                                coada.add(prieten);
                            }
                        }
                    }
                    //BFS
                }
            }
            if(gata==true)break;
        }

        return cnt;
    }

    public List<String> get_comp_conecta(){
        List<Prietenie> lst = prietenii.get_all();
        Map<String, List<String>> m = get_map();
        Map<String,Boolean> ok = new HashMap<>();
        for(Prietenie p :lst){
            ok.put(p.get_prieten_1(),false);
            ok.put(p.get_prieten_2(),false);
        }
        int maxim = 0;
        String prieten_maxim="";
        while(true){
            Boolean gata=true;
            for(Map.Entry<String,Boolean>pereche:ok.entrySet()){
                if(pereche.getValue()==false){
                    int nr= 0;
                    gata=false;
                    //BFS
                    Queue<String> coada = new PriorityQueue<>();
                    coada.add(pereche.getKey());
                    pereche.setValue(true);
                    while(!coada.isEmpty()){
                        nr=nr+1;
                        String top = coada.remove();
                        // Mergem la toti prietenii lui top
                        for(String prieten: m.get(top)){
                            if(ok.get(prieten).equals(false)){ // Nu a fost vizitat
                                ok.put(prieten,true);
                                coada.add(prieten);
                            }
                        }
                    }
                    //BFS
                    if(maxim==0 || maxim<nr){
                        maxim=nr;
                        prieten_maxim=pereche.getKey();
                    }
                }
            }
            if(gata==true)break;
        }
        List<String>lista=new ArrayList<>();

        Queue<String> coada = new PriorityQueue<>();
        coada.add(prieten_maxim);
        for(Prietenie p :lst){
            ok.put(p.get_prieten_1(),false);
            ok.put(p.get_prieten_2(),false);
        }
        ok.put(prieten_maxim,true);
        while(!coada.isEmpty()){
            String top = coada.remove();
            //lista.add(top);
            lista.add(utilizatori.cauta_dupa_id(top).get_email());
            // Mergem la toti prietenii lui top
            for(String prieten: m.get(top)){
                if(ok.get(prieten).equals(false)){ // Nu a fost vizitat
                    ok.put(prieten,true);
                    coada.add(prieten);
                }
            }
        }

        return lista;
    }
}
