package com.example.myfacebook.ui;

import com.example.myfacebook.domain.Friendship;
import com.example.myfacebook.domain.User;
import com.example.myfacebook.service.Service;

import java.util.List;
import java.util.Scanner;

public class Console {
    Service srv;

    public Console(Service srv) {
        this.srv = srv;
    }

    public void menu() {
        System.out.println("Facebook options list:");
        System.out.println("1.Add user");
        System.out.println("2.Remove user");
        System.out.println("3.Add friend");
        System.out.println("4.Remove friend");
        System.out.println("5.Print list of users");
        System.out.println("6.Print list of friends of user");
        System.out.println("7.Print number of communities");
        System.out.println("8.Print largest community");
        System.out.println("0.exit");
    }

    public void run() {

        while (true) {
            menu();
            Scanner in = new Scanner(System.in);
            String option = in.next();
            if (option.equals("1")) {

                try{
                    System.out.print("id: ");
                    int id = Integer.parseInt(in.next());
                    if(id>1000){
                        System.out.println("invalid data!");
                    }else{
                        System.out.print("name: ");
                        String name = in.next();

                        System.out.print("age: ");
                        int age = Integer.parseInt(in.next());

                        System.out.print("sex: ");
                        String sex = in.next();

                        System.out.print("city: ");
                        String city = in.next();

                        System.out.print("password: ");
                        String password = in.next();

                        srv.addUser(id, name, age, sex, city, password);
                    }



                }catch(Exception e){
                    System.out.println("invalid data!");
                }


            } else if (option.equals("2")) {
                System.out.println("enter id:");
                int idDel = Integer.parseInt(in.next());
                User us = srv.getUserByID(idDel);
                if(us!=null){
                    srv.removeUser(us);
                }else{
                    System.out.println("user does not exist!");
                }


            } else if (option.equals("3")) {
                try{
                    System.out.println("enter id:");
                    int fr1 = Integer.parseInt(in.next());
                    System.out.println("enter id of new friend:");
                    int fr2 = Integer.parseInt(in.next());
                    srv.addFriend(fr1, fr2);
                }catch(Exception e){
                    System.out.println("invalid data!");
                }


            } else if (option.equals("4")) {
                try{
                    System.out.println("enter id:");
                    int fr1 = Integer.parseInt(in.next());
                    System.out.println("enter id of friend to remove:");
                    int fr2 = Integer.parseInt(in.next());
                    srv.removeFriendship(fr1, fr2);
                }catch(Exception e){
                    System.out.println("invalid data!");
                }


            } else if (option.equals("5")) {
                List<User> usr = srv.getListUsers();
                for (User u : usr)
                    System.out.println(u.toString());

            } else if (option.equals("6")) {
                System.out.println("enter id:");

                int fr1 = Integer.parseInt(in.next());
                //List<User> usr = srv.getFriends(fr1);
                List<Friendship> fr = srv.getListFriendships();

                System.out.print(srv.getUserByID(fr1).getUsername() + "'s friends are: ");

                for (Friendship u : fr){
                    if(u.getU1()==fr1){
                        System.out.print((srv.getUserByID(u.getU2()).getUsername())+", ");
                    }
                    if(u.getU2()==fr1){
                        System.out.print((srv.getUserByID(u.getU1()).getUsername())+", ");
                    }
                }
                //System.out.print(u.getUsername() + ", ");

                System.out.println("\n");

            } else if (option.equals("7")) {
                int count = srv.howManyComunities();
                System.out.println("Number of current communities: " + count);

            } else if(option.equals("8")){
                System.out.println("Largest community: ");
                srv.maxComm();
                //System.out.println("\n");



            }else if (option.equals("0")) {
                break;
            }else{
                System.out.println("invalid option!");
            }
        }

    }
}
