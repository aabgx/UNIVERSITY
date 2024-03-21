package com.example.socialnetworkgui.utils.events;

import com.example.socialnetworkgui.domain.User;

public class UserEntityChangeEvent implements Event{
    private ChangeEventType type;
    private User oldData, data;

    public UserEntityChangeEvent(ChangeEventType type, User data) {
        this.type = type;
        this.data = data;
    }

    public UserEntityChangeEvent(ChangeEventType type, User oldData, User data) {
        this.type = type;
        this.oldData = oldData;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public User getOldData() {
        return oldData;
    }

    public User getData() {
        return data;
    }
}
