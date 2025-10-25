package game;

import javax.swing.JFrame;

/*
 * The entry point of the Zombie Combat game.
 * 
 * @author Popova Anastasia
 * @id 2268116
 * @author Kroumova Kremena
 * @id 2243377
 */
public class Main {
    public static void main(String[] args) {
        GamePanel gamePanel = new GamePanel();

        // Calculate window dimensions
        double frameWidth = gamePanel.boardWidth + 0.5 * gamePanel.tileSize;
        int frameHeight = gamePanel.boardHeight + gamePanel.tileSize;

        // Creates the main window (JFrame)
        JFrame frame = new JFrame("Zombie Combat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close app when window closes
        frame.setSize((int) frameWidth, frameHeight); // set frame size
        frame.setLocationRelativeTo(null); // center window on screen

        // Create and add the start screen.
        StartPanel startPanel = new StartPanel(frame);
        frame.add(startPanel);

        frame.revalidate(); // ensure layout managers ypdate properly
        frame.repaint(); // trigger visual redraw
        frame.setVisible(true); // show the window
    }
}
