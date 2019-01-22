package kucameow.main.storage;

import kucameow.main.PluginMainClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

public class Region {
    public int budget;
    public int x, z, y;
    public boolean[][] chunks = new boolean[5][5];

    public Region(String name, PluginMainClass pl){
        File reg = new File(pl.getDataFolder() + File.separator + name + ".yml");
        FileConfiguration file = YamlConfiguration.loadConfiguration(reg);

        PluginMainClass.log.info(file.getString("Budget"));
        PluginMainClass.log.info(file.getString("X"));
        PluginMainClass.log.info(file.getString("Y"));
        PluginMainClass.log.info(file.getString("Z"));
        budget = Integer.parseInt(file.getString("Budget").substring(1, file.getString("Budget").length()-1));
        x = Integer.parseInt(file.getString("X").substring(1, file.getString("X").length()-1));
        y = Integer.parseInt(file.getString("Y").substring(1, file.getString("Y").length()-1));
        z = Integer.parseInt(file.getString("Z").substring(1, file.getString("Z").length()-1));

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
