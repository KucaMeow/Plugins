package kucameow.main.commands;

import kucameow.main.PluginMainClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChangeRegionBudget implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 2) return false;
        if(!commandSender.hasPermission("regioncaptureGOD")){
            commandSender.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "У тебя нет разрешения");
        }
        if(!PluginMainClass.regions.containsKey(strings[0])){
            commandSender.sendMessage("Нет такого клана!");
            return false;
        }

        PluginMainClass.regions.get(strings[0]).addBudget(Integer.parseInt(strings[1]));
        commandSender.sendMessage("" + ChatColor.AQUA + ChatColor.BOLD + "Теперь в регионе " + strings[0] + " " + ChatColor.GOLD + PluginMainClass.regions.get(strings[0]).getBudget() + " золота");

        return true;
    }
}
