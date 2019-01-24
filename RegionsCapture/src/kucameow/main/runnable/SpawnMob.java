package kucameow.main.runnable;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

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
