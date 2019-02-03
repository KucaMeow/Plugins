package kucameow.main.tools;

import kucameow.main.PluginMainClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

/**
 * Вспомогательный класс с различными метода для работы с файлами
 */
public class FileTweaks {
    /**
     * Возвращает список всех доступных регионов
     * @param pl ссылка на главный Класс плагина
     * @return ArrayList*String* с списком регионов либо null, если регионов еще нет
     */
    public static ArrayList<String> regionsFromFile(PluginMainClass pl){
        if(!new File(pl.getDataFolder() + File.separator + "regions.yml").exists()) return null;
        FileConfiguration regs = YamlConfiguration.loadConfiguration(new File(pl.getDataFolder() + File.separator + "regions.yml"));
        return (ArrayList<String>) regs.getStringList("regions");
    }
}
