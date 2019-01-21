package kucameow.main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import kucameow.main.commands.CreateRegion;
import kucameow.main.commands.Debug;
import kucameow.main.filesystem.FileCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import kucameow.main.handlers.*;

public class PluginMainClass extends JavaPlugin{
    /**
     * Main plugin class with initialisation and so on
     */

    public static Logger log = Logger.getLogger("Minecraft");

    public void onEnable() {
        log.warning("\n\nInitialisation of RegionCapture plugin started");
        //Initialisation:
        Bukkit.getPluginManager().registerEvents(new MainHandler(this), this);
        //FileCreator.CreateRegionFile("Test", this, new Location(getServer().getWorlds().get(0), 0, 0, 0));
        getCommand("cr").setExecutor(new CreateRegion(this));
        getCommand("crinfo").setExecutor(new Debug(this));
        log.warning("\n\nInitialisation of RegionCapture plugin finished. Plugin works now\n");
    }
    public void onDisable(){
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
    }
}
