package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelThree extends LevelView {

    private final Group root;
    private final double screenWidth;
    private final double screenHeight;

    public LevelViewLevelThree(Group root, int heartsToDisplay, double screenWidth, double screenHeight) {
        super(root, heartsToDisplay, screenWidth, screenHeight);
        this.root = root;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    // We can add specific Level 3 elements here like environmental features
}
