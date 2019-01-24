package kucameow.main.commands;

import kucameow.main.PluginMainClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ZombieSpawnPoint implements CommandExecutor {

    private final PluginMainClass pl;

    public ZombieSpawnPoint(PluginMainClass pl){
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.hasPermission("RegionsCaptureGOD")) return true;
        ((Player)commandSender).getWorld().spawnEntity(((Player) commandSender).getLocation(), EntityType.ZOMBIE);
        File file = new File(pl.getDataFolder() + File.separator + "mobs" + File.separator + "zombie.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        ArrayList<String> temp = (ArrayList<String>) configuration.getStringList("Coords");
        temp.add(((Player) commandSender).getLocation().toString());
        configuration.set("Coords", temp);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
