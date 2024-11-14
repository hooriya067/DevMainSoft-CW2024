//package com.example.demo;
//
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//import java.util.Objects;

//public class GameOverImage extends ImageView {
//
//	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
//
//	public GameOverImage(double xPosition, double yPosition) {
//		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
//		setLayoutX(xPosition);
//		setLayoutY(yPosition);
//	}
//
//}
package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GameOverImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	private static final double IMAGE_WIDTH = 400;


	public GameOverImage(double screenWidth, double screenHeight) {
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));

		// Resize the image while preserving the original aspect ratio
		setFitWidth(IMAGE_WIDTH);
		setPreserveRatio(true);

		// Set the position to be in the center of the screen
		double xPosition = (screenWidth - getFitWidth()) / 2;
		double yPosition = (screenHeight - getFitHeight()) /5; // Adjusted y-position to bring it slightly upwards

		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}
}


