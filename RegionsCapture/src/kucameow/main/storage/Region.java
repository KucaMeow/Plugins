package kucameow.main.storage;

import com.mysql.fabric.xmlrpc.base.Array;
import kucameow.main.PluginMainClass;
import kucameow.main.tools.ChunkTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Region {
    public PluginMainClass pl;
    public String name;
    public Capture capture = null;

    public int budget;
    public int x, z, y;
    public boolean[][] chunksBooleans = new boolean[5][5];
    public Chunk[][] chunks = new Chunk[5][5];

    public Region(String name, PluginMainClass pl){
        this.name = name;
        this.pl = pl;
        File reg = new File(pl.getDataFolder() + File.separator + name + ".yml");
        FileConfiguration file = YamlConfiguration.loadConfiguration(reg);

        PluginMainClass.log.info(file.getString("Budget"));
        PluginMainClass.log.info(file.getString("X"));
        PluginMainClass.log.info(file.getString("Y"));
        PluginMainClass.log.info(file.getString("Z"));
        budget = file.getInt("Budget");
        x = file.getInt("X");
        y = file.getInt("Y");
        z = file.getInt("Z");

        ArrayList<String> temp = (ArrayList<String>) file.getStringList("Chunks");
        PluginMainClass.log.info(temp.toString());
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                chunks[i][j] = Bukkit.getWorld("world").getChunkAt(x + i - 2, z + j - 2);
                if(temp.get(i).charAt(j) == '#'){
                    chunksBooleans[i][j] = true;
                    ChunkTweaks.createChunkBorder(Bukkit
                            .getWorld("world")
                            .getChunkAt(x + i - 2, z + j - 2)
                            , Material.GLOWSTONE
                            , y+1
                            );
                }
                if(temp.get(i).charAt(j) == 'x'){
                    chunksBooleans[i][j] = false;
                    ChunkTweaks.createChunkBorder(Bukkit
                                    .getWorld("world")
                                    .getChunkAt(x + i - 2, z + j - 2)
                            , Material.SOUL_SAND
                            , y+1
                    );
                }
            }
        }
    }

    public void changeBudget (int newAmount){
        budget = newAmount;
        FileConfiguration file = YamlConfiguration.loadConfiguration(new File(pl.getDataFolder() + File.separator + name + ".yml"));

        file.set("Budget", budget);
        try {
            file.save(new File(pl.getDataFolder() + File.separator + name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBudget(int addAmount){
        changeBudget(budget + addAmount);
    }

    public void removeChunk(Chunk chunk){
        int a = 0, b = 0;
        for(int i = 0; i < 5; ++i){
            for (int j = 0; j < 5; ++j) {
                if(chunk.equals(chunks[i][j])) {
                    a = i;
                    b = j;
                    break;
                }
            }
        }

        chunksBooleans[a][b] = false;

        ChunkTweaks.createChunkBorder(chunks[a][b], Material.SOUL_SAND, y + 1);

        FileConfiguration reg = YamlConfiguration.loadConfiguration(new File(pl.getDataFolder() + File.separator + name + ".yml"));
        ArrayList<String> temp = (ArrayList<String>) reg.getStringList("Chunks");
        StringBuilder tempStr = new StringBuilder();
        for(int f = 0; f < temp.get(a).length(); f++){
            if(f == b) {
                tempStr.append('x');
                continue;
            }
            tempStr.append(temp.get(a).charAt(f));
        }
        temp.set(a, tempStr.toString());
        reg.set("Chunks", temp);
        try {
            reg.save(new File(pl.getDataFolder() + File.separator + name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
