package kucameow.main.mobs;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

/**
 * Общий слушатель для задавания мобам общий свойств
 */
public class GlobalMobHandler implements Listener {

    /**
     * Предотвращает горение монстров
     * @param event Воспламенение моба
     */
    @EventHandler
    public void monsterFire (EntityCombustEvent event){
        if(event.getEntity() instanceof Monster){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void villagerDamage (EntityDamageEvent event){
        if(event.getEntity() instanceof Villager){
            //event.setCancelled(true);
        }
    }

    @EventHandler
    public void villagerSpawn (EntitySpawnEvent event){
        if(event.getEntity() instanceof Villager){
            ((Villager)event.getEntity()).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
        }
    }
}
