package kucameow.main;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Plugin1 extends JavaPlugin {
    Logger log = Logger.getLogger("Minecraft");
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(new Handler() ,this);
        getCommand("givealmazi").setExecutor(new GoAway(this));
        log.info("Plugin successfully started");
    }
    public void onDisable(){
        log.severe("Plugin stopped");
    }
}
