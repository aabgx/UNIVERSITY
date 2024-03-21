package com.example.myfacebook.service;

import com.example.myfacebook.domain.Friendship;
import com.example.myfacebook.domain.Message;
import com.example.myfacebook.domain.Request;
import com.example.myfacebook.domain.User;
import com.example.myfacebook.domain.validators.Validator;
import com.example.myfacebook.exceptions.RepositoryException;
import com.example.myfacebook.repo.Repository;
import com.example.myfacebook.repo.db.MessageDBRepo;
import com.example.myfacebook.repo.db.RequestDBRepo;
import com.example.myfacebook.utils.events.ChangeEventType;
import com.example.myfacebook.utils.events.UserEntityChangeEvent;
import com.example.myfacebook.utils.observer.Observable;
import com.example.myfacebook.utils.observer.Observer;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Service<ID> implements Observable<UserEntityChangeEvent> {
    private Repository<Integer, User> usrepo;
    private Repository<Integer, Friendship> frepo;
    private Repository<Integer, Request> reqrepo;
    private Repository<Integer, Message> messrepo;
    private Validator<User> validator;
    private List<Observer<UserEntityChangeEvent>> observers = new ArrayList<>();

    int[][] network = new int[10000][10000];

    public Service(Repository<Integer, User> repo, Repository<Integer, Friendship> repoFriendships, RequestDBRepo reqrepo, MessageDBRepo messrepo, Validator validator) throws FileNotFoundException {
        this.usrepo = repo;
        this.frepo = repoFriendships;
        this.reqrepo = reqrepo;
        this.messrepo = messrepo;
        this.validator = validator;
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                network[i][j] = 0;
            }
        }
    }
