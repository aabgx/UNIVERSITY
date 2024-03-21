package com.example.myfacebook.utils.events;


import com.example.myfacebook.domain.User;

public class UserEntityChangeEvent implements Event {
    private com.example.myfacebook.utils.events.ChangeEventType type;
    private User data, oldData;

    public UserEntityChangeEvent(com.example.myfacebook.utils.events.ChangeEventType type, User data) {
        this.type = type;
        this.data = data;
    }
    public UserEntityChangeEvent(com.example.myfacebook.utils.events.ChangeEventType type, User data, User oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public com.example.myfacebook.utils.events.ChangeEventType getType() {
        return type;
    }

    public User getData() {
        return data;
    }

    public User getOldData() {
        return oldData;
    }
}