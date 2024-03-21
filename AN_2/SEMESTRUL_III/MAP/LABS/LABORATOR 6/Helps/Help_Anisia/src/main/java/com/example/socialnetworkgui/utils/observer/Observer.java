package com.example.socialnetworkgui.utils.observer;

import com.example.socialnetworkgui.utils.events.Event;

public interface Observer <E extends Event>{
    void update(E e);
}
