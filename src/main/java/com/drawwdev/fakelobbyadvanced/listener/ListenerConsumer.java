package com.drawwdev.fakelobbyadvanced.listener;

import org.bukkit.event.Listener;

@FunctionalInterface
public interface ListenerConsumer {

    public abstract Listener get();

}
