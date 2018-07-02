package com.drawwdev.fakelobbyadvanced.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandUtils {

    private static SimpleCommandMap cmds = ReflectionUtils.invokeMethod(Bukkit.getServer().getClass(), "getCommandMap", Bukkit.getServer());
    private static String[] commands = new String[0];

    public static Command getCommand(String cmd){
        return cmds.getCommand(cmd);
    }

    public static String[] getCommandList() {
        Collection<Command> commandList = cmds.getCommands();
        if (commands.length != commandList.size()){
            commands = commandList.stream().map(cmd -> cmd.getLabel().replaceFirst(".+?:", "")).distinct().toArray(String[]::new);
            Arrays.sort(commands);
        }
        return commands;
    }

}
