package com.example.demo.actors.active.enemies;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.active.ActiveActor;

/**
 * The {@code EnemyPlaneTypeA} class represents a more advanced type of enemy plane in the game.
 * It features oscillating movement and customizable update behavior via an {@link Runnable} action.
 *
 * <p>
 * This class extends {@link EnemyParent} and defines unique movement and firing behavior
 * tailored for this enemy type. It uses a sine wave for vertical oscillation and fires projectiles
 * of a specialized type at a lower rate than the base {@code EnemyPlane}.
 * </p>
 */
public class EnemyPlaneTypeA extends EnemyParent {

    /**
     * The file name of the image representing this enemy type.
     */
    private static final String IMAGE_NAME = "enemyplaneA.png";

    /**
     * The probability (per frame) of this enemy firing a projectile.
     */
    private static final double FIRE_RATE = 0.005;

    /**
     * A customizable action that can override the default update behavior.
     */
    private Runnable onUpdate;

    /**
     * Constructs an {@code EnemyPlaneTypeA} object with the specified initial position
     * and level context.
     *
     * @param initialX     the initial X-coordinate of the enemy plane.
     * @param initialY     the initial Y-coordinate of the enemy plane.
     * @param levelParent  the {@link LevelParent} instance the enemy belongs to.
     */
    public EnemyPlaneTypeA(double initialX, double initialY, LevelParent levelParent) {
        super(IMAGE_NAME, 60, initialX, initialY, 2, levelParent);
    }

    /**
     * Updates the state of the enemy plane, including position and firing behavior.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Allows customization of the update behavior by setting a {@link Runnable} action.
     * If set, the {@link Runnable} will be executed during position updates.
     *
     * @param onUpdate the {@link Runnable} action to be executed during updates.
     */
    public void setOnUpdate(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }

    /**
     * Updates the position of the enemy plane. By default, the plane moves horizontally
     * to the left and oscillates vertically using a sine wave function.
     * If a custom {@link Runnable} is set, it overrides this default behavior.
     */
    @Override
    public void updatePositionWhenActive() {
        if (onUpdate == null) {
            moveHorizontally(-5);
            double verticalMove = Math.sin(System.currentTimeMillis() / 500.0) * 2; // Oscillate
            moveVertically(verticalMove);
        } else {
            onUpdate.run();
        }
    }

    /**
     * Fires a specialized projectile with a low probability.
     *
     * @return an {@link ActiveActor} representing the fired projectile, or {@code null} if no projectile is fired.
     */
    @Override
    public ActiveActor fireProjectileWhenActive() {
        return Math.random() < FIRE_RATE
                ? ProjectileFactory.createProjectile("ENEMY_PROJECTILE_LEVEL_THREE", getProjectileXPosition(0), getProjectileYPosition(20), levelParent)
                : null;
    }
}
