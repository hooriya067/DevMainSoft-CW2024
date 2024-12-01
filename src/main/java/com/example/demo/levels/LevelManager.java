package com.example.demo.levels;

import com.example.demo.UI.screens.LevelCompletedScreen;
import com.example.demo.UI.screens.LevelIntroScreen;
import com.example.demo.UI.screens.WinImage;
import com.example.demo.controller.Controller;
import com.example.demo.core.StageManager;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;


import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    Stage stage = StageManager.getStage();
    private final List<String> levelSequence = new ArrayList<>();
    private int currentLevelIndex = 0;
    private LevelParent currentLevel;  // Reference to the current level being played

    public LevelManager(Stage stage) {


        // Add levels to the sequence
        levelSequence.add("LEVEL_ONE");
        levelSequence.add("LEVEL_TWO");
        levelSequence.add("LEVEL_THREE");
        levelSequence.add("LEVEL_FOUR");
        levelSequence.add("LEVEL_FIVE");
    }

    // Method to start the first level
    public void startFirstLevel() {
        if (!levelSequence.isEmpty()) {
            goToLevelIntro(levelSequence.get(currentLevelIndex));
        }
    }
    public void goToNextLevel() {
        if (currentLevelIndex + 1 < levelSequence.size()) {
            currentLevelIndex++; // Increment the level index to move to the next level
            LevelCompletedScreen levelCompletedPage = new LevelCompletedScreen(
                    stage.getWidth(),
                    stage.getHeight(),
                    () -> goToLevelIntro(levelSequence.get(currentLevelIndex)), // Go to the intro screen for the next level
                    () -> proceedToLevel(levelSequence.get(currentLevelIndex - 1)) // Replay the previous level
            );
            stage.getScene().setRoot(levelCompletedPage); // Display Level Completed Screen
        } else {
            showFinalWinScreen(); // Show the final win screen if all levels are completed
        }
    }
    private void goToLevelIntro(String levelName) {
        try {
            // Show Level Intro Screen
            LevelIntroScreen levelIntroScreen = new LevelIntroScreen(levelName, this);
            Scene introScene = levelIntroScreen.getScene();
            stage.setScene(introScene);
        } catch (IllegalArgumentException e) {
            showErrorAlert(e);
        }
    }

    // Method to proceed to the level after introduction
    public void proceedToLevel(String levelName) {
    //    try {
            // Instantiate the appropriate level based on the level name
            switch (levelName) {
                case "LEVEL_ONE":
                  currentLevel = new LevelOne(stage.getHeight(), stage.getWidth());
                    break;
                case "LEVEL_TWO":
                    currentLevel = new LevelTwo(stage.getHeight(), stage.getWidth());
                    break;
                case "LEVEL_THREE":
                    currentLevel = new LevelThree(stage.getHeight(), stage.getWidth());
                    break;
                case "LEVEL_FOUR":
                    currentLevel = new LevelFour(stage.getHeight(), stage.getWidth());
                    break;
                case "LEVEL_FIVE":
                   currentLevel = new LevelFive(stage.getHeight(), stage.getWidth());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown level: " + levelName);
            }

            // Set the observer for the current level
            currentLevel.setmyobserver((Controller) stage.getUserData());
            Scene scene = currentLevel.initializeScenario();
            stage.setScene(scene);
            currentLevel.startGame();
//
//        } catch (IllegalArgumentException e) {
//            showErrorAlert(e);
    //   }
    }

    private void showFinalWinScreen() {
        // Debugging: Print stage dimensions
        System.out.println("Stage dimensions: " + stage.getWidth() + "x" + stage.getHeight());

        // Create a dim background overlay
        javafx.scene.shape.Rectangle dimBackground = new javafx.scene.shape.Rectangle(stage.getWidth(), stage.getHeight());
        dimBackground.setFill(javafx.scene.paint.Color.BLACK);
        dimBackground.setOpacity(0.5); // Semi-transparent black overlay

        // Create the WinImage overlay
        WinImage winImage = new WinImage(stage.getWidth(), stage.getHeight());
        winImage.setVisible(true); // Ensure WinImage is visible

        // Group all elements together
        Group winScreenRoot = new Group(dimBackground, winImage);

        // Get the existing root of the scene
        Scene currentScene = stage.getScene();

        if (currentScene.getRoot() instanceof Group) {
            // If the root is a Group, add the WinScreenRoot directly
            Group root = (Group) currentScene.getRoot();
            root.getChildren().add(winScreenRoot);
        } else {
            // If the root is not a Group, wrap it in a new Group and set it as the root
            Group newRoot = new Group();
            newRoot.getChildren().add(currentScene.getRoot()); // Preserve the current root as a child
            newRoot.getChildren().add(winScreenRoot);
            currentScene.setRoot(newRoot);
        }

        // Ensure the overlay comes to the front
        winScreenRoot.toFront();
    }
    // Method to display an error alert
    private void showErrorAlert(Exception e) {
        System.out.println("Error: " + e.getMessage());

    }
    public String getCurrentLevelName() {
        if (currentLevelIndex >= 0 && currentLevelIndex < levelSequence.size()) {
            return levelSequence.get(currentLevelIndex);
        } else {
            throw new IllegalStateException("Invalid level index: " + currentLevelIndex);
        }
    }

}
