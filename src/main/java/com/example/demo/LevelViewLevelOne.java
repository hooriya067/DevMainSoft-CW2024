package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelOne extends LevelView {

    public LevelViewLevelOne(Group root, int heartsToDisplay, double screenWidth, double screenHeight) {
        super(root, heartsToDisplay, screenWidth, screenHeight); // Corrected: added 'heartsToDisplay'
    }

    // Currently no additional methods or properties, but this class can be expanded in the future.
}

