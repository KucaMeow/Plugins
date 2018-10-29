package kucameow.main.handlers;

import kucameow.main.PluginMainClass;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionHandler implements Listener {

    public static Map<Player, Area> areas = new HashMap<>();

    private PluginMainClass plugin;

    public RegionHandler(PluginMainClass pl){
        this.plugin = pl;
    }

    private void saveCoor(PlayerInteractEvent e, Location loc, boolean first){
        Area a;
        a = areas.get(e.getPlayer());

        if(a == null){
            e.getPlayer().sendMessage("Ошибка выделения территории. Перезайди на сервер");
            return;
        }

        if(first){
            a.setX1((int)loc.getX());
            a.setY1((int)loc.getY());
            a.setZ1((int)loc.getZ());
        }
        else {
            a.setX2((int)loc.getX());
            a.setY2((int)loc.getY());
            a.setZ2((int)loc.getZ());
        }
        areas.replace(e.getPlayer(), a);
    }

    private String writeC(Location loc){
        return  "\nX: " + loc.getX() +
                "\nY: " + loc.getY() +
                "\nZ: " + loc.getZ();
    }

    private String writeC(Player p, boolean first){
        Area a = areas.get(p);
        if(first)
            return  "\nX: " + a.getX1() +
                    "\nY: " + a.getY1() +
                    "\nZ: " + a.getZ1();
        else
            return  "\nX: " + a.getX2() +
                    "\nY: " + a.getY2() +
                    "\nZ: " + a.getZ2();
    }

    private String coorSum(Player player){
        Area a = areas.get(player);
        return Integer.toString((Math.abs(a.getX1() - a.getX2()) + 1) * (Math.abs(a.getY1() - a.getY2()) + 1) * (Math.abs(a.getZ1() - a.getZ2()) + 1));
    }

    //Getting positions
    @EventHandler
    public void getFirstLocation(PlayerInteractEvent e){
        if(!e.getPlayer().hasPermission("admin")) return;
        ItemStack i = e.getItem();
        if(i.getType() != Material.STONE_AXE) return;
        if(e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        e.setCancelled(true);
        saveCoor(e, e.getClickedBlock().getLocation(), true);
        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Выделена точка #1: " + writeC(e.getClickedBlock().getLocation()));
        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Выделено блоков: " + coorSum(e.getPlayer()));
        /*if(second != null){
            int count = (int) ((Math.abs(first.getX() - second.getX()) + 1) * (Math.abs(first.getY() - second.getY()) + 1) * (Math.abs(first.getZ() - second.getZ()) + 1));
            //e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "2ая точка: " + writeC(second));
            e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Выделено блоков: " + count);
        }*/
    }
    @EventHandler
    public void getSecondLocation(PlayerInteractEvent e){
        if(!e.getPlayer().hasPermission("admin")) return;
        ItemStack i = e.getItem();
        if(i.getType() != Material.STONE_AXE) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        e.setCancelled(true);
        saveCoor(e, e.getClickedBlock().getLocation(), false);
        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Выделена точка #2: " + writeC(e.getClickedBlock().getLocation()));
        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Выделено блоков: " + coorSum(e.getPlayer()));
        /*if(first != null){
            int count = (int) ((Math.abs(first.getX() - second.getX()) + 1) * (Math.abs(first.getY() - second.getY()) + 1) * (Math.abs(first.getZ() - second.getZ()) + 1));
            //e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "1ая точка: " + writeC(first));
            e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Выделено блоков: " + count);
        }*/
    }
    @EventHandler
    public void getInfo(PlayerInteractEvent e){
        //if(!e.getPlayer().hasPermission("admin")) return;
        if(e.getItem().getType() != Material.STONE_AXE) return;
        if(e.getAction() != Action.RIGHT_CLICK_AIR) return;
        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "\nТочка #1: " + writeC(e.getPlayer(), true) + " \n\n");
        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Точка #2: " + writeC(e.getPlayer(), false) + " \n\n");
        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Выделено блоков: " + coorSum(e.getPlayer()) + "\n");
    }

    //TODO Переделать систему проверки на наличие игрока в каком-либо клане
    private boolean checkRegion(Block block, Player p){
        FileConfiguration r = YamlConfiguration.loadConfiguration(plugin.regionsF);
        boolean out = false;

        List<String> regs = r.getStringList("name");
        for (String s : regs){
            //p.sendMessage(s);
            List<String> c = r.getStringList("reg." + s);
            out = out || block.getX() >= Math.min(Integer.parseInt(c.get(0)),Integer.parseInt(c.get(1))) && block.getX() <= Math.max(Integer.parseInt(c.get(0)),Integer.parseInt(c.get(1)))
                    &&
                         block.getZ() >= Math.min(Integer.parseInt(c.get(4)),Integer.parseInt(c.get(5))) && block.getZ() <= Math.max(Integer.parseInt(c.get(4)),Integer.parseInt(c.get(5)));
        }
        if(out && p.hasPermission("cr.break")){
            p.sendMessage("There's some region, but you have permission");
            return false;
        }
        return out;
    }

    //Check of breaking/building
    @EventHandler
    public void firstSave(BlockBreakEvent ev){
        if(!checkRegion(ev.getBlock(), ev.getPlayer())) return;
        Player p = ev.getPlayer();
        ev.setCancelled(true);
    }
    @EventHandler
    public void secondSave(PlayerInteractEvent ev){
        if(!checkRegion(ev.getClickedBlock(), ev.getPlayer())) return;
        Player p = ev.getPlayer();
        if(ev.getAction() == Action.PHYSICAL && ev.getClickedBlock().getType() == Material.SOIL){
            ev.setCancelled(true);
        }
    }
    @EventHandler
    public void thirdSave(BlockPlaceEvent ev){
        if(!checkRegion(ev.getBlock(), ev.getPlayer())) return;
        Player p = ev.getPlayer();
        ev.setCancelled(true);
    }

}
