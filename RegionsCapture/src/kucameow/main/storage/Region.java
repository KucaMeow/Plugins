package kucameow.main.storage;

import kucameow.main.PluginMainClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

public class Region {
    int budget;
    int x, z, y;
    boolean[][] chunks = new boolean[5][5];

    public Region(String name, PluginMainClass pl){
        File reg = new File(pl.getDataFolder() + File.separator + name + ".yml");
        FileConfiguration file = YamlConfiguration.loadConfiguration(reg);

        budget = Integer.parseInt(file.getStringList("Budget").get(0));
        x = Integer.parseInt(file.getStringList("X").get(0));
        y = Integer.parseInt(file.getStringList("Y").get(0));
        z = Integer.parseInt(file.getStringList("Z").get(0));

        ArrayList<String> temp = (ArrayList<String>) file.getStringList("Chunks");
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(temp.get(i).charAt(j) == '#'){
                    chunks[i][j] = true;
                }
            }
        }
    }
}
