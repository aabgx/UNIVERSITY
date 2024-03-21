package socialnetwork.com.service;

import socialnetwork.com.domain.Friend;
import socialnetwork.com.domain.User;
import socialnetwork.com.repo_db.repository.Repository;
import socialnetwork.com.validators.ValidationException;
import socialnetwork.com.validators.ValidatorPrietenie;
import socialnetwork.com.validators.ValidatorUtilizator;

import java.time.format.DateTimeFormatter;
import java.util.*;


public class Service {
    private final Repository<Integer, User> users;
    private final Repository<Integer, Friend> friends;
    private String email,password;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /***
     * Constructorul implicit
     * @param users Repository-ul cu utilizatoriii
     * @param friends Repoitory-ul cu prietenii
     */
    public Service(Repository<Integer, User> users, Repository<Integer, Friend> friends) {
        this.users = users;
        this.friends = friends;
    }

    /***
     * Adauga un utilizator in lista cu utilizatori
     * @param nume numele utilizatorului
     * @param prenume prenumele utilizatorului
     * @param email emailul utilizatorului
     * @param parola parola utilizatorului
     * @return
     */
    public String addUser(String nume, String prenume, String email, String parola){
        ValidatorUtilizator v = new ValidatorUtilizator();
        User u = new User(null,prenume,nume,email,parola);
        v.validate(u);
        List<User> lst= users.getAll();
        for(User el:lst){
            if(el.getEmail().equals(email))
            {
                throw new ValidationException("Emailul deja exista!");
            }
        }
        users.add(u);
        return u.toString();
    }

    /***
     * Sterge un utilizator
     * @param email emailul utilizatorului
     * @param parola parola utilizatorul
     * @return Utilizatorul sters
     */
    public User deleteUser(String email, String parola){
        List<User> lst = users.getAll();
        for(User u:lst){
            if(u.getEmail().equals(email) && u.getPassword().equals(parola)){
                users.delete(u);
                return u;
            }
        }
        throw new ValidationException("Nu s-a gasit nici un utilizator cu acest email si parola!");
    }

    /***
     * Sterge un utilizator dupa id-ul lui
     * @param id  Id-ul utilizatorului
     * @return Utilizatorul Sters
     */
    public String deleteUserById(Integer id){
        if(id==-1)
            throw new ValidationException("Id-ul este un numar pozitiv!");
        User u = users.findById(id);
        users.delete(u);
        if(u==null)
            throw new ValidationException("Nu s-a gasit nici un utilizator cu acest id!");
        return u.toString();
    }

    /***
     * Determina un string cu numele unor utilizatori dintr-o prietenie
     * @param prieten_1 id prietenul 1
     * @param prieten_2 id prietenul 1
     * @return Stringul cu afisarea
     */
    private String friendString(Integer prieten_1, Integer prieten_2){
        return users.findById(prieten_1).getFirstname()+", "+ users.findById(prieten_2).getFirstname();
    }

    /***
     * Adauga o prietenie dupa id-ul celor 2 prieteni
     * @param prieten_1 id-ul primului prieten
     * @param prieten_2 id-ul celui de-al doilea prieten
     * @return Stringul prieteniei
     */
    public String addFriendById(Integer prieten_1, Integer prieten_2){
        ValidatorPrietenie v = new ValidatorPrietenie();
        List<Friend> lst = friends.getAll();

        for(Friend el:lst){
            if((el.getPrieten1().equals(prieten_1) && el.getPrieten2().equals(prieten_2))||(el.getPrieten1().equals(prieten_2) && el.getPrieten2().equals(prieten_1))){
                throw new ValidationException("Prietenia deja exista!");
            }
        }
        Friend p = new Friend(prieten_1,prieten_2);
        friends.add(p);
        return friendString(prieten_1,prieten_2);
    }

    /***
     * Determina id-ul unui utilizator dupa email-ul sau
     * @param email emailul utilizatorului
     * @return Id-ul utilizatorului
     */
    private Integer getIdFromEmail(String email){
        List<User> lst = users.getAll();
        for(User el:lst){
            if(el.getEmail().equals(email))
                return el.get_id();
        }
        return 0;
    }

    /***
     * Adauga o prietenie dupa emailul celor 2 prieteni
     * @param email_1 emailul primului prieten
     * @param email_2 emailul celui de-al doilea prieten
     * @return String-ul prieteniei
     */
    public String addFriend(String email_1, String email_2){
        return addFriendById(getIdFromEmail(email_1), getIdFromEmail(email_2));
    }

