/**
 * The {@code LevelCompletedScreen} class represents the UI screen displayed upon completing a game level.
 * It provides visual feedback, including the number of stars earned for the level, and buttons to proceed
 * to the next level or replay the current one.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Dimmed background to emphasize the level completion details.</li>
 *     <li>Display of a "Level Completed" image and a star rating.</li>
 *     <li>Buttons for proceeding to the next level or replaying the current level.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link StarManager}: Manages the star ratings for levels.</li>
 *     <li>{@link StarDisplay}: Displays the earned stars for the level.</li>
 *     <li>{@link NextButton}: Button to proceed to the next level.</li>
 *     <li>{@link PlayAgainButton}: Button to replay the current level.</li>
 *     <li>{@link LevelManager}: Provides information about the current level.</li>
 * </ul>
 */
package com.example.demo.UI.screens;

import com.example.demo.Managers.StarManager;
import com.example.demo.UI.buttons.NextButton;
import com.example.demo.UI.buttons.PlayAgainButton;
import com.example.demo.actors.collectibles.StarDisplay;
import com.example.demo.core.StageManager;
import com.example.demo.Managers.LevelManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;

public class LevelCompletedScreen extends Pane {

    private static final String IMAGE_NAME = "/com/example/demo/images/levelcompleted.png";
    private static final double IMAGE_WIDTH = 550;
    private static final double IMAGE_Y_OFFSET = 200;

    /**
     * Constructs a {@code LevelCompletedScreen} with visual elements and buttons for navigation.
     *
     * @param screenWidth   the width of the screen
     * @param screenHeight  the height of the screen
     * @param onNextLevel   the action to execute when proceeding to the next level
     * @param onReplayLevel the action to execute when replaying the current level
     */
    public LevelCompletedScreen(double screenWidth, double screenHeight, Runnable onNextLevel, Runnable onReplayLevel) {
        Stage stage = StageManager.getStage();
        LevelManager levelManager = StageManager.getLevelManager();
        String currentLevelName = levelManager.getCurrentLevelName();

        // Create a dimmed background
        Rectangle dimBackground = new Rectangle(screenWidth, screenHeight, Color.BLACK);
        dimBackground.setOpacity(0.7);

        // Add "Level Completed" image
        ImageView levelCompletedImage = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm())
        );
        levelCompletedImage.setFitWidth(IMAGE_WIDTH);
        levelCompletedImage.setPreserveRatio(true);

        // Center the image and shift it upwards
        double imageX = (screenWidth - IMAGE_WIDTH) / 2;
        double imageY = (screenHeight / 4) - IMAGE_Y_OFFSET;
        levelCompletedImage.setLayoutX(imageX);
        levelCompletedImage.setLayoutY(imageY);

        // Display the number of stars earned for the current level
        int starsEarned = StarManager.getInstance().getLevelStarsMap().getOrDefault(currentLevelName, 0);
        StarDisplay starDisplay = new StarDisplay(stage.getWidth() / 2 - 125, stage.getHeight() / 1.35, starsEarned);

        // Create the "Next Level" button
        NextButton nextButton = new NextButton();
        nextButton.getButton().setPrefWidth(200);
        nextButton.getButton().setPrefHeight(70);
        nextButton.getButton().setLayoutX((screenWidth / 2) - 600);
        nextButton.getButton().setLayoutY((screenHeight / 2) + 115);
        nextButton.setOnClick(onNextLevel);

        // Create the "Replay Level" button
        PlayAgainButton replayButton = new PlayAgainButton();
        replayButton.getButton().setPrefWidth(200);
        replayButton.getButton().setPrefHeight(70);
        replayButton.getButton().setLayoutX((screenWidth / 2) + 300);
        replayButton.getButton().setLayoutY((screenHeight / 2) + 170);
        replayButton.setOnClick(onReplayLevel);

        // Add all elements to the screen
        this.getChildren().addAll(dimBackground, levelCompletedImage, starDisplay.getContainer(), nextButton.getButton(), replayButton.getButton());

        // Ensure star display and level completed image are at the front
        starDisplay.getContainer().toFront();
        levelCompletedImage.toFront();
    }
}
