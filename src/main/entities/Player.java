package entities;

import game.GamePanel;
import game.InputController;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * displays, updates the movement of the player and the image change of the
 * sprite, creates the bullets and manages the attack
 * removes the player when he dies
 */
public class Player extends Entity {

    private InputController keyH;
    // stores which direction the player is currently facing
    // up, down, left, or right
    private String direction = "right";

    /**
     * 
     * @param gp
     * @param keyH
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Player(GamePanel gp, InputController keyH, int x, int y, int width, int height) {
        super(gp, x, y, width, height);
        this.gp = gp;
        this.keyH = keyH;
        this.speed = 4;
        this.hp = 100;
        this.maxHp = 100;

        getPlayerImage();
    }

    /*
     * takes the sprite's images from resources
     */
    public void getPlayerImage() {
        try {
            up = ImageIO.read(this.getClass().getResourceAsStream("/resources/up1png.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/resources/down1.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/resources/left1.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/resources/right1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    GamePanel gp;

    @Override
    // key input is catched by InputController and updates the player coordinates
    // and calls the repaint component
    public void update() {
        // playerSpeed is the distance measured in pixels by which the player will move
        // in the according direction
        // also checks the boundaries so the player can not go outside the game screen
        if (keyH.upPressed && y - speed >= 0) {
            y -= speed;
            direction = "up"; // remember current direction
        }
        if (keyH.downPressed && y + speed + height <= gp.boardHeight) {
            y += speed;
            direction = "down";
        }
        if (keyH.leftPressed && x - speed >= 0) {
            x -= speed;
            direction = "left";
        }
        if (keyH.rightPressed && x + speed + width <= gp.boardWidth) {
            x += speed;
            direction = "right";
        }

        // shooting when spacebar is pressed
        if (keyH.spacePressed) {
            shoot();
        }
    }

    // control the rate at which the bullets are spawned
    private long lastShotTime = 0;
    private long shootCooldown = 300; // milliseconds

    // Creates a new bullet at the player's current position
    // and adds it to the game panel's bullet list.
    private void shoot() {

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime < shootCooldown) {
            return;
        }
        lastShotTime = currentTime;
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
        Bullet bullet = new Bullet(gp, bulletX, bulletY, 10, 5, dx, dy, bulletSpeed);
        // add it to the list of active bullets
        gp.bullets.add(bullet);
    }

    private long lastAttackTime = 0;
    private long attackCooldown = 1000; // 1000 milliseconds = 1 second

    /*
     * 
     */
    public void attack(Entity target, int damageAmount) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastAttackTime >= attackCooldown) {
            target.takeDamage(damageAmount);
            lastAttackTime = currentTime;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // g2.setColor(Color.BLUE);
        // g2.fillRect(x, y, width, height);
        BufferedImage image = null;

        // changing the look of the sprite based on the direction it is going
        if (direction.equals("up")) {
            image = up;
        } else if (direction.equals("down")) {
            image = down;
        } else if (direction.equals("left")) {
            image = left;
        } else if (direction.equals("right")) {
            image = right;
        }

        if (image != null) {
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        }
        drawHpBar(g2, 0, -10);
    }

}
