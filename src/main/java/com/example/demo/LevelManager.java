package com.example.demo;

import com.example.demo.controller.Controller;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private final Stage stage;
    private final List<String> levelSequence = new ArrayList<>();
    private int currentLevelIndex = 0;
    private LevelParent currentLevel;  // Reference to the current level being played

    public LevelManager(Stage stage) {
        this.stage = stage;

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
            goToLevel(levelSequence.get(currentLevelIndex));
        }
    }

    // Method to proceed to the next level
    public void goToNextLevel() {
        currentLevelIndex++;
        if (currentLevelIndex < levelSequence.size()) {
            goToLevel(levelSequence.get(currentLevelIndex));
        } else {
            showFinalWinScreen(); // Show final win screen when all levels are completed
        }
    }

    private void goToLevel(String levelName) {
        try {
            // Create a LevelIntroScreen instance before starting the actual level
            LevelIntroScreen levelIntroScreen = new LevelIntroScreen( levelName, this);
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
            Scene scene = currentLevel.initializeScene();
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

}
