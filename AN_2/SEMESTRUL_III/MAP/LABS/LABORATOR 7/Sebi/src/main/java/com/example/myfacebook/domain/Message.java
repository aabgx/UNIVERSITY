package com.example.myfacebook.domain;

public class Message extends Entity<Integer>{
    int idFromUser;
    int idToUser;
    String dataSent;

    public Message(int idFromUser, int idToUser, String dataSent) {
        this.idFromUser = idFromUser;
        this.idToUser = idToUser;
        this.dataSent = dataSent;
    }

    public int getIdMessage() {
        return getId();
    }

    public void setIdMessage(int idMessage) {
        super.setId(idMessage);
    }

    public int getIdFromUser() {
        return idFromUser;
    }

    public void setIdFromUser(int idFromUser) {
        this.idFromUser = idFromUser;
    }

    public int getIdToUser() {
        return idToUser;
    }

    public void setIdToUser(int idToUser) {
        this.idToUser = idToUser;
    }

    public String getDataSent() {
        return dataSent;
    }

    public void setDataSent(String dataSent) {
        this.dataSent = dataSent;
    }
}
