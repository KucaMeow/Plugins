package kucameow.main.storage;

import kucameow.main.PluginMainClass;
import kucameow.main.runnable.CaptureObserver;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Объект захвата
 * Содержится в каждом объекте региона
 */
public class Capture {
    /**
     * Чанк, в котором ведется захват
     */
    private Chunk chunk;
    /**
     * Лист захватчиков
     */
    public List<Player> warriors = new ArrayList<>();
    /**
     * Набор уровней и экспы игроков
     */
    public Map<Player, PlayerExp> playersExp = new HashMap<>();
    /**
     * Текущее состояние
     * 0 - готовность к захвату
     * 1 - возможность начать захват
     * 2 - состояние захвата
     */
    public int state = 0;
    /**
     * Время в таймере (сначала время подготовки к захвату, затем оно меняется на время самого захвата)
     * Учавтсвует в отсчете таймера в CaptureObserver
     */
    public int time = PluginMainClass.preTime;
    /**
     * Ссылка на регион, в котором он хранится
     */
    private Region region;

    public Capture(Chunk chunk, Region region, PluginMainClass pl) {
        this.chunk = chunk;
        this.region = region;
        Bukkit.getScheduler().runTaskAsynchronously(pl, new CaptureObserver(region));
        for(Map.Entry<Player, String> clansEntry : PluginMainClass.clans.entrySet()){
            Player player;
            if(clansEntry.getValue().equals(region.name)) player = clansEntry.getKey();
            else continue;
            playersExp.put(player, new PlayerExp(player.getLevel(), player.getExp()));
        }
    }

    public Chunk getChunk() {
        return chunk;
    }
    public Region getRegion() {
        return region;
    }
}
