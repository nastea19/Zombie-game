package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
/**
 * Super class that is used for both player and zombie class.
 */
public abstract class Entity {
    protected GamePanel gamePanel;

    protected int x; // x-coordinate of the entity
    protected int y; // y-coordinate of the entity
    protected int width; 
    protected int height;
    protected int speed;

    protected BufferedImage image;

    protected int hp;
    protected int maxHp;

    public Entity(GamePanel gamePanel, int x, int y, int width, int height) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHp() {
        return hp;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    // Common methods every entity has
    public abstract void update();

    public abstract void draw(Graphics2D g2);
}
