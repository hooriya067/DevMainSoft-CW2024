package com.example.demo.actors.active.enemies;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.actors.active.ActiveActor;

/**
 * The {@code EnemyPlaneTypeB} class represents an advanced enemy plane in the game.
 * It moves horizontally at a slower pace than other enemies and fires homing missiles
 * targeting the player's plane.
 *
 * <p>
 * This class extends {@link EnemyParent} and implements custom behavior for movement
 * and projectile firing. The homing missiles actively track the {@link UserPlane}.
 * </p>
 */
public class EnemyPlaneTypeB extends EnemyParent {

    /**
     * Reference to the {@link LevelParent} instance the enemy belongs to.
     */
    private final LevelParent levelParent;

    /**
     * Constructs an {@code EnemyPlaneTypeB} object with the specified initial position
     * and level context.
     *
     * @param initialX     the initial X-coordinate of the enemy plane.
     * @param initialY     the initial Y-coordinate of the enemy plane.
     * @param levelParent  the {@link LevelParent} instance the enemy belongs to.
     */
    public EnemyPlaneTypeB(double initialX, double initialY, LevelParent levelParent) {
        super("enemyplaneB.png", 60, initialX, initialY, 3, levelParent);
        this.levelParent = levelParent;
    }

    /**
     * Updates the state of the enemy plane, including position and firing behavior.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Updates the position of the enemy plane. This implementation moves the plane
     * horizontally to the left at a slow pace.
     */
    @Override
    public void updatePositionWhenActive() {
        moveHorizontally(-2);
    }

    /**
     * Fires a homing missile targeting the {@link UserPlane}. The missile tracks
     * the player's current position and follows it.
     *
     * @return an {@link ActiveActor} representing the homing missile, or {@code null} if no missile is fired.
     */
    @Override
    public ActiveActor fireProjectileWhenActive() {
        UserPlane userPlane = levelParent.getUser(); // Access UserPlane from LevelParent
        if (Math.random() < 0.007) {
            return ProjectileFactory.createProjectile(
                    "HOMING_MISSILE",
                    getProjectileXPosition(0),
                    getProjectileYPosition(20),
                    userPlane
            );
        }
        return null;
    }
}
