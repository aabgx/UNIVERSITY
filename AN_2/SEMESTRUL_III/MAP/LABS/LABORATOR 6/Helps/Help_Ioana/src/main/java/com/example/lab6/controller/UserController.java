package com.example.lab6.controller;

import com.example.lab6.HelloApplication;
import com.example.lab6.domain.Friendship;
import com.example.lab6.domain.User;
import com.example.lab6.events.UserChangeEvent;
import com.example.lab6.observer.Observer;
import com.example.lab6.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController implements Observer<UserChangeEvent> {
    @FXML
    private Button btnFriendReq;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnAdd;
    @FXML
    private TableColumn<User,String> tableColumnFriendsName;
    @FXML
    private TableView<User> tableUserView;
    private Service serv;
    private ObservableList<User> modelFriends = FXCollections.observableArrayList();
    private ObservableList<User> modelUser = FXCollections.observableArrayList();
    @FXML
    private TableView<User> tableFriendView;
    @FXML
    private TextField searchField;
    private User userCurent;
    @FXML
    private TableColumn<User,String> tableColumnName;
    @FXML
    private TableColumn<User,String> tableColumnPassword;


    @FXML
    public void setUserCurent(User userCurent) {
        this.userCurent = userCurent;
    }
    public void setUserServ(Service serv)
    {
        this.serv = serv;
        serv.addObserver(this);
        initModel();
    }
    public void initialize()
    {
        tableColumnPassword.setCellValueFactory(new PropertyValueFactory<User, String>("Parola"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<User, String>("Nume"));
        tableColumnFriendsName.setCellValueFactory(new PropertyValueFactory<User,String>("Nume"));

        tableFriendView.setItems(modelFriends);
        tableUserView.setItems(modelUser);
    }
    @Override
    public void update(UserChangeEvent userChangeEvent)
    {
        initModel();
        initialize();
    }
    public void initModel()
    {
        modelFriends.setAll();
        List<User> lista = serv.getUserFriends(userCurent);
        int id=0;
        for(Friendship f: serv.getFriendsList().stream().filter(x -> x.isAcceptat()).toList())
        {
            if(f.getU1().equals(userCurent))
            {
                modelFriends.add(f.getU2());
            }
            if(f.getU2().equals(userCurent))
            {
                modelFriends.add(f.getU1());
            }
        }
        List<User> listaPr = new ArrayList<>();
            for(User u:serv.getUserList()) {
                Friendship fr = new Friendship(userCurent, u, LocalDateTime.now());
                if (!serv.getFriendsList().contains(fr)) {
                    if (!modelUser.contains(u)) {
                        modelUser.add(u);

                    }
                }
            }


        //modelUser.setAll(serv.getUserList());
    }
    public void handleAddFriend(ActionEvent actionEvent)
    {
        User user = tableUserView.getSelectionModel().getSelectedItem();
        serv.addFriendService(userCurent.getId(),user.getId(), LocalDateTime.now());
        //modelFriends.setAll(serv.getUserFriends(userCurent));
        for(Friendship f:serv.getFriendsList())
        {
            if(f.isAcceptat())
            {
                if(f.getU1().equals(user))
                {
                    modelFriends.add(f.getU2());
                }
                else
                {
                    if(f.getU2().equals(user))
                    {
                        modelFriends.add(f.getU1());
                    }
                }
            }
        }
        tableFriendView.setItems(modelFriends);
    }
    public void handleActivateButtonAdd(MouseEvent actionEvent)
    {
        tableFriendView.getSelectionModel().clearSelection();
        btnDel.setDisable(true);
        btnAdd.setDisable(false);
    }
    public void handleActivateButtonDelete(MouseEvent actionEvent)
    {
        tableUserView.getSelectionModel().clearSelection();
        btnAdd.setDisable(true);
        btnDel.setDisable(false);
    }
    public void handleDeleteUser(ActionEvent actionEvent)
    {
        User user = tableFriendView.getSelectionModel().getSelectedItem();
        serv.removeFriendService(userCurent.getId(),user.getId(),LocalDateTime.now());
        modelFriends.setAll(serv.getUserFriends(userCurent));
        tableFriendView.setItems(modelFriends);
    }
    public void handleSearchUser(KeyEvent actionEvent)
    {
        modelUser.setAll(serv.getUserList());
        tableUserView.setItems(modelUser);
        String valoareNume = String.valueOf(searchField.getCharacters());
        if(!valoareNume.equals(""))
        {
            List<User> prieteni = serv.getUserList().stream().filter(x -> x.getNume().contains(valoareNume)).toList();
            modelUser.setAll(prieteni);
            tableUserView.setItems(modelUser);
        }
        btnAdd.setDisable(true);
    }
    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 600);
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        LoginController loginController = fxmlLoader.getController();
        loginController.setUserServ(this.serv);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

    }
    public void handleClickSearch(MouseEvent actionEvent)
    {
        searchField.setText("");
    }
    public void handleShowFriendRequest(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/FriendReqView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 600);
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        FriendRequestController friendRequestController = fxmlLoader.getController();
        friendRequestController.setController(this);
        friendRequestController.setUserCurent(userCurent);
        friendRequestController.setUserServ(this.serv);
        //((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
