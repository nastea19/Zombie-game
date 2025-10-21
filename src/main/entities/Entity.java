package entities;

import game.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Super class that is used for both player, zombie, and base class. 
 * It defines shared attributes (position, health, movement speed) 
 * and common behaviors such as taking damage, attacking, and drawing HP bars.
 */

public class Entity {
    protected GamePanel gamePanel;

    public int x; // x-coordinate of the entity
    public int y; // y-coordinate of the entity
    public int width; // width of the entity in pixels
    public int height; // height of the entity in pixels
    protected int speed; // movement speed pixels per update

    /** Buffered images used for rendering the entity in different directions. */
    public BufferedImage image;
    public BufferedImage up;
    public BufferedImage down;
    public BufferedImage left;
    public BufferedImage right;
    
    /** Current movement direction ("up", "down", "left", "right"). */
    public String direction;

    protected int hp; // current health points od the entity
    protected int maxHp; // maximum health points of the entity

    /** Time tracking variables for attack cooldown. */
    private long lastAttackTime = 0;
    private long attackCooldown = 1000; // 1000 milliseconds = 1 second

    /**
     * Constructs a new {@code Entity} with the specified parameters.
     *
     * @param gamePanel reference to the main {@code GamePanel}
     * @param x         initial X position
     * @param y         initial Y position
     * @param width     width of the entity
     * @param height    height of the entity
     */
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
     * 
     * @param damageAmount amount of damage taken
     */
    public void takeDamage(int damageAmount) {
        hp -= damageAmount;

        if (hp < 0) {
            hp = 0; // does not allow hp to be a negative number
        }
    }

    /**
     * Makes this entity attack another entity if the cooldown period has passed.
     *
     * @param target the entity to attack
     * @param damageAmount the amount of damage inflicted
     */
    public void attack(Entity target, int damageAmount) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastAttackTime >= attackCooldown) {
            target.takeDamage(damageAmount);
            lastAttackTime = currentTime;
        }
    }

    /**
     * Draws a health bar above the entity representing its current HP status.
     *
     * @param g2  the graphics context used for drawing
     * @param offsetX  horizontal offset from the entity’s position
     * @param offsetY  vertical offset from the entity’s position
     */
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

    /**
     * Sets the entity’s HP, ensuring it stays within the valid range [0, maxHp].
     *
     * @param hp the new HP value
     */
    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    /**
     * Updates the entity’s state. This method is overridden by subclasses
     * (e.g., Player, Zombie, Base, Bullet) to define specific movement or behavior.
     */
    public void update() {

    }

    /**
     * Draws the entity on the screen using the given graphics context.
     * Should be overridden by subclasses.
     *
     * @param g2 the {@code Graphics2D} context for drawing
     */
    public void draw(Graphics2D g2) {

    }
}
