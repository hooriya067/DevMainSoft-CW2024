package com.example.demo;

public class Coin extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "coin.png";
    private static final int IMAGE_SIZE = 25; // Size of the coin
    private static final double HORIZONTAL_VELOCITY = -9; // Leftward movement
    private final LevelParent level; // Reference to the current level

    public Coin(double initialX, double initialY, LevelParent level) {
        super(IMAGE_NAME, IMAGE_SIZE, initialX, initialY);
        this.level = level; // Initialize the level reference
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void updatePosition() {
        if (GameStateManager.isPaused) {
            return; // Skip updating position if paused
        }

        moveHorizontally(HORIZONTAL_VELOCITY);


        // Check if the coin is off-screen and remove it
        if (getTranslateX() < 0) {
            destroy();
        }
    }

    private void collectCoin() {
        CoinSystem.getInstance().addCoins(1); // Add coin to the system
        destroy(); // Mark the coin as destroyed
        level.getRoot().getChildren().remove(this); // Remove the coin from the level's root
        System.out.println("Coin collected! Current coin count: " + CoinSystem.getInstance().getCoins());
    }

    @Override
    public void takeDamage() {
        // Coins do not take damage, so they are destroyed when interacted with
        destroy();
    }
}
