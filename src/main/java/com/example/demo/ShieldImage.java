package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 50;

	public ShieldImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	public void showShield() {
		this.setVisible(true);
		this.toFront(); // Ensure the shield is rendered above other elements
	}
	public void hideShield() {
		this.setVisible(false);
	}

	public void updatePosition(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
//	public static int getShieldSize() {
//		return SHIELD_SIZE;
//	}

}
