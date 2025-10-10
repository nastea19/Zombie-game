package tile;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.awt.Graphics2D;
import java.awt.Color;
import main.GamePanel;

/**
 * Base class represents the player's base in the game.
 * The base has HP (health points).
 * When zombies deal damage, base HP decreases.
 * When HP <= 0, game is over.
 */
public class Base {
    public int x; // position on screen x-coordinate
    public int y; // position on screen y-cootdinate
    public int baseWidth; // tile size (32x32)
    public int baseHeight; // tile size (32x32)
    public int baseHP; // health points of the player's base
    public BufferedImage image; // image for the base

    GamePanel gamePanel; // reference to the main game panel

    /**
     * Constructor to initialize the Base project.
     * @param  gamePanel Reference to GamePanel
     * @param x X-coordinate of the base
     * @param y Y-coordinate of the base
     * @param image BufferedImage representing he base tile
     */
    public Base(GamePanel gamePanel, int x, int y, int baseWidth, int baseHeight, BufferedImage image) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.baseWidth = gamePanel.boardWidth / 8; 
        this.baseHeight = gamePanel.boardHeight;
        this.baseHP = 100; // Starting HP
        this.image = image;
    }

    /**
     * Draws the base tile on the screen.
     * @param g2 Graphics2D objects from GamePanel
     */
    public void draw(Graphics2D g2) {
        for (int i = 0; i < baseWidth; i += gamePanel.tileSize) {
            for (int j = 0; j < baseHeight; j += gamePanel.tileSize) {
                g2.drawImage(image, x + i, y + j, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }

        g2.setColor(Color.GREEN);
        g2.fillRect(5, 5, baseWidth * baseHP / 100, 5);
        g2.setColor(Color.BLACK);
        g2.drawRect(5, 5, baseWidth, 5);
    }
}
