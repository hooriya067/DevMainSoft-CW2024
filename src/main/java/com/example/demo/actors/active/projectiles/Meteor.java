package com.example.demo.actors.active.projectiles;

import com.example.demo.actors.active.ActiveActor;
import com.example.demo.core.GameStateManager;
import com.example.demo.Levels.LevelParent;
/**
 * The {@code Meteor} class represents a meteor object that moves diagonally across the screen,
 * typically from the left edge to the bottom-right area. The class extends {@link ActiveActor}
 * and implements behaviors such as movement, destruction, and collision handling.
 */
public class Meteor extends ActiveActor {

    /**
     * The file name of the image representing the meteor.
     */
    private static final String IMAGE_NAME = "meteor.png";

    /**
     * The height of the meteor image in pixels.
     */
    private static final int IMAGE_HEIGHT = 50;

    /**
     * The constant speed at which the meteor moves.
     */
    private final double speed = 5;

    /**
     * The horizontal direction vector for the meteor's movement.
     */
    private double directionX;

    /**
     * The vertical direction vector for the meteor's movement.
     */
    private double directionY;

    /**
     * The level context that manages the meteor.
     */
    private final LevelParent level;

    /**
     * Constructs a {@code Meteor} object with a randomized initial position and diagonal movement direction.
     *
     * @param level the {@link LevelParent} that contains this meteor.
     */
    public Meteor(LevelParent level) {
        super(IMAGE_NAME, IMAGE_HEIGHT,
                level.getScreenWidth() - 50 + Math.random() * 100, // Random X near the right edge
                Math.random() < 0.5 ? -50 : Math.random() * 100); // Random Y above the screen
        this.level = level;
        initializeDirection(); // Set up diagonal movement
    }

    /**
     * Initializes the movement direction of the meteor by calculating a normalized vector
     * that points from the spawn location to a target area on the screen.
     */
    private void initializeDirection() {
        // Spawn point (left edge)
        double spawnX = -55;
        double spawnY = Math.random() * level.getScreenHeight() * 0.5;

        // Target point (bottom-right)
        double targetX = Math.random() * (level.getScreenWidth() / 2) + level.getScreenWidth();
        double targetY = level.getScreenHeight();

        // Calculate direction vector
        directionX = targetX - spawnX;
        directionY = targetY - spawnY;

        // Normalize vector to ensure constant speed
        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);
        if (magnitude > 0) {
            directionX = (directionX / magnitude) * speed;
            directionY = (directionY / magnitude) * speed;
        } else {
            System.err.println("Invalid direction vector magnitude!");
        }

        setTranslateX(spawnX);
        setTranslateY(spawnY);
    }

    /**
     * Updates the state of the meteor by moving it diagonally across the screen
     * and checking if it has exited the visible area.
     */
    @Override
    public void updateActor() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }

        // Move the meteor
        setTranslateX(getTranslateX() + directionX);
        setTranslateY(getTranslateY() + directionY);

        // Destroy meteor if it leaves the screen bounds
        if (getTranslateX() < -50 || getTranslateY() > level.getScreenHeight()) {
            destroy();
        }
    }

    /**
     * This method is intentionally left empty as the {@code Meteor} class calculates position updates
     * in the {@link #updateActor()} method.
     */
    @Override
    public void updatePosition() {}

    /**
     * Handles the logic for taking damage, which results in immediate destruction of the meteor.
     */
    @Override
    public void takeDamage() {
        destroy();
    }
}
