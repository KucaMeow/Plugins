package kucameow.main.mobs;


import kucameow.main.PluginMainClass;
import kucameow.main.filesystem.FileCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class WeaponSeller implements Listener {

    private Inventory inventory = Bukkit.createInventory(null, 54, "" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Оружейник");

    public WeaponSeller(){
        if(!(new File(PluginMainClass.pluginMainClass.getDataFolder()
                + File.separator + "sellers" + File.separator + "weapon.yml")).exists()) {
            FileCreator.createWeaponSellerFile();
        }

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File(PluginMainClass.pluginMainClass.getDataFolder()
                + File.separator + "sellers" + File.separator + "weapon.yml"));
        int idArmorStart = 298;
        for(int i = 0; i < 45; i++){
            if(i % 9 == 4) {
                i += 4;
                continue;
            }
            ItemStack item = new ItemStack(Material.getMaterial(idArmorStart++));
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + item.getType().toString().replace("_", " ") + ": " + cfg.get(item.getType().toString()));
            item.setItemMeta(im);
            inventory.setItem(i, item);
        }
        int[] ids = {267, 258, 268, 271, 272, 275, 276, 279, 283, 286};
        for (int i = 4, j = 0; i < 45 && j < ids.length; i++){
            if(i % 9 == 6){
                i += 6;
                continue;
            }
            ItemStack item = new ItemStack(Material.getMaterial(ids[j]));
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + item.getType().toString().replace("_", " ") + ": " + cfg.get(item.getType().toString()));
            item.setItemMeta(im);
            inventory.setItem(i, item);
            j++;
        }
    }

    @EventHandler
    public void villager (PlayerInteractEntityEvent event){
        if(event.getRightClicked() instanceof Villager
                && event.getRightClicked().getCustomName().contains("Оружейник")
        ){
            event.setCancelled(true);
            event.getPlayer().openInventory(inventory);
        }
    }

    @EventHandler
    public void inventory (InventoryClickEvent ev){
        if(!ev.getInventory().equals(inventory)) return;

        ev.setCancelled(true);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
