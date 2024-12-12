package com.example.demo.actors.active.projectiles;

import com.example.demo.core.GameStateManager;

/**
 * The {@code BombProjectile} class represents a bomb-like projectile that moves
 * downward at a consistent speed. It is a specialized type of projectile that
 * inherits from {@link ProjectileParent}.
 */
public class BombProjectile extends ProjectileParent {

    /**
     * The file name of the image representing the bomb projectile.
     */
    private static final String IMAGE_NAME = "bomb_projectile.png";

    /**
     * The height of the bomb projectile image in pixels.
     */
    private static final int IMAGE_HEIGHT = 35;

    /**
     * The velocity at which the bomb moves downward.
     */
    private static final double VELOCITY = 4.0;

    /**
     * Constructs a {@code BombProjectile} object with a specified initial position.
     *
     * @param initialXPos the initial X-coordinate of the bomb projectile.
     * @param initialYPos the initial Y-coordinate of the bomb projectile.
     */
    public BombProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, VELOCITY);
    }

    /**
     * Updates the position of the bomb projectile. The bomb moves downward
     * at a constant velocity unless the game is paused.
     */
    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;  // Skip updating position if paused
        }
        moveVertically(VELOCITY); // Bombs move downward
    }
    /**
     * Updates the state of the bomb projectile, including its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Handles the logic for taking damage. Bomb projectiles are destroyed on impact.
     */
    @Override
    public void takeDamage() {
        this.destroy();
    }
}
