package kucameow.main.mobs;

import kucameow.main.PluginMainClass;
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

public class ZombieHandler implements Listener {

    private int health, money;
    private PluginMainClass pl;

    public ZombieHandler(PluginMainClass pl){
        File file = new File(pl.getDataFolder() + File.separator + "mobs" + File.separator + "zombie.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()) {
            try {
                file.createNewFile();
                configuration.set("HP", 300);
                configuration.set("Money", 50);
                ArrayList<String> temp = new ArrayList<String>();
                temp.add("this is spawn coords:");
                configuration.set("Coords", temp);
                configuration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.pl = pl;
        health = configuration.getInt("HP");
        money = configuration.getInt("Money");

    }

    @EventHandler
    public void zombieSpawn (EntitySpawnEvent event){
        if(event.getEntityType() != EntityType.ZOMBIE) return;

        Entity entity = event.getEntity();
        entity.setCustomName("" + ChatColor.BOLD + ChatColor.RED + "Zombie");
        entity.setCustomNameVisible(true);
        ((Zombie) entity).setMaxHealth(health);
        ((Zombie) entity).setHealth(health);
        ((Zombie) entity).setCanPickupItems(false);
        entity.setFireTicks(0);
        entity.setInvulnerable(false);
        ((Zombie) entity).setRemoveWhenFarAway(false);
    }

    @EventHandler
    public void zombieHit (EntityDamageEvent event){
        if(!(event.getEntity() instanceof Zombie)) return;
        event.getEntity().setCustomName("" + ChatColor.BOLD + ChatColor.RED + "Zombie " + (int)((Zombie)event.getEntity()).getHealth());
        event.getEntity().setInvulnerable(false);
    }

    @EventHandler
    public void zombieFire (EntityCombustEvent event){
        if(event.getEntity() instanceof Monster){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void zombieDie (EntityDeathEvent event){
        if(event.getEntityType() != EntityType.ZOMBIE) return;
        if(event.getEntity().getKiller() == null) return;
        event.getDrops().clear();
        if (event.getEntity().getKiller() instanceof Player) PluginMainClass.regions.get(PluginMainClass.clans.get(event.getEntity().getKiller())).addBudget(money);
    }
}
