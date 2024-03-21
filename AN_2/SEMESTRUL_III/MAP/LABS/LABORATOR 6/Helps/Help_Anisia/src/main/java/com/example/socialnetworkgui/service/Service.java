package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.Pair;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.exceptions.EntityAlreadyFound;
import com.example.socialnetworkgui.domain.exceptions.EntityNotFound;
import com.example.socialnetworkgui.domain.exceptions.ValidationException;
import com.example.socialnetworkgui.repo.Repository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service class - defines boundary between UI layer and Repository layer
 */
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

    /**
     * Generates ID for newly added User
     * @return Long, generated ID
     */
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

    public List<User> findWithName(String fName, String lName){
        List<User> found= new ArrayList<>();
        for(User u: userRepo.findAll()){
            if(u.getFirstName().equals(fName)&&u.getLastName().equals(lName)){
                found.add(u);
            }
        }
        return found;
    }

    /**
     * Serches for user with given id
     * @param id, Long, given id
     * @return User, user with given id or null if such user does not exist
     */
    public User findById(Long id){
        return userRepo.findOne(id);
    }

    public Friendship findById(Pair<Long, Long> id){ return friendshipRepo.findOne(id); };

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
                //User first = userRepo.findOne(f.getId().getSecond());
                //first.removeFriend(deleted);
            }
            connectFriends();
        }
        else throw new EntityNotFound("Userul cu id-ul dat nu exista in lista!");
    }

    /**
     * Gets list of friendships for deleted user
     * @param deleted, User, user to be deleted
     * @return List<Friendship>, list of all friendships of deleted user
     */
    private List<Friendship> getFriendships(User deleted){
        List<Friendship> friends= new ArrayList<>();
        for(User u: deleted.getFriends()){
            Long id1= deleted.getId();
            Long id2= u.getId();
            LocalDateTime d= LocalDateTime.now();  //nu conteaza data pe care o dam, nu o vom folosi niciodata
            friends.add(new Friendship(id1, id2, d));
        }
        return friends;
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
            /*User u1= userRepo.findOne(id1);
            User u2= userRepo.findOne(id2);
            u1.addFriend(u2);
            u2.addFriend(u1);*/
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
            this.connectFriends();
            /*User u1= userRepo.findOne(id1);
            User u2= userRepo.findOne(id2);
            u1.removeFriend(u2);
            u2.removeFriend(u1);*/
        }
    }

    /**
     * Updates firstName, lastName and email for given user
     * @param newUser User, the newly created user
     * @throws EntityNotFound if user to be updated does not exist
     */
    public void updateUser(User newUser){
        User oldUser= userRepo.findOne(newUser.getId());
        User returned= userRepo.update(newUser);
        if(returned!=null){
            /*for(User f: oldUser.getFriends()){
                f.updateFriend(newUser);
            }*/
            this.connectFriends();
        }
        else throw new EntityNotFound("User does not exist in repository!");
    }

    public void updateFriendship(Friendship newFriendship){
        Friendship oldF= friendshipRepo.findOne(new Pair<>(newFriendship.getId().getFirst(), newFriendship.getId().getSecond()));
        Friendship returned= friendshipRepo.update(newFriendship);
        if(returned==null){
            throw new EntityNotFound("Friendship with given id does not exist!");
        }
    }

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
     * Return the number of users in userRepository
     * @return int, number of users in repository
     */
    private int getUserRepoSize(){
        Collection<User> all= (Collection<User>)userRepo.findAll();
        return all.size();
    }

    /**
     * Finds number of connex components in users graph
     * @return int, number of communities (connex components)
     */
    public int getNrCommunities(){
        int nrUsers= getUserRepoSize();
        Map<User, Integer> visited= new HashMap<>(nrUsers);
        for(User u: userRepo.findAll()){
            visited.put(u,0);
        }
        int nrCommunities = 0;

        for(User usr: userRepo.findAll()){
            if(visited.get(usr)==0){
                nrCommunities++;
                DFS(usr, visited);
            }
        }
        return nrCommunities;
    }

    private int maxLength=0;
    private int currentLength=0;

    private List<User> compMax= new ArrayList<>();

    /**
     * Assign number of order (Community) to Users belonging to that community
     * @param start User, starting point in DFS
     * @param visited Map<User, Integer> holds the Community Number for each User in repository
     * @param Community int, current Community Number
     */
    private void DFS2(User start, Map<User, Integer> visited, int Community){
        currentLength++;
        visited.replace(start,Community);
        for(User friend: start.getFriends()){
            if(visited.get(friend)==0){
                DFS2(friend, visited, Community);
                visited.replace(friend, 0);
            }
        }
        if(currentLength>maxLength){
            compMax.clear();
            maxLength=currentLength;
            for(User u: userRepo.findAll())
                if(visited.get(u)==Community) compMax.add(u);
        }
        currentLength--;
    }

    private void DFS3(User start, Map<User, Integer> visited, int Community){
        visited.replace(start,Community);
        for(User friend: start.getFriends()){
            if(visited.get(friend)==0){
                DFS3(friend, visited, Community);
            }
        }
    }

    /**
     * return list of users in community with given number of order
     * @param visited Map<User,Integer>, sores number of community for each user
     * @param CommunityNr int, given community number
     * @return List<User>, list of users in community CommunityNr
     */
    public List<User> getInCommunity(Map<User,Integer> visited, int CommunityNr){
        List<User> users= new ArrayList<>();
        for(User u: userRepo.findAll()){
            if(visited.get(u)==CommunityNr) users.add(u);
        }
        return users;
    }

    /**
     * Finds community with the longest path (connex component with the longest path)
     * @return List<User>, users in most sociable community
     */
    public List<User> getMostSociableCommunity(){
        int maxim = 0;  //length of most sociable community
        int nrUsers= getUserRepoSize();
        Map<User, Integer> visited= new HashMap<>(nrUsers);
        Map<User, Integer> visitedMax= new HashMap<>(nrUsers);

        for(User u: userRepo.findAll()){
            visited.put(u,0);   //users do not belong to any community
            visitedMax.put(u, 0);
        }

        int nrOfCommunity=0;
        int nrOfMaximCommunity=0;
        List<User> sociableCommunity;

        for(User u: userRepo.findAll()){
            if(visited.get(u)==0){
                nrOfCommunity++;
                DFS3(u, visited, nrOfCommunity);  //assign number of community to users in that community
            }
        }

        System.out.println("Numar de comunitati:"+nrOfCommunity);
        for(int i=1;i<=nrOfCommunity;i++){
            List<User> currentCommunity= getInCommunity(visited, i);
            //System.out.println("Comunitatea: "+i);
            for(User u: currentCommunity){
                //DFS starting from each user in current community
                for(User k: currentCommunity) visited.replace(k, 0);
                maxLength=0;
                currentLength=0;
                DFS2(u, visited, i);
                if(maxLength> maxim){
                    maxim =maxLength;
                    nrOfMaximCommunity=i;  //store index of most sociable community
                    //System.out.println(i+" "+maxim);
                    //System.out.println(compMax);
                    for(User h: compMax) visitedMax.replace(h, nrOfMaximCommunity);
                }
                //System.out.println("Start: "+u+" "+maxLength);
            }
            //System.out.println("Comunitatea: "+i+" are lungimea maxima "+maxim);
        }

        sociableCommunity= getInCommunity(visitedMax, nrOfMaximCommunity);
        System.out.println("Lungime: "+maxim);
        return sociableCommunity;
    }
}
