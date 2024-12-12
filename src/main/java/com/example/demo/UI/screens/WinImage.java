
package com.example.demo.UI.screens;

import com.example.demo.Managers.BulletSystemManager;
import com.example.demo.Managers.SoundManager;
import com.example.demo.Managers.StarManager;
import com.example.demo.UI.buttons.PlayAgainButton;
import com.example.demo.UI.buttons.QuitButton;
import com.example.demo.actors.collectibles.StarDisplay;
import com.example.demo.controller.Controller;
import com.example.demo.core.StageManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;
/**
 * The {@link WinImage} class represents the screen displayed when the player wins the game.
 * It includes the victory image, final stars display, and action buttons for replaying the game or quitting.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Displays a win image centered on the screen.</li>
 *     <li>Shows the player's final star count, calculated by {@link StarManager}.</li>
 *     <li>Provides buttons for replaying the game or exiting the application.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link StarManager}: Used to calculate the player's final stars.</li>
 *     <li>{@link PlayAgainButton}: Button for replaying the game from the beginning.</li>
 *     <li>{@link QuitButton}: Button for closing the application.</li>
 *     <li>{@link Controller}: Handles the initialization and launching of the game.</li>
 *     <li>{@link SoundManager}: Plays background music for the win screen.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>{@code
 * Stage stage = StageManager.getStage();
 * WinImage winImage = new WinImage(stage.getWidth(), stage.getHeight());
 * stage.getScene().setRoot(winImage);
 * }</pre>
 */	/**
	 * The {@code WinImage} class represents a visual overlay displayed when the player wins the game.
	 * It contains a congratulatory image that is displayed on the screen.
	 */
	public class WinImage extends Pane {

		/**
		 * The path to the image resource used as the win overlay.
		 */
		private static final String IMAGE_NAME = "/com/example/demo/images/winimage.png";

		/**
		 * The width of the win image in pixels.
		 */
		private static final double IMAGE_WIDTH = 500;

		/**
		 * The height of the win image in pixels.
		 */
		private static final double IMAGE_HEIGHT = 800;

		// Constructor and methods here


	/**
	 * Constructs a {@code WinImage} object and initializes its UI elements.
	 *
	 * @param screenWidth  the width of the screen
	 * @param screenHeight the height of the screen
	 */
	public WinImage(double screenWidth, double screenHeight) {
		Stage stage = StageManager.getStage();

		// Create and position the win image
		ImageView winImage = new ImageView(
				new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm())
		);
		winImage.setFitWidth(IMAGE_WIDTH);
		winImage.setFitHeight(IMAGE_HEIGHT);
		winImage.setPreserveRatio(true);

		double winImageX = (screenWidth - IMAGE_WIDTH) / 2;
		double winImageY = screenHeight / 70;
		winImage.setLayoutX(winImageX);
		winImage.setLayoutY(winImageY);

		// Display the final stars earned
		int finalStars = StarManager.getInstance().calculateFinalStars();
		StarDisplay starDisplay = new StarDisplay(screenWidth / 2 - 125, screenHeight / 1.5, finalStars);

		// Create the "Play Again" button
		PlayAgainButton playAgainButton = new PlayAgainButton();
		playAgainButton.getButton().setLayoutX((screenWidth / 2) - 600); // Set X position
		playAgainButton.getButton().setLayoutY((screenHeight / 2) + 170); // Set Y position

		// Create the "Quit" button
		QuitButton quitButton = new QuitButton();
		quitButton.getButton().setLayoutX((screenWidth / 2) + 300);
		quitButton.getButton().setLayoutY((screenHeight / 2) + 170);

		// Define actions for the buttons
		playAgainButton.setOnPlayAgain(() -> {
			try {
				BulletSystemManager.getInstance().setBullets(120); // Reset bullet count
				Controller gameController = new Controller(stage);
				gameController.launchGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		quitButton.setOnClick(stage::close);

		// Add all elements to the Pane
		getChildren().addAll(winImage, starDisplay.getContainer(), playAgainButton.getButton(), quitButton.getButton());

		// Bring the Win Image to the front
		winImage.toFront();
		this.setVisible(true);
	}
}
