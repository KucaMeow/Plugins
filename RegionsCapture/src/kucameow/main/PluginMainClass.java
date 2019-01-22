package kucameow.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import kucameow.main.commands.CreateRegion;
import kucameow.main.commands.Debug;
import kucameow.main.storage.Region;
import kucameow.main.tools.FileTweaks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import kucameow.main.handlers.*;

public class PluginMainClass extends JavaPlugin{
    /**
     * Main plugin class with initialisation and so on
     */

    public static Logger log = Logger.getLogger("Minecraft");

    public static Map<String, Region> regions = new HashMap<>();

    public void onEnable() {
        log.warning("\n\nInitialisation of RegionCapture plugin started");
        //Initialisation:
        Bukkit.getPluginManager().registerEvents(new MainHandler(this), this);
        //FileCreator.CreateRegionFile("Test", this, new Location(getServer().getWorlds().get(0), 0, 0, 0));
        getCommand("cr").setExecutor(new CreateRegion(this));
        getCommand("crinfo").setExecutor(new Debug(this));

        //creating server regions
        for (String regName : FileTweaks.regionsFromFile(this)){
            log.info(regName + " has been added");
            regions.put(regName, new Region(regName, this));
        }

        log.warning("\n\nInitialisation of RegionCapture plugin finished. Plugin works now\n");
    }
    public void onDisable(){
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
    }
}
