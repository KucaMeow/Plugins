package kucameow.main.runnable;

import kucameow.main.storage.Region;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EnemiesListShower implements Runnable {
    Player player;
    Region region;

    public EnemiesListShower(Player player, Region region) {
        this.player = player;
        this.region = region;
    }


    @Override
    public void run() {
        player.sendTitle(ChatColor.RED + "У вас " + region.capture.warriors.size() + " противников", "", 10, 10, 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Player players : region.capture.warriors){
            player.sendTitle(ChatColor.DARK_RED + players.getDisplayName(), "", 10, 10, 10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
