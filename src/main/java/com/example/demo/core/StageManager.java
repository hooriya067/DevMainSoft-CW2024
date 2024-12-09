package com.example.demo.core;

import com.example.demo.Managers.LevelManager;
import javafx.stage.Stage;

public class StageManager {
    private static Stage currentStage;
    private static LevelManager levelManager;

    // Set the global Stage instance
    public static void setStage(Stage stage) {
        currentStage = stage;
    }

    // Get the global Stage instance
    public static Stage getStage() {
        if (currentStage == null) {
            throw new IllegalStateException("Stage has not been set!");
        }
        return currentStage;
    }


    public static void setLevelManager(LevelManager manager) {
        levelManager = manager;
    }
    public static LevelManager getLevelManager() {
        if (levelManager == null) {
            throw new IllegalStateException("LevelManager has not been set!");
        }
        return levelManager;
    }
}
