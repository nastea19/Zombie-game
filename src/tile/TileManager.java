package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[10];

        baseSetup();
    }

    public Base base;

    private void baseSetup() {
        try {
            // Load base image from resources
            BufferedImage baseImage = ImageIO.read(getClass().getResourceAsStream("/resources/stone.png"));
            
            int baseWidth = gamePanel.boardWidth / 4;
            int baseHeight = gamePanel.boardHeight;
            int x = 0;
            int y = 0;
            base = new Base(gamePanel, x, y, baseWidth, baseHeight, baseImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Base getBase() {
        return base;
    }

    /**
     * Draws all tiles managed by this TileManager.
     * @param g2 Graphics2D object from GamePanel
     */

    public void draw(Graphics2D g2) {
        base.draw(g2);
    }
}
