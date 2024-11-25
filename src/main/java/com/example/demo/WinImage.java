package com.example.demo;

import com.example.demo.controller.Controller;
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
		// Create the Win Image
		ImageView winImage = new ImageView(
				new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm())
		);
		winImage.setFitWidth(IMAGE_WIDTH);
		winImage.setFitHeight(IMAGE_HEIGHT);
		winImage.setPreserveRatio(true);

		// Shift Win Image upwards
		double winImageX = (screenWidth - IMAGE_WIDTH) / 2;
		double winImageY = screenHeight / 15; // Move further up
		winImage.setLayoutX(winImageX);
		winImage.setLayoutY(winImageY);

		// Create Play Again Button
		PlayAgainButton playAgainButton = new PlayAgainButton(
//				(screenWidth / 2) - 400, // Left position
//				(screenHeight / 2) + 150 // Downward shift
				(screenWidth / 2) - 600,
				(screenHeight / 2) + 170
		);

		// Create Quit Button
		QuitButton quitButton = new QuitButton();
		ImageView quitButtonImage = quitButton.getQuitButtonImage();

		// Adjust Quit Button position (right of Play Again button)
//		double quitX = (screenWidth / 2) + 100; // Right position
//		double quitY = (screenHeight / 2) + 150; // Same vertical position as Play Again button
		double quitX = (screenWidth / 2) + 300; // Adjusted spacing for right alignment
		double quitY = (screenHeight / 2) + 170; // Same vertical position as Play Again button

		quitButtonImage.setLayoutX(quitX);
		quitButtonImage.setLayoutY(quitY);

		// Add functionality to buttons
		playAgainButton.setOnPlayAgain(() -> {
			try {
				Stage stage = (Stage) this.getScene().getWindow(); // Dynamically retrieve the Stage
				Controller gameController = new Controller(stage);
				gameController.launchGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		quitButtonImage.setOnMouseClicked(event -> {
			Stage stage = (Stage) this.getScene().getWindow(); // Dynamically retrieve the Stage
			stage.close(); // Exit the application
		});

		// Add all elements to the Pane
		this.getChildren().addAll(winImage, playAgainButton, quitButtonImage);

		// Bring the Win Image to the front
		winImage.toFront();
		this.setVisible(true);
	}
}
