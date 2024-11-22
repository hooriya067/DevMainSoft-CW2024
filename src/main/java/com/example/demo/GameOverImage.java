package com.example.demo;

import com.example.demo.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;

public class GameOverImage extends Pane {

	private static final String IMAGE_NAME = "/com/example/demo/images/gameover1.png";
	private static final double IMAGE_WIDTH = 600;

	public GameOverImage(double screenWidth, double screenHeight) {
		// Create a dim background rectangle
		Rectangle dimBackground = new Rectangle(screenWidth, screenHeight, Color.BLACK);
		dimBackground.setOpacity(0.5); // Set transparency for dim effect

		// Create Game Over Image
		ImageView gameOverImage = new ImageView(
				new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm())
		);
		gameOverImage.setFitWidth(IMAGE_WIDTH);
		gameOverImage.setPreserveRatio(true);

		// Adjusted position of Game Over image (move further up)
		double gameOverX = (screenWidth - IMAGE_WIDTH) / 2;
		double gameOverY = screenHeight / 15; // Reduced Y value to move it higher
		gameOverImage.setLayoutX(gameOverX);
		gameOverImage.setLayoutY(gameOverY);

		// Create Play Again Button
		PlayAgainButton playAgainButton = new PlayAgainButton(
				(screenWidth / 2) - 600, // Adjusted spacing for left alignment
				(screenHeight / 2) + 170 // Shifted further down
		);

	// Set action for Play Again button
		playAgainButton.setOnPlayAgain(() -> {
			try {
				// Restart the game using Controller logic
				Controller gameController = new Controller((Stage) this.getScene().getWindow());
				gameController.launchGame(); // Launch the game from Level 1
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// Add Quit Button
		QuitButton quitButton = new QuitButton();
		ImageView quitButtonImage = quitButton.getQuitButtonImage();

		// Adjust Quit Button position
		double quitX = (screenWidth / 2) + 300; // Adjusted spacing for right alignment
		double quitY = (screenHeight / 2) + 170; // Same vertical position as Play Again button

		quitButtonImage.setLayoutX(quitX);
		quitButtonImage.setLayoutY(quitY);

		// Dynamically get the Stage from the Scene
		quitButtonImage.setOnMouseClicked(event -> {
			Stage stage = (Stage) this.getScene().getWindow(); // Retrieve the Stage
			stage.close(); // Close the application
		});

		// Add all elements to the Pane in the correct order
		this.getChildren().addAll(dimBackground, gameOverImage, playAgainButton, quitButtonImage);

		// Bring Game Over image to the front
		gameOverImage.toFront();
	}
}
