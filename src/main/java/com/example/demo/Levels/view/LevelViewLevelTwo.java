/**
 * The {@code LevelViewLevelTwo} class extends {@link LevelViewParent} to provide specific
 * UI functionalities and visual elements for Level Two of the game.
 *
 * <p>This class introduces new elements such as a shield image, methods to handle
 * the shield's visibility and position, and an alert system for the shield activation.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link LevelViewParent}: Provides the foundational UI management for all levels.</li>
 *     <li>{@link ShieldImage}: Represents the visual component for the shield collectible.</li>
 *     <li>{@link AlertManager}: Displays alerts to inform the player of in-game events like shield activation.</li>
 *     <li>{@link LevelParent}: The parent level class to which this UI is associated.</li>
 * </ul>
 */
package com.example.demo.Levels.view;

import com.example.demo.Managers.AlertManager;
import com.example.demo.actors.collectibles.ShieldImage;
import com.example.demo.Levels.LevelParent;
import javafx.scene.Group;

/**
 * Represents the UI elements and functionalities specific to Level Two.
 */
public class LevelViewLevelTwo extends LevelViewParent {

	/**
	 * X-position for the shield image.
	 */
	private static final int SHIELD_X_POSITION = 1150;

	/**
	 * Y-position for the shield image.
	 */
	private static final int SHIELD_Y_POSITION = 500;

	/**
	 * The root group to which UI elements are added.
	 */
	private final Group root;

	/**
	 * The shield image displayed on the screen.
	 */
	private final ShieldImage shieldImage;

	/**
	 * The width of the screen.
	 */
	private final double screenWidth;

	/**
	 * The height of the screen.
	 */
	private final double screenHeight;

	/**
	 * Flag to track if the shield activation message has been displayed.
	 */
	private boolean shieldMessageDisplayed = false;

	/**
	 * Constructs a LevelViewLevelTwo instance with the specified parameters.
	 *
	 * @param root            the root group for UI elements
	 * @param heartsToDisplay the number of hearts to display
	 * @param screenWidth     the width of the screen
	 * @param screenHeight    the height of the screen
	 * @param levelParent     reference to the parent level
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay, double screenWidth, double screenHeight, LevelParent levelParent) {
		super(root, heartsToDisplay, levelParent);
		this.root = root;
		this.screenWidth = getLevelParent().getScreenWidth();
		this.screenHeight = screenHeight;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);

		addImagesToRoot();
	}

	/**
	 * Adds the shield image to the root group.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	/**
	 * Initializes parameters for winning the level. (Currently empty for Level Two)
	 */
	@Override
	protected void initializeWinningParameter() {
		// No specific winning parameters for Level Two.
	}

	/**
	 * Updates the parameters for winning the level during gameplay. (Currently empty for Level Two)
	 */
	@Override
	public void updateWinningParameter() {
		// No specific updates for winning parameters in Level Two.
	}

	/**
	 * Displays the shield image on the screen and shows an alert if not already displayed.
	 */
	public void showShield() {
		shieldImage.showShield();

		if (!shieldMessageDisplayed) {
			AlertManager.getInstance().showInfoAlert(
					"Shield Activated!",
					screenWidth,
					screenHeight
			);
			shieldMessageDisplayed = true;
		}
	}

	/**
	 * Hides the shield image from the screen and resets the alert flag.
	 */
	public void hideShield() {
		shieldImage.hideShield();
		shieldMessageDisplayed = false;
	}

	/**
	 * Updates the shield image's position on the screen.
	 *
	 * @param x the new X-coordinate for the shield image
	 * @param y the new Y-coordinate for the shield image
	 */
	public void updateShieldPosition(double x, double y) {
		shieldImage.updatePosition(x, y);
	}
}
