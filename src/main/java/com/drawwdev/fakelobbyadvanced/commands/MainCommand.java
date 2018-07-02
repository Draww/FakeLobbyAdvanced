package com.drawwdev.fakelobbyadvanced.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.drawwdev.fakelobbyadvanced.Main;
import com.drawwdev.fakelobbyadvanced.utils.StringUtils;
import org.bukkit.entity.Player;

@CommandAlias("fakelobbyadvanced|fakelobby|flobbyadvanced|fbadvanced|fba")
public class MainCommand {

    @Subcommand("setspawn")
    @CommandPermission("fakelobbyadvanced.setspawn")
    @Syntax("")
    public void onSetSpawn(Player player) {
        if (Main.getInstance().getSpawnLocation().change(player.getLocation())) {
            player.sendMessage(StringUtils.cc(Main.getInstance().getFakeConfig().getConfig().getString("prefix") + "Spawn belirlendi!"));
        }
    }

    @Subcommand("spawn")
    @CommandPermission("fakelobbyadvanced.spawn")
    @Syntax("")
    public void onSpawn(Player player) {
        if (Main.getInstance().getSpawnLocation().teleport(player)) {
            player.sendMessage(StringUtils.cc(Main.getInstance().getFakeConfig().getConfig().getString("prefix") + "Spawn'a ışınlandın!"));
        }
    }

}
