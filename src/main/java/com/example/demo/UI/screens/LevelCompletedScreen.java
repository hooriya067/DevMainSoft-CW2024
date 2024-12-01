package com.example.demo.UI.screens;

import com.example.demo.UI.buttons.NextButton;
import com.example.demo.UI.buttons.PlayAgainButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class LevelCompletedScreen extends Pane {

    private static final String IMAGE_NAME = "/com/example/demo/images/levelcompleted.png";
    private static final double IMAGE_WIDTH = 500; // Adjusted size for the Level Completed image
    private static final double IMAGE_Y_OFFSET = 100; // Move image upwards

    public LevelCompletedScreen(double screenWidth, double screenHeight, Runnable onNextLevel, Runnable onReplayLevel) {


        Rectangle dimBackground = new Rectangle(screenWidth, screenHeight, Color.BLACK);
        dimBackground.setOpacity(0.5); // Semi-transparent black for overlay effect

        // Create Level Completed image
        ImageView levelCompletedImage = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm())
        );
        levelCompletedImage.setFitWidth(IMAGE_WIDTH);
        levelCompletedImage.setPreserveRatio(true);

        // Center the image and shift upwards
        double imageX = (screenWidth - IMAGE_WIDTH) / 2;
        double imageY = (screenHeight / 4) - IMAGE_Y_OFFSET; // Adjusted position
        levelCompletedImage.setLayoutX(imageX);
        levelCompletedImage.setLayoutY(imageY);

        // Add Next Button
        NextButton nextButton = new NextButton();
        nextButton.getButton().setPrefWidth(200); // Standardized button size
        nextButton.getButton().setPrefHeight(70);
        nextButton.getButton().setLayoutX((screenWidth / 2) -600); // Adjust X position for Next button
        nextButton.getButton().setLayoutY((screenHeight / 2) + 115); // Adjust Y position for placement
        nextButton.setOnClick(onNextLevel); // Set action to go to the next level

        // Add Replay Button
        PlayAgainButton replayButton = new PlayAgainButton();
        replayButton.getButton().setPrefWidth(200); // Standardized button size
        replayButton.getButton().setPrefHeight(70);
        replayButton.getButton().setLayoutX((screenWidth / 2) + 300); // Adjust X position for Replay button
        replayButton.getButton().setLayoutY((screenHeight / 2) + 170); // Adjust Y position for placement
        replayButton.setOnClick(onReplayLevel); // Set action to replay the current level


        this.getChildren().addAll(dimBackground, levelCompletedImage, nextButton.getButton(), replayButton.getButton());

        levelCompletedImage.toFront();
    }
}
