package kucameow.main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;

public class Handler implements Listener {

    private Plugin1 pl;

    public Handler(Plugin1 pl){
        this.pl = pl;
    }

    @EventHandler
    public void join(PlayerJoinEvent ev){
        Player pl = ev.getPlayer();
        pl.sendMessage("Тебя приветсвует KucaMeow");
        pl.sendMessage("Получай 10 экспы!");
        pl.giveExp(10);
        //pl.kickPlayer("Ты дебил");
    }


    /*
    @EventHandler
    public void breake2(EntityBlockFormEvent ev){
        ev.setCancelled(true);
        Player p = (Player) ev.getEntity();
        p.sendMessage("" + p.getDisplayName() + ", ты сломал мой блок! Пожалйста, хватит так делать");
        p.sendMessage("Это был мой " + ev.getBlock().getType());
    }
    */

    @EventHandler
    public void hitZombie(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)) return;
        if(!(e.getEntity().getType() == EntityType.ZOMBIE)) return;
        if(e.getEntity().isCustomNameVisible()) return;
        e.getEntity().setCustomNameVisible(true);
        if(Math.random() > 0.5) {
            e.getEntity().setCustomName((ChatColor.DARK_RED + "Обиженый зомби"));
            e.getEntity().setGlowing(true);
        }
        else {
            e.getEntity().setCustomName(("Расстроенный, но не огорченный зомби").trim());
            e.getEntity().setGlowing(false);
        }
    }

    @EventHandler
    public void deadZombie(EntityDeathEvent e){
        if(e.getEntity().isCustomNameVisible()) for(Player p: pl.getServer().getOnlinePlayers()){
            p.sendMessage(e.getEntity().getCustomName() + ": " + ChatColor.RESET + "Я не хотел умирать((((9(9(9");
        }
    }
}
