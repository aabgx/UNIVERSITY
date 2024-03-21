package service;

import domain.Friendship;
import domain.Pair;
import domain.User;
import domain.exceptions.EntityAlreadyFound;
import domain.exceptions.EntityNotFound;
import repo.Repository;

import java.util.*;

public class Service {
    private Repository<Long, User> userRepo;
    private Repository<Pair<Long, Long>, Friendship> friendshipRepo;

    public Service(Repository<Long, User> userRepo, Repository<Pair<Long, Long>, Friendship> friendshipRepo) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
        connectFriends();
    }

    /**
     * Prints all users in file
     */
    public void printUsers(){
        for(User u: userRepo.findAll()){
            System.out.println(u);
        }
    }

    /**
     * Prints all friendships in file
     */
    public void printFriendships(){
        for(Friendship f: friendshipRepo.findAll()){
            System.out.println(f);
        }
    }

    /**
     * Adds friends in list for each user id in friendships file
     */
    private void connectFriends(){
        for(Friendship f: friendshipRepo.findAll()){
            User p1= userRepo.findOne(f.getId().getFirst());
            User p2= userRepo.findOne(f.getId().getSecond());
            p1.addFriend(p2);
            p2.addFriend(p1);
        }
    }

    /**
     * Generates ID for newly added User
     * @return Long, generated ID
     */
    private Long generateID(){
        Long maxID=0L;
        for(User u: userRepo.findAll()){
            if(u.getId()>maxID) maxID=u.getId();
        }
        return maxID+1;
    }

    /**
     * Returns user with given email
     * @param email, String, the given email
     * @return User, user with given email or null if such user does not exist
     */
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
     * @throws domain.exceptions.ValidationException if user is not valid
     */
    public User addUser(String fName, String lName, String email){
        User newUser= new User(fName, lName, email);
        newUser.setId(this.generateID());
        if(findWithEmail(newUser.getEmail())!=null){
            throw new EntityAlreadyFound("Userul cu emailul dat exista deja in lista!");
        }
        return userRepo.save(newUser);
    }

    /**
     * Deletes user with given id
     * @param id- Long, user id
     * @throws EntityNotFound if user with given id does not exist
     */
    public void deleteUser(Long id){
        User deleted= userRepo.delete(id);
        if(deleted!=null) {
            for (Friendship f : getFriendships(deleted)) {
                //delete friendship in friendshipRepo
                friendshipRepo.delete(new Pair<>(f.getId().getFirst(), f.getId().getSecond()));

                //remove friend in friends list
                User first = userRepo.findOne(f.getId().getSecond());
                first.removeFriend(deleted);
            }
        }
        else throw new EntityNotFound("Userul cu id-ul dat nu exista in lista!");
    }

    /**
     * Gets list of friendships for deleted user
     * @param deleted, User, user to be deleted
     * @return List<Friendship>, list of all friendships of deleted user
     */
    private List<Friendship> getFriendships(User deleted){
        List<Friendship> friends= new ArrayList<Friendship>();
        for(User u: deleted.getFriends()){
            Long id1= deleted.getId();
            Long id2= u.getId();
            friends.add(new Friendship(id1, id2));
        }
        return friends;
    }

    /**
     * Adds friendship with given ids
     * @param id1- Long, id of first user
     * @param id2- Long, id of second user
     * @throws domain.exceptions.ValidationException if Friendship is not valid
     * @throws EntityAlreadyFound if friendship already exists
     */
    public void addFriendship(Long id1, Long id2){
        Friendship added= friendshipRepo.save(new Friendship(id1, id2));
        if(added!=null){
            //System.out.println("Friendship already exists");
            //exceptie
            throw new EntityAlreadyFound("Freindship already exists!");
        }
        else{
            User u1= userRepo.findOne(id1);
            User u2= userRepo.findOne(id2);
            u1.addFriend(u2);
            u2.addFriend(u1);
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
            //System.out.println("Friendship not found");
            throw new EntityNotFound("Friendship does not exist!");
        }
        else{
            User u1= userRepo.findOne(id1);
            User u2= userRepo.findOne(id2);
            u1.removeFriend(u2);
            u2.removeFriend(u1);
        }
    }

    private int nrCommunities;

    /**
     * Performs Depth First Search starting from User start
     * @param start- User, user from which DFS begins
     * @param map- HashMap<User, Integer>, map to mark visited Users in DFS
     */
    private void DFS(User start, Map<User, Integer> map){
        map.replace(start, 1);
        for(Friendship f: friendshipRepo.findAll()){
            if(Objects.equals(f.getId().getFirst(), start.getId())){
                if(map.get(userRepo.findOne(f.getId().getSecond()))==0)
                    DFS(userRepo.findOne(f.getId().getSecond()), map);
            }
            if(Objects.equals(f.getId().getSecond(), start.getId())){
                if(map.get(userRepo.findOne(f.getId().getFirst()))==0)
                    DFS(userRepo.findOne(f.getId().getFirst()), map);
            }
        }
    }

    /**
     * Finds number of connex components in users graph
     * @return int, number of communities (connex components)
     */
    public int getNrCommunities(){
        Collection<User> all= (Collection<User>)userRepo.findAll();
        //Optional<User> first= all.stream().findFirst();
        int nrUsers= all.size();
        Map<User, Integer> visited= new HashMap<>(nrUsers);
        for(User u: userRepo.findAll()){
            visited.put(u,0);
        }
        nrCommunities=0;

        for(User usr: userRepo.findAll()){
            if(visited.get(usr)==0){
                nrCommunities++;
                DFS(usr, visited);
            }
        }
        return nrCommunities;
    }

    public int getMostSociableCommunity(){
        return 0;//??
    }
}
