package com.example.demo.actors.active.projectiles;

/**
 * The {@code EnemyProjectile} class represents projectiles fired by enemy planes.
 * It inherits from {@link ProjectileParent} and moves horizontally at a fixed velocity.
 */
public class EnemyProjectile extends ProjectileParent {

	/**
	 * The file name of the image representing the enemy projectile.
	 */
	private static final String IMAGE_NAME = "enemyFire.png";

	/**
	 * The height of the enemy projectile image in pixels.
	 */
	private static final int IMAGE_HEIGHT = 20;

	/**
	 * The horizontal velocity at which the enemy projectile moves.
	 */
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs an {@code EnemyProjectile} object with the specified initial position.
	 *
	 * @param initialXPos the initial X-coordinate of the enemy projectile.
	 * @param initialYPos the initial Y-coordinate of the enemy projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY);
	}
}

