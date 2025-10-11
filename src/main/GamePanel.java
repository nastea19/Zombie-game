//view
package main;

import java.awt.*;
import javax.swing.*;

import entities.Bullet;
import entities.Player;
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

    public TileManager tileManager;
    InputController keyH = new InputController();
    Player player;

    public GamePanel() {
        setBackground(Color.GREEN);
        this.addKeyListener(keyH);
        // ?
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.setDoubleBuffered(true);

        tileManager = new TileManager(this);

        // width/height for player (e.g., one tile)
        player = new Player(this, keyH, 100, 100, tileSize, tileSize);
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
        tileManager.draw(g2);
        player.draw(g2);
        //draw each bullet in the list
        for (Bullet b : bullets) {
            b.draw(g2);
        }
    }

    // FPS
    int FPS = 60;
    // keeps the program running until you stop it
    // useful for repetitive processes (fps)
    Thread gameThread;

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
        player.update(); // player handles its own movement

        // loops through all bullets in the list
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i); // Get one bullet from the list
            bullet.update(); 

            // if the bullet goes off-screen, remove it from the list
            if (bullet.isOffScreen()) {
                bullets.remove(i);
                i--; // Adjust index since list size changed
            }
        }
    }

    // This list stores all active bullets currently on the screen.
    // each time the player shoots, a new bullet is added here
    // in the update loop, each bullet will move and be removed when it goes off-screen
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();


}