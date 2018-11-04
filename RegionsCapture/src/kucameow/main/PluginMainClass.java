package kucameow.main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import kucameow.main.commands.RegionCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import kucameow.main.handlers.*;

public class PluginMainClass extends JavaPlugin{

    public File regionsF;

    public static Logger log = Logger.getLogger("Minecraft");

    public void onEnable() {

        regionsF = new File(getDataFolder() + File.separator + "regions.yml");
        if(!regionsF.exists()){
            try {
                regionsF.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Player p : Bukkit.getOnlinePlayers()){
            LocationPicker.locs.put(p, null);
        }

        log.warning("\n\nInitialisation of RegionCapture plugin started");
        //Initialisation:
        Bukkit.getPluginManager().registerEvents(new MainHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new RegionHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new LocationPicker(this), this);
        Bukkit.getPluginManager().registerEvents(new RegionBreaking(this), this);
        getCommand("cr").setExecutor(new RegionCreator(this));
        log.warning("\n\nInitialisation of RegionCapture plugin finished. Plugin works now\n");
    }
    public void onDisable(){
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
    }
}
