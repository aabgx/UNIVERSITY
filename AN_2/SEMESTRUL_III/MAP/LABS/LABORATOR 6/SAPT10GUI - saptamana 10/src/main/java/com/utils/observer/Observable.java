package com.utils.observer;

import com.utils.events.Event;

public interface Observable<E extends Event>{
    void addObserver(Observer<E> e);

    void removeObserver(Observer<E> e);

    void notifyObservers(E t);
}
