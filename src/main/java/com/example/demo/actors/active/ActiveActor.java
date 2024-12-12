package com.example.demo.actors.active;

import com.example.demo.actors.active.destructible.Destructible;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The {@code ActiveActor} class serves as a base class for all interactive game elements
 * that are graphically represented and can be updated, such as user planes, enemies,
 * and projectiles. It includes fundamental properties like image loading and movement.
 * This class implements {@link Destructible} to handle object destruction.
 *
 * <p>
 * Note: The previously used {@code ActiveActorDestructible} class has been merged into
 * this class to streamline functionality by combining graphical behaviors and destructible
 * capabilities into a single, reusable abstraction.
 * </p>
 */
public abstract class ActiveActor extends ImageView implements Destructible {

	/**
	 * The base directory for all image resources used by active actors.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * A flag indicating whether the actor has been destroyed.
	 */
	private boolean isDestroyed;

	/**
	 * The name of the image representing this actor.
	 */
	private final String imageName;

	/**
	 * Constructs an {@code ActiveActor} object with the specified image, dimensions, and position.
	 *
	 * @param imageName    the name of the image file representing this actor.
	 * @param imageHeight  the height of the image in pixels.
	 * @param initialXPos  the initial X-coordinate of the actor.
	 * @param initialYPos  the initial Y-coordinate of the actor.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.imageName = imageName;
		try {
			this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Image resource for " + imageName + " not found. Check the file path.");
		}
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
		this.isDestroyed = false;
	}

	/**
	 * Retrieves the name of the image file associated with this actor.
	 *
	 * @return the image name.
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * Updates the position of the actor based on its specific movement logic.
	 * This method is abstract and must be implemented by subclasses.
	 */
	public abstract void updatePosition();

	/**
	 * Updates the state of the actor, including position and any additional behaviors.
	 * This method is abstract and must be implemented by subclasses.
	 */
	public abstract void updateActor();

	/**
	 * Moves the actor horizontally by a specified amount.
	 *
	 * @param horizontalMove the amount to move horizontally.
	 */
	public void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified amount.
	 *
	 * @param verticalMove the amount to move vertically.
	 */
	public void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	/**
	 * Marks the actor as destroyed. This default implementation sets the {@code isDestroyed} flag to {@code true}.
	 */
	@Override
	public void destroy() {
		isDestroyed = true;
	}

	/**
	 * Determines whether the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed; {@code false} otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Handles the logic for taking damage. By default, it destroys the actor.
	 */
	@Override
	public void takeDamage() {
		destroy();
	}
}