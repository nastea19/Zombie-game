package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.InputController;

public class Player extends Entity {

    private InputController keyH;
    // stores which direction the player is currently facing
    // up, down, left, or right
    private String direction = "right";

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
            direction = "up"; // remember current direction
        } else if (keyH.downPressed) {
            y += speed;
            direction = "down";
        } else if (keyH.leftPressed) {
            x -= speed;
            direction = "left";
        } else if (keyH.rightPressed) {
            x += speed;
            direction = "right";
        }

        // shooting when spacebar is pressed
        if (keyH.spacePressed) {
            shoot();
        }
    }

    // Creates a new bullet at the player's current position
    // and adds it to the game panel's bullet list.
    private void shoot() {
        // set bullet’s starting position (from player center)
        int bulletX = x + width / 2;
        int bulletY = y + height / 2;

        // set direction
        int dx = 0; // horizontal direction
        int dy = 0; // vertical direction
        int bulletSpeed = 10; // how fast the bullet moves

        // decides which way the bullet moves based on the player's direction
        if (direction.equals("up")) {
            dy = -1; // move upward on the screen (negative y)
        } else if (direction.equals("down")) {
            dy = 1; // move downward
        } else if (direction.equals("left")) {
            dx = -1; // move to the left
        } else if (direction.equals("right")) {
            dx = 1; // move to the right
        }

        // create a new bullet object
        Bullet bullet = new Bullet(gamePanel, bulletX, bulletY, 10, 5, dx, dy, bulletSpeed);

        // add it to the list of active bullets
        gamePanel.bullets.add(bullet);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.fillRect(x, y, width, height);

        drawHpBar(g2, 0, -10);
    }

}
