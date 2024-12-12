/**
 * The HeartDisplay class manages the display of heart images on the game screen
 * to represent the player's health. It provides functionality for initializing,
 * adding, and removing heart images dynamically.
 *
 * <p>
 * The heart images are displayed in an {@code HBox} container positioned at
 * specified coordinates on the screen. This class supports operations to
 * visually update the player's health by adding or removing hearts from the
 * container.
 * </p>
 *
 * @since 1.0
 */
package com.example.demo.actors.collectibles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;
/**
 * The {@code HeartDisplay} class manages the display of heart images on the game screen
 * to visually represent the player's health. It allows for dynamic updates by adding or
 * removing heart images as the player's health changes during gameplay.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Displays heart icons using an {@code HBox} container.</li>
 *     <li>Supports adding hearts to increase health.</li>
 *     <li>Supports removing hearts to decrease health.</li>
 *     <li>Provides customizable initial position and number of hearts.</li>
 * </ul>
 *
 * <p><b>Constants:</b></p>
 * <ul>
 *     <li>{@code HEART_IMAGE_NAME}: Specifies the file path for the heart image resource.</li>
 *     <li>{@code HEART_HEIGHT}: Defines the height of each heart icon for consistent scaling.</li>
 *     <li>{@code INDEX_OF_FIRST_ITEM}: Specifies the index of the first heart in the container for removal operations.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link HBox}: Used as the container for arranging heart images.</li>
 *     <li>{@link ImageView}: Used for rendering the heart images.</li>
 * </ul>
 *
 * @since 1.0
 */

public class HeartDisplay {

	/**
	 * Path to the heart image resource.
	 */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	/**
	 * Height of each heart image.
	 */
	private static final int HEART_HEIGHT = 50;

	/**
	 * Index of the first heart in the container (used for removal operations).
	 */
	private static final int INDEX_OF_FIRST_ITEM = 0;

	/**
	 * Container for displaying heart images.
	 */
	private HBox container;

	/**
	 * X-coordinate for positioning the container.
	 */
	private final double containerXPosition;

	/**
	 * Y-coordinate for positioning the container.
	 */
	private final double containerYPosition;

	/**
	 * Total number of hearts to display initially.
	 */
	private int numberOfHeartsToDisplay;

	/**
	 * Constructs a HeartDisplay object and initializes it with the given parameters.
	 *
	 * @param xPosition the X-coordinate for positioning the heart container.
	 * @param yPosition the Y-coordinate for positioning the heart container.
	 * @param heartsToDisplay the initial number of hearts to display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the {@code HBox} container for the hearts.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Populates the container with the initial number of hearts.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes a heart from the container to visually decrease the player's health.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
		}
	}

	/**
	 * Adds a new heart to the container to visually increase the player's health.
	 */
	public void addHeart() {
		ImageView newHeart = new ImageView(new Image(
				Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()
		));

		newHeart.setFitHeight(HEART_HEIGHT);
		newHeart.setPreserveRatio(true);

		container.getChildren().add(newHeart);

		// Update the number of hearts being displayed
		numberOfHeartsToDisplay++;
	}

	/**
	 * Retrieves the {@code HBox} container holding the heart images.
	 *
	 * @return the heart container.
	 */
	public HBox getContainer() {
		return container;
	}
}
