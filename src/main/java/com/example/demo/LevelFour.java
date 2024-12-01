
package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class LevelFour extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/BG4.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 15;


    private List<StealthEnemyPlane> stealthEnemies;
    private final List<FlarePowerUp> powerUps = new ArrayList<>();





    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.inputHandler.setMovementMode(InputHandler.MovementMode.FULL);

        stealthEnemies = new ArrayList<>();

    }


    @Override
    protected void spawnEnemyUnits() {
        double spawnProbability = 0.15; // Probability for spawning enemies
        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = 70 + (Math.random() * (getEnemyMaximumYPosition() - 70));

            EnemyParent newStealthEnemy = EnemyFactory.createEnemy("STEALTH", getScreenWidth(), randomYPosition, this);
            addEnemyUnit(newStealthEnemy);

            // Ensure stealth enemies are added to the dedicated list
            if (newStealthEnemy instanceof StealthEnemyPlane) {
                stealthEnemies.add((StealthEnemyPlane) newStealthEnemy);
            }
        }
    }
    private void spawnBombs() {
        if (GameStateManager.isPaused) {
            return;
        }
        double spawnProbability = 0.01; // Adjust this value for bomb frequency
        if (Math.random() < spawnProbability) {
            double randomXPosition = Math.random() * getScreenWidth();
            ActiveActorDestructible bomb = ProjectileFactory.createProjectile("BOMB", randomXPosition, 0, null);
            addProjectileToLevel(bomb);
        }
    }

    private void spawnFlarePowerUp() {
        if (GameStateManager.isPaused) {
            return;
        }
        double spawnProbability = 0.03; // Adjust this value to change the frequency of flare power-ups
        if (Math.random() < spawnProbability) {
            double randomXPosition = Math.random() * getScreenWidth(); // Random X position for the flare
            FlarePowerUp flarePowerUp = new FlarePowerUp(randomXPosition, 0, this); // Flare starts from the top (Y = 0)
            powerUps.add(flarePowerUp);
            getRoot().getChildren().add(flarePowerUp);
        }
    }
    private void handlePowerUpCollisions() {
        // Iterate through all power-ups to check if the user plane collects them
        List<FlarePowerUp> toRemove = new ArrayList<>();
        for (FlarePowerUp powerUp : powerUps) {
            if (powerUp.getBoundsInParent().intersects(getUser().getBoundsInParent())) {
                powerUp.activate(getUser()); // Activate the flare
                toRemove.add(powerUp); // Mark for removal after activation
            }
        }
        // Remove collected power-ups from the list and root node
        powerUps.removeAll(toRemove);
        getRoot().getChildren().removeAll(toRemove);
    }



    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
    }


    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    private void updatePowerUps() {
        for (FlarePowerUp powerUp : powerUps) {
            powerUp.updateActor(); // Ensure that the position is updated
        }
    }
    @Override
    protected void updateScene() {
        super.updateScene();
        spawnBombs();
        spawnFlarePowerUp();
        handlePowerUpCollisions();
        updatePowerUps();
    }
    // Method to reveal stealth enemies
    public void revealStealthEnemies(boolean reveal) {
        for (StealthEnemyPlane stealthEnemy : stealthEnemies) {
            if (!stealthEnemy.isDestroyed()) {
                stealthEnemy.setVisible(reveal);
            }
        }
    }

    @Override
    protected void misc() {

    }}

