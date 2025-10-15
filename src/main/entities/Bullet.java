package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import game.GamePanel;

public class Bullet extends Entity {

    private int speed; // Movement speed in pixels per frame
    private int dx, dy; // movement's direction: dx is horizontal, dy is vertical

    private boolean active = true; // bullet is active until it hits something

    public Bullet(GamePanel gp, int x, int y, int width, int height, int dx, int dy, int speed) {
        super(gp, x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    // update method moves the bullet every frame
    public void update() {
        if (!active)
            return;

        // update x and y coordinate based on the direction and speed
        x += dx * speed;
        y += dy * speed;

        // Collision with SINGLE zombie for now
        if (gamePanel.zombie != null) {
            // checking for intersection based on the zombie coordinates
            boolean collision = x < gamePanel.zombie.x + gamePanel.zombie.width &&
                    x + width > gamePanel.zombie.x &&
                    y < gamePanel.zombie.y + gamePanel.zombie.height &&
                    y + height > gamePanel.zombie.y;

            if (collision) {
                gamePanel.zombie.takeDamage(10); // deal 10 damage
                active = false; // bullet disappears
            }
        }
        // if off-screen, then the bullet can not deal damage
        if (isOffScreen()) {
            active = false;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRect(x, y, width, height);
    }

    public boolean isOffScreen() {
        // Off-screen if x or y is outside the board width/height
        return x < 0 || x > gamePanel.boardWidth || y < 0 || y > gamePanel.boardHeight;
    }

    public boolean isActive() {
        return active;
        
    }
}
