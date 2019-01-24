package kucameow.main.runnable;

import kucameow.main.storage.Region;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerForbidMoving implements Runnable{
    Player player;

    public PlayerForbidMoving(Player player, Region region) {
        this.player = player;
        this.region = region;
    }

    Region region;

    @Override
    public void run() {
        Bukkit.getScheduler().runTask(region.pl, new Forbid(player, 0));
        while (region.capture != null) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getScheduler().runTask(region.pl, new Forbid(player, (float) 0.2));
    }
}
