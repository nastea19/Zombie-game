package entities;

import game.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;

/*
 * We use the bullet entity for shooting from the player and it deals damage to zombies
 */
public class Bullet extends Entity {

    private int speed; // Movement speed in pixels per frame
    private int dx; // movement direction horizontal
    private int dy; // movement direction - vertical

    private boolean active = true; // bullet is active until it hits something

    /**
     * @param gp
     * @param x
     * @param y
     * @param width
     * @param height
     * @param dx
     * @param dy
     * @param speed
     */
    public Bullet(GamePanel gp, int x, int y, int width, int height, int dx, int dy, int speed) {
        super(gp, x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    /*
     * this method updates the position of he bullet, removes them when off-screen,
     * checks for zombie-bullet collision and deals damage
     */
    public void update() {
        if (!active) {
            return;
        }
        // update x and y coordinate based on the direction and speed
        x += dx * speed;
        y += dy * speed;

        // Collision with SINGLE zombie for now
        if (gamePanel.zombies != null && !gamePanel.zombies.isEmpty()) {
            synchronized (gamePanel.zombies) {
                for (Zombie z : gamePanel.zombies) {
                    // checking for intersection based on the zombie coordinates
                    boolean collision = x < z.x + z.width &&
                            x + width > z.x &&
                            y < z.y + z.height &&
                            y + height > z.y;

                    if (collision) {
                        z.takeDamage(10); // deal 10 damage
                        active = false; // bullet disappears
                        break;
                    }
                }
            }
        }
        // if off-screen, then the bullet can not deal damage
        if (isOffScreen()) {
            active = false;
        }
    }

    /*
     * displays the bullet
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRect(x, y, width, height);
    }

    /*
     * Off-screen if x or y is outside the board width/height
     */
    public boolean isOffScreen() {
        return x < 0 || x > gamePanel.boardWidth || y < 0 || y > gamePanel.boardHeight;
    }

    /*
     * checks whether the bullet is functional (not removed)
     */
    public boolean isActive() {
        return active;

    }
}
