package com.example.demo.Levels;

import com.example.demo.Levels.view.LevelViewParent;
import com.example.demo.Levels.view.LevelViewLevelOne;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.actors.active.Factories.UserPlaneFactory;
/**
 * Represents the first level of the game.
 * It introduces basic enemy mechanics and lays the foundation for the player to get accustomed to the gameplay.
 *
 * <p>Extends {@link LevelParent} to inherit core level functionalities.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li><b>{@link LevelParent}:</b> Provides shared methods and structure for all levels.</li>
 *     <li><b>{@link EnemyFactory}:</b> Used to dynamically generate enemy units.</li>
 *     <li><b>{@link LevelViewLevelOne}:</b> Customizes the user interface for this specific level.</li>
 * </ul>
 */
public class LevelOne extends LevelParent {

	/**
	 * The file path for the background image used in Level One.
	 * This image is displayed as the level's background.
	 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background01.png";

	/**
	 * The total number of enemies that can appear in Level One.
	 * This value limits the maximum number of enemies spawned simultaneously.
	 */
	private static final int TOTAL_ENEMIES = 5;

	/**
	 * The number of kills required to advance to the next level.
	 * This value represents the primary objective for completing Level One.
	 */
	private static final int KILLS_TO_ADVANCE = 10;

	/**
	 * The height of the toolbar area in pixels.
	 * This area is reserved for UI components and is used to calculate spawn positions.
	 */
	private static final double TOOLBAR_HEIGHT = 90;

/**
 * Constructs a new {@code LevelOne} instance with the specified screen dimensions.
 *
 * @param screenHeight the height of the game screen in pixels.
 * @param screenWidth  the width of the game screen in pixels.
 */

	/**
	 * Constructs a new {@code LevelOne} instance with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth  the width of the game screen.
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, UserPlaneFactory.getInitialHealth());
	}

	/**
	 * Spawns enemy units dynamically based on a probability.
	 * Ensures the number of active enemies does not exceed the maximum allowed for this level.
	 */
	@Override
	protected void spawnEnemyUnits() {
		double spawnProbability = 0.1; // Example: 10% chance to spawn an enemy each update cycle

		if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
			double randomYPosition = TOOLBAR_HEIGHT + (Math.random() * (getEnemyMaximumYPosition() - TOOLBAR_HEIGHT));

			// Use EnemyFactory to create an enemy of type "ENEMY1"
			ActiveActor newEnemy = EnemyFactory.createEnemy("ENEMY1", getScreenWidth(), randomYPosition, this);

			addEnemyUnit(newEnemy); // Add the new enemy to the level
		}
	}

	/**
	 * Instantiates the custom view for Level One.
	 *
	 * @return a {@link LevelViewLevelOne} object representing the user interface for this level.
	 */
	@Override
	protected LevelViewParent instantiateLevelView() {
		return new LevelViewLevelOne(getRoot(), UserPlaneFactory.getInitialHealth() , this);
	}

	/**
	 * Checks if the player has reached the required kill target to advance to the next level.
	 *
	 * @return {@code true} if the player has met the kill target; {@code false} otherwise.
	 */
	protected boolean userHasReachedKillTarget() {
		return getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	/**
	 * Calculates the optimal number of bullets needed to complete this level.
	 *
	 * @return the recommended bullet count as an integer.
	 */
	@Override
	public int calculateOptimalBullets() {
		return KILLS_TO_ADVANCE;
	}
}
