package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import main.GamePanel;

/**
 * Base class represents the player's base in the game.
 * The base has HP (health points).
 * When zombies deal damage, base HP decreases.
 * When HP <= 0, game is over.
 */
public class Base extends Entity {

    public Base(GamePanel gamePanel, int x, int y, int width, int height, BufferedImage image) {
        super(gamePanel, x, y, width, height);
        this.image = image;
        this.maxHp = 1000;
        this.hp = maxHp;

    }

    private boolean destroyed = false;

    // Check if the base HP has reached 0 and if it hasn’t already been destroyed
    // If so, mark as destroyed, show a Game Over popup safely on the Event Dispatch Thread,
    // and exit the game
    @Override
    public void update() {
        if (hp <= 0 && !destroyed) {
            destroyed = true;
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(gamePanel, "Game Over!");
                System.exit(0);
            });
        }
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
}
