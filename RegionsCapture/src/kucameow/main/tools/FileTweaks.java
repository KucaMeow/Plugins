package kucameow.main.tools;

import kucameow.main.PluginMainClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

public class FileTweaks {
    public static ArrayList<String> regionsFromFile(PluginMainClass pl){
        FileConfiguration regs = YamlConfiguration.loadConfiguration(new File(pl.getDataFolder() + File.separator + "regions.yml"));

        return (ArrayList<String>) regs.getStringList("regions");
    }
}
