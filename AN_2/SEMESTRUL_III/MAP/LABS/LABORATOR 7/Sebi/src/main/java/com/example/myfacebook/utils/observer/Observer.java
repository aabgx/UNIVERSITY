package com.example.myfacebook.utils.observer;


import com.example.myfacebook.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}