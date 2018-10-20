package kucameow.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GoAway implements CommandExecutor {
    private Plugin1 pl;
    public GoAway(Plugin1 plugin1) {
        pl = plugin1;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 0) return false;
        commandSender.sendMessage(ChatColor.RED + "ТЫ РЕАЛЬНО ХОТЕЛ ПОЛУЧИТЬ АЛМАЗЫ, ДУРАЧОК???");
        return true;
    }
}
