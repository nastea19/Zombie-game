package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import main.GamePanel;

public class Bullet {

    // reference to the main game panel (needed for screen dimensions)
    private GamePanel gamePanel;

    private int x, y; // position of the bullet
    private int width, height; // bullet's dimensions
    private int speed; // Movement speed in pixels per frame
    private int dx, dy; // movement's direction: dx is horizontal, dy is vertical

    public Bullet(GamePanel gp, int startX, int startY, int width, int height, int dx, int dy, int speed) {
        this.gamePanel = gp;
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    // update method moves the bullet every frame
    public void update() {
        // update x and y coordinate based on the direction and speed
        x += dx * speed;
        y += dy * speed;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRect(x, y, width, height);
    }

    public boolean isOffScreen() {
        // Off-screen if x or y is outside the board width/height
        return x < 0 || x > gamePanel.boardWidth || y < 0 || y > gamePanel.boardHeight;
    }

    //creating the boundaries of the bullet for future collision detection
    public java.awt.Rectangle getBounds() {
        return new java.awt.Rectangle(x, y, width, height);
    }
}
