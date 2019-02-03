package kucameow.main.runnable;

import kucameow.main.PluginMainClass;
import kucameow.main.commands.UpdateConfig;
import org.bukkit.Bukkit;

public class ServerLoaded implements Runnable {
    private UpdateConfig updateConfig;
    public ServerLoaded(UpdateConfig updater) {
        updateConfig = updater;
    }

    @Override
    public void run() {
        updateConfig.update();
        PluginMainClass.log.warning("Run mobb spawning");
        Bukkit.getScheduler().runTaskAsynchronously(PluginMainClass.pluginMainClass, new SpawnMobs());
    }
}
