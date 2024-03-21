package com.example.lab6.observer;


import com.example.lab6.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
