package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.Pair;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.exceptions.EntityAlreadyFound;
import com.example.socialnetworkgui.domain.exceptions.EntityNotFound;
import com.example.socialnetworkgui.domain.exceptions.ValidationException;
import com.example.socialnetworkgui.repo.Repository;
import com.example.socialnetworkgui.utils.events.ChangeEventType;
import com.example.socialnetworkgui.utils.events.FriendshipEntityChangeEvent;
import com.example.socialnetworkgui.utils.events.UserEntityChangeEvent;
import com.example.socialnetworkgui.utils.observer.Observable;
import com.example.socialnetworkgui.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceGUI implements Observable<FriendshipEntityChangeEvent> {

    private User loggedUser= null;

    private Repository<Long, User> userRepo;
    private Repository<Pair<Long, Long>, Friendship> friendshipRepo;
    private List<Observer<FriendshipEntityChangeEvent>> observers= new ArrayList<>();

    public ServiceGUI(Repository<Long, User> userRepo, Repository<Pair<Long, Long>, Friendship> friendshipRepo) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
        this.connectFriends();
    }

    private void connectFriends(){
        for (User u : userRepo.findAll()){
            u.getFriends().clear();
        }

        for(Friendship f: friendshipRepo.findAll()){
            User p1= userRepo.findOne(f.getId().getFirst());
            User p2= userRepo.findOne(f.getId().getSecond());
            p1.addFriend(p2);
            p2.addFriend(p1);
        }
    }

    public User logIn(String firstName, String lastName, String email){
        User found= findByNameEmail(firstName, lastName, email);
        return found;
    }

    public void logOut(){
        loggedUser=null;
    }

    public User findByNameEmail(String firstName, String lastName, String email){
        for(User u: getAllUsers()){
            if(u.getFirstName().equals(firstName)&&u.getLastName().equals(lastName)&&
            u.getEmail().equals(email)) {
                loggedUser=u;
                return u;
            }
        }
        throw new EntityNotFound("User-ul nu exista!");
    }

    public User getLoggedUser(){
        return this.loggedUser;
    }

    /**
     * Adds friendship with given ids
     * @param id1- Long, id of first user
     * @param id2- Long, id of second user
     * @throws ValidationException if Friendship is not valid
     * @throws EntityAlreadyFound if friendship already exists
     */
    public void addFriendship(Long id1, Long id2){
        LocalDateTime date= LocalDateTime.now();
        Friendship added= friendshipRepo.save(new Friendship(id1, id2, date));
        if(added!=null){
            //System.out.println("Friendship already exists");
            //exceptie
            throw new EntityAlreadyFound("Friendship already exists!");
        }
        else{
            if(userRepo.findOne(id1)==null){
                throw new EntityNotFound("User with id:" + id1+" does not exist!");
            }
            if(userRepo.findOne(id2)==null){
                throw new EntityNotFound("User with id:" + id2+" does not exist!");
            }
            connectFriends();
            notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD, added));
        }
    }

    /**
     * Removes friendship formed between users with given ids
     * @param id1- Long, id of first user
     * @param id2- Long, id of second user
     */
    public void removeFriendship(Long id1, Long id2){
        Friendship removed= friendshipRepo.delete(new Pair<>(id1, id2));
        if(removed==null){
            throw new EntityNotFound("Friendship does not exist!");
        }
        else{
            this.connectFriends();
            notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.DELETE, removed));
        }
    }

    private Long generateID(){
        Long maxID=0L;
        for(User u: userRepo.findAll()){
            if(u.getId()>maxID) maxID=u.getId();
        }
        int found;
        for(Long i=1L; i<=maxID;i++){
            found=0;
            for(User u: userRepo.findAll()){
                if(Objects.equals(u.getId(), i)){
                    found=1;
                    break;
                }
            }
            if(found==0) return i;
        }
        return maxID+1;
    }

    public User findWithEmail(String email){
        for(User u: userRepo.findAll()){
            if(u.getEmail().equals(email)) return u;
        }
        return null;
    }

    /**
     * Add a user in repository
     * @param fName- String, user's firstName
     * @param lName- String, user's lastName
     * @param email- String, user's email
     * @return User, added user (null if it did not exist before)
     * @throws EntityAlreadyFound if user already exists
     * @throws ValidationException if user is not valid
     */
    public User addUser(String fName, String lName, String email){
        User newUser= new User(fName, lName, email);
        newUser.setId(this.generateID());
        if(findWithEmail(newUser.getEmail())!=null){
            throw new EntityAlreadyFound("Userul cu emailul dat exista deja in lista!");
        }
        return userRepo.save(newUser);
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

    public Iterable<User> getAllUsers(){
        return userRepo.findAll();
    }
}
