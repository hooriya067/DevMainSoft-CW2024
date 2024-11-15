package com.example.demo;

public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String LEVEL_TWO = "com.example.demo.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	//private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

//	@Override
//	protected void checkIfGameOver() {
//		if (userIsDestroyed()) {
//			loseGame();
//		}
//		else if (userHasReachedKillTarget()) {
//			stopLevel();
//			goToNextLevel(LEVEL_TWO);
//		}
//	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		double spawnProbability = 0.1; // 10% chance to spawn an enemy each update cycle

		if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
			double randomYPosition = Math.random() * getEnemyMaximumYPosition();
			ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), randomYPosition);
			addEnemyUnit(newEnemy);
		}
	}


	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight());
	}

//	protected boolean userHasReachedKillTarget() {
//		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
//	}
	protected boolean userHasReachedKillTarget() {
		return getNumberOfKills() >= KILLS_TO_ADVANCE;  // Now using LevelParent's numberOfKills
	}


	@Override
	protected void misc() {

	}

}
