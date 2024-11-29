package com.example.demo;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;

    private Label killsLabel;


    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.inputHandler.setMovementMode(InputHandler.MovementMode.FULL);

        initializeWinningParameter(); // Initialize the kill counter label once
    }

    @Override
    protected void initializeWinningParameter() {
        killsLabel = new Label("Kills: 0");
        killsLabel.setLayoutX(getScreenWidth() / 2 - 100);
        killsLabel.setLayoutY(20);

        // CSS for styling
        String labelStyle = "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(#ff0000, #ff5500); -fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);";
        killsLabel.setStyle(labelStyle);


        getRoot().getChildren().addAll(killsLabel);
        killsLabel.toFront();

    }

    @Override
    protected void updateWinningParameter() {
        Platform.runLater(() -> {
            killsLabel.setText("Kills: " + getNumberOfKills());
            killsLabel.toFront();

        });
    }

        @Override
    protected void spawnEnemyUnits() {
        double spawnProbability = 0.1;

        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = 40 + (Math.random() * (getEnemyMaximumYPosition() - 40));
            ActiveActorDestructible newEnemy;

            if (Math.random() < 0.4) {
                newEnemy = new EnemyPlaneTypeA(getScreenWidth(), randomYPosition, this); // Agile enemy type, pass the level reference
            } else {
                newEnemy = new EnemyPlaneTypeB(getScreenWidth(), randomYPosition, this); // Heavier drone type, pass the level reference
            }

            addEnemyUnit(newEnemy);
        }

        if (getCurrentNumberOfEnemies() == 0) {
            // Add a formation of EnemyPlaneTypeA
            List<EnemyPlaneTypeA> formationUnits = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                formationUnits.add(new EnemyPlaneTypeA(getScreenWidth(), 100 + i * 50, this)); // Pass level reference for each unit
            }

            Formation formation = new Formation(formationUnits);
            formationUnits.forEach(this::addEnemyUnit);

            // If the leader is destroyed, scatter the formation
            if (formation.isLeaderDestroyed()) {
                formation.onLeaderDestroyed();
            }
        }
    }


    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight());
    }

    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    @Override
    protected void misc() {
        // Add any additional behavior such as animations or shield updates if needed
    }
}