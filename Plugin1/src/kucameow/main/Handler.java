package kucameow.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Handler implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent ev){
        Player pl = ev.getPlayer();
        pl.sendMessage("Тебя приветсвует KucaMeow");
        pl.sendMessage("Получай 10 экспы!");
        pl.giveExp(10);
    }

    @EventHandler
    public void breake(BlockBreakEvent ev){
        Player p = ev.getPlayer();
        p.sendMessage("" + p.getDisplayName() + ", ты сломал мой блок! Пожалйста, хватит так делать");
        p.sendMessage("Это был мой " + ev.getBlock().getType());
    }
}
