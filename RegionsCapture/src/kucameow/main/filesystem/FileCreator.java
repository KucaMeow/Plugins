package kucameow.main.filesystem;

import kucameow.main.PluginMainClass;
import org.bukkit.Location;
import org.bukkit.configuration.file.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileCreator {
    public static void CreateRegionFile(String name, PluginMainClass pl, Location loc){
        File file = new File(pl.getDataFolder() + File.separator + name + ".yml");
        if(file.exists()){
            PluginMainClass.log.warning("Region's already exist");
            //return;
        }
        else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration fileC = YamlConfiguration.loadConfiguration(file);

        ArrayList<String> temp = new ArrayList<>();
        temp.add("xxxxx");
        temp.add("xxxxx");
        temp.add("xxoxx");
        temp.add("xxxxx");
        temp.add("xxxxx");
        fileC.set("Chunks", temp);

        temp = new ArrayList<>();
        temp.add("" + loc.getChunk().getX());
        fileC.set("X", temp);

        temp = new ArrayList<>();
        temp.add("" + (loc.getBlockY() - 1));
        fileC.set("y", temp);

        temp = new ArrayList<>();
        temp.add("" + loc.getChunk().getZ());
        fileC.set("Z", temp);

        temp = new ArrayList<>();
        temp.add("0");
        fileC.set("Budget", temp);

        PluginMainClass.log.info("Region file " + name + " created");
        try {
            fileC.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
