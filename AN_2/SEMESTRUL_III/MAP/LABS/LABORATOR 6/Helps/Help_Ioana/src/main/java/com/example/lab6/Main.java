package com.example.lab6;
import com.example.lab6.repo.FriendRepositoryDatabase;
import com.example.lab6.repo.UserRepositoryDatabase;
import com.example.lab6.service.Service;
import com.example.lab6.ui.Ui;
import com.example.lab6.validator.Validator;

public class Main {
    public static void main(String[] args)
    {
        //UserTest test1 = new UserTest();
        //FriendshipTest test2 = new FriendshipTest();
        //UserFriendshipsTest test3 = new UserFriendshipsTest();
        //CommunityTest test4 = new CommunityTest();

        //test1.testsUsers();
        //test2.testsFriends();
        //test3.testUserFriends();
        //test4.testCommunityComponent();
        UserRepositoryDatabase repo1 = new UserRepositoryDatabase("jdbc:postgresql://localhost:5432/Lab4","postgres","postgres");
        FriendRepositoryDatabase repo2 = new FriendRepositoryDatabase("jdbc:postgresql://localhost:5432/Lab4","postgres","postgres",repo1);
        Validator val = new Validator();
        Service serv = new Service(repo1,val, repo2);
        Ui meniu = new Ui(serv);
        meniu.start();
    }
}