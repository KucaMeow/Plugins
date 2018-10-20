package kucameow.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Plugin1 extends JavaPlugin {
    Logger log = Logger.getLogger("Minecraft");
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(new Handler() ,this);

        log.info("======================");
        log.info("Hello");
        log.warning("Brevnishko was here");
        log.severe("And saw it");
        log.info("======================");
        getCommand("givealmazi").setExecutor(new GoAway(this));
    }
    public void onDisable(){
        log.info("Test stopped");
    }
}
