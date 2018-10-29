package kucameow.main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import kucameow.main.commands.RegionCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import kucameow.main.handlers.*;
//import kucameow.main.commands.*;

public class PluginMainClass extends JavaPlugin{

    public File regionsF;

    public Logger log = Logger.getLogger("Minecraft");

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
            RegionHandler.areas.put(p, new Area(0,0,0,0,0,0));
        }

        log.warning("\n\nInitialisation of RegionCapture plugin started");
        //Initialisation:
        Bukkit.getPluginManager().registerEvents(new MainHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new RegionHandler(this), this);
        getCommand("cr").setExecutor(new RegionCreator(this));
        log.warning("\n\nInitialisation of RegionCapture plugin finished. Plugin works now\n");
    }
    public void onDisable(){
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
        log.severe("PLUGIN SOMEHOW WAS STOPPED");
    }
}
