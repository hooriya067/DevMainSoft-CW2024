package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelFour extends LevelView {

    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    private final Group root;
    private final double screenWidth;
    private final double screenHeight;


    public LevelViewLevelFour(Group root, int heartsToDisplay, double screenWidth, double screenHeight, LevelParent level) {
        super(root, heartsToDisplay, screenWidth, screenHeight);
        this.root = root;
        this.screenWidth =  SCREEN_WIDTH;
        this.screenHeight =  SCREEN_HEIGHT;

    }


}
