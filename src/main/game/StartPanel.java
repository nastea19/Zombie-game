package game;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartPanel extends JPanel{
    private JFrame frame;
    public StartPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(420, 420));
        frame.setResizable(false);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener((ActionEvent e) -> StartGame());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(0, 0, 0, 0);
        
        add(startButton);
    
    }

    private void StartGame() {
        frame.getContentPane().removeAll();

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.revalidate();
        frame.repaint();

        gamePanel.startGameThread();
        gamePanel.requestFocusInWindow();
    }
}
