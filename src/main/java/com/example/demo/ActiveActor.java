package com.example.demo;

import javafx.scene.image.*;

import java.util.Objects;

public abstract class ActiveActor extends ImageView {
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		// Ensure that the image name provided actually resolves to a valid resource
		try {
			this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Image resource for " + imageName + " not found. Check the file path.");
		}
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}


	public abstract void updatePosition();

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
