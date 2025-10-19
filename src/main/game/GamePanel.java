//view
package game;

import entities.Base;
import entities.Bullet;
import entities.Player;
import entities.Zombie;
import entities.ZombieSpawner;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import tile.TileManager;
import java.util.ArrayList;
import java.util.Iterator;

//Runnable is the key for using Threads
public class GamePanel extends JPanel implements Runnable {
    // screen settings
    final int rowCount = 12;
    final int columnCount = 18;
    public int tileSize = 32; // pixels
    public int boardWidth = columnCount * tileSize;
    public int boardHeight = rowCount * tileSize;

    public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    private ZombieSpawner zombieSpawner;
    private int killsCounter = 0;
    public Base base;

    public TileManager tileManager;
    private BufferedImage backgroundImage;
    private boolean gameOver = false; 

    // for manual double buffering
    private BufferedImage offscreenImage;
    private Graphics2D offscreenGraphics;

    InputController keyH = new InputController();
    Player player;

    public int min;
    public int max;

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
        System.out.println(this.getPreferredSize());
        this.setDoubleBuffered(true);

        tileManager = new TileManager(this);
        base = tileManager.getBase();

        // width/height for player (e.g., one tile)
        player = new Player(this, keyH, 100, 100, tileSize, tileSize);

        zombieSpawner = new ZombieSpawner(zombies, boardWidth, boardHeight, tileSize, base);

        // creates a hidden image and a graphics context you can draw
        offscreenImage = new BufferedImage(boardWidth, boardHeight, BufferedImage.TYPE_INT_ARGB);
        offscreenGraphics = offscreenImage.createGraphics();

    }

    @Override
    // "super" relates to the parent class (JPanel)
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // if offscreen graphics hasn't been created, create it
        if (offscreenImage == null || offscreenGraphics == null) {
            offscreenImage = new BufferedImage(boardWidth, boardHeight, BufferedImage.TYPE_INT_ARGB);
            offscreenGraphics = offscreenImage.createGraphics();
        }

        // clear the offscreen buffer
        offscreenGraphics.setColor(Color.BLACK);
        offscreenGraphics.fillRect(0, 0, boardWidth, boardHeight);

        // draw everything to the offscreen graphics
        offscreenGraphics.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);
        tileManager.draw(offscreenGraphics);

        // draw each zombie in the list:
        for (Zombie zombie : zombies) {
            zombie.draw(offscreenGraphics);
        }
        // draw each bullet in the list
        synchronized (bullets) {
            for (Bullet b : bullets) {
                b.draw(offscreenGraphics);
            }
        }

        // the player disappears when HP <=0
        if (player != null && player.getHp() >= 0) {
            player.draw(offscreenGraphics);
        }

        // draw the entire offscreen image to the real screen
        g.drawImage(offscreenImage, 0, 0, null);

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

        synchronized (zombies) {
            Iterator<Zombie> it = zombies.iterator();
            while (it.hasNext()) {
                Zombie zombie = it.next();
                if (zombie.getHp() <= 0) {
                    it.remove();
                    killsCounter++;
                } else {
                    zombie.update();
                }
            }
        }
        zombieSpawner.update();

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
            if (!gameOver && player != null && player.getHp() <= 0) {
                gameOver = true; // set flag so dialog shows only once
                player = null; // player disappears
                showGameOverDialog("You killed " + killsCounter + " zombies!");
            }

            if (!gameOver && base != null && base.getHp() <= 0) {
                gameOver = true; // set flag so dialog shows only once
                base = null; // base disappears
                showGameOverDialog("You killed " + killsCounter + " zombies!");
            }

        }
    }

    // Game over dialog with "Start Over" button
    private void showGameOverDialog(String message) {
        gameThread = null; //stop the game loop

        SwingUtilities.invokeLater(() -> {
            // Create a restart button
            JButton restartButton = new JButton("Start Over");

            // create the message
            JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
            panel.add(new JLabel("Game Over! " + message, SwingConstants.CENTER));
            panel.add(restartButton);

            JOptionPane optionPane = new JOptionPane(panel, JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION);
            JDialog dialog = optionPane.createDialog(this, "Game Over");

            // Add button behavior
            restartButton.addActionListener(e -> {
                dialog.dispose(); // close popup
                restartGame(); // restart the game
            });

            dialog.setVisible(true);
        });

    }

    // all the HP values are restored and the game starts over
    private void restartGame() {
        zombies.clear();
        bullets.clear();

        
        base = tileManager.getBase();
        base.resetHp();

        player = new Player(this, keyH, 100, 100, tileSize, tileSize);
        zombieSpawner = new ZombieSpawner(zombies, boardWidth, boardHeight, tileSize, base);
        killsCounter = 0;

        gameOver = false; // reset the game over flag

        startGameThread();
        requestFocusInWindow();
    }

    // This list stores all active bullets currently on the screen.
    // each time the player shoots, a new bullet is added here
    // in the update loop, each bullet will move and be removed when it goes
    // off-screen
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

}