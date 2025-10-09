//view

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    
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
}