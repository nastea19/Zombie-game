//view
package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.Buffer;

import javax.swing.*;

import tile.TileManager;

//Runnable is the key for using Threads
public class GamePanel extends JPanel implements Runnable {

    // set pleayer's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // screen settings
    final int rowCount = 12;
    final int columnCount = 18;
    public int tileSize = 32; // pixels
    public int boardWidth = columnCount * tileSize;
    public int boardHeight = rowCount * tileSize;

    public GamePanel() {
        setBackground(Color.GREEN);
        this.addKeyListener(keyH);
        // ?
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.setDoubleBuffered(true);
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

    public TileManager tileManager = new TileManager(this);

    @Override
    // "super" relates to the parent class (JPanel)
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2); 
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();

    }

    // FPS
    int FPS = 60;

    InputController keyH = new InputController();

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

            //checking how much time passed??
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    // key input is catched by InputController and updates the player coordinates
    // and calls the repaint component
    public void update() {
        // playerSpeed is the distance measured in pixels by which the player will move
        // in the according direction
        if (keyH.upPressed == true) {
            playerY -= playerSpeed;
        } else if (keyH.downPressed == true) {
            playerY += playerSpeed;
        } else if (keyH.leftPressed == true) {
            playerX -= playerSpeed;
        } else if (keyH.rightPressed == true) {
            playerX += playerSpeed;
        }
    }
}