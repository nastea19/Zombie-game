package entities;

import game.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;

/*
 * 
 */
public class Bullet extends Entity {

    private int speed; // Movement speed in pixels per frame
    private int dx; // movement direction horizontal
    private int dy; // movement direction - vertical

    private boolean active = true; // bullet is active until it hits something

    /*
     * 
     */
    public Bullet(GamePanel gp, int x, int y, int width, int height, int dx, int dy, int speed) {
        super(gp, x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    /*
     * 
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
     * 
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRect(x, y, width, height);
    }

    /*
     * 
     */
    public boolean isOffScreen() {
        // Off-screen if x or y is outside the board width/height
        return x < 0 || x > gamePanel.boardWidth || y < 0 || y > gamePanel.boardHeight;
    }

    /*
     * 
     */
    public boolean isActive() {
        return active;
        
    }
}
