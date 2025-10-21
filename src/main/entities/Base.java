package entities;

import game.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Base class represents the player's base in the game.
 * The base has HP (health points).
 * When zombies deal damage, base HP decreases.
 * When HP <= 0, game is over.
 */
public class Base extends Entity {

    /**
     * Constructs a {@code Base} object with the specified position, size, and image.
     *
     * @param gamePanel reference to the main {@link GamePanel} used for rendering and updates
     * @param x the X-coordinate of the base
     * @param y the Y-coordinate of the base
     * @param width the width of the base in pixels
     * @param height the height of the base in pixels
     * @param image the tile image used to visually build the base
     */
    public Base(GamePanel gamePanel, int x, int y, int width, int height, BufferedImage image) {
        super(gamePanel, x, y, width, height);
        this.image = image;
        this.maxHp = 1000;
        this.hp = maxHp;

    }

    // Check if the base HP has reached 0 and if it hasn’t already been destroyed
    // If so, mark as destroyed, show a Game Over popup safely on the Event Dispatch
    // Thread,
    // and exit the game
    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D g2) {
        // draw the base made of tiles
        for (int i = 0; i < width; i += gamePanel.tileSize) {
            for (int j = 0; j < height; j += gamePanel.tileSize) {
                g2.drawImage(image, x + i, y + j, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }

        drawHpBar(g2, 5, 5);
    }

    /*
     * Resets the base's original maximum value.
     * Used when reseting the game.
     */
    public void resetHp() {
        this.hp = 1000;
    }
}
