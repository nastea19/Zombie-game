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
    private Base base;
    private boolean active = true;
    private BufferedImage zombieImage;
    GamePanel gamePanel = new GamePanel();
    
    public Zombie(GamePanel gamePanel, int x, int y, int width, int height, Base base) {
        super(gamePanel, x, y, width, height);
        this.base = base;
        this.maxHp = 100;
        this.hp = maxHp;
        this.speed = 1;

        getZombieImage();
    }

    public boolean isActive() {
        return active;
    }

    public void getZombieImage() {
        try {
           zombieImage = ImageIO.read(this.getClass().getResourceAsStream("/resources/zombie.png"));
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
