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

        if(!r.contains("Regions")){
            List<String> l = new ArrayList<>();
            l.add("Test");
            r.set("Regions", l);
            try {
                r.save(pl.regionsF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if(args.length != 1 || args[0].equals("Test") || !(sender instanceof Player)) return false;
        FileConfiguration r = YamlConfiguration.loadConfiguration(pl.regionsF);

//        if(!r.contains("Regions." + args[0])){
//            List<String> l = r.getStringList("Regions");
//            l.add(args[0]);
//            r.set("Regions", l);
//            try {
//                r.save(pl.regionsF);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            l = new ArrayList<>();
//            l.add("0");
//            l.add("0");
//            l.add("0");
//            l.add("0");
//            l.add("0");
//            l.add("0");
//
//            r.set("Regions." + args[0], l);
//            try {
//                r.save(pl.regionsF);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        if(!r.contains("Regions." + args[0])){
            r.set("Regions", args[0]);
            List<String> a = new ArrayList<>();
            a.add("");a.add("");a.add("");a.add("");a.add("");a.add("");
            r.set("Regions"+args[0], a);
            try {
                r.save(pl.regionsF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> l = r.getStringList("Regions." + args[0]);
        FileConfiguration p = YamlConfiguration.loadConfiguration(pl.playersF);

        List<String> x = p.getStringList("players." + sender.getName() + ".x");
        List<String> y = p.getStringList("players." + sender.getName() + ".y");
        List<String> z = p.getStringList("players." + sender.getName() + ".z");

        l.set(0, x.get(0));
        l.set(1, x.get(1));
        l.set(2, y.get(0));
        l.set(3, y.get(1));
        l.set(4, z.get(0));
        l.set(5, z.get(1));

        r.set("Regions." + args[0], l);
        try {
            r.save(pl.regionsF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
