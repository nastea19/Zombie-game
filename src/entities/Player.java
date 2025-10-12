package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.InputController;

public class Player extends Entity {

    private InputController keyH;

    public Player(GamePanel gp, InputController keyH, int x, int y, int width, int height) {
        super(gp, x, y, width, height);
        this.keyH = keyH;
        this.speed = 4;
        this.hp = 100;
        this.maxHp = 100;
    }

    GamePanel gp;

    @Override
    // key input is catched by InputController and updates the player coordinates
    // and calls the repaint component
    public void update() {
        // playerSpeed is the distance measured in pixels by which the player will move
        // in the according direction
        if (keyH.upPressed) {
            y -= speed;
        } else if (keyH.downPressed) {
            y += speed;
        } else if (keyH.leftPressed) {
            x -= speed;
        } else if (keyH.rightPressed) {
            x += speed;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.fillRect(x, y, width, height);

        drawHpBar(g2, 0, -10);
    }

}
