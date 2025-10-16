package game;

import javax.swing.JFrame;

// import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Zombie Combat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);

        StartPanel startPanel = new StartPanel(frame);
        frame.add(startPanel);

        frame.setVisible(true);
    }
    
}
