package com.example.demo.actors.active.projectiles;

/**
 * The {@code UserProjectile} class represents a projectile fired by the user in the game.
 * It inherits from {@link ProjectileParent} and uses predefined properties specific to user projectiles.
 */
public class UserProjectile extends ProjectileParent {

	/**
	 * The image file name for the user projectile.
	 */
	private static final String IMAGE_NAME = "userfire.png";

	/**
	 * The height of the user projectile image in pixels.
	 */
	private static final int IMAGE_HEIGHT = 20;

	/**
	 * The horizontal velocity of the user projectile, defining its movement speed.
	 */
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a {@code UserProjectile} with the specified initial position.
	 *
	 * @param initialXPos the initial X-coordinate of the projectile.
	 * @param initialYPos the initial Y-coordinate of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY);
	}
}
