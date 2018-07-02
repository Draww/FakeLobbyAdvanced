package com.drawwdev.fakelobbyadvanced.listener;

import com.drawwdev.fakelobbyadvanced.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class NoRainEvent implements Listener {

    private Main plugin;

    public NoRainEvent() {
        this.plugin = Main.getInstance();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.isCancelled()) return;
        if (plugin.getFakeConfig().getConfig().contains("world.options.onWeatherChange.conditions.world")) {
            World world = Bukkit.getWorld(plugin.getFakeConfig().getConfig().getString("world.options.onWeatherChange.conditions.world"));
            if (world != null) {
                if (event.getWorld().getName().equals(world.getName())) {
                    event.setCancelled(event.toWeatherState());
                    return;
                }
            }
        }
        event.setCancelled(event.toWeatherState());
    }

}
