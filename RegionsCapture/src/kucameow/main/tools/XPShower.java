package kucameow.main.tools;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class XPShower implements Runnable {
    private float exp;
    private int level;
    private final Player pl;
    private final int time;
    private float timeLast = 0;

    public XPShower(Player player, int seconds) {
        this.pl = player;
        this.time = seconds;
        level = pl.getLevel();
        exp = pl.getExp();
    }

    @Override
    public void run() {
        while (timeLast <= time) {
            pl.setLevel(0);
            pl.setExp((float) (timeLast / (1.0*time)));
            timeLast += 0.1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            level += pl.getLevel();
        }
        pl.setLevel(level);
        pl.setExp(exp);
        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }
}
