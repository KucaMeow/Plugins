package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import kucameow.main.storage.Region;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BuyRegion implements Listener {

    private boolean checkNearChunk(int i, int j, Region region){
        boolean out = false;
        if (i != 0) out = out || region.chunksBooleans[i - 1][j];
        if (i != 4) out = out || region.chunksBooleans[i + 1][j];

        if (j != 0) out = out || region.chunksBooleans[i][j - 1];
        if (j != 4) out = out || region.chunksBooleans[i][j + 1];

        return out || ((j == 3 || j == 1)&& i == 2) || ((i == 3 || i == 1)&& j == 2);
    }

    @EventHandler
    public void inventory (InventoryClickEvent ev){
        if(!ev.getInventory().equals(PluginMainClass.regions.get(PluginMainClass.clans.get((Player)ev.getWhoClicked())).getInventory())) return;
        int i = 0, j;
        int a = ev.getSlot();
        j = a%9 - 2;
        while (a > 9){
            i++;
            a -= 9;
        }
        Region region =  PluginMainClass.regions.get(PluginMainClass.clans.get(ev.getWhoClicked()));
        if(ev.getCurrentItem().getType().equals(Material.RED_SHULKER_BOX)) {
            if(region.getBudget() >= PluginMainClass.regionCost) {
                if(region.regionsCaptured <= PluginMainClass.avRegs) {
                    if(checkNearChunk(i, j, region)) {
                        region.addChunk(region.chunks[i][j]);
                        region.addBudget(-1 * PluginMainClass.regionCost);
                    }
                    else ev.getWhoClicked().sendMessage(ChatColor.DARK_RED + "Нет прилигающего региона");
                }
                else ev.getWhoClicked().sendMessage(ChatColor.DARK_RED + "У вас слишком много регионов");
            }
            else ev.getWhoClicked().sendMessage(ChatColor.DARK_RED + "У вас недостаточно денег");
        }
        ev.setCancelled(true);
    }
}
