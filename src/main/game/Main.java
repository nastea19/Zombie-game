package game;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        GamePanel gamePanel = new GamePanel();
        double frameWidth = gamePanel.boardWidth + 0.5 * gamePanel.tileSize;
        int frameHeight = gamePanel.boardHeight + gamePanel.tileSize;
        JFrame frame = new JFrame("Zombie Combat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);

        StartPanel startPanel = new StartPanel(frame);
        frame.add(startPanel);

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

}
