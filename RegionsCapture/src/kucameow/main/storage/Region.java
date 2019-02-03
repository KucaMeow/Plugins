package kucameow.main.storage;

import com.mysql.fabric.xmlrpc.base.Array;
import kucameow.main.PluginMainClass;
import kucameow.main.tools.ChunkTweaks;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Объект региона для хранения его в памяти сервера
 */
public class Region {
    /**
     * Ссылка на главный Класс плагина
     */
    public PluginMainClass pl;
    /**
     * Название региона
     */
    public String name;
    /**
     * Объект захвата
     */
    public Capture capture = null;
    /**
     * Бюджет
     */
    private int budget;
    /**
     * Координаты центрального чанка
     */
    public int x, z, y;
    /**
     * Массив доступных регионов
     * true - Доступен
     * false - Недоступен
     */
    public boolean[][] chunksBooleans = new boolean[5][5];
    /**
     * Массив чанков
     */
    public Chunk[][] chunks = new Chunk[5][5];
    /**
     * Мир, в котором находится регион
     */
    private World world;
    /**
     * "Инвентарь" для покупки регионов
     */
    private Inventory inventory = Bukkit.createInventory(null, 45, "Регионы");
    ItemStack budI = new ItemStack(Material.GOLD_INGOT);
    ItemStack avReg = new ItemStack(Material.GREEN_SHULKER_BOX);
    ItemStack nAvReg = new ItemStack(Material.RED_SHULKER_BOX);

    public int regionsCaptured = 0;
    /**
     * Конструктор, копирующий всю информацию из файла в память сервера
     * @param name Имя региона
     * @param pl сыллка на главный Класс плагина
     */
    public Region(String name, PluginMainClass pl){
        ItemMeta im = avReg.getItemMeta();
        im.setDisplayName("" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Доступен");
        avReg.setItemMeta(im);

        im = nAvReg.getItemMeta();
        im.setDisplayName("" + ChatColor.DARK_RED + ChatColor.BOLD + "Недоступен");
        nAvReg.setItemMeta(im);
        this.name = name;
        this.pl = pl;
        update();
    }

    public void update(){
        File reg = new File(pl.getDataFolder() + File.separator + name + ".yml");
        if(!reg.exists()){
            PluginMainClass.log.warning("Региона " + name + " не существует");
            return;
        }
        FileConfiguration file = YamlConfiguration.loadConfiguration(reg);

        PluginMainClass.log.info(file.getString("Budget"));
        PluginMainClass.log.info(file.getString("X"));
        PluginMainClass.log.info(file.getString("Y"));
        PluginMainClass.log.info(file.getString("Z"));
        budget = file.getInt("Budget");
        x = file.getInt("X");
        y = file.getInt("Y");
        z = file.getInt("Z");
        regionsCaptured = file.getInt("regionsCaptured");
        world = Bukkit.getWorld(file.getString("world"));


        ItemMeta im = budI.getItemMeta();
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Бюджет: " + budget);
        budI.setItemMeta(im);
        inventory.setItem(0, budI);

        ArrayList<String> temp = (ArrayList<String>) file.getStringList("Chunks");
        PluginMainClass.log.info(temp.toString());
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                chunks[i][j] = world.getChunkAt(x + j - 2, z + i - 2);
                if(temp.get(i).charAt(j) == '#'){
                    chunksBooleans[i][j] = true;
                    inventory.setItem(2 + i*9 + j, avReg);
                    ChunkTweaks.createChunkBorder(
                            world
                                    .getChunkAt(x + j - 2, z + i - 2)
                            , PluginMainClass.availableBlock
                            , y+1
                    );
                }
                if(temp.get(i).charAt(j) == 'x'){
                    inventory.setItem(2 + i*9 + j, nAvReg);
                    chunksBooleans[i][j] = false;
                    ChunkTweaks.createChunkBorder(
                            world
                                    .getChunkAt(x + j - 2, z + i - 2)
                            , PluginMainClass.unavailableBlock
                            , y+1
                    );
                }
            }
        }
    }

    /**
     * Меняет бюджет клана на новое значение
     * @param newAmount новое значение
     */
    public void changeBudget (int newAmount){
        budget = newAmount;
        FileConfiguration file = YamlConfiguration.loadConfiguration(new File(pl.getDataFolder() + File.separator + name + ".yml"));

        file.set("Budget", budget);
        try {
            file.save(new File(pl.getDataFolder() + File.separator + name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ItemMeta im = budI.getItemMeta();
        im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Бюджет: " + budget);
        budI.setItemMeta(im);
        inventory.setItem(0, budI);
    }

    /**
     * Добавляет к бюджету клана значение
     * @param addAmount значение
     */
    public void addBudget(int addAmount){
        changeBudget(budget + addAmount);
    }

    /**
     * Удаляет чанк из региона
     * @param chunk чанк, удаляемый из региона
     */
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
        inventory.setItem(2 + a*9 + b, nAvReg);

        ChunkTweaks.createChunkBorder(chunks[a][b], PluginMainClass.unavailableBlock, y + 1);

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

    public void addChunk(Chunk chunk){
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

        chunksBooleans[a][b] = true;
        inventory.setItem(2 + a*9 + b, avReg);

        ChunkTweaks.createChunkBorder(chunks[a][b], PluginMainClass.availableBlock, y + 1);

        FileConfiguration reg = YamlConfiguration.loadConfiguration(new File(pl.getDataFolder() + File.separator + name + ".yml"));
        ArrayList<String> temp = (ArrayList<String>) reg.getStringList("Chunks");
        StringBuilder tempStr = new StringBuilder();
        for(int f = 0; f < temp.get(a).length(); f++){
            if(f == b) {
                tempStr.append('#');
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

    public int getBudget() {
        return budget;
    }

    public World getWorld() {
        return world;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
