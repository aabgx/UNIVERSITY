package com.example.lab6.ui;

import com.example.lab6.domain.Community;
import com.example.lab6.domain.Friendship;
import com.example.lab6.domain.Nod;
import com.example.lab6.domain.User;
import com.example.lab6.service.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Ui {
     private Service serv;
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    protected final static DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Ui(Service serv) {
        this.serv = serv;
    }

    public void start() {

        System.out.println(ANSI_PURPLE + "1.Add user.\n2.Remove user.\n3.Add friend.\n4.Remove friend.\n5.Numarul de comunitati.\n6.Afisare cea mai sociabila comunitate\n7.Meniu\n8.Afisare liste.\n9.Exit" + ANSI_RESET);
        while (true)
        {
            System.out.println("Optiunea dumneavoastra: ");
            Scanner read = new Scanner(System.in);
            String optiune = read.nextLine();
            switch (optiune)
            {
                case "1":System.out.print("User: ");
                    String userName = read.nextLine();
                    System.out.print("Parola: ");
                    String userPassword = read.nextLine();
                    serv.addUserService(userName,userPassword);
                    System.out.println();break;
                case "2":System.out.print("User: ");
                    userName = read.nextLine();
                    //read.nextLine();
                    System.out.print("Parola: ");
                    userPassword = read.nextLine();
                    serv.removeUser(userName,userPassword);
                    System.out.println();break;
                case "3":System.out.print("Id: ");
                    int userName1 = read.nextInt();
                    System.out.print("Id: ");
                    int userName2 = read.nextInt();
                    read.nextLine();
                    System.out.print("Data(dd/MM/yyyy HH:mm): ");
                    String userDate = read.nextLine();
                    serv.addFriendService(userName1, userName2, LocalDateTime.parse(userDate,formatter));
                    System.out.println();break;
                case "4":System.out.print("Id: ");
                    userName1 = read.nextInt();
                    System.out.print("Id: ");
                    userName2 = read.nextInt();
                    read.nextLine();
                    System.out.print("Data(dd/MM/yyyy HH:mm): ");
                    userDate = read.nextLine();
                    serv.removeFriendService(userName1, userName2, LocalDateTime.parse(userDate,formatter));
                    System.out.println();break;
                case "5":System.out.println(ANSI_CYAN + "Numarul de componente conexe: \n" + serv.numarComponente() + ANSI_RESET);
                    int id=1;
                    for (Community c : serv.getRetea().getComponenteConexe()) {
                        System.out.println(ANSI_BLUE +id+"."+c + ANSI_RESET);
                        id++;
                    }break;
                case "6":serv.secvmax();
                    System.out.println(ANSI_CYAN + "Comunitatea cea mai sociabila este: " + serv.getRetea().getDrumLung() + ANSI_RESET);
                    System.out.println(ANSI_BLUE + "Cu cea mai ramura cea mai sociabila: ");
                    for (Nod s : serv.getRetea().secventaDrumLung()) {
                        System.out.print(s);
                    }
                    System.out.println(ANSI_RESET);break;
                case "7": System.out.println(ANSI_PURPLE + "1.Add user.\n2.Remove user.\n3.Add friend.\n4.Remove friend.\n5.Numarul de comunitati.\n6.Afisare cea mai sociabila comunitate\n7.Meniu\n8.Afisare liste.\n9.Exit" + ANSI_RESET);break;
                case "8":for (User e : serv.getUserList()) {
                    System.out.printf(ANSI_GREEN + e.toString() + ANSI_RESET);
                }
                    for (Friendship e : serv.getFriendsList()) {
                        System.out.printf(ANSI_PURPLE + e.toString() + ANSI_RESET);
                    }break;
                case "9":return ;
            }
        }
    }
}
