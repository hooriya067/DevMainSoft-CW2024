package com.example.demo.UI.screens;

import com.example.demo.UI.buttons.PlayAgainButton;
import com.example.demo.UI.buttons.QuitButton;
import com.example.demo.controller.Controller;
import com.example.demo.core.StageManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class WinImage extends Pane {

	private static final String IMAGE_NAME = "/com/example/demo/images/winimage.png";
	private static final double IMAGE_WIDTH = 600;
	private static final double IMAGE_HEIGHT = 500;

	public WinImage(double screenWidth, double screenHeight) {
		Stage stage = StageManager.getStage();
		// Create the Win Image
		ImageView winImage = new ImageView(
				new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm())
		);
		winImage.setFitWidth(IMAGE_WIDTH);
		winImage.setFitHeight(IMAGE_HEIGHT);
		winImage.setPreserveRatio(true);

		// Position the Win Image
		double winImageX = (screenWidth - IMAGE_WIDTH) / 2;
		double winImageY = screenHeight / 15;
		winImage.setLayoutX(winImageX);
		winImage.setLayoutY(winImageY);

		// Create and position Play Again Button
		PlayAgainButton playAgainButton = new PlayAgainButton();
		playAgainButton.getButton().setLayoutX((screenWidth / 2) - 600); // Set X position
		playAgainButton.getButton().setLayoutY((screenHeight / 2) + 170); // Set Y position

		// Create and position Quit Button
		QuitButton quitButton = new QuitButton();
		quitButton.getButton().setLayoutX((screenWidth / 2) + 300);
		quitButton.getButton().setLayoutY((screenHeight / 2) + 170);

		// Add functionality to buttons
		playAgainButton.setOnPlayAgain(() -> {
			try {

				Controller gameController = new Controller(stage);
				gameController.launchGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		quitButton.setOnClick(() -> {
			stage.close(); // Exit the application
		});

		// Add all elements to the Pane
		getChildren().addAll(winImage, playAgainButton.getButton(), quitButton.getButton());

		// Bring the Win Image to the front
		winImage.toFront();
		this.setVisible(true);
	}
}