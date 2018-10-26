package kucameow.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RegionCreator implements CommandExecutor {
    private Plugin1 pl;
    public RegionCreator(Plugin1 plugin1) {
        pl = plugin1;
        FileConfiguration r = YamlConfiguration.loadConfiguration(pl.regionsF);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if(args.length != 1 || !(sender instanceof Player)) return false;
        FileConfiguration r = YamlConfiguration.loadConfiguration(pl.regionsF);

        List<String> l = r.getStringList("reg."+args[0]);
        Area a = RegionHandler.areas.get(sender);

        if(!r.contains("reg")){
            r.set("reg", new ArrayList<>());
            try {
                r.save(pl.regionsF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(a == null){
            sender.sendMessage("Ошибка выделения области. Перезайди на сервер");
            return true;
        }

        if(l.isEmpty()){
            l = new ArrayList<>();
            l.add(Integer.toString(a.getX1()));
            l.add(Integer.toString(a.getX2()));
            l.add(Integer.toString(a.getY1()));
            l.add(Integer.toString(a.getY2()));
            l.add(Integer.toString(a.getZ1()));
            l.add(Integer.toString(a.getZ2()));
        }
        else {
            l.set(0, Integer.toString(a.getX1()));
            l.set(1, Integer.toString(a.getX2()));
            l.set(2, Integer.toString(a.getY1()));
            l.set(3, Integer.toString(a.getY2()));
            l.set(4, Integer.toString(a.getZ1()));
            l.set(5, Integer.toString(a.getZ2()));
        }
        List<String> f = r.getStringList("name");
        if(!f.contains(args[0])) f.add(args[0]);
        r.set("name", f);

        r.set("reg."+args[0], l);
        sender.sendMessage("Try to save your region");
        try {
            r.save(pl.regionsF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
