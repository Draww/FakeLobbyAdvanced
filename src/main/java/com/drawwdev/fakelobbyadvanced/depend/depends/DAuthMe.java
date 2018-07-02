package com.drawwdev.fakelobbyadvanced.depend.depends;

import com.drawwdev.fakelobbyadvanced.Main;
import com.drawwdev.fakelobbyadvanced.depend.Depend;
import com.drawwdev.fakelobbyadvanced.depend.DependType;
import com.drawwdev.fakelobbyadvanced.utils.ColorUtils;
import com.drawwdev.fakelobbyadvanced.utils.StringUtils;
import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.StringJoiner;

public class DAuthMe implements Depend<DAuthMe>, Listener {

    private Main plugin;
    private Boolean dependent = false;
    private Boolean first = true;

    private AuthMeApi authMeApi = null;

    public DAuthMe() {
        this.plugin = Main.getInstance();
        setup();
    }

    @Override
    public String name() {
        return "AuthMe";
    }

    @Override
    public DependType dependType() {
        return DependType.HIGH;
    }

    @Override
    public Boolean dependent() {
        return dependent;
    }

    @Override
    public boolean setup() {
        if (plugin.getServer().getPluginManager().getPlugin("AuthMe") == null) {
            dependent = false;
            if (!first) HandlerList.unregisterAll(this);
            return false;
        }
        dependent = true;
        if (first) plugin.getServer().getPluginManager().registerEvents(this, plugin);
        initHook();
        first = false;
        return true;
    }

    @Override
    public DAuthMe get() {
        return this;
    }

    public Boolean isFirst() {
        return first;
    }

    public AuthMeApi initHook() {
        authMeApi = AuthMeApi.getInstance();
        return authMeApi;
    }

    public void disableHook() {
        authMeApi = null;
    }

    public AuthMeApi getAuthMeApi() {
        return authMeApi;
    }

    public boolean isHookActive() {
        return authMeApi != null;
    }

    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        if ("AuthMe".equals(event.getPlugin().getName())) {
            disableHook();
        }
    }

    @EventHandler
    public void onEnable(PluginEnableEvent event) {
        if ("AuthMe".equals(event.getPlugin().getName())) {
            initHook();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLogin(LoginEvent event) {
        final Player player = event.getPlayer();
        if (event.isLogin()) {
            plugin.getPluginMessageFunction().connect(player, plugin.getFakeConfig().getConfig().getString("authConnect.server"));
            if (plugin.getFakeConfig().getConfig().getBoolean("authConnect.options.kick.status")) {
                plugin.wait(() -> {
                    if (!player.isOnline()) return;
                    if (plugin.getFakeConfig().getConfig().getBoolean("authConnect.options.kick.status")) {
                        StringJoiner joiner = new StringJoiner("\n", StringUtils.cc(plugin.getFakeConfig().getConfig().getString("prefix")), "");
                        plugin.getFakeConfig().getConfig().getStringList("authConnect.options.kick.message").forEach(joiner::add);
                        player.kickPlayer(joiner.toString());
                        return;
                    }
                    player.kickPlayer("");
                }, plugin.getFakeConfig().getConfig().getInt("authConnect.options.kick.timeout"));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (plugin.getFakeConfig().getConfig().getBoolean("join.options.clearchat.status")) {
            Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                Integer line = plugin.getFakeConfig().getConfig().getInt("join.options.clearchat.line");
                for (int i = 0; i < line; i++) {
                    player.sendMessage(" ");
                }
            });
        }
        plugin.getSpawnLocation().teleport(player);
        if (plugin.getFakeConfig().getConfig().getBoolean("join.options.invisible.status")) {
            Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    player.hidePlayer(plugin, p);
                    p.hidePlayer(plugin, player);
                }
            });
            player.addPotionEffect((new PotionEffect(PotionEffectType.INVISIBILITY, 999, 1)));
        }
        if (plugin.getFakeConfig().getConfig().getBoolean("join.options.kick.status")) {
            plugin.wait(() -> {
                if (!player.isOnline()) return;
                if (plugin.getFakeConfig().getConfig().getBoolean("join.options.kick.status")) {
                    StringJoiner joiner = new StringJoiner("\n", StringUtils.cc(plugin.getFakeConfig().getConfig().getString("prefix")), "");
                    plugin.getFakeConfig().getConfig().getStringList("join.options.kick.message").forEach(joiner::add);
                    player.kickPlayer(joiner.toString());
                    return;
                }
                player.kickPlayer("");
            }, plugin.getFakeConfig().getConfig().getInt("join.options.kick.timeout"));
        }
        if (plugin.getFakeConfig().getConfig().getBoolean("join.options.firework_effect.status")) {
            Firework firework = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            FireworkEffect.Type type = FireworkEffect.Type.valueOf(plugin.getFakeConfig().getConfig().getString("join.options.firework_effect.type", "BALL"));
            Color color1 = ColorUtils.getColor(plugin.getFakeConfig().getConfig().getInt("join.options.firework_effect.color1", 1));
            Color color2 = ColorUtils.getColor(plugin.getFakeConfig().getConfig().getInt("join.options.firework_effect.color2", 1));

            FireworkEffect effect = FireworkEffect.builder()
                    .flicker(plugin.getFakeConfig().getConfig().getBoolean("join.options.firework_effect.flicker", false))
                    .trail(plugin.getFakeConfig().getConfig().getBoolean("join.options.firework_effect.trail", false))
                    .withColor(color1)
                    .withFade(color2)
                    .with(type)
                    .build();

            fireworkMeta.addEffect(effect);
            fireworkMeta.setPower(plugin.getFakeConfig().getConfig().getInt("join.options.firework_effect.power", 1));

            firework.setFireworkMeta(fireworkMeta);
        }
    }

}
