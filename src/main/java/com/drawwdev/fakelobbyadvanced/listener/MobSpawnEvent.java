package com.drawwdev.fakelobbyadvanced.listener;

import com.drawwdev.fakelobbyadvanced.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawnEvent implements Listener {

    private Main plugin;

    public MobSpawnEvent() {
        this.plugin = Main.getInstance();
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.isCancelled()) return;
        if (plugin.getFakeConfig().getConfig().contains("world.options.onMobSpawn.conditions.world")) {
            World world = Bukkit.getWorld(plugin.getFakeConfig().getConfig().getString("world.options.onMobSpawn.conditions.world"));
            if (world != null) {
                if (event.getLocation().getWorld().getName().equals(world.getName())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
        event.setCancelled(true);
    }

}
