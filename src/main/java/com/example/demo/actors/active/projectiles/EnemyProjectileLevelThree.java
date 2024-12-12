package com.example.demo.actors.active.projectiles;

/**
 * The {@code EnemyProjectileLevelThree} class represents a specialized projectile used by enemies in Level 3.
 * It inherits from {@link ProjectileParent} and provides distinct visual and movement characteristics.
 */
public class EnemyProjectileLevelThree extends ProjectileParent {

    /**
     * The file name of the image representing the projectile.
     */
    private static final String IMAGE_NAME = "enemyfire2.png";

    /**
     * The height of the projectile image in pixels.
     */
    private static final int IMAGE_HEIGHT = 25;

    /**
     * The horizontal velocity at which the projectile moves.
     */
    private static final int HORIZONTAL_VELOCITY = -10;

    /**
     * Constructs an {@code EnemyProjectileLevelThree} object with the specified initial position.
     *
     * @param initialXPos the initial X-coordinate of the projectile.
     * @param initialYPos the initial Y-coordinate of the projectile.
     */
    public EnemyProjectileLevelThree(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY);
    }
}
