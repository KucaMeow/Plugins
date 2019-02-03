package kucameow.main.commands;

import kucameow.main.PluginMainClass;
import kucameow.main.filesystem.FileCreator;
import kucameow.main.tools.ChunkTweaks;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Класс команды, создающей регион
 * command: /cr *имя*
 * commandResult: Создает регион с данным именем; Создает файл региона, вносит его в список регионов и заполняет игровое поле
 */
public class CreateRegion implements CommandExecutor {
    private final PluginMainClass pl;

    /**
     * @param pl
     */
    public CreateRegion(PluginMainClass pl){
        this.pl = pl;
    }

    /**
     * Выполнение команды
     * @return true, если регион создан успешно, false, если не указано имя региона или нет нужных прав
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 1 || !(commandSender instanceof Player) || !commandSender.hasPermission("regionsCapture.admin")) {
            commandSender.sendMessage(ChatColor.RED + "Ошибка. Возможно у вас нет прав или вы допустили ошибку");
            return false;
        }

        FileCreator.createRegionFile(strings[0], pl, ((Player) commandSender).getLocation());
        Location l = ((Player) commandSender).getLocation();
        for(int i = -2; i <= 2; i++){
            for(int j = -2; j <= 2; j++){
                ChunkTweaks.createChunkBorder(PluginMainClass.unavailableBlock, new Location(l.getWorld(), l.getX() + i*16, l.getY(), l.getZ() + j*16));
            }
        }

        ChunkTweaks.createChunkBorder(PluginMainClass.centralBlock, l);
        //pl.getServer().reload();

        return true;
    }
}
