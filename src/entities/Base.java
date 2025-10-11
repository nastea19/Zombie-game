package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import main.GamePanel;

/**
 * Base class represents the player's base in the game.
 * The base has HP (health points).
 * When zombies deal damage, base HP decreases.
 * When HP <= 0, game is over.
 */
public class Base extends Entity{
    public Base(GamePanel gamePanel, int x, int y, int width, int height, BufferedImage image) {
        super(gamePanel, x, y, width, height);
        this.image = image;
        this.hp = this.maxHp = 100;

    }

    @Override
    public void update() {
        if(hp <= 0) {
            // destruction logic
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        for (int i = 0; i < width; i += gamePanel.tileSize) {
            for (int j = 0; j < height; j += gamePanel.tileSize) {
                g2.drawImage(image, x + i, y + j, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }

        g2.setColor(Color.GREEN);
        g2.fillRect(5, 5, width * hp / 100, 5);
        g2.setColor(Color.BLACK);
        g2.drawRect(5, 5, width, 5);
    }
}
