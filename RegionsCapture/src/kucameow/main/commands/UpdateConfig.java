package kucameow.main.commands;

import kucameow.main.PluginMainClass;
import kucameow.main.storage.Region;
import kucameow.main.tools.FileTweaks;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.Map;
import java.util.Objects;

public class UpdateConfig implements CommandExecutor {
    public void update(){
        PluginMainClass pl = PluginMainClass.pluginMainClass;
        if(PluginMainClass.regions.isEmpty() && FileTweaks.regionsFromFile(pl) != null){
            if(FileTweaks.regionsFromFile(pl) != null) {
                for (String regName : Objects.requireNonNull(FileTweaks.regionsFromFile(pl))) {
                    PluginMainClass.log.info(regName + " has been added");
                    PluginMainClass.regions.put(regName, new Region(regName, pl));
                }
                PluginMainClass.log.info(PluginMainClass.regions.toString());
            }
        }
        if(FileTweaks.regionsFromFile(pl) != null) {
            for (Map.Entry<String, Region> regionEntry : PluginMainClass.regions.entrySet()) {
                regionEntry.getValue().update();
            }
        }
        pl.update();
        PluginMainClass.log.warning("Configs updated");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        update();
        PluginMainClass.log.warning("Config was updated");
        commandSender.sendMessage(ChatColor.AQUA + "Обновлено");
        return true;
    }
}
