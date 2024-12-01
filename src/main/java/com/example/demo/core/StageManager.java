package com.example.demo.core;

import javafx.stage.Stage;

public class StageManager {
    private static Stage currentStage;

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



