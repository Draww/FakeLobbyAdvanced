package com.drawwdev.fakelobbyadvanced.listener;

import com.drawwdev.fakelobbyadvanced.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerFlyEvent implements Listener {

    private Main plugin;

    public PlayerFlyEvent() {
        this.plugin = Main.getInstance();
    }

    @EventHandler
    public void onPlayerFlyingEvent(PlayerToggleFlightEvent event) {
        if (event.isCancelled()) return;
        final Player player = event.getPlayer();
        World world = Bukkit.getWorld(plugin.getFakeConfig().getConfig().getString("world.options.onFlyPlayer.conditions.world"));
        if (world != null) {
            if (player.getWorld().getName().equals(world.getName())) {
                if (!player.hasPermission("fakelobbyadvanced.flybypass")) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    event.setCancelled(true);
                }
            }
        }
    }

}
