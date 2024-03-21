package service;

import domain.Friendship;
import domain.User;
import repos.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Service {
    private final Repository<User> userRepository;
    private final Repository<Friendship> friendshipRepository;
    private String currentUserId = null;

    public Service(Repository<User> userRepository, Repository<Friendship> friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public boolean loginUser(String email, String password) {
        for (User user : userRepository.getAll()) {
            if (user.getEmail().equals(email)) {
                if (user.getPassword().equals(password)) {
                    currentUserId = user.getId();
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean createUser(String firstName, String lastName, String email, String password, LocalDate birthdate) {
        try {
            User newUser = new User(null, firstName, lastName, email, password, birthdate);
            userRepository.add(newUser);
            currentUserId = newUser.getId();
        } catch (Error e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public void logoutUser() {
        currentUserId = null;
    }

    /**
     * Add a friend to the current user
     *
     * @param friendId String
     */
    public void addFriend(String friendId) {
        User userB = userRepository.getById(friendId);
        User userA = userRepository.getById(currentUserId);
        if (userB == null || userA == null) {
            throw new Error("User not found");
        }
        friendshipRepository.add(new Friendship(null, currentUserId, friendId));
    }

    /**
     * Remove a friend by id from the current user
     *
     * @param friendshipId
     */
    public void removeFriend(String friendshipId) {
        Friendship friendship = friendshipRepository.getById(friendshipId);
        if (friendship == null) {
            throw new Error("Friendship not found");
        }
        friendshipRepository.remove(friendship);
    }

    public List<Friendship> getUserFriendships() {
        List<Friendship> friendships = new ArrayList<>();
        for (Friendship friendship : friendshipRepository.getAll()) {
            if (friendship.getFriendOneId().equals(currentUserId) || friendship.getFriendTwoId().equals(currentUserId)) {
                friendships.add(friendship);
            }
        }
        return friendships;
    }

    /**
     * Delete the current user account
     */
    public void deleteAccount() {
        if (currentUserId == null) {
            return;
        }
        for (Friendship friendship : getUserFriendships()) {
            friendshipRepository.remove(friendship);
        }
        userRepository.remove(userRepository.getById(currentUserId));
        ;
        currentUserId = null;
    }

    /**
     * Returns the current user id
     *
     * @return String
     */
    public String getCurrentUserId() {
        return currentUserId;
    }

    /**
     * Returns the current user
     *
     * @return User
     */
    public User getCurrentUser() {
        return userRepository.getById(currentUserId);
    }

    /**
     * DFS algorithm
     *
     * @param start   int
     * @param visited boolean[]
     */
    private void DFS(int start, boolean[] visited) {
        List<User> userList = userRepository.getAll();
        visited[start] = true;
        //System.out.println(visited[0]);
        for (int x = 0; x < userList.size(); x++) {
            if (!visited[x]) {
                for (Friendship f : friendshipRepository.getAll()) {
                    //daca il gasim pe primul la un id din lista de prietenii si prietenul sau nu e vizitat
                    if (f.getFriendOneId().equals(userList.get(start).getId()) && !visited[userList.indexOf(userRepository.getById(f.getFriendTwoId()))]) {
                        //marim dimensiunea comunitatii
                        communitySize++;
                        //continuam cu cautarea prietenilor acelui prieten
                        DFS(userList.indexOf(userRepository.getById(f.getFriendTwoId())), visited);
                    }
                    if (f.getFriendTwoId().equals(userList.get(start).getId()) && !visited[userList.indexOf(userRepository.getById(f.getFriendOneId()))]) {
                        communitySize++;
                        DFS(userList.indexOf(userRepository.getById(f.getFriendOneId())), visited);
                    }
                }
            }
        }
    }

    /**
     * Get community numbers
     *
     * @return int
     */
    public int getCommunitiesNumber() {
        List<User> userList = userRepository.getAll();

        int communities = 0;
        boolean[] visited = new boolean[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            visited[i] = false;
        }

        for (int i = 0; i < userList.size(); i++) {
            if (!visited[i]) {
                DFS(i, visited);
                communities++;
            }
        }

        return communities;
    }

    private int communitySize;

    public int getLargestCommunity() {
        List<User> userList = userRepository.getAll();

        int maxCommunitySize = 0;
        communitySize = 1;
        boolean[] visited = new boolean[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            visited[i] = false;
        }

        for (int i = 0; i < userList.size(); i++) {
            if (!visited[i]) {
                DFS(i, visited);
                maxCommunitySize = Math.max(maxCommunitySize, communitySize);
                communitySize = 1;
            }
        }

        return maxCommunitySize;
    }

    public void updateUserName(String firstName, String lastName) {
        User user = getCurrentUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepository.update(user);
    }
}
