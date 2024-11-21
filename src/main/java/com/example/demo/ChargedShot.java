package com.example.demo;

public class ChargedShot extends Projectile {

    private static final String IMAGE_NAME = "chargedshot.png";
    private static final int IMAGE_HEIGHT = 25;
    private final double velocity;

    public ChargedShot(double initialXPos, double initialYPos, double velocity) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, velocity);
        this.velocity = velocity; // Velocity passed from UserPlaneUpgraded to determine the speed of the shot
    }

    @Override
    public void updatePosition() {
        if (GameStateManager.isPaused) {
            return; // Skip updating if the game is paused
        }
        moveHorizontally(velocity); // Move with the calculated velocity
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}

