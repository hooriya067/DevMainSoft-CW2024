package com.example.demo.actors.active.projectiles;


import com.example.demo.actors.active.ActiveActor;
import com.example.demo.core.GameStateManager;

/**
 * The {@code ProjectileParent} class serves as a base class for all projectiles in the game.
 * It provides shared behavior for movement and destruction while allowing specific projectile types
 * to inherit and customize their functionality.
 * <p>
 * This class extends {@link ActiveActor} and includes horizontal velocity for consistent movement
 * across the screen.
 * </p>
 */
public class ProjectileParent extends ActiveActor {

	/**
	 * The horizontal velocity of the projectile, determining its movement speed.
	 */
	private final double horizontalVelocity;

	/**
	 * Constructs a {@code ProjectileParent} object with the specified image, dimensions, position, and velocity.
	 *
	 * @param imageName          the name of the image file representing the projectile.
	 * @param imageHeight        the height of the projectile image in pixels.
	 * @param initialXPos        the initial X-coordinate of the projectile.
	 * @param initialYPos        the initial Y-coordinate of the projectile.
	 * @param horizontalVelocity the horizontal velocity of the projectile.
	 */
	public ProjectileParent(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.horizontalVelocity = horizontalVelocity;
	}

	/**
	 * Handles the logic for taking damage. By default, a projectile is destroyed upon taking damage.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile by moving it horizontally based on its velocity.
	 * If the game is paused, the position is not updated.
	 */
	@Override
	public void updatePosition() {
		if (GameStateManager.getInstance().isGamePaused()) {
			return;
		}
		moveHorizontally(horizontalVelocity);
	}

	/**
	 * Updates the state of the projectile by invoking its position update logic.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
