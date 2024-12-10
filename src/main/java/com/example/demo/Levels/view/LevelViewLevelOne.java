package com.example.demo.Levels.view;

import com.example.demo.core.GameConfig;
import com.example.demo.Levels.LevelParent;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;

public class LevelViewLevelOne extends LevelViewParent {
    private Label killsLabel;
    public LevelViewLevelOne(Group root, int heartsToDisplay, LevelParent levelParent) {
        super(root, heartsToDisplay,levelParent);

    }
    @Override
    protected void initializeWinningParameter() {
        killsLabel = new Label("Kills: 0");
        killsLabel.setLayoutX((double) GameConfig.SCREEN_WIDTH / 2 - 100); // Adjust position
        killsLabel.setLayoutY(20);
        killsLabel.setStyle(
                "-fx-font-size: 30px;" +              // Larger font size for better visibility
                        "-fx-font-weight: bold;" +             // Bold font
                        "-fx-text-fill: linear-gradient(#ff0000, #ff5500);" +  // Dual-color text (red to orange gradient)
                        "-fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);" // Shadow effect (black, blurred)
        );
        getRoot().getChildren().add(killsLabel);
        System.out.println("Kill counter label initialized and added to root.");
    }
    @Override
    public void updateWinningParameter() {
        Platform.runLater(() -> {
            killsLabel.setText("Kills: " + getLevelParent().getNumberOfKills());
            killsLabel.toFront();
        });
    }


}

