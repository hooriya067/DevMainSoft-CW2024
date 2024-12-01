package com.example.demo.levels;

import com.example.demo.levels.view.LevelView;
import com.example.demo.levels.view.LevelViewLevelTwo;
import com.example.demo.actors.active.enemies.Boss;
import com.example.demo.actors.active.Factories.EnemyFactory;
import javafx.application.Platform;
import javafx.scene.control.Label;
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;

	private Label bossHealthLabel;
	private final Boss boss;  // Declare boss as final and initialize it properly in the constructor
	private LevelViewLevelTwo levelView;


	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		EnemyFactory enemyFactory = new EnemyFactory(this);
		boss = (Boss) enemyFactory.createEnemy("BOSS", 1000, 100, this);
		initializeWinningParameter();
	}

	protected void initializeWinningParameter() {
		bossHealthLabel = new Label("Boss Health: 100");  // Set to initial boss health
		bossHealthLabel.setLayoutX(getScreenWidth() / 2 - 100); // Center the label a bit better
		bossHealthLabel.setLayoutY(20);

		// Apply CSS to make the label bold, with shadow and a more appealing font style
		bossHealthLabel.setStyle(
				"-fx-font-size: 30px;" +              // Larger font size
						"-fx-font-weight: bold;" +             // Bold font
						"-fx-text-fill: linear-gradient(#ff0000, #ff5500);" +  // Dual-color text (red to orange gradient)
						"-fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);" // Shadow effect (black, blurred)
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
				//System.out.println("Boss health label updated: " + boss.getHealth());
			});
		}
	}
	// Getter methods for screen dimensions
	public double getScreenWidth() {
		return super.getScreenWidth();
	}

	public double getScreenHeight() {
		return super.getScreenHeight();
	}


	@Override
	protected void spawnEnemyUnits() {
		// No need to initialize boss here, as it is already initialized in the constructor
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
		return levelView;
	}



	private void updateShieldImage() {
		if (levelView == null || boss == null) {
			return; // Ensure no null references
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
	protected void misc() {
		updateShieldImage();
	}
	@Override
	protected boolean userHasReachedKillTarget() {
		return boss.isDestroyed();
	}

	@Override
	protected void updateScene() {
		super.updateScene();
		updateWinningParameter();
	}
}


