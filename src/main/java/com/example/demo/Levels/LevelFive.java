package com.example.demo.Levels;

import com.example.demo.Managers.AlertManager;
import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.Levels.view.LevelVeiwLevelFive;
import com.example.demo.Levels.view.LevelViewParent;


/**
 * The {@code LevelFive} class represents the final level in the game, incorporating advanced
 * gameplay elements such as varied enemy types, meteor storms (commented out for optional use),
 * and cloud-based visual effects. This level emphasizes challenging player engagement.
 *
 * <p>Extends {@link LevelParent} to inherit core level functionality, and utilizes advanced
 * features like movement modes, alerts, and enemy spawning logic.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li><b>LevelParent:</b> Provides core functionality for level structure and logic.</li>
 *     <li><b>EnemyFactory:</b> Used for creating enemies dynamically during gameplay.</li>
 *     <li><b>LevelVeiwLevelFive:</b> A custom view class for Level Five, managing UI and visual elements like clouds.</li>
 *     <li><b>AlertManager:</b> Used for displaying initial alerts to the player at the start of the level.</li>
 * </ul>
 */
public class LevelFive extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background5.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 8;
    private static final int KILLS_TO_ADVANCE = 2;
    private static final double TOOLBAR_HEIGHT = 90;

    //   private boolean meteorStormActive = true; // Flag to handle meteor storm phase

    /**
     * Constructs a new {@code LevelFive} instance.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth  the width of the game screen.
     */
    public LevelFive(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.inputHandler.setMovementMode(InputHandlingManager.MovementMode.FULL);

        // Show initial alert to the player
        AlertManager.getInstance().showInfoAlert(
                "SHOWDOWNNN!!!",
                screenWidth,
                screenHeight
        );
    }

    /**
     * Spawns enemy units with varying types based on random probabilities.
     */
    @Override
    protected void spawnEnemyUnits() {
        double spawnProbability = 0.25; // 25% chance to spawn an enemy per update
        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = TOOLBAR_HEIGHT + 40 + (Math.random() * (getEnemyMaximumYPosition() - TOOLBAR_HEIGHT - 40));
            String enemyType;
            double enemyTypeChance = Math.random();

            // Determine enemy type based on random chance
            if (enemyTypeChance < 0.6) {
                enemyType = "ENEMY1";
            } else if (enemyTypeChance < 0.8) {
                enemyType = "TYPEA";
            } else {
                enemyType = "TYPEB";
            }

            // Create and add the enemy to the level
            ActiveActor newEnemy = EnemyFactory.createEnemy(enemyType, getScreenWidth(), randomYPosition, this);
            addEnemyUnit(newEnemy);
        }
    }
//
//    private void spawnMeteors() {
//        double meteorProbability = 0.3; // 30% chance to spawn a meteor
//        if (Math.random() < meteorProbability) {
//            Meteor meteor = new Meteor(this);
//            addProjectileToLevel(meteor); // Add meteor to the level
//           // System.out.println("Meteor spawned at X: " + meteor.getTranslateX() + ", Y: " + meteor.getTranslateY());
//        }
//    }
    /**
     * Instantiates the custom view for Level Five.
     *
     * @return the {@link LevelVeiwLevelFive} object for this level.
     */
    @Override
    protected LevelViewParent instantiateLevelView() {
        return new LevelVeiwLevelFive(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
    }

    /**
     * Calculates the optimal number of bullets required to complete the level.
     *
     * @return the number of bullets as an integer.
     */
    @Override
    public int calculateOptimalBullets() {
        return KILLS_TO_ADVANCE * 2;
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
     * Updates additional scene elements for Level Five, such as clouds.
     */
    @Override
    protected void updateSceneFurther() {
        super.updateSceneFurther();
       // misc();
        ((LevelVeiwLevelFive) getLevelView()).updateClouds();
    }
    //    protected void misc() {
//        if (getNumberOfKills() > 5) {
//            meteorStormActive = false; // Disable meteor storm after 5 kills
//        }
//    }
}
