package com.utils.events;

import com.domain.Utilizator;

public class UserEntityChangeEvent implements Event {
    private ChangeEventType type;
    private Utilizator oldData, data;

    public UserEntityChangeEvent(ChangeEventType type, Utilizator data) {
        this.type = type;
        this.data = data;
    }

    public UserEntityChangeEvent(ChangeEventType type, Utilizator oldData, Utilizator data) {
        this.type = type;
        this.oldData = oldData;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Utilizator getOldData() {
        return oldData;
    }

    public Utilizator getData() {
        return data;
    }
}