    /***
     * Sterge o prietenie dupa id-ul celor 2 prieteni
     * @param prieten_1 id-ul primului prieten
     * @param prieten_2 id-ul celui de-al doilea prieten
     * @return String-ul prieteniei
     */
    public String deleteFriendById(Integer prieten_1, Integer prieten_2){
        List<Friend> lst = friends.getAll();
        for(Friend el:lst){
            if((el.getPrieten1().equals(prieten_1) && el.getPrieten2().equals(prieten_2)) || (el.getPrieten1().equals(prieten_2) && el.getPrieten2().equals(prieten_1))){
                friends.delete(el);
                return friendString(prieten_1,prieten_2);
            }
        }
        throw new ValidationException("Nu exista o prietenie intre cei doi!");
    }

    /***
     * Sterge o prietenie dupa email-ul celor 2 prieteni
     * @param email_1 emailul primului prieten
     * @param email_2 emailul celui de-al doilea prieten
     * @return Stringul prieteniei
     */
    public String deleteFrind(String email_1, String email_2){
        return deleteFriendById(getIdFromEmail(email_1), getIdFromEmail(email_2));
    }

    /***
     * Determina o lista cu toti utilizatorii
     * @return Lista cu toti utilizatorii
     */
    public List<User> getAllUsers(){
        return users.getAll();
    }

    /***
     * Determina o lista cu totate prieteniile
     * @return Lista cu toate prieteniile
     */
    public List<Friend> getAllFriends(){
        return friends.getAll();
    }

    /***
     * Determina toate prieteniile tuturor utilizatorilor sub forma de dictionar
     * @return Dictionarul descris
     */
    private Map<Integer,List<Integer>> getMap(){
        List<Friend> lst = friends.getAll();
        Map<Integer, List<Integer>> m = new HashMap<>();
        for(Friend p:lst){
            if(m.get(p.getPrieten1())==null){
                m.put(p.getPrieten1(),new ArrayList<>(1));
            }
            m.get(p.getPrieten1()).add(p.getPrieten2());

            if(m.get(p.getPrieten2())==null){
                m.put(p.getPrieten2(),new ArrayList<>(1));
            }
            m.get(p.getPrieten2()).add(p.getPrieten1());
        }

        return m;
    }

