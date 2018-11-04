package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import kucameow.main.filesystem.RegionSaver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class RegionBreaking implements Listener {
    public RegionBreaking(PluginMainClass pl){
        this.pl = pl;
    }

    private PluginMainClass pl;

    @EventHandler
    public void regionMainBlockBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        if(block.getType() != Material.OBSIDIAN) return;

        FileConfiguration r = YamlConfiguration.loadConfiguration(pl.regionsF);
        List<String> regs = r.getStringList("name");
        for (String s : regs){
            List<String> c = r.getStringList("reg." + s);
            if(block.getX() == Integer.parseInt(c.get(0)) && block.getY() == Integer.parseInt(c.get(1)) && block.getZ() == Integer.parseInt(c.get(2))){
                RegionSaver.changeRegionClan(s, pl);
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.spawnParticle(Particle.EXPLOSION_NORMAL, block.getLocation(), 100);
                    player.playSound(block.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                }
                event.getPlayer().sendMessage("Ты уничтожил базу. Теперь она нейтральная");
                return;
            }
        }
    }
}
