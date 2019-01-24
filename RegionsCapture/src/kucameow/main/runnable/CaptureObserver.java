package kucameow.main.runnable;

import kucameow.main.PluginMainClass;
import kucameow.main.storage.Region;
import kucameow.main.tools.ChunkTweaks;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class CaptureObserver implements Runnable {

    private final Region region;

    public CaptureObserver(Region region){
        this.region = region;
    }

    @Override
    public void run() {
        double timeLast = 0;
        while (region.capture.warriors.size() > 0) {
            ChunkTweaks.createChunkParticleBorder(region.capture.getChunk(),
                    region.capture.state == 2 ? Particle.FLAME : Particle.REDSTONE,
                    region.capture.state == 2 ? PluginMainClass.radius : PluginMainClass.preRadius,
                    region.pl);
            for(int i = 0; i < region.capture.warriors.size(); i++){
                Player player = region.capture.warriors.get(i);
                if(timeLast <= region.capture.time) player.setExp((float) (timeLast / (1.0*region.capture.time)));
                player.setLevel((int)(region.capture.time - timeLast));
                if(!ChunkTweaks.checkPlayerNearChunk(region.capture.getChunk(),
                        player,
                        region.capture.state == 2 ? PluginMainClass.radius : PluginMainClass.preRadius)){
                    while (region.capture.warriors.contains(player)) region.capture.warriors.remove(player);
                    if(region.capture.state == 2){
                        player.teleport(new Location(player.getWorld(),
                                PluginMainClass.regions.get(PluginMainClass.clans.get(player)).x*16 + 8,
                                PluginMainClass.regions.get(PluginMainClass.clans.get(player)).y + 1,
                                PluginMainClass.regions.get(PluginMainClass.clans.get(player)).z*16 + 8
                        ));
                        //Bukkit.getScheduler().runTaskAsynchronously(region.pl, new PlayerForbidMoving(player, region));
                    }
                    player.sendTitle("", ChatColor.RED + "Ты покинул захват", 10, 10, 10);
                    player.setExp(0);
                    player.setLevel(0);
                }
            }
            for(Map.Entry<Player, String> clansEntry : PluginMainClass.clans.entrySet()){
                Player player;
                if(clansEntry.getValue().equals(region.name)) player = clansEntry.getKey();
                else continue;
                if(timeLast <= region.capture.time) player.setExp((float) (timeLast / (1.0*region.capture.time)));
                player.setLevel((int)(region.capture.time - timeLast));
            }
            timeLast += 0.3;
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (timeLast > region.capture.time && region.capture.state == 0){
                for (Player pl : region.capture.warriors){
                    pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    pl.sendTitle("" + ChatColor.GOLD + ChatColor.BOLD + "Чтобы начать захват",
                            ChatColor.GOLD + "нажми " + ChatColor.RESET + ChatColor.DARK_RED + "ПКМ",
                            10, 20, 10);
                }
                region.capture.state = 1;
            }
            else if (timeLast > region.capture.time && region.capture.state == 2){
                for (Player player:
                        region.capture.warriors) {
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                    player.setLevel(0);
                    player.setExp(0);
                }
                for(Map.Entry<Player, String> clansEntry : PluginMainClass.clans.entrySet()){
                    Player player;
                    if(clansEntry.getValue().equals(region.name)) player = clansEntry.getKey();
                    else continue;
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                    player.setLevel(0);
                    player.setExp(0);
                }
                //region.removeChunk(region.capture.getChunk());
                Bukkit.getScheduler().runTask(region.pl, new RemoveChunkFromRegion(region, region.capture.getChunk()));
                break;
            }
        }
        region.capture = null;
        for(Map.Entry<Player, String> clansEntry : PluginMainClass.clans.entrySet()){
            Player player;
            if(clansEntry.getValue().equals(region.name)) player = clansEntry.getKey();
            else continue;
            player.setLevel(0);
            player.setExp(0);
        }
    }
}
