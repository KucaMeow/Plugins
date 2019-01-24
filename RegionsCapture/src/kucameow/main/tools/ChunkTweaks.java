package kucameow.main.tools;

import kucameow.main.PluginMainClass;
import kucameow.main.storage.Region;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class ChunkTweaks {
    public static void createChunkBorder (Chunk chunk, Material material, int Y){
        for(int i = 0; i < 16; i++) {
                chunk.getBlock(i, Y - 1, 0).setType(material);
                chunk.getBlock(0, Y - 1, i).setType(material);
                chunk.getBlock(i, Y - 1, 15).setType(material);
                chunk.getBlock(15, Y - 1, i).setType(material);
        }
    }

    public static void createChunkBorder (Material material, Location loc){
        createChunkBorder(loc.getChunk(), material, loc.getBlockY());
    }

    public static void createChunkParticleBorder (Chunk chunk, Particle particle, int radius, PluginMainClass pl){
        int x = chunk.getBlock(0, 0, 0).getX() + 8;
        int z = chunk.getBlock(0, 0, 0).getZ() + 8;
        int y = 0;
        for(int i = 0; i < 255; i++){
            if(chunk.getBlock(0, i + 1, 0).getType().equals(Material.AIR) || chunk.getBlock(0, i, 0).getType().equals(Material.SEA_LANTERN) || chunk.getBlock(0, i, 0).getType().equals(Material.SOUL_SAND) || chunk.getBlock(0, i, 0).getType().equals(Material.GLOWSTONE)) {
                y = chunk.getBlock(0, i, 0).getY() + 2;
                break;
            }
        }
        for(int i = -radius; i <= radius; i++){
                for (Player player:
                     pl.getServer().getOnlinePlayers()) {
                    player.spawnParticle(particle, x + i, y , z + radius, 0);
                    player.spawnParticle(particle, x + i, y , z - radius, 0);
                    player.spawnParticle(particle, x + radius, y , z + i, 0);
                    player.spawnParticle(particle, x - radius, y , z + i, 0);
                }
        }
    }

    private static double mod(double i){
        return i < 0 ? -i : i;
    }
    private static double distance(Player player, Chunk chunk){
        int x = chunk.getBlock(0, 0, 0).getX() + 8;
        int z = chunk.getBlock(0, 0, 0).getZ() + 8;
        return player.getLocation().distance(new Location(player.getWorld(), x, player.getLocation().getY(), z));
    }

    public static Region getNearestRegion(Player player){
        for(Map.Entry<String, Region> regionEntry : PluginMainClass.regions.entrySet()){
            if (
                    mod(player.getLocation().getX() - regionEntry.getValue().x*16) <= 50
                    &&
                    mod(player.getLocation().getZ() - regionEntry.getValue().z*16) <= 50
            ) {
                return regionEntry.getValue();
            }
        }

        return null;
    }

    public static Chunk getNearestChunk(Player player, Region region, int radius){
        Chunk outChunk = null;
        double minDistance = Double.MAX_VALUE;
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(!region.chunksBooleans[i][j] || (i == j && i == 0)) continue;
                if(player.getLocation().getChunk().equals(region.chunks[i][j])) return region.chunks[i][j];
                if(distance(player, region.chunks[i][j]) < minDistance){
                    outChunk = region.chunks[i][j];
                    minDistance = distance(player, region.chunks[i][j]);
                }
            }
        }
        if(outChunk != null && !checkPlayerNearChunk(outChunk, player, radius)) return null;
        return outChunk;
    }

    public static boolean checkPlayerNearChunk (Chunk chunk, Player player, int radius){
        int x = chunk.getBlock(0, 0, 0).getX() + 8;
        int z = chunk.getBlock(0, 0, 0).getZ() + 8;

        return (mod(player.getLocation().getX() - x) <= radius && mod(player.getLocation().getZ() - z) <= radius);
    }
}
