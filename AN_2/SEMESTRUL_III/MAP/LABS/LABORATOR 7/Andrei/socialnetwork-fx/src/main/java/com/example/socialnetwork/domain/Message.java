package com.example.socialnetwork.domain;

public class Message extends Entity<Long> {
    private Long sentId;
    private Long recvId;
    private String message;
    private String date;

    public Message(Long sentId, Long recvId, String message, String date) {
        this.sentId = sentId;
        this.recvId = recvId;
        this.message = message;
        this.date = date;
    }

    public Message(Long sentId, Long recvId, String message) {
        this.sentId = sentId;
        this.recvId = recvId;
        this.message = message;
    }

    public Long getSentId() {
        return sentId;
    }

    public void setSentId(Long sentId) {
        this.sentId = sentId;
    }

    public Long getRecvId() {
        return recvId;
    }

    public void setRecvId(Long recvId) {
        this.recvId = recvId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sentId=" + sentId +
                ", recvId=" + recvId +
                ", message='" + message + '\'' +
                "}";
    }


}
