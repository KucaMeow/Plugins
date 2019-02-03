package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

/**
 * Главный слушатель, отвечающий за общие действия
 */
public class MainHandler implements Listener {
    private PluginMainClass pl;

    public MainHandler (PluginMainClass pl){
        this.pl = pl;
    }

    /**
     * Устанавливает игроку его клан, когда он только зашел на сервер
     * @param ev Игрок присоединился к серверу
     */
    @EventHandler
    public void setPlayerClan(PlayerJoinEvent ev){
        if(!PluginMainClass.clans.containsKey(ev.getPlayer()))
            PluginMainClass.clans.put(ev.getPlayer(), "Test"); //TODO get player clan
        //ev.getPlayer().setWalkSpeed((float) 0.2);
        if(PluginMainClass.regions.containsKey(PluginMainClass.clans.get(ev.getPlayer()))) {
            Location location = new Location(ev.getPlayer().getWorld(),
                    PluginMainClass.regions.get(PluginMainClass.clans.get(ev.getPlayer())).x * 16 + 8,
                    PluginMainClass.regions.get(PluginMainClass.clans.get(ev.getPlayer())).y + 1,
                    PluginMainClass.regions.get(PluginMainClass.clans.get(ev.getPlayer())).z * 16 + 8
            );
            ev.getPlayer().setBedSpawnLocation(location, true);
        }
    }
}
