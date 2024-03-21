package com.example.socialnetworkgui.utils.events;

import com.example.socialnetworkgui.domain.Request;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.utils.observer.Observable;

public class RequestEntityChangeEvent implements Event {
    private ChangeEventType type;
    private Request oldData, data;

    public RequestEntityChangeEvent(ChangeEventType type, Request data) {
        this.type = type;
        this.data = data;
    }

    public RequestEntityChangeEvent(ChangeEventType type, Request oldData, Request data) {
        this.type = type;
        this.oldData = oldData;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Request getOldData() {
        return oldData;
    }

    public Request getData() {
        return data;
    }
}
