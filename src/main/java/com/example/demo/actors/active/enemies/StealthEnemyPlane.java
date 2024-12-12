package com.example.demo.actors.active.enemies;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.actors.active.ActiveActor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The {@code StealthEnemyPlane} class represents an advanced enemy plane that remains
 * invisible unless within a certain detection radius of the player's plane. It moves
 * horizontally and fires projectiles with a low probability.
 *
 * <p>
 * This class extends {@link EnemyParent} and includes stealth mechanics, making it a
 * challenging adversary for players. Visibility is dynamically updated based on the player's
 * proximity to the enemy.
 * </p>
 */
public class StealthEnemyPlane extends EnemyParent {

    /**
     * The name of the image representing the stealth enemy.
     */
    private static final String IMAGE_NAME = "stealth_enemy.png";

    /**
     * The height of the enemy plane's image.
     */
    private static final int IMAGE_HEIGHT = 50;

    /**
     * The health points of the stealth enemy plane.
     */
    private static final int PLANE_HEALTH = 3;

    /**
     * The initial velocity of the plane.
     */
    private static final double INITIAL_VELOCITY = -3.0;

    /**
     * The radius within which the plane becomes visible to the player.
     */
    private static final double DETECTION_RADIUS = 200.0;

    /**
     * The probability of firing a projectile in each update cycle.
     */
    private static final double FIRE_RATE = 0.001;

    /**
     * Indicates whether the plane is currently visible.
     */
    private boolean isVisible;

    /**
     * A timeline that periodically checks and updates the plane's visibility.
     */
    private Timeline visibilityCheckTimeline;

    /**
     * Constructs a {@code StealthEnemyPlane} with the specified initial position and level context.
     *
     * @param initialX     the initial X-coordinate of the stealth enemy.
     * @param initialY     the initial Y-coordinate of the stealth enemy.
     * @param levelParent  the {@link LevelParent} instance the enemy belongs to.
     */
    public StealthEnemyPlane(double initialX, double initialY, LevelParent levelParent) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY, PLANE_HEALTH, levelParent);
        setHorizontalVelocity(INITIAL_VELOCITY);
        isVisible = false;
        setVisible(isVisible);
        initializeVisibilityCheck();
    }

    /**
     * Initializes the timeline that periodically checks and updates the visibility of the plane.
     */
    private void initializeVisibilityCheck() {
        visibilityCheckTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> updateVisibility()));
        visibilityCheckTimeline.setCycleCount(Timeline.INDEFINITE);
        visibilityCheckTimeline.play();
    }

    /**
     * Updates the visibility of the plane based on its distance to the player's plane.
     * The plane becomes visible when within the detection radius.
     */
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

    /**
     * Fires a projectile if conditions are met. The projectile's type and behavior are
     * determined by the level context and the enemy's state.
     *
     * @return an {@link ActiveActor} representing the projectile, or {@code null} if no projectile is fired.
     */
    @Override
    protected ActiveActor fireProjectileWhenActive() {
        return Math.random() < FIRE_RATE
                ? ProjectileFactory.createProjectile("ENEMY_PROJECTILE", getProjectileXPosition(0), getProjectileYPosition(20), levelParent)
                : null;
    }

    /**
     * Updates the state of the plane, including its position and visibility.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Updates the position of the plane while active. The plane moves horizontally at a constant velocity.
     */
    @Override
    protected void updatePositionWhenActive() {
        moveHorizontally(getHorizontalVelocity());
    }

    /**
     * Destroys the plane and stops the visibility check timeline.
     */
    @Override
    public void destroy() {
        super.destroy();
        if (visibilityCheckTimeline != null) {
            visibilityCheckTimeline.stop();
        }
    }
}

