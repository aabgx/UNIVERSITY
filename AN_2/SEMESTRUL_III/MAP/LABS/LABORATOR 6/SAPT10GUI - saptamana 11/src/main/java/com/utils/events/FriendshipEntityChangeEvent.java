package com.utils.events;

import com.domain.Prietenie;

public class FriendshipEntityChangeEvent implements Event {
    private ChangeEventType type;
    private Prietenie oldData, data;

    public FriendshipEntityChangeEvent(ChangeEventType type, Prietenie data) {
        this.type = type;
        this.data = data;
    }

    public FriendshipEntityChangeEvent(ChangeEventType type, Prietenie oldData, Prietenie data) {
        this.type = type;
        this.oldData = oldData;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Prietenie getOldData() {
        return oldData;
    }

    public Prietenie getData() {
        return data;
    }
}
