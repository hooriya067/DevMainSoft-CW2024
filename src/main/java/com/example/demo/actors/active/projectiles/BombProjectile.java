package com.example.demo.actors.active.projectiles;

import com.example.demo.core.GameStateManager;

public class BombProjectile extends Projectile {

    private static final String IMAGE_NAME = "bomb_projectile.png";
    private static final int IMAGE_HEIGHT = 35;
    private static final double VELOCITY = 4.0; // Adjusted speed for the bomb

    public BombProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, VELOCITY);
    }

    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;  // Skip updating position if paused
        }
        moveVertically(VELOCITY); // Bombs move downward
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void takeDamage() {
        // Bombs should be destroyed on impact, so we can simply destroy them
        this.destroy();
    }
}
