package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionHandler implements Listener {

    private PluginMainClass plugin;

    public RegionHandler(PluginMainClass pl){
        this.plugin = pl;
    }

    //TODO Переделать систему проверки на наличие игрока в каком-либо клане
    private boolean checkRegion(Block block, Player p){
        FileConfiguration r = YamlConfiguration.loadConfiguration(plugin.regionsF);
        boolean out = false;

        List<String> regs = r.getStringList("name");
        String rgname = "NULL";
        for (String s : regs){
            List<String> c = r.getStringList("reg." + s);
            out = out || block.getX() >= Integer.parseInt(c.get(0)) - Integer.parseInt(c.get(3)) && block.getX() <= Integer.parseInt(c.get(0)) + Integer.parseInt(c.get(3))
                    &&
                         block.getZ() >= Integer.parseInt(c.get(2)) - Integer.parseInt(c.get(3)) && block.getZ() <= Integer.parseInt(c.get(2)) + Integer.parseInt(c.get(3));
            if(out) rgname = s;
        }
        if(out && p.hasPermission("cr.break")){
            p.sendMessage("Ты ломаешь блок в регионе " + rgname);
            return false;
        }
        if(out) p.sendMessage("Регион " + rgname + " принадлежит другому клану");
        return out;
    }

    //Check of breaking/building
    @EventHandler
    public void firstSave(BlockBreakEvent ev){
        if(!checkRegion(ev.getBlock(), ev.getPlayer())) return;
        Player p = ev.getPlayer();
        ev.setCancelled(true);
    }
    @EventHandler
    public void secondSave(PlayerInteractEvent ev){
        if(ev.getAction() == Action.LEFT_CLICK_BLOCK || ev.getAction() == Action.RIGHT_CLICK_BLOCK) return;
        if(!checkRegion(ev.getClickedBlock(), ev.getPlayer())) return;
        Player p = ev.getPlayer();
        if(ev.getAction() == Action.PHYSICAL && ev.getClickedBlock().getType() == Material.SOIL){
            ev.setCancelled(true);
        }
    }
    @EventHandler
    public void thirdSave(BlockPlaceEvent ev){
        if(!checkRegion(ev.getBlock(), ev.getPlayer())) return;
        Player p = ev.getPlayer();
        ev.setCancelled(true);
    }

}
