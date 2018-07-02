package com.drawwdev.fakelobbyadvanced.listener;

import com.drawwdev.fakelobbyadvanced.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.HashMap;

public class FakeListener {

    private Main plugin;
    private HashMap<Class, Listener> listenerMap;

    public FakeListener(Main plugin, Listener... listeners) {
        this.plugin = plugin;
        Arrays.stream(listeners).forEach(this::add);
    }

    public FakeListener(Main plugin) {
        this.plugin = plugin;
    }

    public FakeListener add(Listener listener) {
        if (has(listener)) return this;
        listenerMap.put(listener.getClass(), listener);
        return this;
    }

    public FakeListener add(ListenerConsumer consumer) {
        Listener listener = consumer.get();
        if (listener == null || has(listener)) return this;
        listenerMap.put(listener.getClass(), listener);
        return this;
    }

    public FakeListener addAll(Listener... listeners) {
        Arrays.stream(listeners).forEach(this::add);
        return this;
    }

    public FakeListener addAll(ListenerConsumer... consumers) {
        Arrays.stream(consumers).forEach(this::add);
        return this;
    }

    public FakeListener register(Listener listener) {
        if (!has(listener)) return this;
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        return this;
    }

    public FakeListener registerAll() {
        listenerMap.values().forEach(l -> Bukkit.getPluginManager().registerEvents(l, plugin));
        return this;
    }

    public FakeListener unregisterAll() {
        listenerMap.values().forEach(HandlerList::unregisterAll);
        return this;
    }

    public FakeListener unregister(Listener listener) {
        if (!has(listener)) return this;
        HandlerList.unregisterAll(listener);
        return this;
    }

    public FakeListener removeAll() {
        listenerMap.values().forEach(HandlerList::unregisterAll);
        listenerMap.clear();
        return this;
    }

    public FakeListener remove(Listener listener) {
        if (!has(listener)) return this;
        HandlerList.unregisterAll(listener);
        listenerMap.remove(listener.getClass());
        return this;
    }

    public FakeListener restart() {
        listenerMap.values().forEach(HandlerList::unregisterAll);
        listenerMap.clear();
        return this;
    }

    public boolean has(Listener listener) {
        return listenerMap.containsKey(listener.getClass());
    }

    public Main getPlugin() {
        return plugin;
    }

    public HashMap<Class, Listener> getListenerMap() {
        return listenerMap;
    }
}
