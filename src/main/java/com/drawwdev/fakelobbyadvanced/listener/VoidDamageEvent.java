package com.drawwdev.fakelobbyadvanced.listener;

import com.drawwdev.fakelobbyadvanced.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VoidDamageEvent implements Listener {

    private Main plugin;

    public VoidDamageEvent() {
        this.plugin = Main.getInstance();
    }

    @EventHandler
    public void onVoidDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            if (event.getEntity() == null) return;
            if (!(event.getEntity() instanceof Player)) return;
            World world = Bukkit.getWorld(plugin.getFakeConfig().getConfig().getString("world.options.onVoidDamage.conditions.world"));
            if (world == null || event.getEntity().getWorld().getName().equals(world.getName())) return;
            event.setCancelled(true);
            if (plugin.getFakeConfig().getConfig().getBoolean("world.options.onVoidDamage.teleportSpawn") &&
                    plugin.getSpawnLocation().isActivate()) {
                final Player player = (Player) event.getEntity();
                plugin.getSpawnLocation().teleport(player);
            }
        }
    }

}
