package com.example.demo.actors.active.enemies;

import com.example.demo.levels.LevelParent;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.active.destructible.EnemyParent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class StealthEnemyPlane extends EnemyParent {

    private static final String IMAGE_NAME = "stealth_enemy.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final int PLANE_HEALTH = 3;
    private static final double INITIAL_VELOCITY = -3.0;
    private static final double DETECTION_RADIUS = 200.0; // Distance at which the plane becomes visible
    private static final double FIRE_RATE = 0.001; // Lower firing rate for stealth planes
    private boolean isVisible;
    private Timeline visibilityCheckTimeline;

    public StealthEnemyPlane(double initialX, double initialY, LevelParent levelParent) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY, PLANE_HEALTH, levelParent);
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
            double distance = Math.sqrt(
                    Math.pow(userPlane.getLayoutX() - getLayoutX(), 2) +
                            Math.pow(userPlane.getLayoutY() - getLayoutY(), 2)
            );
            if (distance <= DETECTION_RADIUS) {
                isVisible = true;
            }
            setVisible(isVisible);
        }
    }

    @Override
    protected ActiveActorDestructible fireProjectileWhenActive() {
        return Math.random() < FIRE_RATE
                ? ProjectileFactory.createProjectile("ENEMY_PROJECTILE", getProjectileXPosition(0), getProjectileYPosition(20), levelParent)
                : null;}

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    protected void updatePositionWhenActive() {
        moveHorizontally(getHorizontalVelocity());
    }

    @Override
    public void destroy() {
        super.destroy();
        if (visibilityCheckTimeline != null) {
            visibilityCheckTimeline.stop();
        }
    }
}
