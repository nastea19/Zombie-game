package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import main.GamePanel;

public abstract class Zombie extends Entity{ 
    public Zombie(GamePanel gamePanel, int x, int y, int width, int height, BufferedImage image) {
        super(gamePanel, x, y, width, height);
        this.image = image;
        this.hp = this.maxHp = 100;
    }

}
