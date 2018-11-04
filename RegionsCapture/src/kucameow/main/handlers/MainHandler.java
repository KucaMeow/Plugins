package kucameow.main.handlers;

import kucameow.main.filesystem.PlayerInfoMessages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import java.util.List;

public class MainHandler implements Listener {
    private kucameow.main.PluginMainClass plugin;

    public MainHandler(kucameow.main.PluginMainClass plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(LocationPicker.locs.get(event.getPlayer()) == null){
            LocationPicker.locs.put(event.getPlayer(), null);
        }
        event.getPlayer().sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "На этом севрере работает плагин захвата территорий. Для справки введите /rc help");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        if(LocationPicker.locs.get(event.getPlayer()) != null){
            LocationPicker.locs.remove(event.getPlayer());
        }
    }

    @EventHandler
    public void checkRegion(PlayerInteractEvent event){
        if(event.getPlayer().getItemInHand().getType() != Material.APPLE || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        FileConfiguration r = YamlConfiguration.loadConfiguration(plugin.regionsF);

        List<String> regs = r.getStringList("name");
        for (String s : regs){
            List<String> c = r.getStringList("reg." + s);
            if(
                    event.getClickedBlock().getX() <= Integer.parseInt(c.get(0)) + Integer.parseInt(c.get(3)) && event.getClickedBlock().getX() >= Integer.parseInt(c.get(0)) - Integer.parseInt(c.get(3))
                    &&
                    event.getClickedBlock().getZ() <= Integer.parseInt(c.get(2)) + Integer.parseInt(c.get(3)) && event.getClickedBlock().getZ() >= Integer.parseInt(c.get(2)) - Integer.parseInt(c.get(3))
            ){
                PlayerInfoMessages.giveRegionInfo(event.getPlayer(), s, plugin);
                return;
            }
        }
    }
}
