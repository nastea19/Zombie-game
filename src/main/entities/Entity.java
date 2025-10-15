package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import game.GamePanel;
/**
 * Super class that is used for both player and zombie class.
 */
public class Entity {
    protected GamePanel gamePanel;

    public int x; // x-coordinate of the entity
    public int y; // y-coordinate of the entity
    public int width; 
    public int height;
    protected int speed;

    public BufferedImage image, up, down, right, left;
    public String direction;


    protected int hp;
    protected int maxHp;

    public Entity(GamePanel gamePanel, int x, int y, int width, int height) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * The method is used in Base, Player, Zombie entities.
     * It occurs after zombie-player, zombie-base, bullet-zombie collision.
     */
    public void takeDamage(int damageAmount) {
        hp -= damageAmount;

        if (hp < 0) {
            hp = 0; // does not allow hp to be a negative number
        }
    }

    private long lastAttackTime = 0;
    private long attackCooldown = 1000; // 1000 milliseconds = 1 second

    public void attack(Entity target, int damageAmount) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastAttackTime >= attackCooldown) {
            target.takeDamage(damageAmount);
            lastAttackTime = currentTime;
        }
    }

    protected void drawHpBar(Graphics2D g2, int offsetX, int offsetY) {
        if (maxHp <= 0) {
            return;
        }
        // calculate HP ratio (how much health is left)
        double hpRatio = (double) hp / maxHp;
        if (hpRatio < 0) {
            hpRatio = 0;
        }

        // bar dimensions
        int barX = x + offsetX;
        int barY = y + offsetY;
        int barWidth = width;
        int boardHeight = 5;

        // draw missing health in red
        g2.setColor(Color.red);
        g2.fillRect(barX, barY, barWidth, boardHeight);

        // draw remaining health in green
        g2.setColor(Color.green);
        g2.fillRect(barX, barY, (int) (barWidth * hpRatio), boardHeight);

        // draw border
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY, barWidth, boardHeight);
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
    public void update() {

    }

    public void draw(Graphics2D g2) {

    }
}
