
package com.example.demo.UI.screens;

import com.example.demo.Managers.BulletSystemManager;
import com.example.demo.UI.buttons.PlayAgainButton;
import com.example.demo.UI.buttons.QuitButton;
import com.example.demo.controller.Controller;
import com.example.demo.core.StageManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;
/**
 * The {@code GameOverImage} class represents a graphical overlay displayed when the game is over.
 * It includes a "Game Over" image, a dimmed background, and buttons for "Play Again" and "Quit".
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Displays a visually appealing "Game Over" image with a dimmed background.</li>
 *     <li>Includes a "Play Again" button that resets the game to Level 1 with bullet count reset.</li>
 *     <li>Provides a "Quit" button to close the game application.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link PlayAgainButton}: Used for restarting the game.</li>
 *     <li>{@link QuitButton}: Used for exiting the game.</li>
 *     <li>{@link BulletSystemManager}: Resets the bullet count when restarting the game.</li>
 *     <li>{@link Controller}: Handles the game flow when restarting.</li>
 *     <li>{@link StageManager}: Provides the {@link javafx.stage.Stage} for the game window.</li>
 * </ul>
 */
public class GameOverScreen extends Pane {

	/**
	 * Path to the "Game Over" image resource.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Width of the "Game Over" image.
	 */
	private static final double IMAGE_WIDTH = 600;

	/**
	 * Constructs a {@code GameOverImage} and initializes its components.
	 *
	 * @param screenWidth  the width of the screen
	 * @param screenHeight the height of the screen
	 */
	public GameOverScreen(double screenWidth, double screenHeight) {

		// Create a dimmed background
		Rectangle dimBackground = new Rectangle(screenWidth, screenHeight, Color.BLACK);
		dimBackground.setOpacity(0.7); // Set transparency for dim effect

		// Create Game Over Image
		ImageView gameOverImage = new ImageView(
				new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm())
		);
		gameOverImage.setFitWidth(IMAGE_WIDTH);
		gameOverImage.setPreserveRatio(true);

		// Center the "Game Over" image on the screen
		double gameOverX = (screenWidth - IMAGE_WIDTH) / 2;
		double gameOverY = screenHeight / 200;
		gameOverImage.setLayoutX(gameOverX);
		gameOverImage.setLayoutY(gameOverY);

		// Create Play Again Button
		PlayAgainButton playAgainButton = new PlayAgainButton();
		playAgainButton.getButton().setLayoutX((screenWidth / 2) - 600); // Set X position
		playAgainButton.getButton().setLayoutY((screenHeight / 2) + 170); // Set Y position

		// Set action for Play Again button
		playAgainButton.setOnPlayAgain(() -> {
			try {
				BulletSystemManager.getInstance().setBullets(120); // Reset bullet count
				Stage stage = StageManager.getStage();
				Controller gameController = new Controller(stage);
				gameController.launchGame(); // Launch the game from Level 1
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// Add Quit Button
		QuitButton quitButton = new QuitButton();
		quitButton.getButton().setLayoutX((screenWidth / 2) + 300); // Set X position
		quitButton.getButton().setLayoutY((screenHeight / 2) + 170); // Set Y position

		// Set action for Quit button
		quitButton.setOnClick(() -> {
			Stage stage = (Stage) this.getScene().getWindow(); // Dynamically retrieve the Stage
			stage.close(); // Exit the application
		});

		// Add all elements to the Pane in the correct order
		this.getChildren().addAll(dimBackground, gameOverImage, playAgainButton.getButton(), quitButton.getButton());

		// Bring Game Over image to the front
		gameOverImage.toFront();
	}
}
