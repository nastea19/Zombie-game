//view
package game;

import entities.Base;
import entities.Bullet;
import entities.Player;
import entities.Zombie;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import tile.TileManager;
import java.util.ArrayList;

//Runnable is the key for using Threads
public class GamePanel extends JPanel implements Runnable {
    // screen settings
    final int rowCount = 12;
    final int columnCount = 18;
    public int tileSize = 32; // pixels
    public int boardWidth = columnCount * tileSize;
    public int boardHeight = rowCount * tileSize;

    public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    public Base base;

    public TileManager tileManager;
    private BufferedImage backgroundImage;
    InputController keyH = new InputController();
    Player player;

    public int min;
    public int max;

    /**
     * Might put this method in a new class for utils.
     */
    public int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public GamePanel() {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/resources/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(Color.black);
        this.addKeyListener(keyH);
        // ?
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.setDoubleBuffered(true);

        tileManager = new TileManager(this);
        base = tileManager.getBase();

        // width/height for player (e.g., one tile)
        player = new Player(this, keyH, 100, 100, tileSize, tileSize);

        for (int i = 0; i < 3; i++) {
            int x = boardWidth - tileSize;
            int y = getRandomNumber(0 + tileSize, boardHeight) - tileSize;
            zombies.add(new Zombie(this, x, y, tileSize, tileSize, base));
        }

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Zombie Combat"); // Creates the window
        GamePanel panel = new GamePanel(); // Creates an instance of our custom game panel

        frame.add(panel); // add the panel to the window so it becomes the visible area we draw on
        frame.pack();
        frame.setVisible(true); // makes the window visible
        frame.setLocationRelativeTo(null); // sets the window on the center of the screen
        frame.setResizable(false); // stops the user from resizing the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // determines if the user clicks on the x button of the window

        panel.startGameThread();
    }

    @Override
    // "super" relates to the parent class (JPanel)
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, this); // draws a baxkground
        tileManager.draw(g2); // draws the base

        // draw each zombie in the list:
        for (Zombie zombie : zombies) {
            zombie.draw(g2);
        }
        // draw each bullet in the list
        synchronized (bullets) {
            for (Bullet b : bullets) {
                b.draw(g2);
            }
        }

        // the player disappears when HP <=0
        if (player != null && player.getHp() >= 0) {
            player.draw(g2);
        }
    }

    // FPS
    int FPS = 60;
    // keeps the program running until you stop it
    // useful for repetitive processes (fps)
    public Thread gameThread;

    public void startGameThread() {
        // initiation and starting of the game thread
        // by "this" we are passing GamePanel class to this thread's constructor
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    // starting the thread causes the object's run method to be called in that
    // separately executing thread
    public void run() {
        // we use nanoseconds
        double drawInterval = 1000000000 / FPS; // 0.01666
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // as long as the thread exists, the game will update
        while (gameThread != null) {

            currentTime = System.nanoTime();

            // checking how much time passed??
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (base != null) {
            base.update();
        }

        if (player != null) {
            player.update(); // player handles its own movement
        }

        zombies.removeIf(z -> z.getHp() <= 0);

        synchronized (zombies) {
            for (Zombie z : zombies) {
                z.update();
            }
        }

        // loops through all bullets in the list
        synchronized (bullets) {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i); // Get one bullet from the list
                bullet.update();

                // if the bullet goes off-screen, remove it from the list
                if (!bullet.isActive()) {
                    bullets.remove(i);
                    i--; // Adjust index since list size changed
                }
            }
        }

        if (player != null) {
            synchronized (zombies) {
                for (Zombie z : zombies) {
                    boolean collision = player.x < z.x + z.width
                            && player.x + player.width > z.x
                            && player.y < z.y + z.height
                            && player.y + player.height > z.y;

                    if (collision) {
                        // Zombie deals damage directly to player
                        player.takeDamage(1);
                    }
                }
            }

            // Remove player if HP <= 0
            // we use invokeLater so it does not interfere in the game thread, just is
            // handled by EDT after
            if (player.getHp() <= 0) {
                player = null; // player disappears
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                });
                gameThread = null; // stop game loop
            }

        }
    }

    // This list stores all active bullets currently on the screen.
    // each time the player shoots, a new bullet is added here
    // in the update loop, each bullet will move and be removed when it goes
    // off-screen
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

}