import domain.Friendship;
import domain.User;
import domain.exceptions.EntityAlreadyFound;
import domain.exceptions.EntityNotFound;
import domain.exceptions.ValidationException;
import service.Service;

import java.util.Scanner;

public class UI {
    private Scanner scanner;
    private Service service;

    public UI(Service service) {
        this.service = service;
        this.scanner= new Scanner(System.in);
    }

    private void printMenu(){
        System.out.println("1. Print Users");
        System.out.println("2. Print Friendships");
        System.out.println("3. Add User");
        System.out.println("4. Remove User");
        System.out.println("5. Add friend");
        System.out.println("6. Remove Friend");
        System.out.println("7. Get Number of Communities");
        System.out.println("X. Leave app");
    }

    /**
     * Function that displays a message and reads a line of text
     * @param message- displayed message
     * @return String, the text to be read
     */
    private String readLine(String message){
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Function that displays a message and reads a Long number
     * @param message- displayed message
     * @return Long, the Long number to be read
     */
    private Long readLong(String message){
        System.out.print(message);
        return scanner.nextLong();
    }

    /**
     * Prints all users in file
     */
    private void printUsers(){
        service.printUsers();
    }

    /**
     * Prints all friendships in file
     */
    private void printFriendships(){
        service.printFriendships();
    }

    private void addUser(){
        String fName= readLine("First Name: ");
        String lName= readLine("Last Name: ");
        String email= readLine("Email: ");
        try{
            User added= service.addUser(fName, lName, email);
            if(added==null)
                System.out.println("Successfully added user!");
            else System.out.println("User already exists");
        }catch (ValidationException | IllegalArgumentException | EntityAlreadyFound e){
            System.out.println(e.getMessage());
        }
    }

    public void removeUser(){
        Long id= readLong("ID: ");
        scanner.nextLine();
        try{
            service.deleteUser(id);
            System.out.println("Deleted user!");
        }catch (EntityNotFound | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public void addFriendship(){
        Long id1= readLong("ID 1: ");
        scanner.nextLine();
        Long id2= readLong("ID 2: ");
        scanner.nextLine();
        try{
            service.addFriendship(id1, id2);
            System.out.println("Added friendship!");
        }catch (EntityAlreadyFound | ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    public void removeFriendship(){
        Long id1= readLong("ID 1: ");
        scanner.nextLine();
        Long id2= readLong("ID 2: ");
        scanner.nextLine();
        try{
            service.removeFriendship(id1, id2);
            System.out.println("Removed friendship!");
        }catch (EntityNotFound | ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    public void getNrCommunities(){
        System.out.println("Number of communities is: "+ service.getNrCommunities());
    }

    public void run(){
        boolean isRunning= true;
        while (isRunning){
            printMenu();
            String option= readLine("Option: ");
            switch (option){
                case "1"-> printUsers();
                case "2"-> printFriendships();
                case "3"-> addUser();
                case "4"-> removeUser();
                case "5"-> addFriendship();
                case "6"-> removeFriendship();
                case "7"-> getNrCommunities();
                case "X" -> isRunning=false;
                default -> System.out.println("Invalid command!");
            }
        }
    }
}
