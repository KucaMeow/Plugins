package kucameow.main.commands;

import kucameow.main.PluginMainClass;
import kucameow.main.mobs.WeaponSeller;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * Дебаг команда
 * command: /crdeb
 * commandResult: Зависит от потребности теста
 */
public class Debug implements CommandExecutor {

    private final PluginMainClass pl;

    public Debug(PluginMainClass pl){
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        EntityType entityType = EntityType.VILLAGER;
        Entity entity = ((Player)commandSender).getWorld().spawnEntity(((Player)commandSender).getLocation(), EntityType.VILLAGER);
        entity.setCustomName("" + ChatColor.GOLD + ChatColor.BOLD + "Оружейник");
        entity.setCustomNameVisible(true);
        return true;
    }
}
