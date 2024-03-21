package com.example.lab6.observer;

import com.example.lab6.events.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E entity);
}
