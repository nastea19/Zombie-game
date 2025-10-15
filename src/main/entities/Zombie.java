package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import game.GamePanel;
import entities.Base;
import entities.Entity;

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
    public Zombie(GamePanel gamePanel, int x, int y, int width, int height, Base base) {
        super(gamePanel, x, y, width, height);
        this.base = base;
        this.maxHp = 100;
        this.hp = maxHp;
        this.speed = 1;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void update() {
        if (x > base.x + base.width) {
            x -= speed;
        } else if (x <= base.x + base.width){
            attack(base, 10);
        } 
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(0x3F7E26));
        g2.fillRect(x, y, width, height);

        drawHpBar(g2, 0, -10);
    }
}
