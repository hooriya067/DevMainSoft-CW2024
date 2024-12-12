package com.example.demo.actors.collectibles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The {@code ShieldImage} class represents a shield image in the game that can be displayed, hidden, or updated dynamically.
 * This class extends {@link ImageView} to allow graphical representation of the shield.
 */
public class ShieldImage extends ImageView {

	/**
	 * The file path to the shield image resource.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/shield2.png";

	/**
	 * The size of the shield image in pixels.
	 */
	private static final int SHIELD_SIZE = 70;

	/**
	 * Constructs a {@code ShieldImage} object at a specified position.
	 *
	 * @param xPosition the initial X-coordinate of the shield.
	 * @param yPosition the initial Y-coordinate of the shield.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Makes the shield image visible and brings it to the front.
	 */
	public void showShield() {
		this.setVisible(true);
		this.toFront();
	}

	/**
	 * Hides the shield image, making it invisible.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

	/**
	 * Updates the position of the shield image to new coordinates.
	 *
	 * @param xPosition the new X-coordinate of the shield.
	 * @param yPosition the new Y-coordinate of the shield.
	 */
	public void updatePosition(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
}
