package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import kucameow.main.runnable.EnemiesListShower;
import kucameow.main.runnable.PlayerListShower;
import kucameow.main.storage.Capture;
import kucameow.main.storage.Region;
import kucameow.main.tools.ChunkTweaks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import java.util.Map;

public class CaptureReady implements Listener {

    public CaptureReady(PluginMainClass pluginMainClass) {
        pl = pluginMainClass;
    }

    private PluginMainClass pl;

    @EventHandler
    public void captureReady(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Region region = ChunkTweaks.getNearestRegion(player);
        if(region == null || region.equals(PluginMainClass.regions.get(PluginMainClass.clans.get(player)))) return;
        if(region.capture != null && region.capture.warriors.contains(player)) return;
        if(region.capture == null && ChunkTweaks.getNearestChunk(player, region, PluginMainClass.preRadius) != null){
            region.capture = new Capture(ChunkTweaks.getNearestChunk(player, region, PluginMainClass.preRadius), region, pl);
        }
        if(region.capture != null && !ChunkTweaks.checkPlayerNearChunk(region.capture.getChunk(), player, PluginMainClass.preRadius)) return;
        if(region.capture == null) return;
        if(!region.capture.warriors.contains(player) && region.capture.state != 2) {
            player.sendTitle("", ChatColor.DARK_GREEN + "Ты готов к захвату", 10, 10, 10);
            region.capture.warriors.add(player);
            if(region.capture.state == 1)
                player.sendTitle("" + ChatColor.GOLD + ChatColor.BOLD + "Чтобы начать захват",
                        ChatColor.GOLD + "нажми " + ChatColor.RESET + ChatColor.DARK_RED + "ПКМ",
                        10, 20, 10);
        }
    }
//
//    @EventHandler
//    public void ca()

    @EventHandler
    public void captureStart(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Region region = ChunkTweaks.getNearestRegion(player);
        if(region == null) return;

        if(region.capture != null &&
                ChunkTweaks.getNearestChunk(player, region, PluginMainClass.preRadius) != null &&
                region.capture.state == 1
        ){
            if     (event.getAction() == Action.RIGHT_CLICK_AIR ||
                    event.getAction() == Action.RIGHT_CLICK_BLOCK
            ) {
                Bukkit.getScheduler().runTaskAsynchronously(pl, new PlayerListShower(player, region));
                for(Map.Entry<Player, String> clansEntry : PluginMainClass.clans.entrySet()){
                    Player player2;
                    if(clansEntry.getValue().equals(region.name)) player2 = clansEntry.getKey();
                    else continue;
                    Bukkit.getScheduler().runTaskAsynchronously(pl, new EnemiesListShower(player2, region));
                }
                region.capture.state = 2;
                region.capture.time = PluginMainClass.time + region.capture.warriors.size()*10;
            }
        }
    }
}
