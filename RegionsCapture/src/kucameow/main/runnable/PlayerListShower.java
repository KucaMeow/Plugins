package kucameow.main.runnable;

import kucameow.main.PluginMainClass;
import kucameow.main.storage.Region;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Поток показывающий захватчикам их количество и ники союзников
 */
public class PlayerListShower implements Runnable{
    Player player;
    Region region;

    public PlayerListShower(Player player, Region region) {
        this.player = player;
        this.region = region;
    }


    @Override
    public void run() {
        player.sendTitle(ChatColor.RED + "Вас " + region.capture.warriors.size() + " учавствует в захвате", "", 10, 10, 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Player players : region.capture.warriors){
            player.sendTitle(ChatColor.RED + players.getDisplayName(), "", 10, 10, 10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        player.sendTitle(ChatColor.DARK_AQUA + "Захват займет " + region.capture.time + " секунд", "", 10, 10, 10);
    }
}
