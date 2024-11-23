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
//        levelSequence.add("LEVEL_TWO");
//        levelSequence.add("LEVEL_THREE");
//        levelSequence.add("LEVEL_FOUR");
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

    // Method to transition to a specific level
    private void goToLevel(String levelName) {
        try {
            // Instantiate the appropriate level based on the level name
            switch (levelName) {
                case "LEVEL_ONE":
                    currentLevel = new LevelOne(stage.getHeight(), stage.getWidth());
                //   currentLevel = new LevelFour(stage.getHeight(), stage.getWidth());
                    break;
//                case "LEVEL_TWO":
//                    currentLevel = new LevelTwo(stage.getHeight(), stage.getWidth());
//                    break;
//                case "LEVEL_THREE":
//                    currentLevel = new LevelThree(stage.getHeight(), stage.getWidth());
//                    break;
//                case "LEVEL_FOUR":
//                    currentLevel = new LevelFour(stage.getHeight(), stage.getWidth());
//                   // currentLevel = new LevelOne(stage.getHeight(), stage.getWidth());
//                    break;
                default:
                    throw new IllegalArgumentException("Unknown level: " + levelName);
            }

            // Set the observer for the current level
            currentLevel.setmyobserver((Controller) stage.getUserData());
            Scene scene = currentLevel.initializeScene();
            stage.setScene(scene);
            currentLevel.startGame();
        } catch (IllegalArgumentException e) {
            showErrorAlert(e);
        }
    }


    // Method to show the final win screen after all levels are complete
    private void showFinalWinScreen() {
        // Use WinImage to create the final win scene
        WinImage winImage = new WinImage(stage.getWidth() / 2 - 300, stage.getHeight() / 2 - 250);
        winImage.showWinImage(); // Make the WinImage visible




        Group winScreenRoot = new Group(winImage);
       Scene winScene = new Scene(winScreenRoot, stage.getWidth(), stage.getHeight());
        stage.setScene(winScene); // Set the scene to the stage
    }


    // Method to display an error alert
    private void showErrorAlert(Exception e) {
        System.out.println("Error: " + e.getMessage());

    }

}
