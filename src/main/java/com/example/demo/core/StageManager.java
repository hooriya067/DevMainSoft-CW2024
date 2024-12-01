package com.example.demo.core;

import com.example.demo.levels.LevelManager;
import javafx.scene.Group;
import javafx.stage.Stage;

public class StageManager {
    private static Stage currentStage;
    private static LevelManager levelManager;

    public static void setStage(Stage stage) {
        currentStage = stage;
    }

    public static Stage getStage() {
        if (currentStage == null) {
            throw new IllegalStateException("Stage has not been set!");
        }
        return currentStage;
    }

}



