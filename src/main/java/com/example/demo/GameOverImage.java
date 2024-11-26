package com.example.demo;

import com.example.demo.controller.Controller;
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

		double gameOverX = (screenWidth - IMAGE_WIDTH) / 2;
		double gameOverY = screenHeight / 15;
		gameOverImage.setLayoutX(gameOverX);
		gameOverImage.setLayoutY(gameOverY);

		// Create Play Again Button
		PlayAgainButton playAgainButton = new PlayAgainButton();
		playAgainButton.getButton().setLayoutX((screenWidth / 2) - 600); // Set X position
		playAgainButton.getButton().setLayoutY((screenHeight / 2) + 170); // Set Y position

		// Set action for Play Again button
		playAgainButton.setOnPlayAgain(() -> {
			try {
				// Restart the game using Controller logic
				Stage stage = (Stage) this.getScene().getWindow();
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
