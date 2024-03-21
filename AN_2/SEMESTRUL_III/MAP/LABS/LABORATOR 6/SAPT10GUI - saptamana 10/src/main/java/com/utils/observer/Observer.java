package com.utils.observer;

import com.utils.events.Event;

public interface Observer<E extends Event>{
    void update(E e);
}
