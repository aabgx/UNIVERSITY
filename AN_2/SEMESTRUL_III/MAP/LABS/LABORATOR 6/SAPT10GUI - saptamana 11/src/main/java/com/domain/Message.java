package com.domain;

public class Message extends Entitate<String>{
    String idFromUser;
    String idToUser;
    String dataSent;

    public Message(String id, String idFromUser, String idToUser, String dataSent) {
        super(id);
        this.idFromUser = idFromUser;
        this.idToUser = idToUser;
        this.dataSent = dataSent;
    }

    public String getIdMessage() {
        return getId();
    }

    public String getIdFromUser() {
        return idFromUser;
    }

    public void setIdFromUser(String idFromUser) {
        this.idFromUser = idFromUser;
    }

    public String getIdToUser() {
        return idToUser;
    }

    public void setIdToUser(String idToUser) {
        this.idToUser = idToUser;
    }

    public String getDataSent() {
        return dataSent;
    }

    public void setDataSent(String dataSent) {
        this.dataSent = dataSent;
    }
}
