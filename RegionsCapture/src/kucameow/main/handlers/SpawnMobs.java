package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import kucameow.main.runnable.SpawnMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

import java.io.File;
import java.util.ArrayList;

public class SpawnMobs implements Runnable{
    @Override
    public void run() {
        while (true) {
            for(Entity e : Bukkit.getWorld("world").getEntities()){
                if(e instanceof Zombie) e.remove();
            }
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(new File(PluginMainClass.pluginMainClass.getDataFolder() + File.separator + "mobs" + File.separator + "zombie.yml"));
            ArrayList<String> temp = (ArrayList<String>) fileConfiguration.getStringList("Coords");
            if(temp.size() > 1) {
                for (String loc : temp) {
                    if (loc.contains("this is")) continue;
                    double x = Double.parseDouble(loc.substring(loc.indexOf(",x=") + 3, loc.indexOf(",y")));
                    double y = Double.parseDouble(loc.substring(loc.indexOf(",y=") + 3, loc.indexOf(",z")));
                    double z = Double.parseDouble(loc.substring(loc.indexOf(",z=") + 3, loc.indexOf(",p")));
                    Location location = new Location(Bukkit.getWorld("world"), x, y, z);

                    Bukkit.getScheduler().runTask(PluginMainClass.pluginMainClass, new SpawnMob(EntityType.ZOMBIE, location));
                    //location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                }
            }
            try {
                Thread.sleep(15*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
