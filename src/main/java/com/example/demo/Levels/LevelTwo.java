package com.example.demo.Levels;

import com.example.demo.Levels.view.LevelViewParent;
import com.example.demo.Levels.view.LevelViewLevelTwo;
import com.example.demo.actors.active.enemies.Boss;
import com.example.demo.actors.active.Factories.EnemyFactory;
import javafx.application.Platform;
import javafx.scene.control.Label;
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;

	private Label bossHealthLabel;
	private final Boss boss;
	private LevelViewLevelTwo levelView;


	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		EnemyFactory enemyFactory = new EnemyFactory(this);
		boss = (Boss) enemyFactory.createEnemy("BOSS", 1000, 100, this);
		initializeWinningParameter();
	}
	protected void initializeWinningParameter() {
		bossHealthLabel = new Label("Boss Health: 20");
		bossHealthLabel.setLayoutX(getScreenWidth() / 2 - 100);
		bossHealthLabel.setLayoutY(20);

		bossHealthLabel.setStyle(
				"-fx-font-size: 30px;" +
						"-fx-font-weight: bold;" +
						"-fx-text-fill: linear-gradient(#ff0000, #ff5500);" +
						"-fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);"
		);

		getRoot().getChildren().add(bossHealthLabel);
		bossHealthLabel.toFront();
		System.out.println("Boss health label initialized and added to root.");
	}

	protected void updateWinningParameter() {
		if (boss != null && !boss.isDestroyed()) {
			Platform.runLater(() -> {
				bossHealthLabel.setText("Boss Health: " + boss.getHealth());
				bossHealthLabel.toFront();
			});
		}
	}
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}
	@Override
	protected LevelViewParent instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
		return levelView;
	}



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

	@Override
	public int calculateOptimalBullets() {
		return 20;
	}
	@Override
	protected boolean userHasReachedKillTarget() {
		return boss.isDestroyed();
	}

	@Override
	protected void updateSceneFurther() {
		super.updateSceneFurther();
		updateWinningParameter();
		updateShieldImage();
	}
}


