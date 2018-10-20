package kucameow.main;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import kucameow.main.handlers.*;
//import kucameow.main.commands.*;

public class PluginMainClass extends JavaPlugin{
    Logger log = Logger.getLogger("Minecraft");
    public void onEnable() {
        log.warning("\n\nInitialisation of RegionCapture plugin started");
        Bukkit.getPluginManager().registerEvents(new MainHandler(), this);
        log.warning("\n\nInitialisation of RegionCapture plugin finished. Plugin works now\n");
    }
    public void onDisable(){

    }
}
