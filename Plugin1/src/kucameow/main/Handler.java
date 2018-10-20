package kucameow.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Handler implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent ev){
        Player pl = ev.getPlayer();
        pl.sendMessage("Тебя приветсвует KucaMeow");
    }
}
