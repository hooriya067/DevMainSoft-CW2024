package com.example.demo;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String LEVEL_TWO = "com.example.demo.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	//private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final double TOOLBAR_HEIGHT = 70;
	private Label killsLabel;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		initializeWinningParameter(); // Initialize the kill counter label once
	}
		@Override
		protected void initializeWinningParameter() {
			killsLabel = new Label("Kills: 0");
			killsLabel.setLayoutX(getScreenWidth() / 2 - 100); // Adjust position to better center the label
			killsLabel.setLayoutY(20);

			// Apply CSS to make the label bold, with shadow and more appealing font style
			killsLabel.setStyle(
					"-fx-font-size: 30px;" +              // Larger font size for better visibility
							"-fx-font-weight: bold;" +             // Bold font
							"-fx-text-fill: linear-gradient(#ff0000, #ff5500);" +  // Dual-color text (red to orange gradient)
							"-fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);" // Shadow effect (black, blurred)
			);

			getRoot().getChildren().add(killsLabel);
			//killsLabel.toFront();
			System.out.println("Kill counter label initialized and added to root.");
		}

	@Override
	protected void updateWinningParameter() {
		Platform.runLater(() -> {
			killsLabel.setText("Kills: " + getNumberOfKills());
			killsLabel.toFront();
//			System.out.println("Kill counter label updated: " + getNumberOfKills());
		});
	}


	protected void spawnEnemyUnits() {
		double spawnProbability = 0.1; // Example: 10% chance to spawn an enemy each update cycle

		if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
			double randomYPosition = TOOLBAR_HEIGHT + (Math.random() * (getEnemyMaximumYPosition() - TOOLBAR_HEIGHT));
			ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), randomYPosition);
			addEnemyUnit(newEnemy);
		}
	}


	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelOne(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight());
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
