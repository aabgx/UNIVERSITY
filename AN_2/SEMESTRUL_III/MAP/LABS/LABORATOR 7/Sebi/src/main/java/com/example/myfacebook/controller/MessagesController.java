package com.example.myfacebook.controller;

import com.example.myfacebook.domain.Friendship;
import com.example.myfacebook.domain.Message;
import com.example.myfacebook.domain.User;
import com.example.myfacebook.exceptions.RepositoryException;
import com.example.myfacebook.service.Service;
import com.example.myfacebook.utils.events.UserEntityChangeEvent;
import com.example.myfacebook.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessagesController implements Observer<UserEntityChangeEvent> {


    Service service;
    User loggedInUser;
    User receiverUser;
    ObservableList<Message> MessModel = FXCollections.observableArrayList();

    @FXML
    private Label messagesToLabel;

    @FXML
    private TextField inputMessage;

    @FXML
    private Button sendButton;

    @FXML
    private TableView messageTable;

    @FXML
    private TableColumn messagesColumn;



    public void setService(Service service) {
        this.service = service;
        service.addObserver((Observer<UserEntityChangeEvent>) this);
        initModel();
        refresh();
    }

    void initData(User sender, User receiver) {
        messagesToLabel.setText(receiver.getUsername());
        initialize();
        this.loggedInUser = sender;
        this.receiverUser = receiver;
    }

    void initialize() {
        messagesColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("dataSent"));
        messageTable.setItems(MessModel);
    }

    private void initModel() {
        List<Message> messages = service.getListMessagesFromUsers(loggedInUser.getUserID(), receiverUser.getUserID());
        List<Message> sortedList = messages.stream()
                .sorted(Comparator.comparingInt(Message::getIdMessage))
                .collect(Collectors.toList());
        List<Message> mess = StreamSupport.stream(sortedList.spliterator(), false)
                .collect(Collectors.toList());
        MessModel.setAll(mess);
    }

    private void refresh(){
        TimerTask task = new TimerTask() {
            public void run() {
                initModel();
                System.out.println("refresh "+loggedInUser.getUsername());
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        timer.schedule(task, new Date(), delay);
    }


    @Override
    public void update(UserEntityChangeEvent userEntityChangeEvent) {
        initModel();
    }

    @FXML
    void handleSendMessage(ActionEvent event) {
        String message=inputMessage.getText();
        service.addMessage(loggedInUser.getUserID(), receiverUser.getUserID(), message);
        initModel();
    }

    void listener(){

    }

}
