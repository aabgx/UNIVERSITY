package com.example.lab6.controller;

import com.example.lab6.domain.Friendship;
import com.example.lab6.domain.Pair;
import com.example.lab6.domain.User;
import com.example.lab6.events.ChangeEventType;
import com.example.lab6.events.UserChangeEvent;
import com.example.lab6.observer.Observer;
import com.example.lab6.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FriendRequestController implements Observer<UserChangeEvent> {
    @FXML
    private TableColumn tableColumnPasswordReq;
    @FXML
    private TableColumn tableColumnNameReq;
    @FXML
    private TableView tableFriendReqView;
    Service serv;
    private User userCurent;
    @FXML
    private Button btnFriendReq;

    private ObservableList<User> modelFriendRequest = FXCollections.observableArrayList();

    private UserController controller;

    public void setController(UserController controller) {
        this.controller = controller;
    }

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
        tableColumnPasswordReq.setCellValueFactory(new PropertyValueFactory<User, String>("Parola"));
        tableColumnNameReq.setCellValueFactory(new PropertyValueFactory<User, String>("Nume"));

        tableFriendReqView.setItems(modelFriendRequest);
    }
    private void initModel()
    {
        modelFriendRequest.setAll();
        List<Friendship> lista = serv.getFriendsList().stream().filter(x -> !x.isAcceptat()).filter(x->x.getU2().equals(userCurent)).toList();
        for(Friendship f:lista)
        {
            if(f.getU1().equals(userCurent))
            {
                modelFriendRequest.add(f.getU2());
            }
            if(f.getU2().equals(userCurent))
            {
                modelFriendRequest.add(f.getU1());
            }
        }
    }

    @Override
    public void update(UserChangeEvent userChangeEvent) {
        initModel();initModel();
    }
    public void handleAcceptRequest(ActionEvent actionEvent)
    {
        User user = (User)tableFriendReqView.getSelectionModel().getSelectedItem();
        user.setId(serv.getUserList().stream().filter(x->x.equals(user)).toList().get(0).getId());
        Friendship friend = new Friendship(user,userCurent, LocalDateTime.now());
        friend.setId(new Pair<>(user.getId(),userCurent.getId()));
        List<Friendship> lista = serv.getFriendsList().stream().filter(x->x.equals(friend)).toList();
        for(Friendship f:serv.getFriendsList())
        {
            if(f.equals(lista.get(0)))
            {
                f.setAcceptat(true);
            }
        }
        serv.setAcceptatFriendService(friend);
        update(null);
        controller.update(null);
    }

}
