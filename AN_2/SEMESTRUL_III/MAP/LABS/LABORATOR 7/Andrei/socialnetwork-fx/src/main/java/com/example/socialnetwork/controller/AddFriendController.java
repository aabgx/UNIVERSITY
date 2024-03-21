package com.example.socialnetwork.controller;

import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.service.Service;
import com.example.socialnetwork.utils.events.UserChangeEvent;
import com.example.socialnetwork.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AddFriendController implements Observer<UserChangeEvent> {
    private Service service;
    ObservableList<User> model = FXCollections.observableArrayList();
    private User user;

    @FXML
    private TableView<User> usersTable;
    @FXML
    TableColumn<User, String> tableColFirstName;
    @FXML
    TableColumn<User, String> tableColLastName;
    @FXML
    TextField textFieldFirstName;
    @FXML
    TextField textFieldLastName;
    @FXML
    Label labelCurrentUser;

    public void setProps(Service service, User user) {
        this.service = service;
        this.user = user;
        labelCurrentUser.setText(user.getFirstName() + " " + user.getLastName());
        service.addObserver(this);
        handleFilter();
    }

    private User getSelectedUser() {
        return usersTable.getSelectionModel().getSelectedItem();
    }

    public void update(UserChangeEvent userChangeEvent) {
        handleFilter();
    }

    @FXML
    public void initialize() {
        tableColFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        usersTable.setItems(model);

        textFieldFirstName.textProperty().addListener((o -> handleFilter()));
        textFieldLastName.textProperty().addListener((o -> handleFilter()));
    }

    private void handleFilter() {
        Predicate<User> p1 = u -> u.getFirstName().startsWith(textFieldFirstName.getText());
        Predicate<User> p2 = u -> u.getLastName().startsWith(textFieldLastName.getText());
        Predicate<User> p3 = u -> !u.equals(user);
        Predicate<User> p4 = u -> !service.getAllFriendsAsUsers(user.getId()).contains(u);
        Predicate<User> p5 = p1.and(p2).and(p3).and(p4);

        model.setAll(StreamSupport.stream(service.getUsers().spliterator(), false)
                .filter(p5)
                .collect(Collectors.toList()));
    }

    @FXML
    public void handleAddFriend() {
        User userToAdd = getSelectedUser();
        if (userToAdd != null) {
            try {
                service.sendFriendRequest(user.getId(), userToAdd.getId());
                MessageAlert.showSuccessMessage(null, "Friend request sent!");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
    }


}
