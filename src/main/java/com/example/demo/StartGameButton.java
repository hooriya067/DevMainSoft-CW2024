package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class StartGameButton {
    private final Button startButton;

    public StartGameButton() {
        // Load the start button image
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/start_icon.png")).toExternalForm());
        ImageView startButtonImage = new ImageView(image);
        startButtonImage.setFitWidth(300);
        startButtonImage.setPreserveRatio(true);
        startButtonImage.setPickOnBounds(true);  // Allow clicks even in transparent areas

        // Create a button and set the graphic as the image
        startButton = new Button();
        startButton.setGraphic(startButtonImage);
        startButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");  // Transparent background and hand cursor for hover
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setOnStartGame(Runnable action) {
        startButton.setOnAction(event -> action.run());
    }
}
