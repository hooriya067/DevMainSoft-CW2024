package com.example.demo.actors.user;

import com.example.demo.Managers.BulletSystemManager;
import com.example.demo.Managers.SoundManager;
import com.example.demo.actors.active.FighterPlane;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.ProjectileFactory;

/**
 * The {@code UserPlane} class represents the player's plane in the game.
 * It provides movement controls, health management, and firing functionality.
 * This class extends {@link FighterPlane} to inherit base plane behaviors.
 */
public class UserPlane extends FighterPlane {

	/**
	 * The lower boundary for the plane's vertical movement.
	 */
	private final double yLowerBound;

	/**
	 * The height of the toolbar to constrain vertical movement.
	 */
	private final double toolbarHeight;

	/**
	 * The vertical velocity of the plane.
	 */
	private int verticalVelocity;

	/**
	 * The horizontal velocity of the plane.
	 */
	private int horizontalVelocity;

	/**
	 * A multiplier to control the vertical movement direction.
	 */
	private int velocityMultiplier;

	/**
	 * A multiplier to control the horizontal movement direction.
	 */
	private int horizontalVelocityMultiplier;

	/**
	 * Constructs a {@code UserPlane} object with specified parameters.
	 *
	 * @param imageName            the name of the image representing the plane.
	 * @param imageHeight          the height of the plane's image.
	 * @param initialX             the initial X-coordinate of the plane.
	 * @param initialY             the initial Y-coordinate of the plane.
	 * @param initialHealth        the initial health of the plane.
	 * @param yLowerBound          the lower boundary for vertical movement.
	 * @param toolbarHeight        the toolbar height constraining movement.
	 * @param verticalVelocity     the vertical velocity of the plane.
	 * @param horizontalVelocity   the horizontal velocity of the plane.
	 */
	public UserPlane(String imageName, int imageHeight, double initialX, double initialY, int initialHealth,
					 double yLowerBound, double toolbarHeight, int verticalVelocity, int horizontalVelocity) {
		super(imageName, imageHeight, initialX, initialY, initialHealth);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
		this.yLowerBound = yLowerBound;
		this.toolbarHeight = toolbarHeight;
		this.verticalVelocity = verticalVelocity;
		this.horizontalVelocity = horizontalVelocity;
		this.velocityMultiplier = 0;
		this.horizontalVelocityMultiplier = 0;
	}

	/**
	 * Updates the position of the user plane based on movement inputs.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			double initialTranslateX = getTranslateX();

			this.moveVertically(verticalVelocity * velocityMultiplier);
			double newYPosition = getLayoutY() + getTranslateY();
			if (newYPosition < toolbarHeight || newYPosition > yLowerBound) {
				this.setTranslateY(initialTranslateY);
			}

			this.moveHorizontally(horizontalVelocity * horizontalVelocityMultiplier);
			double newXPosition = getLayoutX() + getTranslateX();
			double screenWidth = getScene().getWidth();
			if (newXPosition < 0 || newXPosition > screenWidth - getFitWidth()) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	/**
	 * Updates the state of the user plane, including position updates.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane, reducing the bullet count.
	 *
	 * @return a new {@link ActiveActor} representing the fired projectile, or {@code null} if bullets are unavailable.
	 */
	@Override
	public ActiveActor fireProjectile() {
		SoundManager.getInstance().playSoundEffect("/com/example/demo/sound/userfire.mp3");
		BulletSystemManager bulletManager = BulletSystemManager.getInstance();

		if (bulletManager.subtractBullets(1)) {
			BulletSystemManager.getInstance().incrementBulletsUsed();
			double currentXPosition = getLayoutX() + getTranslateX() + getFitWidth() + 120;
			double currentYPosition = getLayoutY() + getTranslateY() + 20;
			return ProjectileFactory.createProjectile("USER_PROJECTILE", currentXPosition, currentYPosition, (Object) null);
		} else {
			return null;
		}
	}

	/**
	 * Initiates upward movement for the plane.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Initiates downward movement for the plane.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops vertical movement for the plane.
	 */
	public void stopVerticalMovement() {
		velocityMultiplier = 0;
	}

	/**
	 * Initiates leftward movement for the plane.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Initiates rightward movement for the plane.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops horizontal movement for the plane.
	 */
	public void stopHorizontalMovement() {
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Checks whether the plane is currently moving.
	 *
	 * @return {@code true} if the plane is moving, {@code false} otherwise.
	 */
	private boolean isMoving() {
		return velocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}

	/**
	 * Adds health to the plane.
	 *
	 * @param amount the amount of health to add.
	 */
	public void addHealth(int amount) {
		health += amount;
	}

	/**
	 * Retrieves the vertical velocity of the plane.
	 *
	 * @return the vertical velocity of the plane.
	 */
	public double getVerticalVelocity() {
		return verticalVelocity;
	}
}
