package game;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartPanel extends JPanel{
    private JFrame frame;

    public StartPanel(JFrame frame) {
        this.frame = frame;
        setLayout(null);
        setBounds(0, 0, 420, 420);;

        JButton startButton = new JButton("Start Game");
        startButton.setBounds(150, 180, 120, 40);
        startButton.addActionListener((ActionEvent e) -> StartGame());
        add(startButton);
    }

    private void StartGame() {
        frame.getContentPane().removeAll();

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.revalidate();
        frame.repaint();

        gamePanel.startGameThread();
    }
}
