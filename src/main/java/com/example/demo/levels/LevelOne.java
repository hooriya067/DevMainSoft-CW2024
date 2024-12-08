package com.example.demo.levels;

import com.example.demo.levels.view.LevelView;
import com.example.demo.levels.view.LevelViewLevelOne;
import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.active.Factories.EnemyFactory;

public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background01.png";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 2;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final double TOOLBAR_HEIGHT = 90;


	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

	}



	@Override
	protected void spawnEnemyUnits() {
		double spawnProbability = 0.1; // Example: 10% chance to spawn an enemy each update cycle

		if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
			double randomYPosition = TOOLBAR_HEIGHT + (Math.random() * (getEnemyMaximumYPosition() - TOOLBAR_HEIGHT));
			//Factory
			ActiveActorDestructible newEnemy = EnemyFactory.createEnemy(
					"ENEMY1", getScreenWidth(), randomYPosition, this);
			addEnemyUnit(newEnemy); // Add the new enemy to the level
		}
	}
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelOne(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
	}

	protected boolean userHasReachedKillTarget() {
		return getNumberOfKills() >= KILLS_TO_ADVANCE;  // Now using LevelParent's numberOfKills
	}

	@Override
	public int calculateOptimalBullets() {
		return KILLS_TO_ADVANCE;
	}




}
