package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import kucameow.main.storage.Region;
import kucameow.main.tools.ChunkTweaks;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Map;

/**
 * Слушатель строительства и разрушения с применением требуемых правил
 */
public class DestroyAndBuild implements Listener {

    /**
     * Проверяет возможность игрока дейстовать в этом чанке
     * @param player Игрок
     * @param location Позиция действия (позиция ломаемого/поставленного блока)
     * @param needOtherRegion Булева переменная, которая спрашивает, нужно ли учитывать возможность действия в другом регионе
     * @return Может или не может по правилам
     */
    private boolean canDo(Player player, Location location, boolean needOtherRegion) {
        if(player.hasPermission("RegionsCaptureGOD")) return !(
                        player.getItemInHand().getType().equals(Material.WOOD_SWORD) ||
                        player.getItemInHand().getType().equals(Material.STONE_SWORD) ||
                        player.getItemInHand().getType().equals(Material.IRON_SWORD) ||
                        player.getItemInHand().getType().equals(Material.GOLD_SWORD) ||
                        player.getItemInHand().getType().equals(Material.DIAMOND_SWORD)
        );
        if(!PluginMainClass.regions.containsKey(PluginMainClass.clans.get(player))) return false;
        Region ownReg = PluginMainClass.regions.get(PluginMainClass.clans.get(player));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(i == j && i == 2) continue;
                if (ownReg.chunks[i][j].equals(location.getChunk())) {
                    return (location.getY() > ownReg.y && ownReg.chunksBooleans[i][j] && (ownReg.capture != null || needOtherRegion));
                }
            }
        }
        for (Map.Entry<String, Region> regionEntry : PluginMainClass.regions.entrySet()){
            if(regionEntry.getValue().equals(ownReg)) continue;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if(i == j && i == 2) continue;
                    if (regionEntry.getValue().chunks[i][j].equals(location.getChunk()) && !regionEntry.getValue().chunksBooleans[i][j]) {
                        return (location.getY() > regionEntry.getValue().y && needOtherRegion);
                    }
                }
            }
        }
        player.sendMessage("" + ChatColor.BOLD + ChatColor.DARK_RED + "Ты не можешь это сделать");
        return false;
    }

    //TODO: ИСПРАВИТЬ ЭТУ ХРЕНЬ!!!!
    /**
     * Хендлер ломания
     * @param event Игрок ломает блок
     */
    @EventHandler
    public void destroy(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(player.hasPermission("RegionsCaptureGOD")
//                && !(player.getItemInHand().getType().equals(Material.WOOD_SWORD) ||
//                        player.getItemInHand().getType().equals(Material.STONE_SWORD) ||
//                        player.getItemInHand().getType().equals(Material.IRON_SWORD) ||
//                        player.getItemInHand().getType().equals(Material.GOLD_SWORD) ||
//                        player.getItemInHand().getType().equals(Material.DIAMOND_SWORD))
        ) return;

        Region region = ChunkTweaks.getNearestRegion(player);
        player.sendMessage("Регион посчитался");
        if(region == null) {
            player.sendMessage("Тут нет региона");
            event.setCancelled(true);
            return;
        }
        else player.sendMessage(region.name);
        int j = event.getBlock().getLocation().getChunk().getX() - region.x + 2;
        int i = event.getBlock().getLocation().getChunk().getZ() - region.z + 2;
        if(i > 4 || i < 0 || j > 4 || j < 0){
            event.setCancelled(true);
            return;
        }
        player.sendMessage(i + " " + j);
        player.sendMessage(String.valueOf((event.getBlock().getY() <= region.y)));
        player.sendMessage(String.valueOf(!(region.chunksBooleans[i][j] && region.equals(PluginMainClass.regions.get(PluginMainClass.clans.get(player))))));
        event.setCancelled((event.getBlock().getY() <= region.y) || !(region.chunksBooleans[i][j] && region.equals(PluginMainClass.regions.get(PluginMainClass.clans.get(player)))));

        //event.setCancelled(!canDo(event.getPlayer(), event.getBlock().getLocation(), true));
    }

    /**
     * Хендлер взаимодействия с блоком
     * @param event Игрок тыкает блок
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void iteract(PlayerInteractEvent event){
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
        //event.setCancelled(!canDo(event.getPlayer(), event.getClickedBlock().getLocation(), false));
    }

    /**
     * Хендлер строительства
     * @param event Игрок поставил блок
     */
    @EventHandler
    public void build(BlockPlaceEvent event){
        event.setCancelled(!canDo(event.getPlayer(), event.getBlockPlaced().getLocation(), false));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void clickMainChunk (PlayerInteractEvent ev){
        if (!ev.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        if (!PluginMainClass.regions.containsKey(PluginMainClass.clans.get(ev.getPlayer()))){
            return;
        }
        if (ev.getClickedBlock().getChunk().equals(PluginMainClass.regions.get(PluginMainClass.clans.get(ev.getPlayer())).chunks[2][2])) {
            ev.getPlayer().openInventory(PluginMainClass.regions.get(PluginMainClass.clans.get(ev.getPlayer())).getInventory());
        }
    }
}
