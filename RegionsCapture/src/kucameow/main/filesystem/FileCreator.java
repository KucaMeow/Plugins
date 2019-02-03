package kucameow.main.filesystem;

import kucameow.main.PluginMainClass;
import kucameow.main.storage.Region;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Вспомогательный класс, создающий нужные файлы
 */
public class FileCreator {
    /**
     * Создает файл региона (используется при выполнении  команды /cr)
     * @param name Имя региона
     * @param pl ссылка на Класс главного плагина
     * @param loc Позиция игрока в момент создания региона (получает координаты центраьлного чанка)
     */
    public static void createRegionFile(String name, PluginMainClass pl, Location loc){
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

        fileC.set("X", loc.getChunk().getX());

        fileC.set("Y", (loc.getBlockY() - 1));

        fileC.set("Z", loc.getChunk().getZ());

        fileC.set("world", loc.getWorld().getName());

        fileC.set("regionsCaptured", 0);

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

    /**
     * Создает файл зомби обычного (используется если такого файла еще не сушествует)
     * @param pl ссылка на Класс главного плагина
     */
    public static void createZombieFile(PluginMainClass pl){
        File file = new File(pl.getDataFolder() + File.separator + "mobs" + File.separator + "zombie.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("HP", 300);
        configuration.set("Money", 50);
        ArrayList<String> temp = new ArrayList<>();
        temp.add("this is spawn coords:");
        configuration.set("Coords", temp);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createWeaponSellerFile(){
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File(PluginMainClass.pluginMainClass.getDataFolder()
                + File.separator + "sellers" + File.separator + "weapon.yml"));
        for(int i = 298; i < 318; i++){
            cfg.set((new ItemStack(Material.getMaterial(i))).getType().toString(), i);
        }
        int[] ids = {267, 258, 268, 271, 272, 275, 276, 279, 283, 286};
        for (int i = 0; i < ids.length; i++){
            cfg.set((new ItemStack(Material.getMaterial(ids[i]))).getType().toString(), ids[i]);
        }
        try {
            cfg.save(new File(PluginMainClass.pluginMainClass.getDataFolder()
                    + File.separator + "sellers" + File.separator + "weapon.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
