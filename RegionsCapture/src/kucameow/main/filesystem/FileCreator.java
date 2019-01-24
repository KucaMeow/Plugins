package kucameow.main.filesystem;

import kucameow.main.PluginMainClass;
import kucameow.main.storage.Region;
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
            return;
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

        //temp = new ArrayList<>();
        //temp.add("" + loc.getChunk().getX());
        fileC.set("X", loc.getChunk().getX());

        //temp = new ArrayList<>();
        //temp.add("" + (loc.getBlockY() - 1));
        fileC.set("Y", (loc.getBlockY() - 1));

        //temp = new ArrayList<>();
        //temp.add("" + loc.getChunk().getZ());
        fileC.set("Z", loc.getChunk().getZ());

        //temp = new ArrayList<>();
        //temp.add("0");
        fileC.set("Budget", 0);

        PluginMainClass.log.info("Region file " + name + " created");
        try {
            fileC.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        File regions = new File(pl.getDataFolder() + File.separator + "regions.yml");
        if(!regions.exists()){
            try {
                regions.createNewFile();
                YamlConfiguration.loadConfiguration(regions).set("regions", new ArrayList<String>());
                YamlConfiguration.loadConfiguration(regions).save(regions);
                PluginMainClass.log.info("regions.yml created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration regs = YamlConfiguration.loadConfiguration(regions);

        temp = (ArrayList<String>) regs.getStringList("regions");
        temp.add(name);
        regs.set("regions", temp);
        try {
            regs.save(regions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PluginMainClass.regions.put(name, new Region(name, pl));
    }
}
