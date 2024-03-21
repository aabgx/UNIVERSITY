package com.example.socialnetwork;

import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.repository.database.FriendshipDBRepository;
import com.example.socialnetwork.repository.database.UserDBRepository;
import com.example.socialnetwork.service.Service;
import com.example.socialnetwork.service.ServiceNetwork;

import java.time.LocalDateTime;

public class RunDB {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/academic";
        UserDBRepository userDBRepository = new UserDBRepository(url, "postgres", "postgres");
        FriendshipDBRepository friendshipDBRepository = new FriendshipDBRepository(url, "postgres", "postgres");

        Service service = new Service(userDBRepository, friendshipDBRepository);
        ServiceNetwork serviceNetwork = new ServiceNetwork(userDBRepository, friendshipDBRepository);

        service.getAllFriendships().forEach(System.out::println);
        System.out.println("--------------------------------------------------");
        service.getUsers().forEach(System.out::println);
        System.out.println("--------------------------------------------------");
        service.getAllFriends(9L).forEach(System.out::println);
    }

}

