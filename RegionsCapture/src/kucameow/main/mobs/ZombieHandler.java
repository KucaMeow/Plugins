package kucameow.main.mobs;

import kucameow.main.PluginMainClass;
import kucameow.main.filesystem.FileCreator;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Слушатель для зомби, задающий ему нужные свойства
 */
public class ZombieHandler implements Listener {

    private int health, money;
    private PluginMainClass pl;

    public ZombieHandler(PluginMainClass pl){
        File file = new File(pl.getDataFolder() + File.separator + "mobs" + File.separator + "zombie.yml");
        if(!file.exists()) FileCreator.createZombieFile(pl);
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        this.pl = pl;
        health = configuration.getInt("HP");
        money = configuration.getInt("Money");

    }

    /**
     * Задает нужные параметры при спавне зомби
     * @param event Спавн моба
     */
    @EventHandler
    public void zombieSpawn (EntitySpawnEvent event){
        if(event.getEntityType() != EntityType.ZOMBIE) return;

        Entity entity = event.getEntity();
        entity.setCustomNameVisible(true);
        ((Zombie) entity).setMaxHealth(health);
        ((Zombie) entity).setHealth(health);
        event.getEntity().setCustomName(("" + ChatColor.RED + ChatColor.BOLD + "Zombie " + ChatColor.DARK_RED + ChatColor.BOLD  + (int)((Zombie)event.getEntity()).getHealth()).trim());
        ((Zombie) entity).setCanPickupItems(false);
        entity.setFireTicks(0);
        entity.setInvulnerable(false);
        ((Zombie) entity).setRemoveWhenFarAway(false);
    }

    /**
     * Меняет имя зомби, добавляя ему текущее здоровье
     * @param event Моб получил урон
     */
    @EventHandler
    public void zombieHit (EntityDamageEvent event){
        if(!(event.getEntity() instanceof Zombie)) return;
        event.getEntity().setCustomName(("" + ChatColor.RED + ChatColor.BOLD + "Zombie " + ChatColor.DARK_RED + ChatColor.BOLD  + (int)((Zombie)event.getEntity()).getHealth()).trim());
    }

    /**
     * Добавляет денег клану игрока, убившего зомби
     * @param event Смерть моба
     */
    @EventHandler
    public void zombieDie (EntityDeathEvent event){
        if(event.getEntityType() != EntityType.ZOMBIE) return;
        if(event.getEntity().getKiller() == null) return;
        event.getDrops().clear();
        if (event.getEntity().getKiller() instanceof Player) PluginMainClass.regions.get(PluginMainClass.clans.get(event.getEntity().getKiller())).addBudget(money);
    }
}
