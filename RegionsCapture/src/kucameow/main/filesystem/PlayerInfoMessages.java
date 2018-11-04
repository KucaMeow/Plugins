package kucameow.main.filesystem;

import kucameow.main.PluginMainClass;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

import static java.lang.Integer.parseInt;

public class PlayerInfoMessages {
    public static void giveRegionInfo(Player player, String rgname, PluginMainClass pl){
        List<String> l = RegionSaveControl.getRegInfo(rgname, pl);
        player.sendMessage("X: " + l.get(0));
        player.sendMessage("Y: " + l.get(1));
        player.sendMessage("Z: " + l.get(2));
        player.sendMessage("Радиус: " + l.get(3));
        player.sendMessage("Бюджет: " + l.get(4));
        player.sendMessage("Принадлежит: " + l.get(5));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        for(double i = player.getLocation().getY(); i < player.getLocation().getY() + 15; i += 0.5) {
            player.spawnParticle(Particle.REDSTONE, Double.parseDouble(l.get(0)) - Double.parseDouble(l.get(3)), i, Double.parseDouble(l.get(2)) - Double.parseDouble(l.get(3)), 1);
            player.spawnParticle(Particle.REDSTONE, Double.parseDouble(l.get(0)) - Double.parseDouble(l.get(3)), i, Double.parseDouble(l.get(2)) + Double.parseDouble(l.get(3)), 1);
            player.spawnParticle(Particle.REDSTONE, Double.parseDouble(l.get(0)) + Double.parseDouble(l.get(3)), i, Double.parseDouble(l.get(2)) - Double.parseDouble(l.get(3)), 1);
            player.spawnParticle(Particle.REDSTONE, Double.parseDouble(l.get(0)) + Double.parseDouble(l.get(3)), i, Double.parseDouble(l.get(2)) + Double.parseDouble(l.get(3)), 1);
        }
    }
}
