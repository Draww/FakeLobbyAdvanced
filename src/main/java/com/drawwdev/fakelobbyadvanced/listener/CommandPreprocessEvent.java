package com.drawwdev.fakelobbyadvanced.listener;

import com.drawwdev.fakelobbyadvanced.Main;
import com.drawwdev.fakelobbyadvanced.utils.CommandUtils;
import com.drawwdev.fakelobbyadvanced.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class CommandPreprocessEvent implements Listener {

    private Main plugin;

    public CommandPreprocessEvent() {
        this.plugin = Main.getInstance();
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if (player.hasPermission("fakelobbyadvanced.cmdbypass")) return;
        Boolean control = false;
        String[] splitCommand = event.getMessage().replaceFirst("/", "").split(" ");
        String[] commands = CommandUtils.getCommandList();
        if (Arrays.stream(commands).parallel().anyMatch(splitCommand[0]::contains)) {
            List<String> listWhitelist = plugin.getFakeConfig().getConfig().getStringList("blockedCommands.whitelist");
            if (listWhitelist.stream().parallel().anyMatch(s -> !splitCommand[0].toLowerCase().equals(s))) {
                control = true;
            }
        }
        if (control) {
            event.setCancelled(true);
            player.sendMessage(StringUtils.cc(plugin.getFakeConfig().getConfig().getString("prefix") + "Lobide komut kullanamazsınız!"));
        }
    }

}
