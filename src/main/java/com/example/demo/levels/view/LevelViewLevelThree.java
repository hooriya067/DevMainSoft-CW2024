package com.example.demo.levels.view;

import com.example.demo.core.GameConfig;
import com.example.demo.levels.LevelParent;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;

public class LevelViewLevelThree extends LevelView {
    private Label killsLabel;
    private final Group root;
    private final double screenWidth;
    private final double screenHeight;

    public LevelViewLevelThree(Group root, int heartsToDisplay, double screenWidth, double screenHeight, LevelParent levelParent) {
        super(root, heartsToDisplay, screenWidth, screenHeight,levelParent);
        this.root = root;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public void initializeWinningParameter() {
        killsLabel = new Label("Kills: 0");
        killsLabel.setLayoutX(GameConfig.SCREEN_WIDTH / 2 - 100); // Adjust position to better center the label
        killsLabel.setLayoutY(20);

        // Apply CSS to make the label bold, with shadow and more appealing font style
        killsLabel.setStyle(
                "-fx-font-size: 30px;" +              // Larger font size for better visibility
                        "-fx-font-weight: bold;" +             // Bold font
                        "-fx-text-fill: linear-gradient(#ff0000, #ff5500);" +  // Dual-color text (red to orange gradient)
                        "-fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);" // Shadow effect (black, blurred)
        );

        getRoot().getChildren().add(killsLabel);
        //killsLabel.toFront();
        System.out.println("Kill counter label initialized and added to root.");
    }
    @Override
    public void updateWinningParameter() {
        Platform.runLater(() -> {
            killsLabel.setText("Kills: " + getLevelParent().getNumberOfKills());
            killsLabel.toFront();
           // System.out.println("Kill counter label updated: " + getLevelParent().getNumberOfKills());
        });
    }
    }