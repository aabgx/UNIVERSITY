package ui;

import constants.Constants;
import domain.Friendship;
import domain.User;
import service.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UI {

    private final Scanner scanner;
    private final Service service;
    private boolean authenticated = false;


    public UI(Service service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        //noinspection InfiniteLoopStatement
        while (true) {
            loop();
        }
    }

    private void loop() {
        printMenu();
        String string = readLine(">>> ");

        try {
            if (authenticated) {
                switch (string) {
                    case "1" -> addFriend();
                    case "2" -> removeFriend();
                    case "3" -> listFriends();
                    case "4" -> signOut();
                    case "5" -> deleteAccount();
                    case "6" -> getCommunitiesNumber();
                    case "7" -> largestComunity();
                    case "8" -> updateUserName();
                    default -> {
                    }
                }
            } else {
                switch (string) {
                    case "1" -> signIn();
                    case "2" -> signUp();
                    case "3" -> System.exit(-1);
                    default -> {
                    }
                }
            }
        } catch (Error e) {
            System.out.println(e.getMessage());
        }


    }

    private void printMenu() {
        if (authenticated) {
            System.out.println("1. Add friend");
            System.out.println("2. Remove friend");
            System.out.println("3. Friends list");
            System.out.println("4. Sign out");
            System.out.println("5. Delete account");
            System.out.println("6. Communities number");
            System.out.println("7. Largest community");
            System.out.println("8. Update user name");
        } else {
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
        }
    }

    private String readLine(String helper) {
        System.out.print(helper);
        return scanner.nextLine();
    }

    private void signIn() {
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        authenticated = service.loginUser(email, password);
        if (authenticated) {
            User user = service.getCurrentUser();
            System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName());
        }
    }

    private void signUp() {
        String firstName = readLine("First Name: ");
        String lastName = readLine("Last Name: ");
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        String birthdayString = readLine("BirthDay(dd-MM-yyyy): ");
        try {
            LocalDate birthday = LocalDate.parse(birthdayString, Constants.STANDARD_DATE_FORMAT);
            authenticated = service.createUser(firstName, lastName, email, password, birthday);
            if (!authenticated) {
                System.out.println("Invalid credentials");
            }
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }

    }

    private void signOut() {
        service.logoutUser();
        authenticated = false;
    }

    private void addFriend() {
        String friendId = readLine("FiendId: ");
        service.addFriend(friendId);
    }

    private void removeFriend() {
        String friendshipId = readLine("FriendshipId: ");
        service.removeFriend(friendshipId);
    }

    private void listFriends() {
        System.out.println("Friends: ");
        for (Friendship friendship : service.getUserFriendships()) {
            System.out.println(friendship);
        }
        System.out.println("---------");
    }

    private void deleteAccount() {
        service.deleteAccount();
        System.out.println("Account deleted");
        authenticated = false;
    }

    public void getCommunitiesNumber() {
        System.out.println("Communities: " + service.getCommunitiesNumber());
    }

    private void largestComunity(){
        System.out.println("Largest community: " + service.getLargestCommunity());
    }

    void updateUserName() {
        String newFirstName = readLine("New First Name: ");
        String newLastName = readLine("New Last Name: ");
        service.updateUserName(newFirstName, newLastName);
    }
}
