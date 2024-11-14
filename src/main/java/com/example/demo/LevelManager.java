package com.example.demo;

import com.example.demo.controller.Controller;
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

    public LevelManager(Stage stage) {
        this.stage = stage;

        // Add levels to the sequence
        levelSequence.add("LEVEL_ONE");
        levelSequence.add("LEVEL_TWO");
        // Add more levels as needed
    }

    public void startFirstLevel() {
        if (!levelSequence.isEmpty()) {
            goToLevel(levelSequence.get(currentLevelIndex));
        }
    }

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
            LevelParent currentLevel;

            // Instantiate the appropriate level based on the level name
            switch (levelName) {
                case "LEVEL_ONE":
                    currentLevel = new LevelOne(stage.getHeight(), stage.getWidth());
                    break;
                case "LEVEL_TWO":
                    currentLevel = new LevelTwo(stage.getHeight(), stage.getWidth());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown level: " + levelName);
            }

            currentLevel.setmyobserver((Controller) stage.getUserData());
            Scene scene = currentLevel.initializeScene();
            stage.setScene(scene);
            currentLevel.startGame();
        } catch (IllegalArgumentException e) {
            showErrorAlert(e);
        }
    }

    private void showFinalWinScreen() {
        // Use WinImage to create the final win scene
        WinImage winImage = new WinImage(stage.getWidth() / 2 - 300, stage.getHeight() / 2 - 250);
        winImage.showWinImage(); // Make the WinImage visible

        Button nextButton = new Button("Finish Game");
        nextButton.setLayoutX(stage.getWidth() / 2 - 50);
        nextButton.setLayoutY(stage.getHeight() / 2 + 300);
        nextButton.setOnAction(e -> {
            stage.close(); // Close the game window or restart, if needed
        });

        // Create a group containing the win image and the button
        Group winScreenRoot = new Group(winImage, nextButton);
        Scene winScene = new Scene(winScreenRoot, stage.getWidth(), stage.getHeight());
        stage.setScene(winScene); // Set the scene to the stage
    }

    private void showErrorAlert(Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
