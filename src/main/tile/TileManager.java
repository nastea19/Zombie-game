package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import entities.Base;
import game.GamePanel;

public class TileManager {
    private GamePanel gamePanel;
    private Tile[] tile;
    private Base base;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[10];

        baseSetup();
    }

    private void baseSetup() {
        try {
            // Load base image from resources
            BufferedImage baseImage = ImageIO.read(getClass().getResourceAsStream("/resources/stone.png"));
            
            int baseWidth = gamePanel.boardWidth / 6;
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
        if (base != null) {
            base.draw(g2);
        }
    }
    
    public void update() {
        if (base != null) {
            base.update();
        }
    }
}
