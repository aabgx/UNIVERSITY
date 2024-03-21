package com.example.socialnetwork.controller;

import com.example.socialnetwork.domain.Message;
import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.service.MessageService;
import com.example.socialnetwork.utils.events.MessageEvent;
import com.example.socialnetwork.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChatController implements Observer<MessageEvent> {
    private MessageService service;
    private User user1;
    private User user2;
    ObservableList<Message> model = FXCollections.observableArrayList();
    @FXML
    private VBox VBox;
    @FXML
    private TextField message;

    private void initModel() {
        VBox.getChildren().clear();
        model.setAll(service.getMessages(user1.getId(), user2.getId()));
        model.forEach(x -> {
            Label label = new Label(x.getMessage());
            System.out.println(x);
            if (x.getRecvId().equals(user1.getId())) {
                label.setAlignment(Pos.CENTER_LEFT);
            } else {
                label.setAlignment(Pos.CENTER_RIGHT);
            }
            label.setMinWidth(VBox.getPrefWidth());
            VBox.getChildren().add(label);
        });
    }

    @Override
    public void update(MessageEvent messageEvent) {
        initModel();
    }

    @FXML
    public void initialize() {
    }

    public void setProps(MessageService service, User user1, User user2) {
        this.service = service;
        this.user1 = user1;
        this.user2 = user2;
        service.addObserver(this);
        initModel();
    }

    public void sendMessage() {
        service.sendMessage(user1.getId(), user2.getId(), message.getText());
    }
}
