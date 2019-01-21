package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Main handler with main plugin event handlers
 */
public class MainHandler implements Listener {
    private PluginMainClass pl;

    public MainHandler (PluginMainClass pl){
        this.pl = pl;
    }

    @EventHandler
    public static void EventHandle(){



    }
}
