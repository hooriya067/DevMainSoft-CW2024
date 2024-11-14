package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youwin1.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setFocusTraversable(true); // Allow WinImage to be focusable
	}

	public void showWinImage() {
		this.setVisible(true);
		this.requestFocus(); // Request focus to allow key events to be captured
	}
}
