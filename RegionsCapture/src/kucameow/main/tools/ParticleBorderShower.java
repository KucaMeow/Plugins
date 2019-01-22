package kucameow.main.tools;

import kucameow.main.PluginMainClass;
import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleBorderShower implements Runnable {
    Player player;
    Particle particle;
    Chunk chunk;
    int radius;
    PluginMainClass pl;

    public ParticleBorderShower(Player player, Particle particle, Chunk chunk, int radius, PluginMainClass pl) {
        this.player = player;
        this.particle = particle;
        this.chunk = chunk;
        this.radius = radius;
        this.pl = pl;
    }

    @Override
    public void run() {
        while (ChunkTweaks.checkPlayerNearChunk(chunk, player, radius)){
            ChunkTweaks.createChunkParticleBorder(chunk, particle, radius, pl);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
