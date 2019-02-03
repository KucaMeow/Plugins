package kucameow.main;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import kucameow.main.commands.*;
import kucameow.main.mobs.GlobalMobHandler;
import kucameow.main.mobs.WeaponSeller;
import kucameow.main.mobs.ZombieHandler;
import kucameow.main.runnable.ServerLoaded;
import kucameow.main.runnable.SpawnMobs;
import kucameow.main.storage.Region;
import kucameow.main.tools.FileTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import kucameow.main.handlers.*;

/**
 * Главный класс плагина
 */
public class PluginMainClass extends JavaPlugin{
    public static final Logger log = Logger.getLogger("Minecraft");
    public static final Material
            unavailableBlock = Material.NETHERRACK,
            availableBlock = Material.EMERALD_BLOCK,
            centralBlock = Material.SEA_LANTERN;
    public static int preRadius, radius, preTime, time, mobSpawnTime, avRegs, regionCost;


    public static Map<String, Region> regions = new HashMap<>();
    public static Map<Player, String> clans = new HashMap<>();
    public static PluginMainClass pluginMainClass;

    private List<Listener> handlers = new ArrayList<>();
    private static FileConfiguration cfg;

    private void addMobHandlers() {
        handlers.add(new ZombieHandler(pluginMainClass));
        handlers.add(new WeaponSeller());
    }

    public void update(){
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        cfg = YamlConfiguration.loadConfiguration(config);

        preRadius = cfg.getInt("preCaptureRadius");
        radius = cfg.getInt("CaptureRadius");
        preTime = cfg.getInt("preTime");
        time = cfg.getInt("Time");
        mobSpawnTime = cfg.getInt("mobSpawnTime") * 1000;
        avRegs = cfg.getInt("availableRegions");
        regionCost = cfg.getInt("regionCost");
    }

    public void onEnable() {
        pluginMainClass = this;
        log.warning("\n\nInitialisation of RegionCapture plugin started");
        //Initialisation:
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        cfg = YamlConfiguration.loadConfiguration(config);

        if (!config.exists()) {
            cfg.set("preCaptureRadius", 10);
            cfg.set("CaptureRadius", 13);
            cfg.set("preTime", 3);
            cfg.set("Time", 600);
            cfg.set("regionCost", 2500);
            cfg.set("availableRegions", 25);
            cfg.set("mobSpawnTime", 60 * 15);
            try {
                cfg.save(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UpdateConfig updater = new UpdateConfig();
        //Runs on server loading has completed
        Bukkit.getScheduler().runTask(this, new ServerLoaded(updater));

        //Handlers
        Bukkit.getPluginManager().registerEvents(new MainHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new CaptureReady(this), this);
        Bukkit.getPluginManager().registerEvents(new DestroyAndBuild(), this);
        Bukkit.getPluginManager().registerEvents(new GlobalMobHandler(), this);
        Bukkit.getPluginManager().registerEvents(new BuyRegion(), this);
        addMobHandlers();
        for (Listener handler : handlers){
            Bukkit.getPluginManager().registerEvents(handler, this);
        }

        //Commands
        getCommand("cr").setExecutor(new CreateRegion(this));
        getCommand("zombiespawnpoint").setExecutor(new ZombieSpawnPoint(this));
        getCommand("crdeb").setExecutor(new Debug(this));
        getCommand("changeplayerclan").setExecutor(new ChangePlayerClan());
        getCommand("addmoney").setExecutor(new ChangeRegionBudget());
        getCommand("crconfig").setExecutor(updater);


        log.warning("\n\nInitialisation of RegionCapture plugin finished. Plugin works now\n");


        for (Player player : this.getServer().getOnlinePlayers()) {
            if (!PluginMainClass.clans.containsKey(player))
                PluginMainClass.clans.put(player, "Test"); //TODO get player clan
        }


    }
    public void onDisable(){
        Bukkit.getScheduler().cancelTasks(pluginMainClass);
        Bukkit.getScheduler().cancelAllTasks();
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
    }
}
