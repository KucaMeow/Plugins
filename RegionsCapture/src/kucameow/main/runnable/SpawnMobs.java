package kucameow.main.runnable;

import kucameow.main.PluginMainClass;
import kucameow.main.tools.FileTweaks;
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
import java.util.Objects;

/**
 * Поток, создающий мобов каждый промежуток, указанный в конфиге
 * При этом каждый моб создается в координатах, записанных в его файлах, а предыдущие удаляются
 */
public class SpawnMobs implements Runnable{
    @Override
    public void run() {
        PluginMainClass.log.warning("Running mobb spawning");
        if(FileTweaks.regionsFromFile(PluginMainClass.pluginMainClass) == null || FileTweaks.regionsFromFile(PluginMainClass.pluginMainClass).size() == 0 ) return;
        //World world = PluginMainClass.regions.get(Objects.requireNonNull(FileTweaks.regionsFromFile(PluginMainClass.pluginMainClass)).get(0)).getWorld();
        World world = Bukkit.getWorld(YamlConfiguration.loadConfiguration(new File(PluginMainClass.pluginMainClass.getDataFolder() + File.separator + FileTweaks.regionsFromFile(PluginMainClass.pluginMainClass).get(0) + ".yml")).getString("world"));
        while (true) {
            for(Entity e : world.getEntities()){
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
                    Location location = new Location(world, x, y, z);

                    if(PluginMainClass.pluginMainClass != null) Bukkit.getScheduler().runTask(PluginMainClass.pluginMainClass, new SpawnMob(EntityType.ZOMBIE, location));
                }
            }
            try {
                Thread.sleep(PluginMainClass.mobSpawnTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
