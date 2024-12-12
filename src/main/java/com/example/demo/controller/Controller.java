package com.example.demo.controller;

import com.example.demo.core.GameConfig;
import com.example.demo.core.StageManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.MyObserver;
import com.example.demo.Managers.LevelManager;

/**
 * The {@code Controller} class serves as the main controller for managing the game flow.
 * It initializes and launches the game, handles stage configuration, and listens
 * for level completion events to transition between levels.
 *
 * <p>
 * This class replaces the functionality of the older {@code Controller} class in the
 * previous implementation, where reflection was used for loading levels dynamically.
 * Instead, the new implementation utilizes a {@link LevelManager} for more structured
 * level management and a {@link MyObserver} pattern for handling level win notifications.
 * </p>
 *
 * @see LevelManager
 * @see MyObserver
 * @see StageManager
 */
public class Controller implements MyObserver {

	/**
	 * The {@link Stage} instance for rendering game scenes.
	 */
	private final Stage stage = StageManager.getStage();

	/**
	 * The {@link LevelManager} responsible for managing level transitions.
	 */
	private final LevelManager levelManager;

	/**
	 * Constructs a new {@code Controller} instance.
	 *
	 * @param stage the primary {@link Stage} used for displaying the game.
	 */
	public Controller(Stage stage) {
		this.stage.setUserData(this); // Store reference to controller in stage for LevelManager
		this.levelManager = new LevelManager(stage);
	}

	/**
	 * Launches the game by setting up the initial scene and starting the first level.
	 */
	public void launchGame() {
		Group root = new Group();
		Scene scene = new Scene(root, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
		stage.setScene(scene);
		stage.show();
		levelManager.startFirstLevel();
	}

	/**
	 * Handles level win events triggered by the {@link MyObserver} pattern.
	 *
	 * @param nextLevel the identifier for the next level, typically "NEXT" to indicate
	 *                  progression to the next level.
	 */
	@Override
	public void onLevelWin(String nextLevel) {
		if ("NEXT".equals(nextLevel)) {
			levelManager.goToNextLevel();
		}
	}
}

