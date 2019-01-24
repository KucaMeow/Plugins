package kucameow.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import kucameow.main.commands.CreateRegion;
import kucameow.main.commands.Debug;
import kucameow.main.commands.ZombieSpawnPoint;
import kucameow.main.mobs.ZombieHandler;
import kucameow.main.storage.Region;
import kucameow.main.tools.FileTweaks;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import kucameow.main.handlers.*;

public class PluginMainClass extends JavaPlugin{
    /**
     * Main plugin class with initialisation and so on
     */

    public static Logger log = Logger.getLogger("Minecraft");

    public static Map<String, Region> regions = new HashMap<>();
    public static Map<Player, String> clans = new HashMap<>();
    public static int preRadius, radius, preTime, time;

    public static FileConfiguration cfg;
    public static PluginMainClass pluginMainClass;

    public void onEnable() {
        pluginMainClass = this;
        log.warning("\n\nInitialisation of RegionCapture plugin started");
        //Initialisation:
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        cfg = YamlConfiguration.loadConfiguration(config);

        if (!config.exists()) {
            try {
                config.createNewFile();
                cfg.set("preCaptureRadius", 10);
                cfg.set("CaptureRadius", 13);
                cfg.set("preTime", 3);
                cfg.set("Time", 600);

                cfg.save(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        preRadius = cfg.getInt("preCaptureRadius");
        radius = cfg.getInt("CaptureRadius");
        preTime = cfg.getInt("preTime");
        time = cfg.getInt("Time");

        Bukkit.getPluginManager().registerEvents(new MainHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new CaptureReady(this), this);
        Bukkit.getPluginManager().registerEvents(new DestroyAndBuild(), this);
        Bukkit.getPluginManager().registerEvents(new ZombieHandler(this), this);
        getCommand("cr").setExecutor(new CreateRegion(this));
        getCommand("zombiespawnpoint").setExecutor(new ZombieSpawnPoint(this));
        getCommand("crinfo").setExecutor(new Debug(this));
        Bukkit.getScheduler().runTaskAsynchronously(this, new SpawnMobs());

        //creating server regions
        for (String regName : FileTweaks.regionsFromFile(this)) {
            log.info(regName + " has been added");
            regions.put(regName, new Region(regName, this));
        }

        log.warning("\n\nInitialisation of RegionCapture plugin finished. Plugin works now\n");
        for (Player player : this.getServer().getOnlinePlayers()) {
            if (!PluginMainClass.clans.containsKey(player))
                PluginMainClass.clans.put(player, "Test"); //TODO get player clan
        }
    }
    public void onDisable(){
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
    }
}
