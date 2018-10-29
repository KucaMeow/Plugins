package kucameow.main.handlers;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;

public class MainHandler implements Listener {
    private kucameow.main.PluginMainClass plugin;

    public MainHandler(kucameow.main.PluginMainClass plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(RegionHandler.areas.get(event.getPlayer()) == null){
            RegionHandler.areas.put(event.getPlayer(), new Area(0,0,0,0,0,0));
        }
        event.getPlayer().sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "На этом севрере работает плагин захвата территорий. Для справки введите /rc help");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        if(RegionHandler.areas.get(event.getPlayer()) != null){
            RegionHandler.areas.remove(event.getPlayer());
        }
    }
}
