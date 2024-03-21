package com.example.socialnetwork.service;

import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.repository.Repository0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceNetwork {
    private Map<User, Boolean> visited;
    private Map<User, List<User>> adj;
    private Repository0<Long, User> users;
    private Repository0<Long, Friendship> friends;
    private List<List<User>> components;

    public ServiceNetwork(Repository0<Long, User> users, Repository0<Long, Friendship> friends) {
        this.users = users;
        this.friends = friends;
    }

    private void initialize(){
        adj = new HashMap<User, List<User>>();
        for (User user : users.findAll()) {
            adj.put(user, new ArrayList<User>());
        }
        components = new ArrayList<List<User>>();
        visited = new HashMap<User, Boolean>();
        for (User user : users.findAll()) {
            visited.put(user, false);
        }
        addAll();
    }

    private void addUser(User user1, User user2) {
        adj.computeIfAbsent(user1, k -> new ArrayList<>());
        adj.get(user1).add(user2);
        visited.putIfAbsent(user1, false);
        visited.putIfAbsent(user2, false);
    }

    private void addAll() {
        for (Friendship friendship : friends.findAll()) {
            User user1 = users.findOne(friendship.getLeft());
            User user2 = users.findOne(friendship.getRight());
            addUser(user1, user2);
            addUser(user2, user1);
            visited.putIfAbsent(user1, false);
            visited.putIfAbsent(user2, false);
        }
    }

    private void dfs(User user, List<User> list) {
        visited.put(user, true);
        list.add(user);
        for (User u : adj.get(user)) {
            if (!visited.get(u)) {
                dfs(u, list);
            }
        }
    }

    public List<List<User>> getComponents() {
        initialize();
        for (User user : users.findAll()) {
            if (!visited.get(user)) {
                List<User> list = new ArrayList<>();
                dfs(user, list);
                components.add(list);
            }
        }
        return components;
    }

    public List<User> getComponent(User user) {
        List<User> list = new ArrayList<>();
        dfs(user, list);
        return list;
    }

    public List<User> getComponent(Long id) {
        return getComponent(users.findOne(id));
    }

    public int getNumberOfComponents() {
        return getComponents().size();
    }

    public int getLongestPath(User user1) {
        List<User> path = new ArrayList<>();
        List<User> queue = new ArrayList<>();
        Map<User, User> parent = new HashMap<>();
        queue.add(user1);
        parent.put(user1, null);
        while (!queue.isEmpty()) {
            User user = queue.get(0);
            queue.remove(0);
            for (User u : adj.get(user)) {
                if (!parent.containsKey(u)) {
                    parent.put(u, user);
                    queue.add(u);
                }
            }
        }
        User user = user1;
        while (user != null) {
            path.add(user);
            user = parent.get(user);
        }
        return path.size();
    }

    public int getComponentLongestPath(List<User> component) {
        int max = 0;
        for (User user : component) {
            int path = getLongestPath(user);
            if (path > max) {
                max = path;
            }
        }
        return max;
    }

    public List<User> getLargestComponent(){
        int max = 0;
        for(List<User> list : getComponents()){
            if(getComponentLongestPath(list) > max){
                max = getComponentLongestPath(list);
            }
        }
        for(List<User> list : getComponents()){
            if(getComponentLongestPath(list) == max){
                return list;
            }
        }
        return null;
    }

}
