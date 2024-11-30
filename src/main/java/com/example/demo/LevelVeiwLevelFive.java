package com.example.demo;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;

public class LevelVeiwLevelFive extends LevelView {

    private Label killsLabel;

    public LevelVeiwLevelFive(Group root, int heartsToDisplay, double screenWidth, double screenHeight,LevelParent levelParent) {
        super(root, heartsToDisplay, screenWidth, screenHeight,levelParent);
    }
    @Override
    public void initializeWinningParameter() {
        killsLabel = new Label("Kills: 0");
        killsLabel.setLayoutX(getLevelParent().getScreenWidth() / 2 - 100);
        killsLabel.setLayoutY(20);

        String labelStyle = "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(#ff0000, #ff5500); -fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);";
        killsLabel.setStyle(labelStyle);

        getRoot().getChildren().addAll(killsLabel);
        killsLabel.toFront();
    }

    @Override
    public void updateWinningParameter() {
        Platform.runLater(() -> {
            killsLabel.setText("Kills: " + getLevelParent().getNumberOfKills());
            killsLabel.toFront();
        });
    }

}

