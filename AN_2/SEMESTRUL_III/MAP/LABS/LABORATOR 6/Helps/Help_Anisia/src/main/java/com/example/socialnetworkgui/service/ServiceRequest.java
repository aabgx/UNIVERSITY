package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.*;
import com.example.socialnetworkgui.domain.exceptions.EntityAlreadyFound;
import com.example.socialnetworkgui.domain.exceptions.EntityNotFound;
import com.example.socialnetworkgui.domain.exceptions.ValidationException;
import com.example.socialnetworkgui.repo.Repository;
import com.example.socialnetworkgui.utils.events.ChangeEventType;
import com.example.socialnetworkgui.utils.events.FriendshipEntityChangeEvent;
import com.example.socialnetworkgui.utils.events.RequestEntityChangeEvent;
import com.example.socialnetworkgui.utils.observer.Observable;
import com.example.socialnetworkgui.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequest implements Observable<RequestEntityChangeEvent> {
    private Repository<Long, User> userRepo;
    private Repository<Pair<Long, Long>, Friendship> friendshipRepo;
    private Repository<Pair<Long, Long>, Request> requestRepo;
    private List<Observer<RequestEntityChangeEvent>> observers= new ArrayList<>();

    public ServiceRequest(Repository<Long, User> userRepo, Repository<Pair<Long, Long>, Friendship> friendshipRepo, Repository<Pair<Long, Long>, Request> requestRepo) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
        this.requestRepo = requestRepo;
    }

    @Override
    public void addObserver(Observer<RequestEntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<RequestEntityChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(RequestEntityChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Iterable<Request> getAllRequests(){
        return requestRepo.findAll();
    }

    public void sendRequest(Long id1, Long id2){
        Request r= new Request(id1, id2, LocalDateTime.now(), RequestStatus.SENT);
        Request added=  requestRepo.save(r);
        if(added!=null){
            throw new EntityAlreadyFound("Cererea exista deja!");
        }
        else{
            if(userRepo.findOne(id1)==null){
                throw new EntityNotFound("Userul 1 nu exista!");
            }
            if(userRepo.findOne(id2)==null){
                throw new EntityNotFound("Userul 2 nu exista!");
            }
        }
        notifyObservers(new RequestEntityChangeEvent(ChangeEventType.ADD, added));
    }

    public void deleteRequest(Long id1, Long id2){
        Request deleted= requestRepo.delete(new Pair<>(id1, id2));
        if(deleted==null){
            throw new EntityNotFound("Cererea nu exista!");
        }
        else{
            notifyObservers(new RequestEntityChangeEvent(ChangeEventType.DELETE, deleted));
        }
    }

    public void updateRequest(Request r){
        Request ret= requestRepo.update(r);
        if(ret==null) throw new EntityNotFound("Request not found!");
        else
            notifyObservers(new RequestEntityChangeEvent(ChangeEventType.UPDATE, ret));
    }

    public Iterable<Request> allRequests(){
        return requestRepo.findAll();
    }

    /**
     * Returns user with given id (id from requests table)
     * @param id
     * @return
     */
    public User getWithId(Long id){
        for(User u: userRepo.findAll()){
            if(u.getId().equals(id)) return u;
        }
        return null;
    }

    public User getWithName(String fName, String lName){
        for (User u: userRepo.findAll()){
            if(u.getFirstName().equals(fName)&&u.getLastName().equals(lName)) return u;
        }
        return null;
    }
}
