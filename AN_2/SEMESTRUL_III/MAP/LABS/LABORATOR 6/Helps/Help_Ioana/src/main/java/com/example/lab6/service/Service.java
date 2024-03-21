package com.example.lab6.service;

import com.example.lab6.algoritmi.DFS;
import com.example.lab6.algoritmi.DrumLung;
import com.example.lab6.domain.Friendship;
import com.example.lab6.domain.Network;
import com.example.lab6.domain.Pair;
import com.example.lab6.domain.User;
import com.example.lab6.events.UserChangeEvent;
import com.example.lab6.exception.CustomException;
import com.example.lab6.observer.Observable;
import com.example.lab6.observer.Observer;
import com.example.lab6.repo.FriendRepositoryDatabase;
import com.example.lab6.repo.UserRepositoryDatabase;
import com.example.lab6.validator.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service implements Observable<UserChangeEvent> {
    final private UserRepositoryDatabase repo1;
    final private FriendRepositoryDatabase repo2;
    final private Validator validare;
    private Network retea;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private int id=1;

    private List<Observer<UserChangeEvent>> obs = new ArrayList<>();

    public Service(UserRepositoryDatabase repo1, Validator validare, FriendRepositoryDatabase repo2)
    {
        this.repo1 = repo1;
        this.validare = validare;
        this.repo2 = repo2;
        this.getUserList();
    }
    public List<User> getUserList()
    {
        if(repo1.getList().size()==0)
        {
            return repo1.getList();
        }
        id=repo1.getList().get(repo1.getList().size()-1).getId();
        id++;
        return repo1.getList();
    }
    public Network getRetea()
    {
        return retea;
    }
    public List<Friendship> getFriendsList()
    {
        return repo2.getList();
    }
    public List<User> getUserFriends(User user)
    {
        List<User> prieteni = new ArrayList<>();
        for(Friendship f:getFriendsList())
        {
            if(f.getU1().equals(user))
            {
                prieteni.add(f.getU2());
            }
            if(f.getU2().equals(user))
            {
                prieteni.add(f.getU1());
            }
        }
        return prieteni;
    }
    public void addUserService(String userName, String userPassword)
    {
        User userToAdd = new User(userName,userPassword);
        if(repo1.getList().stream().filter(x->x.equals(userToAdd)).collect(Collectors.toList()).size()==0)
        {
            try{
                validare.validateUser(userName,userPassword);
                userToAdd.setId(id++);
                repo1.save(userToAdd);
            }
            catch(CustomException e) {
                System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
            }
        }
        throw new RuntimeException();

    }
    public List<Friendship> findUserThroughFriendList(User userToBeFound)
    {
        List<Friendship> friendToBeRemoved = new ArrayList<>();
        for(Friendship e:repo2.getList())
        {
            if(e.getU1().equals(userToBeFound) || e.getU2().equals(userToBeFound))
            {
                friendToBeRemoved.add(friendToBeRemoved.size(),e);
            }
        }
        return friendToBeRemoved;
    }
    public void checkFriend(User userToBeRemoved)
    {
        List<Friendship> friendToBeRemoved = findUserThroughFriendList(userToBeRemoved);
        for (Friendship e : friendToBeRemoved)
        {
            repo2.delete(e);
        }
    }
    public void removeUser(String userName,String userPassword)
    {
        User userToRemove = new User(userName,userPassword);
        for(User e:repo1.getList())
        {
            if(e.getNume().equals(userName) && e.getParola().equals(userPassword))
            {
                userToRemove.setId(e.getId());
                break;
            }
        }
        if(userToRemove.getId()==null)
        {
            System.out.println("Nu exista utilizatorul!\n");
            return;
        }
        checkFriend(userToRemove);
        repo1.delete(userToRemove);
    }
    public void addFriendService(int userName1, int userName2, LocalDateTime data)
    {
        if(userName1==userName2)
        {
            return;
        }
        User userToAdd1 = repo1.find(userName1);
        User userToAdd2 = repo1.find(userName2);
        if(userToAdd1!=null && userToAdd2!=null) {
            Friendship friend1 = new Friendship(userToAdd1, userToAdd2, data);
            Pair<Integer> p1 = new Pair<>(userToAdd1.getId(), userToAdd2.getId());
            friend1.setId(p1);
            Friendship friend2 = new Friendship(userToAdd2, userToAdd1, data);
            Pair<Integer> p2 = new Pair<>(userToAdd2.getId(), userToAdd1.getId());
            friend2.setId(p2);
            Friendship exista3 = repo2.find(friend1.getId());
            if (exista3 == null) {
                friend1.setAcceptat(false);
                repo2.save(friend1);
            }
            else
            {
                System.out.println("Cei doi utilizatori sunt deja prieteni!");
            }
        }
        else
        {
            if(userToAdd1==null)
            {
                System.out.println("Utilizatorul cu id-ul "+userName1+" nu exista!");
            }
            else
            {
                System.out.println("Utilizatorul cu id-ul "+userName2+" nu exista!");
            }
        }
    }
    public void setAcceptatFriendService(Friendship f)
    {
        for(Friendship val:getFriendsList())
        {
            if(f.equals(val))
            {
                repo2.delete(val);
                f.setAcceptat(true);
                repo2.save(f);
                return;
            }
        }
    }
    public void removeFriendService(int userName1, int userName2,LocalDateTime data)
    {
        User userToAdd1 = repo1.find(userName1);
        User userToAdd2 = repo1.find(userName2);
            Friendship friend1 = new Friendship(userToAdd1,userToAdd2,data);
            Pair<Integer> p1 = new Pair<>(userToAdd1.getId(),userToAdd2.getId());
            friend1.setId(p1);
            Friendship friend2 = new Friendship(userToAdd2,userToAdd1,data);
            Pair<Integer> p2 = new Pair<>(userToAdd2.getId(),userToAdd1.getId());
            friend2.setId(p2);
            Friendship exista3 = repo2.find(friend1.getId());
            if(exista3!=null)
            {
                repo2.delete(friend1);
            }
            if(exista3==null)
            {
                System.out.println("Cei doi utilizatori nu sunt prieteni!");
            }
    }
    public int numarComponente()
    {
        retea = new DFS(getFriendsList(), getUserList());
        retea.componenteConexe();
        return retea.getNumber();
    }
    public void lungimeDrumLung(DrumLung d)
    {
        d.componenteConexe();
        getRetea().setSociabila(d.getDrumLung());
    }
    public void setRetea(Network retea) {
        this.retea = retea;
    }
    public void secvmax()
    {
        DrumLung retea = new DrumLung(getFriendsList(),getUserList());
        setRetea(retea);
        lungimeDrumLung(retea);
        retea.getDrumMaximComponenta();
    }

    @Override
    public void addObserver(Observer<UserChangeEvent> e)
    {
        obs.add(e);
    }

    @Override
    public void removeObserver(Observer<UserChangeEvent> e)
    {
        obs.remove(e);
    }

    @Override
    public void notifyObservers(UserChangeEvent entity)
    {
        obs.stream().forEach(x->x.update(entity));
    }
}