    /***
     * Determina numarul de componente conexe din graf-ul prieteniilor
     * @return Numarul descris
     */
    public Integer getConexNumber(){
        List<Friend> lst = friends.getAll();
        Map<Integer, List<Integer>> m = getMap();
        Map<Integer,Boolean> ok = new HashMap<>();
        Integer cnt=0;
        for(Friend p :lst){
            ok.put(p.getPrieten1(),false);
            ok.put(p.getPrieten2(),false);
        }
        while(true){
            Boolean gata=true;
            for(Map.Entry<Integer,Boolean>pereche:ok.entrySet()){
                if(pereche.getValue()==false){
                    cnt=cnt+1;
                    gata=false;
                    //BFS
                    Queue<Integer> coada = new PriorityQueue<>();
                    coada.add(pereche.getKey());
                    pereche.setValue(true);
                    while(!coada.isEmpty()){
                        Integer top = coada.remove();
                        // Mergem la toti prietenii lui top
                        for(Integer prieten: m.get(top)){
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

    /***
     * Determina o lista cu toti Utilizatorii din cea mai sociabila comunitate
     * @return Lista descrisa
     */
    public List<String> getCompConexNumber(){
        List<Friend> lst = friends.getAll();
        Map<Integer, List<Integer>> m = getMap();
        Map<Integer,Boolean> ok = new HashMap<>();
        for(Friend p :lst){
            ok.put(p.getPrieten1(),false);
            ok.put(p.getPrieten2(),false);
        }
        int maxim = 0;
        Integer prieten_maxim=-1;
        while(true){
            Boolean gata=true;
            for(Map.Entry<Integer,Boolean>pereche:ok.entrySet()){
                if(pereche.getValue()==false){
                    int nr= 0;
                    gata=false;
                    //BFS
                    Queue<Integer> coada = new PriorityQueue<>();
                    coada.add(pereche.getKey());
                    pereche.setValue(true);
                    while(!coada.isEmpty()){
                        nr=nr+1;
                        Integer top = coada.remove();
                        // Mergem la toti prietenii lui top
                        for(Integer prieten: m.get(top)){
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

        Queue<Integer> coada = new PriorityQueue<>();
        coada.add(prieten_maxim);
        for(Friend p :lst){
            ok.put(p.getPrieten1(),false);
            ok.put(p.getPrieten2(),false);
        }
        ok.put(prieten_maxim,true);
        while(!coada.isEmpty()){
            Integer top = coada.remove();
            //lista.add(top);
            lista.add(users.findById(top).getEmail());
            // Mergem la toti prietenii lui top
            for(Integer prieten: m.get(top)){
                if(ok.get(prieten).equals(false)){ // Nu a fost vizitat
                    ok.put(prieten,true);
                    coada.add(prieten);
                }
            }
        }

        return lista;
    }

    public User updateUser(String email, String parola, String newFirstname, String newLastname, String newEmail, String newParola){
        User u= deleteUser(email,parola);
        String fn=newFirstname,ln=newLastname,em=newEmail,pas=newParola;
        if(fn==null || fn.equals("")){
            fn=u.getFirstname();
        }
        if(ln==null || ln.equals("")){
            ln=u.getLastname();
        }
        if(em==null ||em.equals("")){
            em=u.getEmail();
        }
        if(pas==null || pas.equals("")){
            pas=u.getPassword();
        }
        try {
            User new_u = new User(null, ln, fn, em, pas);
            addUser(ln, fn, em, pas);
            return new_u;
        }
        catch (ValidationException e){
            users.add(u);
            throw new ValidationException(e.getMessage());
        }
    }

    public User updateUserById(Integer id, String newFirstname, String newLastname, String newEmail, String newParola){
        User u = users.findById(id);
        if(u==null){
            throw new ValidationException("Nu exista acest id!");
        }
        return updateUser(u.getEmail(),u.getPassword(),newFirstname,newLastname,newEmail,newParola);
    }

    public void login(String email,String password){
        Integer id = getIdFromEmail(email);
        User u = users.findById(id);
        if(u!=null && u.getPassword().equals(password)){
            this.email=email;
            this.password=password;
            return;
        }
        throw new ValidationException("Nu exista acest utilizator!");
    }

    public User getLoggedUser(){
        return users.findById(getIdFromEmail(this.email));
    }

    public void addFriendToLoginUser(String friendEmail){
        if(!email.equals(friendEmail))
            addFriend(this.email,friendEmail);
    }

    public Friend acceptPendingRequest(String friendEmail){
        Integer friendId = getIdFromEmail(friendEmail);
        Integer id = getIdFromEmail(this.email);
        Friend p=friends.findById(friendId,id);
        if(p==null){
            return null;
        }
        Friend newP = new Friend(p.getPrieten1(),p.getPrieten2(),p.getData(),false);
        return friends.update(p,newP);
    }

    public List<User> getAllFriendRequest(){
        Integer id=getIdFromEmail(this.email);
        List<User> lst = new ArrayList<>();
        List<Friend> all = getAllFriends();
        for(Friend f:all){
            if(f.getPrieten2().equals(id) && f.getPending().equals(true)){
                lst.add(users.findById(f.getPrieten1()));
            }
            if(f.getPrieten1().equals(id) && f.getPending().equals(true)){
                lst.add(users.findById(f.getPrieten2()));
            }
        }
        return lst;
    }

    public List<User> getAllFriendsOfCurrentUser(){
        Integer id=getIdFromEmail(this.email);
        List<User> lst = new ArrayList<>();
        List<Friend> all = getAllFriends();
        for(Friend f:all){
            if(f.getPrieten2().equals(id) && f.getPending().equals(false)){
                lst.add(users.findById(f.getPrieten1()));
            }
            if(f.getPrieten1().equals(id) && f.getPending().equals(false)){
                lst.add(users.findById(f.getPrieten2()));
            }
        }
        return lst;
    }

    public void deleteFriendFromLoggedUser(String friendEmail){
        Integer id = getIdFromEmail(this.email);
        Integer friendId = getIdFromEmail(friendEmail);
        Friend f = friends.findById(id,friendId);
        if (f==null){f=friends.findById(friendId,id);}
        if(f!=null ){
            friends.delete(f);
            return;
        }
        throw new ValidationException("Nu exista aceasta prietenie!");
    }

    public String getDateOfFriendRequest(String friendEmail) {
        Integer id = getIdFromEmail(email);
        Integer friendId = getIdFromEmail(friendEmail);
        Friend p = friends.findById(friendId,id);
        if(p==null) p = friends.findById(id,friendId);
        if(p==null) throw new ValidationException("Nu exista aceasta Prietenie!");
        String date =p.getData().format(formatter);
        return date;
    }

//    /***
//     * Cauta un utilizator dup email
//     * @param email Emailul returnat
//     * @return Utilizatorul cautat
//     */
//    public User cautaDupaEmail(String email){
//        return utilizatori.cautaDupaEmail(email);
//    }
//
//    /***
//     * Cauta utilizatori care au un anumit prenume
//     * @param prenume
//     * @return
//     */
//    public List<User> cautaDupaPrenume(String prenume){
//        return utilizatori.cautaDupaPrenume(prenume);
//    }
//
//    public List<User> cautaDupaNume(String nume){
//        return utilizatori.cautaDupaNume(nume);
//    }
}
