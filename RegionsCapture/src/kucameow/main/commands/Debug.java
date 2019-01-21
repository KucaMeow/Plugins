package kucameow.main.commands;

import kucameow.main.PluginMainClass;
import kucameow.main.tools.ChunkTweaks;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Debug implements CommandExecutor {

    private final PluginMainClass pl;

    public Debug(PluginMainClass pl){
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ChunkTweaks.createChunkBorder(Material.GLOWSTONE, ((Player) commandSender).getLocation());
        return true;
    }
}
