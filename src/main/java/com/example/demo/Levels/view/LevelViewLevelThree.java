package com.example.demo.Levels.view;

import com.example.demo.Managers.AlertManager;
import com.example.demo.core.GameConfig;
import com.example.demo.Levels.LevelParent;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class LevelViewLevelThree extends LevelViewParent {
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
    protected void initializeWinningParameter() {
        killsLabel = new Label("Kills: 0");
        killsLabel.setLayoutX(GameConfig.SCREEN_WIDTH / 2 - 100); // Adjust position to better center the label
        killsLabel.setLayoutY(20);

        // Apply CSS to make the label bold, with shadow and more appealing font style
        killsLabel.setStyle(
                "-fx-font-size: 30px;" +              // Larger font size for better visibility
                        "-fx-font-weight: bold;" +             // Bold font
                        "-fx-text-fill: linear-gradient(#ff0000, #ff5500);" +  // Dual-color text (red to orange gradient)
                        "-fx-effect: drop-shadow(gaussian, black, 8, 0.5, 3, 3);" // Shadow effect (black, blurred)
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
    public void infoAlerts(){

        AlertManager.getInstance().showInfoAlert(
                "Left and Right Moves \n allowed now!",
                screenWidth,
                screenHeight
        );
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            // Show the second alert after the delay
            AlertManager.getInstance().showInfoAlert(
                    "Hit Helicop army's leader \n All of them will\n fall aparttt",
                    screenWidth,
                    screenHeight
            );
        });
        pause.play();
    }
    }
