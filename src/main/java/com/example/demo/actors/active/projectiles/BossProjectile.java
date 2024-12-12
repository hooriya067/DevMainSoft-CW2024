package com.example.demo.actors.active.projectiles;
/**
 * The {@code BossProjectile} class represents a projectile fired by the boss enemy.
 * It inherits from {@link ProjectileParent} and moves horizontally at a fixed velocity.
 */
public class BossProjectile extends ProjectileParent {

	/**
	 * The file name of the image representing the boss projectile.
	 */
	private static final String IMAGE_NAME = "fireball.png";

	/**
	 * The height of the boss projectile image in pixels.
	 */
	private static final int IMAGE_HEIGHT = 50;

	/**
	 * The horizontal velocity at which the boss projectile moves.
	 */
	private static final int HORIZONTAL_VELOCITY = -15;

	/**
	 * The initial X-coordinate of the boss projectile.
	 */
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a {@code BossProjectile} object with a specified initial Y-coordinate.
	 * The X-coordinate is fixed at {@code INITIAL_X_POSITION}.
	 *
	 * @param initialYPos the initial Y-coordinate of the boss projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos, HORIZONTAL_VELOCITY);
	}
}
