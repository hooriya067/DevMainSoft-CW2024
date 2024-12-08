package com.example.demo.actors.active.projectiles;

import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.core.GameStateManager;
import com.example.demo.levels.LevelParent;

public class Meteor extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "meteor.png";
    private static final int IMAGE_HEIGHT = 50;
    private final double speed = 5; // Speed of the meteor
    private double directionX; // Horizontal direction
    private double directionY; // Vertical direction
    private final LevelParent level;

    public Meteor(LevelParent level) {
        // Randomize initial position along the right edge or slightly above the screen
        super(IMAGE_NAME, IMAGE_HEIGHT,
                level.getScreenWidth() - 50 + Math.random() * 100, // Random X near the right edge
                Math.random() < 0.5 ? -50 : Math.random() * 100); // Random Y above the screen
        this.level = level;
        initializeDirection(); // Set up the diagonal movement
       // System.out.println("Meteor spawned at X: " + getTranslateX() + ", Y: " + getTranslateY());
    }
    private void initializeDirection() {
        // Set the spawn point to the left edge of the screen
        double spawnX = -55; // Spawn just outside the left edge
        double spawnY = Math.random() * level.getScreenHeight() * 0.5; // Random Y within the top 50% of the screen

        // Set the meteor's position
        setTranslateX(spawnX);
        setTranslateY(spawnY);

        // Define target point (top-right to bottom-right areas)
        double targetX = Math.random() * (level.getScreenWidth() / 2) + level.getScreenWidth(); // Target is toward the right half
        double targetY = level.getScreenHeight(); // Bottom of the screen

        // Calculate direction vector
        directionX = targetX - spawnX;
        directionY = targetY - spawnY;

        // Normalize the vector to ensure constant speed
        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);
        if (magnitude > 0) { // Avoid division by zero
            directionX = (directionX / magnitude) * speed;
            directionY = (directionY / magnitude) * speed;
        } else {
            System.err.println("Invalid direction vector magnitude!");
        }

        // Debug logs for verification
     //   System.out.println("Meteor spawned at X: " + getTranslateX() + ", Y: " + getTranslateY());
     //   System.out.println("Meteor direction - X: " + directionX + ", Y: " + directionY);
    }


    @Override
    public void updateActor() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }

        // Update position based on the calculated direction
        setTranslateX(getTranslateX() + directionX);
        setTranslateY(getTranslateY() + directionY);

        // Remove meteor if it leaves the screen
        if (getTranslateX() < -50 || getTranslateY() > level.getScreenHeight()) { // Check for left or bottom edge
            destroy();
        }
    }

    @Override
    public void updatePosition() {
        // Movement is handled in updateActor
    }

    @Override
    public void takeDamage() {
        destroy(); // Meteors are destroyed on impact
    }
}
