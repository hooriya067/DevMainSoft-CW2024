package com.example.demo.Levels;

import com.example.demo.Managers.AlertManager;
import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.UserPlaneFactory;
import com.example.demo.actors.collectibles.FlarePowerUp;
import com.example.demo.actors.active.enemies.EnemyParent;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.actors.active.enemies.StealthEnemyPlane;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.core.GameStateManager;
import com.example.demo.Levels.view.LevelViewParent;
import com.example.demo.Levels.view.LevelViewLevelFour;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code LevelFour} class represents the fourth level in the game, introducing unique gameplay mechanics
 * such as stealth enemies and collectible flare power-ups to enhance gameplay dynamics.
 *
 * <p>Extends {@link LevelParent} to leverage core level functionalities and provides new
 * behaviors for stealth enemies and dynamic bomb/flare spawns.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li><b>{@link LevelParent}</b> Provides the foundational structure and methods for level functionality.</li>
 *     <li><b>{@link EnemyFactory}</b> Used to dynamically create different types of enemies, including stealth planes.</li>
 *     <li><b>{@link StealthEnemyPlane}</b> Specific enemy type that remains hidden until revealed by flare power-ups.</li>
 *     <li><b>{@link FlarePowerUp}:</b> Power-up that allows the player to reveal hidden enemies temporarily.</li>
 *     <li><b>{@link ProjectileFactory}:</b> Used to create bomb projectiles dynamically during gameplay.</li>
 *     <li><b>{@link AlertManager}:</b> Displays notifications to the player at the start of the level.</li>
 *     <li><b>{@link GameStateManager}:</b> Manages game states such as paused or active to control gameplay flow.</li>
 *     <li><b>{@link LevelViewLevelFour}</b> Custom view for the level that integrates UI elements.</li>
 * </ul>
 */
public class LevelFour extends LevelParent {

    /**
     * Path to the background image for this level.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/BG4.jpg";

    /**
     * Initial health of the player's plane, retrieved dynamically from {@link UserPlaneFactory}.
     */
    private static final int PLAYER_INITIAL_HEALTH = UserPlaneFactory.getInitialHealth();

    /**
     * Maximum number of enemy units allowed on screen simultaneously.
     */
    private static final int TOTAL_ENEMIES = 5;

    /**
     * Number of kills required to advance to the next level.
     */
    private static final int KILLS_TO_ADVANCE = 10;

    /**
     * List to hold stealth enemies currently in the level.
     */
    private List<StealthEnemyPlane> stealthEnemies;

    /**
     * List of collectible flare power-ups currently available in the level.
     */
    private final List<FlarePowerUp> powerUps = new ArrayList<>();

    /**
     * Constructs a new {@code LevelFour} instance with specified screen dimensions.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth  the width of the game screen.
     */
    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.inputHandler.setMovementMode(InputHandlingManager.MovementMode.FULL);
        stealthEnemies = new ArrayList<>();
        AlertManager.getInstance().showInfoAlert(
                "Enemies are Hidden!! 💀",
                screenWidth,
                screenHeight
        );
    }

    /**
     * Spawns enemy units with a probability and ensures stealth enemies are added to a dedicated list.
     */
    @Override
    protected void spawnEnemyUnits() {
        double spawnProbability = 0.15; // Probability for spawning enemies
        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = 70 + (Math.random() * (getEnemyMaximumYPosition() - 70));

            EnemyParent newStealthEnemy = EnemyFactory.createEnemy("STEALTH", getScreenWidth(), randomYPosition, this);
            addEnemyUnit(newStealthEnemy);

            if (newStealthEnemy instanceof StealthEnemyPlane) {
                stealthEnemies.add((StealthEnemyPlane) newStealthEnemy);
            }
        }
    }

    /**
     * Dynamically spawns bomb projectiles during gameplay based on a random probability.
     */
    private void spawnBombs() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }
        double spawnProbability = 0.01;
        if (Math.random() < spawnProbability) {
            double randomXPosition = Math.random() * getScreenWidth();
            ActiveActor bomb = ProjectileFactory.createProjectile("BOMB", randomXPosition, 0, null);
            addProjectileToLevel(bomb);
        }
    }

    /**
     * Spawns flare power-ups that can be collected by the player to reveal stealth enemies.
     */
    private void spawnFlarePowerUp() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }
        double spawnProbability = 0.03;
        if (Math.random() < spawnProbability) {
            double randomXPosition = Math.random() * getScreenWidth();
            FlarePowerUp flarePowerUp = new FlarePowerUp(randomXPosition, 0, this);
            powerUps.add(flarePowerUp);
            getRoot().getChildren().add(flarePowerUp);
        }
    }

    /**
     * Handles collisions between the player and flare power-ups, activating the power-up effect.
     */
    private void handlePowerUpCollisions() {
        List<FlarePowerUp> toRemove = new ArrayList<>();
        for (FlarePowerUp powerUp : powerUps) {
            if (powerUp.getBoundsInParent().intersects(getUser().getBoundsInParent())) {
                powerUp.activate();
                toRemove.add(powerUp);
            }
        }
        powerUps.removeAll(toRemove);
        getRoot().getChildren().removeAll(toRemove);
    }

    /**
     * Instantiates the custom view for Level Four.
     *
     * @return the {@link LevelViewLevelFour} object for this level.
     */
    @Override
    protected LevelViewParent instantiateLevelView() {
        return new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, this);
    }

    /**
     * Checks if the player has reached the required kill target to advance.
     *
     * @return {@code true} if the player has reached the kill target; {@code false} otherwise.
     */
    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Updates power-ups to ensure their positions and interactions are handled.
     */
    private void updatePowerUps() {
        for (FlarePowerUp powerUp : powerUps) {
            powerUp.updateActor();
        }
    }

    /**
     * Updates additional scene elements such as bombs and power-ups.
     */
    @Override
    protected void updateSceneFurther() {
        super.updateSceneFurther();
        spawnBombs();
        spawnFlarePowerUp();
        handlePowerUpCollisions();
        updatePowerUps();
    }

    /**
     * Reveals or hides stealth enemies based on the provided parameter.
     *
     * @param reveal {@code true} to reveal the stealth enemies; {@code false} to hide them.
     */
    public void revealStealthEnemies(boolean reveal) {
        for (StealthEnemyPlane stealthEnemy : stealthEnemies) {
            if (!stealthEnemy.isDestroyed()) {
                stealthEnemy.setVisible(reveal);
            }
        }
    }

    /**
     * Calculates the optimal number of bullets required to complete the level.
     *
     * @return the number of bullets as an integer.
     */
    @Override
    public int calculateOptimalBullets() {
        return KILLS_TO_ADVANCE * 3;
    }
}
