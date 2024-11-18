
//		@Override
//		protected void checkIfGameOver() {
//			if (userIsDestroyed()) {
//				loseGame();
//			} else if (boss.isDestroyed()) {
//				goToNextLevel("com.example.demo.WinScreen");
//			}
//		}

package com.example.demo;
import com.example.demo.GameConfig;


public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(GameConfig.SCREEN_HEIGHT);

	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

//	@Override
//	protected void checkIfGameOver() {
//		if (userIsDestroyed()) {
//			loseGame();
//		}
//		else if (userHasReachedKillTarget()) {
//			winGame();
//
//		}
//	}
	protected boolean userHasReachedKillTarget() {//to make check if game over functionality centralised
		return boss.isDestroyed();
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight());
		return levelView;
	}

	private void updateShieldImage() {
		if (levelView == null || boss == null) {
			return; // Ensure no null references
		}

		double bossX = boss.getLayoutX() + boss.getTranslateX();
		double bossY = boss.getLayoutY() + boss.getTranslateY();
		levelView.updateShieldPosition(bossX, bossY);

		if (boss.getIsShielded()) {
			levelView.showShield();
		} else {
			levelView.hideShield();
		}
	}

	protected void misc() {
		System.out.println("misc method called");
		updateShieldImage();
	}
}

