package entities;

import java.util.ArrayList;

public class ZombieSpawner {
    private ArrayList<Zombie> zombies; 

    private int boardWidth;
    private int boardHeight;
    private int tileSize;

    private Base base;

    private long lastSpawnTime = 0;
    private long spawnCooldown = 5000; // 10 seconds

    public ZombieSpawner(ArrayList<Zombie> zombies, int boardWidth, int boardHeight, int tileSize, Base base) {
        this.zombies = zombies;
        this.boardWidth = boardHeight;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;
        this.base = base;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSpawnTime >= spawnCooldown) {
            spawnZombies();
            lastSpawnTime = currentTime;
        }
    }

    private void spawnZombies() {
        for (int i = 0; i < 3; i++) {
            int x = boardWidth - tileSize;
            int y = getRandomNumber(tileSize, boardWidth - tileSize);
            zombies.add(new Zombie(null, x, y, tileSize, tileSize, base));
        }
    }

    /**
     * Might put this method in a new class for utils.
     */
    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
