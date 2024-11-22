package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PlayAgainButton extends ImageView {

    private static final String IMAGE_NAME = "/com/example/demo/images/playagain.png";
    private static final double BUTTON_WIDTH = 300;

    public PlayAgainButton(double xPosition, double yPosition) {
        setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));

        // Resize the button image
        setFitWidth(BUTTON_WIDTH);
        setPreserveRatio(true);

        // Set the button's position
        setLayoutX(xPosition);
        setLayoutY(yPosition);
    }
    // Method to set the action for Play Again
    public void setOnPlayAgain(Runnable action) {
        this.setOnMouseClicked(event -> action.run());
    }
}

