package kucameow.main.commands;

import kucameow.main.PluginMainClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChangePlayerClan implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 2) return false;
        if(!commandSender.hasPermission("regioncaptureGOD")){
            commandSender.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "У тебя нет разрешения");
            return true;
        }
        if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(strings[0]))){
            commandSender.sendMessage("Нет такого игрока");
            return true;
        }

        PluginMainClass.clans.replace(Bukkit.getPlayer(strings[0]), strings[1]);
        commandSender.sendMessage("" + ChatColor.AQUA + ChatColor.BOLD + "Теперь игрок " + strings[0] + " находится в клане " + PluginMainClass.clans.get(Bukkit.getPlayer(strings[0])));

        return true;
    }
}
