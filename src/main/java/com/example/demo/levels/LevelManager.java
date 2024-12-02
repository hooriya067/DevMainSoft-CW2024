package com.example.demo.levels;

import com.example.demo.Managers.BulletSystemManager;
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
    private LevelParent currentLevel;
    public String currentLevelName;

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
            stage.getScene().setRoot(levelCompletedPage);
        } else {
            showFinalWinScreen();
        }
    }
    private void goToLevelIntro(String levelName) {
        try {
            currentLevelName = levelName; // Set the global current level name here
            System.out.println("Setting current level name to: " + currentLevelName); // Debug log

            // Show Level Intro Screen
            LevelIntroScreen levelIntroScreen = new LevelIntroScreen(levelName, this);
            Scene introScene = levelIntroScreen.getScene();
            stage.setScene(introScene);
            BulletSystemManager.getInstance().setBulletsUsed(0);
        } catch (IllegalArgumentException e) {
            showErrorAlert(e);
        }
    }
    public void proceedToLevel(String levelName) {
        if (!levelName.equals(currentLevelName)) {
            throw new IllegalStateException("Level name mismatch. Expected: " + currentLevelName + ", Got: " + levelName);
        }

        try {
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

            currentLevel.setmyobserver((Controller) stage.getUserData());
            Scene scene = currentLevel.initializeScenario();
            stage.setScene(scene);
            currentLevel.startGame();
        } catch (IllegalArgumentException e) {
            showErrorAlert(e);
        }
    }
    public void showFinalWinScreen() {
        javafx.scene.shape.Rectangle dimBackground = new javafx.scene.shape.Rectangle(stage.getWidth(), stage.getHeight());
        dimBackground.setFill(javafx.scene.paint.Color.BLACK);
        dimBackground.setOpacity(0.5); // Semi-transparent black overlay

        WinImage winImage = new WinImage(stage.getWidth(), stage.getHeight());
        winImage.setVisible(true); // Ensure WinImage is visible

        Group winScreenRoot = new Group(dimBackground, winImage);

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

        winScreenRoot.toFront();
    }
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
