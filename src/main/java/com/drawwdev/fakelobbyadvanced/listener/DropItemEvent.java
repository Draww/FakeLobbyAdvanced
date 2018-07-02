package com.drawwdev.fakelobbyadvanced.listener;

import com.drawwdev.fakelobbyadvanced.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropItemEvent implements Listener {

    private Main plugin;

    public DropItemEvent() {
        this.plugin = Main.getInstance();
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (event.isCancelled()) return;
        final Player player = event.getPlayer();
        Boolean control = false;
        if (plugin.getFakeConfig().getConfig().contains("world.options.onDropItem.conditions.world")) {
            World world = Bukkit.getWorld(plugin.getFakeConfig().getConfig().getString("world.options.onDropItem.conditions.world"));
            if (world != null) {
                if (player.getWorld().getName().equals(world.getName())) {
                    control = true;
                }
            }
        }
        if (control) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

}
