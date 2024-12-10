package com.example.demo.Levels.view;

import com.example.demo.core.GameConfig;
import com.example.demo.Levels.LevelParent;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;

public class LevelViewLevelFour extends LevelViewParent {

    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    private Label killsLabel;

    public LevelViewLevelFour(Group root, int heartsToDisplay, LevelParent levelParent) {
        super(root, heartsToDisplay,levelParent);
       }


    @Override
    protected void initializeWinningParameter() {
        killsLabel = new Label("Kills: 0");
        killsLabel.setLayoutX((double) GameConfig.SCREEN_WIDTH / 2 - 100);
        killsLabel.setLayoutY(20);

        // CSS for styling
        String labelStyle = "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(#ff0000, #ff5500); -fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);";
        killsLabel.setStyle(labelStyle);

        getRoot().getChildren().addAll(killsLabel);
        // killsLabel.toFront();
    }


    @Override
    public void updateWinningParameter() {
        Platform.runLater(() -> {
            killsLabel.setText("Kills: " + getLevelParent().getNumberOfKills());
            killsLabel.toFront();
        });
    }

}
