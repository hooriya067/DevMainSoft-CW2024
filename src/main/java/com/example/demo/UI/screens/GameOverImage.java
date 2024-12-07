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

public class GameOverImage extends Pane {
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	private static final double IMAGE_WIDTH = 600;

	public GameOverImage(double screenWidth, double screenHeight) {

		Rectangle dimBackground = new Rectangle(screenWidth, screenHeight, Color.BLACK);
		dimBackground.setOpacity(0.7); // Set transparency for dim effect

		// Create Game Over Image
		ImageView gameOverImage = new ImageView(
				new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm())
		);
		gameOverImage.setFitWidth(IMAGE_WIDTH);
		gameOverImage.setPreserveRatio(true);

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
				BulletSystemManager.getInstance().setBullets(100);//putting bullet count back
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
