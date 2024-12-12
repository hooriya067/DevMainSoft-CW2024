/**
 * The {@code LevelThree} class represents the third level in the game, featuring unique gameplay mechanics
 * such as enemy formations and specific kill requirements to advance to the next level.
 *
 * <p>Extends {@link LevelParent} to leverage core level functionalities and introduces customized behaviors
 * for this level.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li><b>{@link LevelParent}</b>: Provides the foundational structure and methods for level functionality.</li>
 *     <li><b>{@link EnemyFactory}</b>: Used to dynamically create different types of enemy units, including formations.</li>
 *     <li><b>{@link Formation}</b>: Manages the behavior of grouped enemies in a V-formation.</li>
 *     <li><b>{@link EnemyPlaneTypeA}</b>: Represents the specific type of enemy planes used in formations.</li>
 *     <li><b>{@link LevelViewLevelThree}</b>: Custom view for this level, integrating UI elements like health display and alerts.</li>
 *     <li><b>{@link InputHandlingManager}</b>: Manages user input and movement modes specific to this level.</li>
 * </ul>
 */
package com.example.demo.Levels;

import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.Levels.view.LevelViewParent;
import com.example.demo.Levels.view.LevelViewLevelThree;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Formation;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.actors.active.enemies.EnemyPlaneTypeA;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the third level of the game with its specific configurations and behaviors.
 */
public class LevelThree extends LevelParent {

    /**
     * The file path to the background image for Level Three.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";

    /**
     * The initial health of the player for this level.
     */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * The maximum number of enemy units allowed on screen at one time.
     */
    private static final int TOTAL_ENEMIES = 5;

    /**
     * The number of kills required to advance to the next level.
     */
    private static final int KILLS_TO_ADVANCE = 4;

    /**
     * The height of the toolbar in the game.
     */
    private static final int TOOLBAR_HEIGHT = 90;

    /**
     * The current formation of enemy planes.
     */
    private Formation enemyFormation;

    /**
     * Constructs LevelThree with specified screen dimensions.
     *
     * @param screenHeight the height of the game screen
     * @param screenWidth  the width of the game screen
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.inputHandler.setMovementMode(InputHandlingManager.MovementMode.FULL);
        ((LevelViewLevelThree) getLevelView()).infoAlerts();
    }

    /**
     * Spawns enemy units and manages the appearance of formations.
     *
     * <p>Individual enemies are spawned with a specified probability, while formations
     * are introduced less frequently. Formations are reset once their leader is destroyed.</p>
     */
    @Override
    protected void spawnEnemyUnits() {
        double spawnProbability = 0.09; // Probability for individual enemies
        double formationSpawnProbability = 0.009; // Probability for spawning a new formation

        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = TOOLBAR_HEIGHT + (Math.random() * (getEnemyMaximumYPosition() - TOOLBAR_HEIGHT));
            String enemyType = Math.random() < 0.39 ? "TYPEA" : "TYPEB";
            ActiveActor newEnemy = EnemyFactory.createEnemy(enemyType, getScreenWidth(), randomYPosition, this);
            addEnemyUnit(newEnemy);
        }

        if (enemyFormation == null && Math.random() < formationSpawnProbability) {
            List<EnemyPlaneTypeA> formationUnits = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                double yPosition = TOOLBAR_HEIGHT + 50 + i * 50; // V-shape spacing
                formationUnits.add(new EnemyPlaneTypeA(getScreenWidth(), yPosition, this));
            }
            enemyFormation = new Formation(formationUnits);
            formationUnits.forEach(this::addEnemyUnit);
        }

        if (enemyFormation != null) {
            enemyFormation.updateFormationPosition();

            // Handle leader destruction and reset the formation
            if (enemyFormation.isLeaderDestroyed()) {
                enemyFormation.onLeaderDestroyed();
                enemyFormation = null; // Reset the formation for a new one later
            }
        }
    }

    /**
     * Instantiates the LevelViewParent object specific to Level Three.
     *
     * @return a new LevelViewLevelThree object
     */
    @Override
    protected LevelViewParent instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
    }

    /**
     * Performs additional updates specific to Level Three, such as updating enemy formations.
     */
    @Override
    protected void updateSceneFurther() {
        if (enemyFormation != null) {
            enemyFormation.updateFormationPosition();

            if (enemyFormation.isLeaderDestroyed()) {
                enemyFormation.onLeaderDestroyed();
                enemyFormation = null;
            }
        }
    }

    /**
     * Checks if the user has reached the required kill target to advance to the next level.
     *
     * @return true if the user has reached the kill target, false otherwise
     */
    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Calculates the optimal number of bullets the player should use to achieve the required kills.
     *
     * @return the calculated number of optimal bullets
     */
    @Override
    public int calculateOptimalBullets() {
        return (KILLS_TO_ADVANCE / 2) * 2 + KILLS_TO_ADVANCE / 2;
    }
}
