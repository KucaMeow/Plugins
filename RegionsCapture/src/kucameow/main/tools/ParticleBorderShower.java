package kucameow.main.tools;

import kucameow.main.PluginMainClass;
import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleBorderShower implements Runnable {
    protected Player player;
    protected Particle particle;
    protected Chunk chunk;
    protected int radius;
    protected PluginMainClass pl;

    protected String message = null;

    public ParticleBorderShower(Player player, Particle particle, Chunk chunk, int radius, PluginMainClass pl, String message) {
        this.player = player;
        this.particle = particle;
        this.chunk = chunk;
        this.radius = radius;
        this.pl = pl;
        this.message = message;
    }

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
            if(message != null) player.sendTitle(message, "", 0, 20, 0);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(message != null) player.sendTitle(message, "", 0, 0, 20);
    }
}
