package kucameow.main.storage;

import kucameow.main.PluginMainClass;
import kucameow.main.runnable.CaptureObserver;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Capture {
    Chunk chunk;
    public List<Player> warriors = new ArrayList<>();
    public int state = 0;
    public int time = PluginMainClass.preTime;
    private Region region;
    private PluginMainClass pl;

    public Capture(Chunk chunk, Region region, PluginMainClass pl) {
        this.chunk = chunk;
        this.region = region;
        this.pl = pl;
        Bukkit.getScheduler().runTaskAsynchronously(pl, new CaptureObserver(region));
    }

    public Chunk getChunk() {
        return chunk;
    }
    public Region getRegion() {
        return region;
    }
}
