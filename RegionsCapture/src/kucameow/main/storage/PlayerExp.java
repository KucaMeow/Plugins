package kucameow.main.storage;

public class PlayerExp {
    private int level;
    private float exp;

    public PlayerExp(int level, float exp) {
        this.level = level;
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public float getExp() {
        return exp;
    }
}
