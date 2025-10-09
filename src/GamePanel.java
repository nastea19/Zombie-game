//view

import java.awt.*;
import javax.swing.*;

//Runnable is the key for using Threads
public class GamePanel extends JPanel implements Runnable {

    public GamePanel() {
        setBackground(Color.BLACK);
    }

    public static void main(String[] args) {
        // Dimensions and scaling of the screen
        int rowCount = 12;
        int columnCount = 18;
        int tileSize = 32; // pixels
        int boardWidth = columnCount * tileSize;
        int boardHeight = rowCount * tileSize;

        JFrame frame = new JFrame("Zombie Combat"); // Creates the window
        GamePanel panel = new GamePanel(); // Creates an instance of our custom game panel

        frame.add(panel); // add the oanel to the window so it becomes the visible area we draw on
        frame.setVisible(true); // makes the window visible
        frame.setSize(boardWidth, boardHeight); // sets the size of the frame
        frame.setLocationRelativeTo(null); // sets the window on the center of the screen
        frame.setResizable(false); // stops the user from resizing the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // determines if the user clicks on the x button of the window

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

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
        // throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
}