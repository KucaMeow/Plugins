package kucameow.main.runnable;

import org.bukkit.entity.Player;

public class Forbid implements Runnable{
    Player player;
    float speed;

    public Forbid(Player player, float speed) {
        this.player = player;
        this.speed = speed;
    }

    @Override
    public void run() {
        player.setWalkSpeed(speed);
    }
}
