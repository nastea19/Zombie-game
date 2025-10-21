package entities;

import game.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * Zombies autogenerate on a random position on the right end of the sceen.
 * They move in the direction of the base.
 * When a zombie-player or zombie-base collision occurs, zombie deals damage.
 * When a zombie-bullet collision occurs, zombie takes damage, bullet disapears.
 * Zombie dies after its HP is below or equal to 0.
 */
public class Zombie extends Entity {
    private Base base; // reference to the player's base, used for collision logic 
    private BufferedImage zombieImage;
    GamePanel gamePanel = new GamePanel();
    
    /**
     * Constructs a {@code Base} object with the specified position, size, and image.
     *
     * @param gamePanel reference to the main {@link GamePanel} used for rendering and updates
     * @param x the X-coordinate of the zombie
     * @param y the Y-coordinate of the zombie
     * @param width the width of the zombie tile in pixels
     * @param height the height of the zombie tile in pixels
     * @param base reference to {@link Base} used for collisions logic
     */
    public Zombie(GamePanel gamePanel, int x, int y, int width, int height, Base base) {
        super(gamePanel, x, y, width, height);
        this.base = base;
        this.maxHp = 100;
        this.hp = maxHp;
        this.speed = 1;

        getZombieImage();
    }

    /*
     * Loads the zombie image from the resource package.
     */
    public void getZombieImage() {
        try {
            zombieImage = ImageIO.read(this.getClass()
            .getResourceAsStream("/resources/zombie.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (x > base.x + base.width) {
            x -= speed;
        } else if (x <= base.x + base.width) {
            attack(base, 10);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (zombieImage != null) {
            g2.drawImage(zombieImage, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
        }

        drawHpBar(g2, 0, -10);
    }
}
