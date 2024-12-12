package com.example.demo.actors.active;
/**
 * The {@code FighterPlane} class represents a type of active actor that has health
 * and the ability to fire projectiles. It serves as a base class for specific plane types,
 * such as user planes and enemy planes, providing shared functionality like health
 * management and projectile firing.
 *
 * <p>
 * This class extends {@link ActiveActor} to inherit graphical and movement behaviors
 * and adds specific functionalities like managing health and firing projectiles.
 * </p>
 */
public abstract class FighterPlane extends ActiveActor {

	/**
	 * The current health of the fighter plane.
	 */
	protected int health;

	/**
	 * The horizontal velocity of the fighter plane.
	 */
	private double horizontalVelocity;

	/**
	 * Constructs a {@code FighterPlane} object with the specified parameters.
	 *
	 * @param imageName     the name of the image representing the plane.
	 * @param imageHeight   the height of the plane's image.
	 * @param initialXPos   the initial X-coordinate of the plane.
	 * @param initialYPos   the initial Y-coordinate of the plane.
	 * @param health        the initial health of the plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Retrieves the horizontal velocity of the plane.
	 *
	 * @return the horizontal velocity.
	 */
	public double getHorizontalVelocity() {
		return horizontalVelocity;
	}

	/**
	 * Sets the horizontal velocity of the plane.
	 *
	 * @param horizontalVelocity the new horizontal velocity.
	 */
	public void setHorizontalVelocity(double horizontalVelocity) {
		this.horizontalVelocity = horizontalVelocity;
	}

	/**
	 * Handles the logic for firing a projectile. This method must be implemented
	 * by subclasses to define specific firing behaviors.
	 *
	 * @return an {@link ActiveActor} representing the fired projectile.
	 */
	public abstract ActiveActor fireProjectile();

	/**
	 * Handles damage logic. Reduces the health of the plane and destroys it if health reaches zero.
	 */
	@Override
	public void takeDamage() {
		if (health > 0) {
			health--;
		}
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Retrieves the X-coordinate for the projectile's initial position, offset by a specified amount.
	 *
	 * @param xPositionOffset the offset from the plane's X-coordinate.
	 * @return the calculated X-coordinate for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Retrieves the Y-coordinate for the projectile's initial position, offset by a specified amount.
	 *
	 * @param yPositionOffset the offset from the plane's Y-coordinate.
	 * @return the calculated Y-coordinate for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks whether the plane's health has reached zero.
	 *
	 * @return {@code true} if the health is zero; {@code false} otherwise.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Retrieves the current health of the plane.
	 *
	 * @return the current health.
	 */
	public int getHealth() {
		return health;
	}
}
