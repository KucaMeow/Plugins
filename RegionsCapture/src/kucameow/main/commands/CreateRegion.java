package kucameow.main.commands;

import kucameow.main.PluginMainClass;
import kucameow.main.filesystem.FileCreator;
import kucameow.main.tools.ChunkTweaks;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateRegion implements CommandExecutor {
    private final PluginMainClass pl;

    public CreateRegion(PluginMainClass pl){
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 1 || !(commandSender instanceof Player) || !commandSender.hasPermission("regionsCapture.admin")) {
            commandSender.sendMessage(ChatColor.RED + "Error. Maybe you don't have permission or wrong syntax");
            return false;
        }

        FileCreator.CreateRegionFile(strings[0], pl, ((Player) commandSender).getLocation());
        Location l = ((Player) commandSender).getLocation();
        for(int i = -2; i <= 2; i++){
            for(int j = -2; j <= 2; j++){
                ChunkTweaks.createChunkBorder(Material.SOUL_SAND, new Location(l.getWorld(), l.getX() + i*16, l.getY(), l.getZ() + j*16));
            }
        }

        ChunkTweaks.createChunkBorder(Material.SEA_LANTERN, l);
        //pl.getServer().reload();

        return true;
    }
}
