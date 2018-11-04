package kucameow.main.filesystem;

import kucameow.main.PluginMainClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.List;

public class RegionSaveControl {
    public static List<String> getRegInfo(String name, PluginMainClass pl){
        FileConfiguration r = YamlConfiguration.loadConfiguration(pl.regionsF);
        return r.getStringList("reg." + name);
    }
    public static void saveRegList(String name, PluginMainClass pl, List<String> l){
        FileConfiguration r = YamlConfiguration.loadConfiguration(pl.regionsF);
        r.set("reg." + name, l);
        try {
            r.save(pl.regionsF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
