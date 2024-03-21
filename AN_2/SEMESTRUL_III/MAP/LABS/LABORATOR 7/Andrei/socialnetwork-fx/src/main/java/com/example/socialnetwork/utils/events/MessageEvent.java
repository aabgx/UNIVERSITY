package com.example.socialnetwork.utils.events;

public class MessageEvent implements Event{
    private Long sentId;
    private Long recvId;

    public MessageEvent(Long sentId, Long recvId) {
        this.sentId = sentId;
        this.recvId = recvId;
    }

    public Long getSentId() {
        return sentId;
    }

    public Long getRecvId() {
        return recvId;
    }
}
