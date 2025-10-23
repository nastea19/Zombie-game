package game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/*
 * The StartPanel is the main screen displayed before the game begins.
 * 
 * It contains a "Start game" button, which when clicked, loads the actual GamePanel.
 */
public class StartPanel extends JPanel{
    private JFrame frame;

    /*
     * Constructor
     * 
     * @param frame
     */
    public StartPanel(JFrame frame) {
        this.frame = frame;

        // Set up the layout and size of the start menu
        setLayout(new GridBagLayout()); // use layout manager to center button
        setPreferredSize(new Dimension(420, 420)); // window size for menu
        frame.setResizable(false); // lock window size

        // "Start Game" button, which when clicked opens "GamePanel"
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener((ActionEvent e) -> startGame());

        // Set up layout constraubts for the button
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // column position
        gbc.gridy = 0; // row position
        gbc.anchor = GridBagConstraints.CENTER; // center the button 
        gbc.insets = new Insets(0, 0, 0, 0);
        
        add(startButton);
    
    }

    /*
     * Called when the player clicks the "Start Game" button
     * 
     * This method removes the StartPanel from the frame and replaces it
     * with a new GamePanel.
     */
    private void startGame() {
        // REmove the start menu from the frame
        frame.getContentPane().removeAll();

        // Create and add the main gameplay panel
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        // Refresh the window to show the new panel
        frame.revalidate(); // re-calculate layout
        frame.repaint(); // visually update the frame

        // Start the main game loop (thread)
        gamePanel.startGameThread();

        // Ensure the panel receives keyboard focus for player controls
        gamePanel.requestFocusInWindow();
    }
}
