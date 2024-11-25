package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class StealthEnemyPlane extends FighterPlane {

    private static final String IMAGE_NAME = "stealth_enemy.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final int PLANE_HEALTH = 1;
    private static final double INITIAL_VELOCITY = -3.0;
    private static final double DETECTION_RADIUS = 200.0; // Distance at which the plane becomes visible
    private boolean isVisible;
    private Timeline visibilityCheckTimeline;
    private final LevelParent levelParent;

    public StealthEnemyPlane(double initialX, double initialY, LevelParent levelParent) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY, 3);
        this.levelParent = levelParent;
        setHorizontalVelocity(INITIAL_VELOCITY);
        isVisible = false; // Initially invisible
        setVisible(isVisible);
        initializeVisibilityCheck();
    }

    // Initialize a timeline to check periodically if the plane should be visible
    private void initializeVisibilityCheck() {
        visibilityCheckTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> updateVisibility()));
        visibilityCheckTimeline.setCycleCount(Timeline.INDEFINITE);
        visibilityCheckTimeline.play();
    }

    // Update the visibility based on the distance to the player's plane
    private void updateVisibility() {
        UserPlane userPlane = levelParent.getUser();
        if (userPlane != null) {
            double distance = Math.sqrt(Math.pow(userPlane.getLayoutX() - getLayoutX(), 2) + Math.pow(userPlane.getLayoutY() - getLayoutY(), 2));
            if (distance <= DETECTION_RADIUS) {
                isVisible = true;
            }
            setVisible(isVisible);
        }
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        double probability = 0.001; // Lower firing rate for stealth planes
        return (Math.random() < probability) ? new EnemyProjectile(getProjectileXPosition(0), getProjectileYPosition(20)) : null;
    }

    @Override
    public void updatePosition() {
        if (GameStateManager.isPaused) {
            return;  // Skip updating position if paused
        }
        moveHorizontally(getHorizontalVelocity());
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void destroy() {
        super.destroy();
        if (visibilityCheckTimeline != null) {
            visibilityCheckTimeline.stop();
        }
    }


}

