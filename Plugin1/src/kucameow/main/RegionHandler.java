package kucameow.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegionHandler implements Listener {

    private Plugin1 plugin;

    public RegionHandler(Plugin1 pl){
        this.plugin = pl;
    }

    //private File regionsFile = new File(pl.getDataFolder() + File.separator + "regions.yml");
    //private FileConfiguration regions = YamlConfiguration.loadConfiguration(regionsFile);


    private void saveCoor(PlayerInteractEvent e, Location loc, boolean first){
        FileConfiguration playerCoord = YamlConfiguration.loadConfiguration(plugin.playersF);
        //Init string and file
        List<String> list;

        if(!playerCoord.contains("players." + e.getPlayer().getName() + ".x")){
            list = playerCoord.getStringList("players");
            if(list == null){
                list = new ArrayList<>();
                list.add(e.getPlayer().getName());
                playerCoord.set("players", list);
                try {
                    playerCoord.save(plugin.playersF);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            list = playerCoord.getStringList("players." + e.getPlayer().getName());
            if(list == null){
                list = new ArrayList<String>();
                list.add("x");
                list.add("y");
                list.add("z");
                playerCoord.set("players" + e.getPlayer().getName(), list);
                try {
                    playerCoord.save(plugin.playersF);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            list = new ArrayList<String>();
            list.add("0");
            list.add("0");
            playerCoord.set("players." + e.getPlayer().getName() + ".x", list);
            try {
                playerCoord.save(plugin.playersF);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            playerCoord.set("players." + e.getPlayer().getName() + ".y", list);
            try {
                playerCoord.save(plugin.playersF);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            playerCoord.set("players." + e.getPlayer().getName() + ".z", list);
            try {
                playerCoord.save(plugin.playersF);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        list = playerCoord.getStringList("players." + e.getPlayer().getName() + ".x");
        if(first) list.set(0, Integer.toString((int)loc.getX()));
        else list.set(1, Integer.toString((int)loc.getX()));
        playerCoord.set("players." + e.getPlayer().getName() + ".x", list);
        try {
            playerCoord.save(plugin.playersF);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        list = playerCoord.getStringList("players." + e.getPlayer().getName() + ".y");
        if(first) list.set(0, Integer.toString((int)loc.getY()));
        else list.set(1, Integer.toString((int)loc.getY()));
        playerCoord.set("players." + e.getPlayer().getName() + ".y", list);
        try {
            playerCoord.save(plugin.playersF);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        list = playerCoord.getStringList("players." + e.getPlayer().getName() + ".z");
        if(first) list.set(0, Integer.toString((int)loc.getZ()));
        else list.set(1, Integer.toString((int)loc.getZ()));
        playerCoord.set("players." + e.getPlayer().getName() + ".z", list);
        try {
            playerCoord.save(plugin.playersF);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private String writeC(Location loc){
        return  "\nX: " + loc.getX() +
                "\nY: " + loc.getY() +
                "\nZ: " + loc.getZ();
    }

    private String writeC(Player p, boolean first){

        String name = p.getName();
        FileConfiguration playerCoord = YamlConfiguration.loadConfiguration(plugin.playersF);
        List<String> user = playerCoord.getStringList("players." + name + ".x");
        int x1 = Integer.parseInt(user.get(0)), x2 = Integer.parseInt(user.get(1));
        user = playerCoord.getStringList("players." + name + ".y");
        int y1 = Integer.parseInt(user.get(0)), y2 = Integer.parseInt(user.get(1));
        user = playerCoord.getStringList("players." + name + ".z");
        int z1 = Integer.parseInt(user.get(0)), z2 = Integer.parseInt(user.get(1));

        if(first)
            return  "\nX: " + x1 +
                    "\nY: " + y1 +
                    "\nZ: " + z1;
        else
            return  "\nX: " + x2 +
                    "\nY: " + y2 +
                    "\nZ: " + z2;
    }

    private String coorSum(Player player){
        FileConfiguration playerCoord = YamlConfiguration.loadConfiguration(plugin.playersF);

        List<String> user = playerCoord.getStringList("players." + player.getName() + ".x");
        int x1 = Integer.parseInt(user.get(0)), x2 = Integer.parseInt(user.get(1));
        user = playerCoord.getStringList("players." + player.getName() + ".y");
        int y1 = Integer.parseInt(user.get(0)), y2 = Integer.parseInt(user.get(1));
        user = playerCoord.getStringList("players." + player.getName() + ".z");
        int z1 = Integer.parseInt(user.get(0)), z2 = Integer.parseInt(user.get(1));

        return Integer.toString((Math.abs(x1 - x2) + 1) * (Math.abs(y1 - y2) + 1) * (Math.abs(z1 - z2) + 1));
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

    private boolean checkRegion(Block block, Player p){
        //String name = p.getName();
        FileConfiguration r = YamlConfiguration.loadConfiguration(plugin.regionsF);

        boolean out = false;

        List<String> regs = r.getStringList("Regions");
        for (String s : regs){
            List<String> c = r.getStringList("Regions." + s);
            out = out || block.getX() >= Math.min(Integer.parseInt(c.get(0)),Integer.parseInt(c.get(1))) && block.getX() <= Math.max(Integer.parseInt(c.get(0)),Integer.parseInt(c.get(1)))
                    &&
                         block.getZ() >= Math.min(Integer.parseInt(c.get(4)),Integer.parseInt(c.get(5))) && block.getZ() <= Math.max(Integer.parseInt(c.get(4)),Integer.parseInt(c.get(5)));
        }

//        FileConfiguration playerCoord = YamlConfiguration.loadConfiguration(plugin.playersF);
//        List<String> user = playerCoord.getStringList("players." + name + ".x");
//        int x1 = Integer.parseInt(user.get(0)), x2 = Integer.parseInt(user.get(1));
//        user = playerCoord.getStringList("players." + name + ".y");
//        int y1 = Integer.parseInt(user.get(0)), y2 = Integer.parseInt(user.get(1));
//        user = playerCoord.getStringList("players." + name + ".z");
//        int z1 = Integer.parseInt(user.get(0)), z2 = Integer.parseInt(user.get(1));
        return out;
    }

    //Check of breaking/building
    @EventHandler
    public void firstSave(BlockBreakEvent ev){
        if(!checkRegion(ev.getBlock(), ev.getPlayer())) return;
        Player p = ev.getPlayer();
        if(!p.hasPermission("test")) return;
        ev.setCancelled(true);
    }
    @EventHandler
    public void secondSave(PlayerInteractEvent ev){
        if(!checkRegion(ev.getClickedBlock(), ev.getPlayer())) return;
        Player p = ev.getPlayer();
        if(!p.hasPermission("test")) return;
        if(ev.getAction() == Action.PHYSICAL && ev.getClickedBlock().getType() == Material.SOIL){
            ev.setCancelled(true);
        }
    }
    @EventHandler
    public void thirdSave(BlockPlaceEvent ev){
        if(!checkRegion(ev.getBlock(), ev.getPlayer())) return;
        Player p = ev.getPlayer();
        if(!p.hasPermission("test")) return;
        ev.setCancelled(true);
    }
}
