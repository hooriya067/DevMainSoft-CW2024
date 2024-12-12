package com.example.demo.Levels;

import com.example.demo.Levels.view.LevelViewParent;
import com.example.demo.Levels.view.LevelViewLevelTwo;
import com.example.demo.actors.active.Factories.UserPlaneFactory;
import com.example.demo.actors.active.enemies.Boss;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.core.GameConfig;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Represents Level Two of the game, introducing a boss enemy and specific winning parameters.
 * This class extends {@link LevelParent} and encapsulates logic unique to this level,
 * including managing a boss enemy, updating the UI with the boss's health, and handling
 * shield mechanics.
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.png";

	/**
	 * Initial health of the player's plane, retrieved dynamically from {@link UserPlaneFactory}.
	 */
	private static final int PLAYER_INITIAL_HEALTH = UserPlaneFactory.getInitialHealth();

	/**
	 * UI element to display the boss's health dynamically.
	 */
	private Label bossHealthLabel;

	/**
	 * The boss enemy instance for this level.
	 */
	private final Boss boss;

	/**
	 * The specific view for Level Two, extending {@link LevelViewParent}.
	 */
	private LevelViewLevelTwo levelView;

	/**
	 * Constructs Level Two with specified screen dimensions.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth  the width of the game screen.
	 */
	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		// Initialize boss using the EnemyFactory
		EnemyFactory enemyFactory = new EnemyFactory(this);
		boss = (Boss) enemyFactory.createEnemy("BOSS", 1000, 100, this);

		initializeWinningParameter();
	}

	/**
	 * Initializes the winning parameter by adding a label to track the boss's health dynamically.
	 */
	protected void initializeWinningParameter() {
		bossHealthLabel = new Label("Boss Health \uD83D\uDC79: 20");
		bossHealthLabel.setLayoutX((double) GameConfig.SCREEN_WIDTH / 2 - 40);
		bossHealthLabel.setLayoutY(30);
		bossHealthLabel.setStyle(
				"-fx-font-size: 25px;" +
						"-fx-font-weight: bold;" +
						"-fx-text-fill: linear-gradient(#5A0000, #ff5500);" +
						"-fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);");
		getRoot().getChildren().add(bossHealthLabel);
		bossHealthLabel.toFront();
		System.out.println("Boss health label initialized and added to root.");
	}

	/**
	 * Updates the boss health label dynamically to reflect the current health of the boss.
	 */
	protected void updateWinningParameter() {
		if (boss != null && !boss.isDestroyed()) {
			Platform.runLater(() -> {
				bossHealthLabel.setText("Boss Health\uD83D\uDC79: " + boss.getHealth());
				bossHealthLabel.toFront();
			});
		}
	}

	/**
	 * Spawns the boss as the sole enemy for this level.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Initializes the specific view for Level Two.
	 *
	 * @return the {@link LevelViewLevelTwo} instance for this level.
	 */
	@Override
	protected LevelViewParent instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
		return levelView;
	}

	/**
	 * Updates the shield image position and visibility dynamically based on the boss's state.
	 */
	private void updateShieldImage() {
		if (levelView == null || boss == null) {
			return;
		}
		double bossX = boss.getLayoutX() + boss.getTranslateX();
		double bossY = boss.getLayoutY() + boss.getTranslateY();
		levelView.updateShieldPosition(bossX, bossY);

		if (boss.isShieldActive()) {
			levelView.showShield();
		} else {
			levelView.hideShield();
		}
	}

	/**
	 * Calculates the optimal number of bullets required to defeat the boss.
	 *
	 * @return the optimal bullet count.
	 */
	@Override
	public int calculateOptimalBullets() {
		return 20;
	}

	/**
	 * Checks if the user has reached the kill target for this level by defeating the boss.
	 *
	 * @return true if the boss is destroyed, false otherwise.
	 */
	@Override
	protected boolean userHasReachedKillTarget() {
		return boss.isDestroyed();
	}

	/**
	 * Handles additional updates unique to this level, such as updating the boss's health and shield state.
	 */
	@Override
	protected void updateSceneFurther() {
		super.updateSceneFurther();
		updateWinningParameter();
		updateShieldImage();
	}
}
