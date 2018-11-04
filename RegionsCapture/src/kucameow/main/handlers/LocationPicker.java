package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LocationPicker implements Listener {
    private PluginMainClass pl;
    public static Map<Player, Location> locs = new HashMap<Player, Location>();

    public LocationPicker(PluginMainClass pl){
        this.pl = pl;
    }

    private void saveCoor(PlayerInteractEvent e, Location loc){
        if(loc == null){
            e.getPlayer().sendMessage("Ошибка выделения территории. Перезайди на сервер");
            return;
        }
        locs.replace(e.getPlayer(), loc);
    }

    private String writeC(Location loc){
        return  "\nX: " + loc.getX() +
                "\nY: " + loc.getY() +
                "\nZ: " + loc.getZ();
    }

    //Getting positions
    @EventHandler
    public void getFirstLocation(PlayerInteractEvent e) {
        if (!e.getPlayer().hasPermission("admin")) return;
        ItemStack i = e.getItem();
        if (i.getType() != Material.STONE_AXE) return;
        if (e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        e.setCancelled(true);
        saveCoor(e, e.getClickedBlock().getLocation());
        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Выделена точка: " + writeC(e.getClickedBlock().getLocation()));
    }
}
