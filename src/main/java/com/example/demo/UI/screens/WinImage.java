package com.example.demo.UI.screens;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class WinImage extends Pane {

	private static final String IMAGE_NAME = "/com/example/demo/images/winimage.png";
	private static final double IMAGE_WIDTH = 500;
	private static final double IMAGE_HEIGHT = 800;

	public WinImage(double screenWidth, double screenHeight) {
		Stage stage = StageManager.getStage();

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

		// Display Final Stars
		int finalStars = StarManager.getInstance().calculateFinalStars();
		StarDisplay starDisplay = new StarDisplay(screenWidth / 2 - 125, screenHeight / 1.5, finalStars);

		PlayAgainButton playAgainButton = new PlayAgainButton();
		playAgainButton.getButton().setLayoutX((screenWidth / 2) - 600); // Set X position
		playAgainButton.getButton().setLayoutY((screenHeight / 2) + 170); // Set Y position

		QuitButton quitButton = new QuitButton();
		quitButton.getButton().setLayoutX((screenWidth / 2) + 300);
		quitButton.getButton().setLayoutY((screenHeight / 2) + 170);

		playAgainButton.setOnPlayAgain(() -> {
			try {
				SoundManager.getInstance().playBackgroundMusic("/com/example/demo/sound/background2.mp3");
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
		getChildren().addAll( winImage, starDisplay.getContainer(), playAgainButton.getButton(), quitButton.getButton());


		// Bring the Win Image to the front
		winImage.toFront();
		this.setVisible(true);
	}
}
