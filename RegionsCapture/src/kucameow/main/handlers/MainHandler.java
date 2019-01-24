package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Main handler with main plugin event handlers
 */
public class MainHandler implements Listener {
    private PluginMainClass pl;

    public MainHandler (PluginMainClass pl){
        this.pl = pl;
    }

    @EventHandler
    public void setPlayerClan(PlayerJoinEvent ev){
        if(!PluginMainClass.clans.containsKey(ev.getPlayer()))
            PluginMainClass.clans.put(ev.getPlayer(), "Test"); //TODO get player clan
        //ev.getPlayer().setWalkSpeed((float) 0.2);
    }
}
