package com.example.socialnetworkgui.utils.observer;

import com.example.socialnetworkgui.utils.events.Event;

public interface Observable <E extends Event>{
    void addObserver(Observer<E> e);

    void removeObserver(Observer<E> e);

    void notifyObservers(E t);
}
