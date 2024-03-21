package com.example.socialnetwork.controller;

import com.example.socialnetwork.domain.FriendDTO;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.exception.RepoException;
import com.example.socialnetwork.repository.database.MessageDBRepository;
import com.example.socialnetwork.service.MessageService;
import com.example.socialnetwork.service.Service;
import com.example.socialnetwork.utils.events.UserChangeEvent;
import com.example.socialnetwork.utils.observer.Observable;
import com.example.socialnetwork.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RequestsController implements Observer<UserChangeEvent> {
    private Service service;
    private User user;
    ObservableList<FriendDTO> model = FXCollections.observableArrayList();

    @FXML
    private TableView<FriendDTO> requestsTable;
    @FXML
    private TableColumn<User, String> tableColFirstName;
    @FXML
    private TableColumn<User, String> tableColLastName;
    @FXML
    private TableColumn<User, String> tableColStatus;
    @FXML
    private TableColumn<User, String> tableColDate;
    @FXML
    Label labelCurrentUser;

    private void initModel() {
        var friends = service.getAllFriends(user.getId());
        model.setAll(friends);
    }

    @Override
    public void update(UserChangeEvent userChangeEvent) {
        initModel();
    }

    public void setProps(Service service, User user) {
        this.service = service;
        this.user = user;
        labelCurrentUser.setText(user.getFirstName() + " " + user.getLastName());
        service.addObserver(this);
        initModel();
    }

    private FriendDTO getSelectedFriend() {
        return requestsTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void initialize() {
        tableColFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        tableColLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        tableColStatus.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
        tableColDate.setCellValueFactory(new PropertyValueFactory<User, String>("date"));

        requestsTable.setItems(model);
    }

    @FXML
    public void handleAdd() throws IOException {
        URL location = getClass().getClassLoader()
                .getResource("com/example/socialnetwork/views/add-friend.fxml");
        var loader = new FXMLLoader(location);

        AnchorPane root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add friend");
        stage.setScene(new Scene(root));

        AddFriendController addFriendController = loader.getController();
        addFriendController.setProps(service, user);

        stage.show();
    }

    @FXML
    public void handleChat() throws IOException {
        URL location = getClass().getClassLoader()
                .getResource("com/example/socialnetwork/views/chat.fxml");
        var loader = new FXMLLoader(location);

        AnchorPane root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Chat");
        stage.setScene(new Scene(root));

        User selectedUser = service.findOne(getSelectedFriend().getFriendId());
        ChatController chatController = loader.getController();
        chatController.setProps(MessageService.getInstance(), user, selectedUser);

        stage.show();

    }

    @FXML
    public void handleAccept() {
        FriendDTO friend = getSelectedFriend();
        Long friendshipId = friend.getId();
        try {
            service.acceptFriendship(friendshipId, user.getId());
            MessageAlert.showSuccessMessage(null, "Friendship accepted!");
        } catch (RepoException rex) {
            MessageAlert.showErrorMessage(null, rex.getMessage());
        }
    }

    @FXML
    public void handleDecline() {
        FriendDTO friend = getSelectedFriend();
        Long friendshipId = friend.getId();
        try {
            service.declineOrRemoveFriendship(friendshipId, user.getId());
            MessageAlert.showSuccessMessage(null, "Friendship removed!");
        } catch (RepoException rex) {
            MessageAlert.showErrorMessage(null, rex.getMessage());
        }
    }

    @FXML
    public void handleRemove() {
        FriendDTO friend = getSelectedFriend();
        Long friendshipId = friend.getId();
        try {
            service.removeFriend(friendshipId, user.getId());
            MessageAlert.showSuccessMessage(null, "Friend removed!");
        } catch (RepoException rex) {
            MessageAlert.showErrorMessage(null, rex.getMessage());
        }
    }


}
