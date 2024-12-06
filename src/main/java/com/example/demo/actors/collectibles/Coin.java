package com.example.demo.actors.collectibles;

import com.example.demo.Managers.CoinSystemManager;
import com.example.demo.levels.LevelParent;
import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.core.GameStateManager;

public class Coin extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "coin.png";
    private static final int IMAGE_SIZE = 25; // Size of the coin
    private static final double HORIZONTAL_VELOCITY = -9; // Leftward movement
    private final LevelParent level; // Reference to the current level

    public Coin(double initialX, double initialY, LevelParent level) {
        super(IMAGE_NAME, IMAGE_SIZE, initialX, initialY);
        this.level = level;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return; // Skip updating position if paused
        }
        moveHorizontally(HORIZONTAL_VELOCITY);
        if (getTranslateX() < 0) {
            destroy();
        }
    }

    private void collectCoin() {
        CoinSystemManager.getInstance().addCoins(1); // Add coin to the system
        destroy(); // Mark the coin as destroyed
        level.getRoot().getChildren().remove(this); // Remove the coin from the level's root
        System.out.println("Coin collected! Current coin count: " + CoinSystemManager.getInstance().getCoins());
    }

    @Override
    public void takeDamage() {
        destroy();
    }
}
