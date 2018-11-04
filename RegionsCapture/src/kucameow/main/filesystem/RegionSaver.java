package kucameow.main.filesystem;

import kucameow.main.PluginMainClass;
import kucameow.main.handlers.LocationPicker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
//////////////////////////////////////////////////
Первые 3 значения - координаты
// Индексы 0 - 2
4ое значение - радиус
// Индекс 3
5ое значени - бюджет
// Индекс 4
6ое значени - Клан
// Индекс 5
//////////////////////////////////////////////////
*/
public class RegionSaver {
    public static boolean saveRegion(Player player, String name, PluginMainClass pl, String clan){

        FileConfiguration r = YamlConfiguration.loadConfiguration(pl.regionsF);

        List<String> l = r.getStringList("reg." + name);
        Location a = LocationPicker.locs.get(player);

        if (!r.contains("reg")) {
            r.set("reg", new ArrayList<>());
            try {
                r.save(pl.regionsF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (a == null) {
            player.sendMessage("Ошибка выделения области. Перезайди на сервер");
            return true;
        }

        if (l.isEmpty()) {
            l = new ArrayList<>();
            l.add(Integer.toString((int) a.getX()));
            l.add(Integer.toString((int) a.getY()));
            l.add(Integer.toString((int) a.getZ()));
            l.add("8");
            l.add("5000");
            if(clan == null)
                l.add("Neutral");
            else
                l.add(clan);
        } else {
            l.set(0, Integer.toString((int) a.getX()));
            l.set(1, Integer.toString((int) a.getY()));
            l.set(2, Integer.toString((int) a.getZ()));
            l.set(3, "8");
            l.set(4, "5000");
            if(clan == null)
                l.set(5, "Neutral");
            else
                l.set(5, clan);
        }
        List<String> f = r.getStringList("name");
        if (!f.contains(name)) f.add(name);
        r.set("name", f);

        r.set("reg." + name, l);
        player.sendMessage("Сохранение нового региона . . .");
        try {
            r.save(pl.regionsF);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Block block = a.getBlock();
        block.setType(Material.OBSIDIAN);
        return true;
    }
    public static void changeRegionClan(String name, PluginMainClass pl){
        List<String> l = RegionSaveControl.getRegInfo(name, pl);
        l.set(5, "Neutral");
        RegionSaveControl.saveRegList(name, pl, l);
    }
}
