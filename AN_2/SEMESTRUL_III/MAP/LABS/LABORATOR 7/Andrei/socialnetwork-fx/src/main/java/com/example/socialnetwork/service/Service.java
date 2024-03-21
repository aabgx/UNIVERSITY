package com.example.socialnetwork.service;

import com.example.socialnetwork.domain.FriendDTO;
import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.exception.RepoException;
import com.example.socialnetwork.repository.Repository0;
import com.example.socialnetwork.utils.Constants;
import com.example.socialnetwork.utils.events.ChangeEventType;
import com.example.socialnetwork.utils.events.UserChangeEvent;
import com.example.socialnetwork.utils.observer.Observable;
import com.example.socialnetwork.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Service implements Observable<UserChangeEvent>{
    private Repository0<Long, User> repoUser;
    private Repository0<Long, Friendship> repoFriendship;
    private List<Observer<UserChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<UserChangeEvent> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<UserChangeEvent> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(UserChangeEvent event) {
        observers.forEach(x -> x.update(event));
    }

    public Service(Repository0<Long, User> repoUser, Repository0<Long, Friendship> repoFriendship) {
        this.repoUser = repoUser;
        this.repoFriendship = repoFriendship;
    }

    public User addUser(User user) {
        return repoUser.save(user);
    }

    public Iterable<User> getAll() {
        return repoUser.findAll();
    }

    public User deleteUser(Long id) {
        boolean found = true;
        while (found) {
            found = false;
            for (Friendship friendship : repoFriendship.findAll()) {
                if (friendship.getLeft().equals(id) || friendship.getRight().equals(id)) {
                    repoFriendship.delete(friendship.getId());
                    found = true;
                    break;
                }
            }
        }
        return repoUser.delete(id);
    }

    public User findOne(Long id) {
        try {
            return repoUser.findOne(id);
        } catch (RepoException e) {
            return null;
        }
    }

    public List<FriendDTO> getAllFriends(Long id) {
        List<FriendDTO> friends = new ArrayList<>();
        for (Friendship friendship : repoFriendship.findAll()) {
            if (friendship.getLeft().equals(id)) {
                User user = repoUser.findOne(friendship.getRight());
                String date = friendship.getFriendsFrom().format(Constants.DATE_TIME_FORMATTER);
                String status = friendship.getStatus();
                if (status.equals("pending")) {
                    status = "sent";
                }
                FriendDTO friendDTO = new FriendDTO(user.getFirstName(), user.getLastName(), status, date, friendship.getId(), user.getId());
                friends.add(friendDTO);
            }
            if (friendship.getRight().equals(id)) {
                User user = repoUser.findOne(friendship.getLeft());
                String date = friendship.getFriendsFrom().format(Constants.DATE_TIME_FORMATTER);
                String status = friendship.getStatus();
                if (status.equals("pending")) {
                    status = "received";
                }
                FriendDTO friendDTO = new FriendDTO(user.getFirstName(), user.getLastName(), status, date, friendship.getId(), user.getId());
                friends.add(friendDTO);
            }
        }
        return friends;
    }


    public List<User> getAllFriendsAsUsers(Long id) {
        List<User> friends = new ArrayList<>();
        for (Friendship friendship : repoFriendship.findAll()) {
            if (friendship.getLeft().equals(id)) {
                User user = repoUser.findOne(friendship.getRight());
                friends.add(user);
            }
            if (friendship.getRight().equals(id)) {
                User user = repoUser.findOne(friendship.getLeft());
                friends.add(user);
            }
        }
        return friends;
    }

    public Friendship addFriendship(Friendship friendship) {
        repoFriendship.save(friendship);
        return friendship;
    }

    public Iterable<Friendship> getAllFriendships() {
        return repoFriendship.findAll();
    }

    public Friendship findFriendship(Long id1, Long id2) {
        for (Friendship f : repoFriendship.findAll()) {
            if (f.getIds().equals(Set.of(id1, id2))) {
                return f;
            }
        }
        return null;
    }

    public Iterable<User> getUsers() {
        return repoUser.findAll();
    }

    public void declineOrRemoveFriendship(Long friendshipId, Long userId) {
        Friendship friendship = repoFriendship.findOne(friendshipId);
        if (friendship == null) {
            throw new RepoException("Friendship does not exist");
        }
        if (friendship.getStatus().equals("friends")) {
            throw new RepoException("You are friends already!");
        }

        repoFriendship.delete(friendshipId);
        notifyObservers(new UserChangeEvent(ChangeEventType.UNIVERSAL, null));
    }

    public void acceptFriendship(Long friendshipId, Long userId) {
        Friendship friendship = repoFriendship.findOne(friendshipId);
        if (friendship == null) {
            throw new RepoException("Friendship does not exist");
        }
        if (friendship.getStatus().equals("friends")) {
            throw new RepoException("You are friends already!");
        }
        if (friendship.getLeft().equals(userId)) {
            throw new RepoException("You can't accept your own request!");
        }

        friendship.setStatus("friends");
        repoFriendship.update(friendship);
        notifyObservers(new UserChangeEvent(ChangeEventType.UNIVERSAL, null));
    }

    public void removeFriend(Long friendshipId, Long userId) {
        Friendship friendship = repoFriendship.findOne(friendshipId);
        if (friendship == null) {
            throw new RepoException("Friendship does not exist");
        }
        if (!friendship.getStatus().equals("friends")) {
            throw new RepoException("You can't remove someone that is not your friend!");
        }
        if (friendship.getLeft().equals(userId) || friendship.getRight().equals(userId)) {
            repoFriendship.delete(friendshipId);
            notifyObservers(new UserChangeEvent(ChangeEventType.UNIVERSAL, null));
        }
    }

    public void sendFriendRequest(Long userFromId, Long userToId) {
        Friendship friendship = findFriendship(userFromId, userToId);
        if (friendship != null) {
            throw new RepoException("Friendship already exists");
        }
        Friendship friendship1 = new Friendship(userFromId, userToId, LocalDateTime.now());
        repoFriendship.save(friendship1);
        notifyObservers(new UserChangeEvent(ChangeEventType.UNIVERSAL, null));
    }
}