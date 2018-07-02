package com.drawwdev.fakelobbyadvanced;

import com.drawwdev.fakelobbyadvanced.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SpawnLocation {

    private Main plugin;

    private Boolean activate;

    private World world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    private Location lastLocation = null;

    public SpawnLocation(Main plugin) {
        this.plugin = plugin;
        activate = plugin.getFakeConfig().getConfig().getBoolean("join.options.teleport.status");
        if (activate) {
            load();
        }
    }

    private boolean load() {
        World world = Bukkit.getWorld(plugin.getFakeConfig().getConfig().getString("join.options.teleport.location.world"));
        if (world == null) {
            activate = false;
            return activate;
        }
        this.world = world;
        try {
            this.x = plugin.getFakeConfig().getConfig().getDouble("join.options.teleport.location.x");
            this.y = plugin.getFakeConfig().getConfig().getDouble("join.options.teleport.location.y");
            this.z = plugin.getFakeConfig().getConfig().getDouble("join.options.teleport.location.z");
            this.yaw = plugin.getFakeConfig().getConfig().getInt("join.options.teleport.location.yaw");
            this.pitch = plugin.getFakeConfig().getConfig().getInt("join.options.teleport.location.pitch");
            lastLocation = new Location(world, x, y, z, yaw, pitch);
        } catch (Exception ex) {
            activate = false;
        }
        return activate;
    }

    public boolean reload() {
        return load();
    }

    public boolean change(Location location) {
        if (!activate) return false;
        Config config = plugin.getFakeConfig();
        config.getConfig().set("join.options.teleport.location.world", location.getWorld().getName());
        config.getConfig().set("join.options.teleport.location.x", location.getX());
        config.getConfig().set("join.options.teleport.location.y", location.getY());
        config.getConfig().set("join.options.teleport.location.z", location.getZ());
        config.getConfig().set("join.options.teleport.location.yaw", location.getYaw());
        config.getConfig().set("join.options.teleport.location.pitch", location.getPitch());
        config.save();
        lastLocation = location;
        return true;
    }

    public boolean teleport(Player player) {
        if (!activate) return false;
        Location location = new Location(world, x, y, z, yaw, pitch);
        lastLocation = location;
        player.teleport(location);
        return true;
    }

    public Main getPlugin() {
        return plugin;
    }

    public Boolean isActivate() {
        return activate;
    }

    public World getWorld() {
        return world;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getZ() {
        return z;
    }

    public Float getYaw() {
        return yaw;
    }

    public Float getPitch() {
        return pitch;
    }

    public Location getLastLocation() {
        return lastLocation;
    }
}
