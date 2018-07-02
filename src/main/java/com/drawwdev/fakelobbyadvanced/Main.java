package com.drawwdev.fakelobbyadvanced;

import co.aikar.commands.BukkitCommandManager;
import com.drawwdev.fakelobbyadvanced.commands.MainCommand;
import com.drawwdev.fakelobbyadvanced.config.Config;
import com.drawwdev.fakelobbyadvanced.depend.Depends;
import com.drawwdev.fakelobbyadvanced.depend.depends.DAuthMe;
import com.drawwdev.fakelobbyadvanced.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    private Config config;
    private Depends depends;

    private SpawnLocation spawnLocation;

    private BukkitCommandManager bukkitCommandManager;
    private PluginMessageFunction pluginMessageFunction;

    private FakeListener fakeListener;

    @Override
    public void onEnable() {
        instance = this;
        depends = new Depends(this).add(
                new DAuthMe()
        ).run();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageListener(this));
        pluginMessageFunction = new PluginMessageFunction(this);
        bukkitCommandManager = new BukkitCommandManager(this);
        bukkitCommandManager.registerCommand(new MainCommand());
        config = new Config(this, "config.yml", true);
        spawnLocation = new SpawnLocation(this);
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    public void registerListeners() {
        fakeListener = new FakeListener(this).addAll(
                () -> {
                    if (config.getConfig().getBoolean("blockedCommands.status")) {
                        return new CommandPreprocessEvent();
                    }
                    return null;
                },
                () -> {
                    if (config.getConfig().getBoolean("world.options.onMobSpawn.disable")) {
                        return new MobSpawnEvent();
                    }
                    return null;
                },
                () -> {
                    if (config.getConfig().getBoolean("world.options.onWeatherChange.disable")) {
                        return new NoRainEvent();
                    }
                    return null;
                },
                () -> {
                    if (config.getConfig().getBoolean("world.options.onVoidDamage.disable")) {
                        return new VoidDamageEvent();
                    }
                    return null;
                },
                () -> {
                    if (config.getConfig().getBoolean("world.options.onFlyPlayer.disable")) {
                        return new PlayerFlyEvent();
                    }
                    return null;
                },
                () -> {
                    if (config.getConfig().getBoolean("world.options.onDropItem.disable")) {
                        return new DropItemEvent();
                    }
                    return null;
                }
        ).registerAll();
    }

    public void reload() {
        fakeListener.restart();
        registerListeners();
    }

    public void wait(Runnable runnable, long l) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, runnable, l);
    }

    public static Main getInstance() {
        return instance;
    }

    public Config getFakeConfig() {
        return config;
    }

    public Depends getDepends() {
        return depends;
    }

    public BukkitCommandManager getBukkitCommandManager() {
        return bukkitCommandManager;
    }

    public PluginMessageFunction getPluginMessageFunction() {
        return pluginMessageFunction;
    }

    public SpawnLocation getSpawnLocation() {
        return spawnLocation;
    }
}
