package com.example.demo.actors.active;

import com.example.demo.actors.active.destructible.Destructible;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public abstract class ActiveActor extends ImageView implements Destructible {

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";
	private boolean isDestroyed;
	private final String imageName; // Add a field for imageName

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.imageName = imageName; // Assign imageName
		try {
			this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Image resource for " + imageName + " not found. Check the file path.");
		}
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
		this.isDestroyed = false; // Initialize as not destroyed
	}

	public String getImageName() { // Add a getter for imageName
		return imageName;
	}

	public abstract void updatePosition(); // Movement logic specific to subclasses
	public abstract void updateActor();

	public void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	public void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	@Override
	public void takeDamage() {
		destroy(); // Default implementation for taking damage
	}

	@Override
	public void destroy() {
		isDestroyed = true;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}
}
