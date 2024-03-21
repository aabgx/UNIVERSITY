package socialnetwork.com.reteadesocializare;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.com.domain.Friend;
import socialnetwork.com.domain.User;
import socialnetwork.com.reteadesocializare.Observers.Observable;
import socialnetwork.com.reteadesocializare.Observers.ObservableI;
import socialnetwork.com.service.Service;
import socialnetwork.com.validators.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UiController extends ObservableI {
    Service srv;
    Stage primaryStage;
    List<String> pages = new ArrayList<>();
    SceneController sceneController;


    public UiController(Stage primaryStage, Service srv){
        this.primaryStage=primaryStage;
        this.srv=srv;
        sceneController = new SceneController(primaryStage);

        buildEverything();
    }

    public void buildEverything(){
        buildSignInPage();
        createAccountPage();
        addFriendScene();
        deleteFriendScene();
        friendRequestScene();
        userScene();

        sceneController.setScene("createAccount");
    }

    public void buildSignInPage(){
        Button login = new Button("Login");
        Form form = new Form("Sign in",login,"Email: ","Password: ");
        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Map<String,TextField> lines=form.getLines();
                String email = lines.get("Email: ").getText();
                String password = lines.get("Password: ").getText();
                try{
                    srv.login(email,password);
                    form.clearLines();
                    notifyObservers();
                }
                catch (ValidationException v){

                }
            }
        });
        Scene scene = new Scene(form);
        sceneController.addNewScene("login", scene);
    }

    public void createAccountPage(){
        Button createAccount = new Button("Create Account");
        Form form = new Form("Create an account",createAccount,"Firstname: ","Lastname: ","Email: ","Password: ");
        createAccount.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Map<String,TextField> lines=form.getLines();
                String firstname = lines.get("Firstname: ").getText();
                String lastname = lines.get("Lastname: ").getText();
                String email = lines.get("Email: ").getText();
                String password = lines.get("Password: ").getText();
                try{
                    srv.addUser(lastname,firstname,email,password);
                    form.clearLines();
                    notifyObservers();
                }
                catch (ValidationException v){

                }

            }
        });
        Scene scene = new Scene(form);
        sceneController.addNewScene("createAccount", scene);
    }

    public void addFriendScene(){
        Button addFriend = new Button("Add Friend");
        Form form = new Form("Send a friend request",addFriend,"Friend Email: ");
        addFriend.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Map<String,TextField> lines=form.getLines();
                String friendEmail = lines.get("Friend Email: ").getText();
                try{
                    srv.addFriendToLoginUser(friendEmail);
                    form.clearLines();
                    notifyObservers();
                }
                catch (ValidationException v){

                }
            }
        });
        Scene scene = new Scene(form);
        sceneController.addNewScene("addFriend", scene);
    }

    public void deleteFriendScene(){
        Button addFriend = new Button("Delete Friend");
        Form form = new Form("Delete a friend",addFriend,"Friend Email: ");
        addFriend.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Map<String,TextField> lines=form.getLines();
                String friendEmail = lines.get("Friend Email: ").getText();

                try{
                    srv.deleteFriendFromLoggedUser(friendEmail);
                    form.clearLines();
                    notifyObservers();
                }
                catch (ValidationException v){

                }
            }
        });
        Scene scene = new Scene(form);
        sceneController.addNewScene("deleteFriend", scene);
    }

    public void friendRequestScene(){
        Button acceptFriend = new Button("Accept friend");
        Button deleteFriend = new Button("Delete friend");

        TableList friendRequest = new TableList(acceptFriend,deleteFriend) {
            @Override
            public void reload() {
                ListView listView = getListView();
                listView.getItems().clear();
                List<User> lst = srv.getAllFriendRequest();
                for(User u:lst){
                    listView.getItems().add(u.getEmail()+"; Date: "+srv.getDateOfFriendRequest(u.getEmail())+"; Status: Pending");
                }
                lst = srv.getAllFriendsOfCurrentUser();
                for(User u:lst){
                    listView.getItems().add(u.getEmail()+"; Date: "+srv.getDateOfFriendRequest(u.getEmail())+"; Status: Friend");
                }
            }
        };
        addObserver(friendRequest);
        acceptFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListView listView= friendRequest.getListView();
                String email = listView.getSelectionModel().getSelectedItem().toString().split(";")[0];
                try{
                    srv.acceptPendingRequest(email);
                    notifyObservers();
                }
                catch (ValidationException v){

                }
            }
        });

        deleteFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListView listView= friendRequest.getListView();
                String email = listView.getSelectionModel().getSelectedItem().toString().split(";")[0];
                try{
                    srv.deleteFriendFromLoggedUser(email);
                    notifyObservers();
                }
                catch (ValidationException v){

                }
            }
        });
        VBox vBox = new VBox(friendRequest);
        Scene scene = new Scene(vBox, 300, 120);
        sceneController.addNewScene("friendRequest",scene);
    }

    public void userScene(){
        Button addFriend = new Button("Add friend");
        Button deleteFriend = new Button("Delete friend");

        TableList userList = new TableList(addFriend,deleteFriend) {
            @Override
            public void reload() {
                ListView listView = getListView();
                listView.getItems().clear();
                List<User> lst = srv.getAllUsers();
                for(User u:lst){
                    listView.getItems().add(u.getEmail());
                }

            }
        };
        addObserver(userList);
        addFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListView listView= userList.getListView();
                String email = listView.getSelectionModel().getSelectedItem().toString();
                try{
                    srv.addFriendToLoginUser(email);
                    notifyObservers();
                }
                catch (ValidationException v){

                }
            }
        });

        deleteFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListView listView= userList.getListView();
                String email = listView.getSelectionModel().getSelectedItem().toString();
                try{
                    srv.deleteFriendFromLoggedUser(email);
                    notifyObservers();
                }
                catch (ValidationException v){

                }
            }
        });

        VBox vBox = new VBox(userList);

        Scene scene = new Scene(vBox, 300, 120);
        sceneController.addNewScene("userList",scene);
    }
    public void start(){
        primaryStage.show();
    }
}
