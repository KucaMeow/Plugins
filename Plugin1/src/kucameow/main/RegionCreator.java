package kucameow.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class RegionCreator implements CommandExecutor {
    private Plugin1 pl;
    public RegionCreator(Plugin1 plugin1) {
        pl = plugin1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if(args.length != 1) return false;

        return true;
    }
}
