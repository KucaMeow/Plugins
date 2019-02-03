package kucameow.main.runnable;

import kucameow.main.PluginMainClass;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 * Вспомогательный поток, спавнящий моба
 */
public class SpawnMob implements Runnable {

    EntityType entityType;
    Location location;

    public SpawnMob(EntityType entityType, Location location) {
        this.entityType = entityType;
        this.location = location;
    }

    @Override
    public void run() {
        location.getWorld().spawnEntity(location, entityType);
    }
}
