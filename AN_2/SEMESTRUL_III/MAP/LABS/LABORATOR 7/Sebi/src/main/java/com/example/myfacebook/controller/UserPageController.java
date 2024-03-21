package com.example.myfacebook.controller;

import com.example.myfacebook.HelloApplication;
import com.example.myfacebook.domain.Friendship;
import com.example.myfacebook.domain.User;
import com.example.myfacebook.exceptions.RepositoryException;
import com.example.myfacebook.service.Service;
import com.example.myfacebook.utils.events.UserEntityChangeEvent;
import com.example.myfacebook.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserPageController implements Observer<UserEntityChangeEvent> {

    Service service;

    User loggedInUser;

    ObservableList<Friendship> Fmodel = FXCollections.observableArrayList();

    ObservableList<User> Rmodel = FXCollections.observableArrayList();


    @FXML
    private TableView<Friendship> friendsTable;

    @FXML
    private TextField requestField;

    @FXML
    private TableView<User> requestsTable;

    @FXML
    private Button sendRequestButton;

    @FXML
    private Button unfriendButton;

    @FXML
    private Button AcceptRequestButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private TableColumn<Friendship, String> dateColumn;

    @FXML
    private TableColumn<Friendship, String> nameColumn;

    @FXML
    private TableColumn<User, String> nameRequestColumn;

    @FXML
    private Button refreshRequests;

    @FXML
    private Button declineRequest;

    @FXML
    private Button refreshFriends;

    @FXML
    private Button OpenDM;

    @FXML
    private Button cancelRequestButton;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
        initModel2();
    }

    void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Friendship, String>("username2"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Friendship, String>("friendsFrom"));
        friendsTable.setItems(Fmodel);
        nameRequestColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        requestsTable.setItems(Rmodel);

    }

    void initData(User user) {
        userNameLabel.setText(user.getUsername());
        initialize();
        this.loggedInUser = user;
    }


    @FXML
    void handleAdd(ActionEvent event) {
        boolean verified = false;
        String name = requestField.getText();
        List<User> usr = service.getListUsers();
        for (User u : usr) {
            if (u.getUsername().equals(name)) {
                ///send request to name
                service.addRequest(loggedInUser.getUserID(), u.getUserID());
                verified = true;
            }
            initModel();
        }
        if (!verified) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "User not found!");
        }
    }

    @FXML
    void AcceptHandle(ActionEvent Event) {
        User fr = requestsTable.getSelectionModel().getSelectedItem();
        if (fr != null) {
            service.addFriend(loggedInUser.getUserID(), fr.getUserID());
            service.deleteRequest(fr.getUserID(), loggedInUser.getUserID());
            initModel();
            initModel2();
        }
    }

    @FXML
    void handleRefreshRequest(ActionEvent Event) {
        initModel2();
    }

    @FXML
    void handleRefreshFriends(ActionEvent Event){
        initModel();
    }

    @FXML
    void handleDecline(ActionEvent Event) {
        User usr = requestsTable.getSelectionModel().getSelectedItem();
        if (usr != null) {
            service.deleteRequest(usr.getUserID(), loggedInUser.getUserID());
            initModel2();
        }
    }


    @FXML
    void handleDelete(ActionEvent event) {
        Friendship fr = friendsTable.getSelectionModel().getSelectedItem();
        if (fr != null) {
            try {
                service.removeFriendship(loggedInUser.getUserID(), fr.getU2());
                initModel();
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void handleCancelRequest(ActionEvent event){
        boolean verified = false;
        String name = requestField.getText();
        List<User> usr = service.getListUsers();
        for (User u : usr) {
            if (u.getUsername().equals(name)) {
                service.deleteRequest(loggedInUser.getUserID(), u.getUserID());
                verified = true;
            }
            initModel();
        }
        if (!verified) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "User not found!");
        }
    }

    private void initModel() {

        List<Friendship> messages = service.getListFriendshipsForUser(loggedInUser.getUserID());
        List<Friendship> friendships = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        Fmodel.setAll(friendships);
    }

    private void initModel2() {
        service.updateRequests(loggedInUser.getUserID());
        List<User> requestorUsers = service.getRequests(loggedInUser.getUserID());
        Rmodel.setAll(requestorUsers);
    }

    @Override
    public void update(UserEntityChangeEvent userEntityChangeEvent) {
        initModel();
    }


    public Stage showPage(User sender, User receiver) {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("messagesUI.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(
                    new Scene(loader.load())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MessagesController controller = loader.getController();
        controller.initData(sender, receiver);
        controller.setService(service);
        stage.show();
        return stage;
    }


    @FXML
    void handleDM(ActionEvent event) {
        Friendship fr = friendsTable.getSelectionModel().getSelectedItem();
        if (fr != null) {
            User receiver=service.getUserByID(fr.getU2());
            User sender=loggedInUser;
            showPage(sender, receiver);
        }
    }



}
