package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import kucameow.main.storage.Region;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;

public class DestroyAndBuild implements Listener {

    private boolean canDo(Player player, Location location, boolean needOtherRegion) {
        if(player.hasPermission("RegionsCaptureGOD")) return true;
        Region ownReg = PluginMainClass.regions.get(PluginMainClass.clans.get(player));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(i == j && i == 2) continue;
                if (ownReg.chunks[i][j].equals(location.getChunk())) {
                    return (location.getY() > ownReg.y && ownReg.chunksBooleans[i][j]);
                }
            }
        }
        for (Map.Entry<String, Region> regionEntry : PluginMainClass.regions.entrySet()){
            if(regionEntry.getValue().equals(ownReg)) continue;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if(i == j && i == 2) continue;
                    if (regionEntry.getValue().chunks[i][j].equals(location.getChunk()) && !regionEntry.getValue().chunksBooleans[i][j]) {
                        return (location.getY() > regionEntry.getValue().y && needOtherRegion);
                    }
                }
            }
        }
        player.sendMessage("" + ChatColor.BOLD + ChatColor.DARK_RED + "Ты не можешь здесь строить и ломать блоки");
        return false;
    }

    @EventHandler
    public void destroy(BlockBreakEvent event){
        event.setCancelled(!canDo(event.getPlayer(), event.getBlock().getLocation(), true));
    }

    @EventHandler
    public void build(BlockPlaceEvent event){
        event.setCancelled(!canDo(event.getPlayer(), event.getBlockPlaced().getLocation(), false));
    }
}
