package kucameow.main.commands;

import kucameow.main.PluginMainClass;
import kucameow.main.filesystem.RegionSaver;
import kucameow.main.handlers.Area;
import kucameow.main.handlers.RegionHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RegionCreator implements CommandExecutor {
    private PluginMainClass pl;

    public RegionCreator(PluginMainClass plugin1) {
        pl = plugin1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if ((args.length != 1 && args.length != 2)|| !(sender instanceof Player)) return false;
        String clan = null;
        if(args.length == 2) clan = args[1];
        return RegionSaver.saveRegion((Player) sender, args[0], pl, clan);
    }
}