////////////----------------------------observer functions -------------------------------

    @Override
    public void addObserver(Observer<UserEntityChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<UserEntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(UserEntityChangeEvent t) {

        observers.stream().forEach(x -> x.update(t));
    }

//////------------------------------ message functions -------------------------------------------


    public Message getMessageByID(int id) {
        return messrepo.findOne(id);
    }

    public Message addMessage(int idFrom, int idTo, String text) {
        int verif=0;
        for(Friendship fr:getListFriendshipsForUser(idFrom)){
            if(fr.getU2().equals(idTo)){
                verif=1;
            }
        }
        if(verif==1){

            Message mess = new Message(idFrom, idTo, text);
            Random rand = new Random(); //instance of random class
            int upperbound = 10000;
            int idm = rand.nextInt(upperbound);
            mess.setId(getMaxIdMess()+1);
            messrepo.save(mess);
            return mess;
        }
        return null;


    }

    public int getMaxIdMess(){
        List<Message>mess=new ArrayList((Collection) messrepo.findAll());
        int max=1;
        for(Message m:mess){
            if(m.getIdMessage()>max){
                max=m.getIdMessage();
            }
        }
        return max;
    }


    public List<Message> getListMessages() {
        List<Message> mess=new ArrayList((Collection) messrepo.findAll());
        List<Message> sortedList = mess.stream()
                .sorted(Comparator.comparingInt(Message::getIdMessage))
                .collect(Collectors.toList());

        return sortedList;

    }

    public void setMessages(){
        List<Message>ms=getListMessages();
        int count=1;
        for(Message m:ms){
            m.setIdMessage(count);
            count++;
        }
    }


    public List<Message> getListMessagesFromUsers(int idfrom, int idto) {
        List<Message> initial = new ArrayList((Collection) messrepo.findAll());
        List<Message> fnlmess = new ArrayList<>();
        for (Message m : initial) {
            if (m.getIdFromUser() == idfrom && m.getIdToUser() == idto) {
                String data=m.getDataSent();
                m.setDataSent(getUserByID(idfrom).getUsername()+" : "+data);
                fnlmess.add(m);
            }
            if (m.getIdFromUser() == idto && m.getIdToUser() == idfrom) {
                String data=m.getDataSent();
                m.setDataSent(getUserByID(idto).getUsername()+" : "+data);
                fnlmess.add(m);
            }
        }

        return fnlmess;
    }


/////------------------------------- user functions ---------------------------------------------

    public User getUserByID(int id) {
        return usrepo.findOne(id);
    }

    public User addUser(int id, String name, int age, String sex, String city, String password) {
        boolean verified = true;

        for (User u : getListUsers()) {
            if (u.getUserID() == id) {
                verified = false;
                System.out.println("user id already exists!");
            }
        }
        if (verified) {
            User user = new User(id, name, age, sex, city, password);
            usrepo.save(user);


        }

        return null;
    }

    public User removeUser(User user) {

        int id = user.getUserID();
        for (Friendship f : getListFriendships()) {

            if (f.getU1() == id || f.getU2() == id) {
                try {
                    removeFriendship(f.getU1(), f.getU2());
                    removeFriendship(f.getU2(), f.getU1());
                    User us1 = getUserByID(f.getU1());
                    User us2 = getUserByID(f.getU2());

                    if (us1 != null || us2 != null) {
                        us1.removeFriend(us2);
                        us2.removeFriend(us1);
                    }


                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Optional<User> usr = Optional.ofNullable(usrepo.delete(id));
        if (usr.isPresent()) {
            notifyObservers(new UserEntityChangeEvent(ChangeEventType.DELETE, usr.get()));
            return usr.get();
        }
        return null;

        //usrepo.delete(id);

    }

    public List<User> getListUsers() {
        List<User> list = new ArrayList((Collection) usrepo.findAll());
        return list;
    }

////---------------------------- request functions -------------------------------------------

    public Request addRequest(int id1, int id2) {
        List<Request> req = new ArrayList((Collection) reqrepo.findAll());
        boolean exists = false;
        for (Request r : req) {
            if (r.getFrom().equals(id1) && r.getTo().equals(id2)) {
                return null;
            }
        }
        Request request = new Request(id1, id2);
        Random rand = new Random(); //instance of random class
        int upperbound = 10000;
        int id = rand.nextInt(upperbound);
        request.setId(id);
        reqrepo.save(request);
        return request;
    }

    public Request deleteRequest(int id1, int id2) {
        List<Request> req = getListRequests();
        for (Request r : req) {
            if (r.getFrom().equals(id1) && r.getTo().equals(id2)) {
                Request re = reqrepo.delete(r.getIdRe());
                return re;
            }
        }
        return null;

    }

    public void updateRequests(int id) {
        List<User> reqs = getRequests(id);
        List<Friendship> frs = getListFriendships();
        for (User u : reqs) {
            for (Friendship fr : frs) {
                if (fr.getU1().equals(id) && fr.getU2().equals(u.getUserID())) {
                    deleteRequest(u.getUserID(), id);
                }
                if (fr.getU1().equals(u.getUserID()) && fr.getU2().equals(id)) {
                    deleteRequest(u.getUserID(), id);
                }
            }
        }


    }

///////------------------------------------- friendship functions ---------------------------------------------------


    public void addFriend(int u1, int u2) {
        network[u1][u2] = 1;
        network[u2][u1] = 1;
        int verified = 0;
        User us1 = usrepo.findOne(u1);
        User us2 = usrepo.findOne(u2);

        LocalDateTime now = LocalDateTime.now();
        String date = now.toString();
        for (Friendship u : frepo.findAll()) {
            if (u.getU1() == u1 && u.getU2() == u2 || u.getU1() == u2 && u.getU2() == u1) {
                verified = 1;
                break;
            }
        }
        if (verified == 0) {
            if (us1 != null && us2 != null) {
                us1.addFriend(us2);
                us2.addFriend(us1);
            }
            Friendship fr = new Friendship(u1, u2, date);
            Random rand = new Random(); //instance of random class
            int upperbound = 10000;
            int id = rand.nextInt(upperbound);
            fr.setId(id);
            frepo.save(fr);
        } else {
            System.out.println("friendship already exists");
        }


    }

    public void removeFriendship(int u1, int u2) throws RepositoryException {
        network[u1][u2] = 0;
        network[u2][u1] = 0;

        User us1 = getUserByID(u1);
        User us2 = getUserByID(u2);
        List<Friendship> list = getListFriendships();
        for (Friendship f : list) {
            if (f.getU1().equals(u1) && f.getU2().equals(u2) || f.getU1().equals(u2) && f.getU2().equals(u1)) {
                //System.out.println(f.getId());
                frepo.delete(f.getId());

            }
        }
        if (us1 != null && us2 != null) {
            us1.removeFriend(us2);
            us2.removeFriend(us1);
        }

    }

    public List<User> getRequests(int userId) {
        List<Request> req = new ArrayList((Collection) reqrepo.findAll());
        List<User> requests = new ArrayList<>();
        for (Request r : req) {
            if (r.getTo().equals(userId)) {
                requests.add(getUserByID(r.getFrom()));
            }
        }
        return requests;
    }


    public List<Request> getListRequests() {
        return new ArrayList((Collection) reqrepo.findAll());

    }

    public List<Friendship> getListFriendships() {
        List<Friendship> list = new ArrayList((Collection) frepo.findAll());
        for (Friendship fr : list) {
            fr.setUsername1(getUserByID(fr.getU1()).getUsername());
            fr.setUsername2(getUserByID(fr.getU2()).getUsername());
        }
        return list;
    }

    public List<Friendship> getListFriendshipsForUser(int userId) {
        List<Friendship> list = new ArrayList((Collection) frepo.findAll());
        List<Friendship> finalList = new ArrayList<>();
        for (Friendship fr : list) {
            if (fr.getU1().equals(userId)) {
                fr.setUsername1(getUserByID(fr.getU1()).getUsername());
                fr.setUsername2(getUserByID(fr.getU2()).getUsername());
                finalList.add(fr);
            }
            if (fr.getU2().equals(userId)) {
                Friendship fr2 = new Friendship(fr.getU2(), fr.getU1(), fr.getFriendsFrom());
                fr2.setUsername1(getUserByID(fr.getU2()).getUsername());
                fr2.setUsername2(getUserByID(fr.getU1()).getUsername());
                fr2.setId(fr.getId());
                finalList.add(fr2);
            }


            //to do

        }
        return finalList;
    }

    public void makeFriendships() {

        for (Friendship f : getListFriendships()) {
            int u1 = f.getU1();
            int u2 = f.getU2();
            network[u1][u2] = 1;
            network[u2][u1] = 1;
            User us1 = getUserByID(u1);
            User us2 = getUserByID(u2);
            if (us1 != null && us2 != null) {
                us1.addFriend(us2);
                us2.addFriend(us1);

            } else {
                System.out.println("wrong ID's" + u1 + " " + u2);
            }


        }
    }


    public List<User> getFriends(int id) {

        User us = getUserByID(id);
        List<User> frs = new ArrayList<>();
        for (Friendship fr : getListFriendships()) {
            if (fr.getU1().equals(id)) {
                frs.add(getUserByID(fr.getU1()));
            }
            if (fr.getU2().equals(id)) {
                frs.add(getUserByID(fr.getU2()));
            }
        }
        if (us != null) {
            return frs;
        }
        return null;


    }


////////------------------------------------- graph functions ---------------------------------------------


    public int maxID() {
        int maxd = 0;
        List<User> usr = getListUsers();
        for (User u : usr) {
            if (u.getUserID() > maxd) {
                System.out.println(maxd + " " + u.getUserID());
                maxd = (int) u.getUserID();
            }
        }
        return maxd;
    }

    boolean hasFriends(int id) {
        for (int i = 1; i <= 999; i++) {
            if (network[i][id] == 1) {
                return true;
            }
        }
        return false;

    }

    public void DFSUtil(List<Integer>[] adj, int v, boolean[] visited, int count) {
        visited[v] = true;
        //int ok=1;

        for (int n : adj[v]) {
            if (!visited[n]) {
                //ok=0;
                DFSUtil(adj, n, visited, count);

            }
        }
    }

    public int howManyComunities() {

        int max = maxID();
        List<Integer>[] adj;
        int size = getListUsers().size() + 1;
        adj = new LinkedList[max];
        for (int i = 0; i < max; ++i)
            adj[i] = new LinkedList();


        System.out.println(max);
        int count = 0;

        int[][] netw = network;

        for (int i = 1; i < 10000; i++) {
            for (int j = 1; j < 10000; j++) {
                if (netw[i][j] == 1) {
                    adj[i].add(j);
                    adj[j].add(i);
                }
            }
        }

        boolean[] visited = new boolean[size + 1];
        int maxCom = 0;

        for (int i = 0; i < size; ++i) {
            if (!visited[i] && hasFriends(i)) {
                count++;
                int z = 0;
                System.out.println(i);
                DFSUtil(adj, i, visited, z);
            }

        }

        return count;
    }

    public void maxComm() {

        int[] visitedd = new int[1000];
        Graph.Graph g = new Graph.Graph(network, visitedd);
        int[] idd = g.longestComponent();

        for (int i = 0; i < idd.length; i++) {
            System.out.println(getUserByID(idd[i]).getUsername());
        }


    }

}
