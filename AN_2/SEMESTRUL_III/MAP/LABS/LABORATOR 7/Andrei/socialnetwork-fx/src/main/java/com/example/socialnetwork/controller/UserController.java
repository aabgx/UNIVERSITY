package com.example.socialnetwork.controller;

import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.service.Service;
import com.example.socialnetwork.utils.events.UserChangeEvent;
import com.example.socialnetwork.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController implements Observer<UserChangeEvent> {
    private Service service;
    ObservableList<User> model = FXCollections.observableArrayList();
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> tableColFirstName;
    @FXML
    private TableColumn<User, String> tableColLastName;

    private void initModel() {
        Iterable<User> usersIt = service.getUsers();
        var users = StreamSupport.stream(usersIt.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(users);
    }

    @Override
    public void update(UserChangeEvent userChangeEvent) {
        initModel();
    }

    private User getSelectedUser() {
        return usersTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleShowRequests() throws IOException {
        URL location = getClass().getClassLoader()
                .getResource("com/example/socialnetwork/views/requests.fxml");

        FXMLLoader loader = new FXMLLoader(location);

        AnchorPane root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Friends and requests");
        Scene scene = new Scene(root);
        stage.setScene(scene);

        User user = getSelectedUser();
        RequestsController requestsController = loader.getController();
        requestsController.setProps(service, user);

        stage.show();
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        tableColFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        tableColLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        usersTable.setItems(model);
    }
}
