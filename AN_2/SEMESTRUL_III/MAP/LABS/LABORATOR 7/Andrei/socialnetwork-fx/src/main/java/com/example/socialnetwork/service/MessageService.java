package com.example.socialnetwork.service;

import com.example.socialnetwork.domain.Message;
import com.example.socialnetwork.repository.database.MessageDBRepository;
import com.example.socialnetwork.repository.database.UserDBRepository;
import com.example.socialnetwork.utils.events.MessageEvent;
import com.example.socialnetwork.utils.observer.Observable;
import com.example.socialnetwork.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessageService implements Observable<MessageEvent> {
    private MessageDBRepository messageDBRepository;
    private static String url = "jdbc:postgresql://localhost:5432/academic";
    private static MessageService instance = null;

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService(url, "postgres", "postgres");
        }
        return instance;
    }

    private List<Observer<MessageEvent>> observers = new ArrayList<>();

    private MessageService(String url, String user, String password) {
        this.messageDBRepository = new MessageDBRepository(url, user, password);
    }

    public void sendMessage(Long from, Long to, String message) {
        messageDBRepository.save(new Message(from, to, message));
        notifyObservers(new MessageEvent(from, to));
    }

    public List<Message> getMessages(Long from, Long to) {
        var messages = messageDBRepository.findAllForChat(from, to);
        messages.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return messages;
    }

    @Override
    public void addObserver(Observer<MessageEvent> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<MessageEvent> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(MessageEvent event) {
        observers.forEach(x -> x.update(event));
    }
}
