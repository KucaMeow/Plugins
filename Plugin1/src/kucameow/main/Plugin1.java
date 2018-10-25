package kucameow.main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Plugin1 extends JavaPlugin {
    Logger log = Logger.getLogger("Minecraft");

    public File regionsF, playersF;

    public void onEnable(){
        regionsF = new File(getDataFolder() + File.separator + "regions.yml");
        if(!regionsF.exists()){
            try {
                regionsF.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playersF = new File(getDataFolder() + File.separator + "players.yml");
        if(!playersF.exists()){
            try {
                playersF.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getPluginManager().registerEvents(new Handler(this) ,this);
        Bukkit.getPluginManager().registerEvents(new RegionHandler(this) ,this);
        getCommand("givealmazi").setExecutor(new GoAway(this));
        getCommand("createregion").setExecutor(new RegionCreator(this));
        getCommand("cr").setExecutor(new RegionCreator(this));
        log.info("Plugin successfully started");
    }
    public void onDisable(){
        log.severe("Plugin stopped");
    }
}
