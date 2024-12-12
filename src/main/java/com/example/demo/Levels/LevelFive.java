package com.example.demo.Levels;

import com.example.demo.Managers.AlertManager;
import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.Levels.view.LevelVeiwLevelFive;
import com.example.demo.Levels.view.LevelViewParent;
import com.example.demo.actors.active.Factories.UserPlaneFactory;

/**
 * The {@code LevelFive} class represents the final and most challenging level in the game.
 * It introduces advanced gameplay mechanics such as diverse enemy types and visual effects
 * like clouds, creating an engaging and visually dynamic experience for the player.
 *
 * <p>This level relies heavily on the underlying architecture provided by {@link LevelParent}.
 * Additional features include:
 * <ul>
 *     <li>Dynamic enemy spawning with varied enemy types.</li>
 *     <li>Integration with {@link AlertManager} to display starting alerts.</li>
 *     <li>Custom UI elements through {@link LevelVeiwLevelFive}.</li>
 * </ul>
 */
public class LevelFive extends LevelParent {

    /**
     * The path to the background image used for this level.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background5.jpg";

    /**
     * Initial health of the user's plane, retrieved from {@link UserPlaneFactory}.
     */
    private static final int PLAYER_INITIAL_HEALTH = UserPlaneFactory.getInitialHealth();

    /**
     * The maximum number of enemies that can be active on the screen at once.
     */
    private static final int TOTAL_ENEMIES = 8;

    /**
     * The number of kills required for the player to complete the level.
     */
    private static final int KILLS_TO_ADVANCE = 15;

    /**
     * The height of the toolbar, used to calculate enemy spawn positions.
     */
    private static final double TOOLBAR_HEIGHT = 90;

    /**
     * Constructs a new instance of {@code LevelFive}.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth  the width of the game screen.
     */
    public LevelFive(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

        // Configure movement mode to allow full directional control
        this.inputHandler.setMovementMode(InputHandlingManager.MovementMode.FULL);

        // Display an alert to the player at the start of the level
        AlertManager.getInstance().showInfoAlert(
                "SHOWDOWNNN!!!",
                screenWidth,
                screenHeight
        );
    }

    /**
     * Dynamically spawns enemy units on the screen with varied types and characteristics.
     * The type of enemy spawned is determined by a random probability distribution.
     */
    @Override
    protected void spawnEnemyUnits() {
        double spawnProbability = 0.25; // 25% chance to spawn an enemy per update

        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            // Calculate a random Y position within allowable bounds
            double randomYPosition = TOOLBAR_HEIGHT + 40 +
                    (Math.random() * (getEnemyMaximumYPosition() - TOOLBAR_HEIGHT - 40));

            // Determine enemy type based on a probability range
            String enemyType;
            double enemyTypeChance = Math.random();
            if (enemyTypeChance < 0.6) {
                enemyType = "ENEMY1";
            } else if (enemyTypeChance < 0.8) {
                enemyType = "TYPEA";
            } else {
                enemyType = "TYPEB";
            }

            // Create and add the new enemy to the level
            ActiveActor newEnemy = EnemyFactory.createEnemy(enemyType, getScreenWidth(), randomYPosition, this);
            addEnemyUnit(newEnemy);
        }
    }

    /**
     * Instantiates the custom view for Level Five, providing specific UI components and visual elements.
     *
     * @return the {@link LevelVeiwLevelFive} instance for this level.
     */
    @Override
    protected LevelViewParent instantiateLevelView() {
        return new LevelVeiwLevelFive(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
    }

    /**
     * Calculates the optimal number of bullets required to complete this level.
     * The calculation is based on the number of required kills.
     *
     * @return the optimal bullet count as an integer.
     */
    @Override
    public int calculateOptimalBullets() {
        return KILLS_TO_ADVANCE * 2;
    }

    /**
     * Checks whether the player has met the kill target required to complete the level.
     *
     * @return {@code true} if the player has achieved the required number of kills, {@code false} otherwise.
     */
    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Updates additional scene-specific elements for Level Five, such as dynamic cloud visuals.
     * This method extends the functionality of {@link LevelParent#updateSceneFurther()}.
     */
    @Override
    protected void updateSceneFurther() {
        super.updateSceneFurther();
        ((LevelVeiwLevelFive) getLevelView()).updateClouds();
    }
}
