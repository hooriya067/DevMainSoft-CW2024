package com.example.demo.controller;

import com.example.demo.Managers.SoundManager;
import com.example.demo.core.GameConfig;
import com.example.demo.UI.screens.MainMenu;
import com.example.demo.core.StageManager;
import com.example.demo.Managers.LevelManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The {@code Main} class serves as the entry point for the application.
 * It initializes the primary {@link Stage}, sets global configurations, and
 * launches the main menu of the game.
 *
 * <p>
 * This implementation replaces the older {@code Main} class by introducing centralized
 * stage and level management using {@link StageManager} and {@link LevelManager}.
 * Previously, the main class handled level transitions less modularly, and stage
 * configuration was directly embedded without a manager for reusable access.
 * </p>
 *
 * @see StageManager
 * @see LevelManager
 * @see MainMenu
 * @see SoundManager
 */
public class Main extends Application {

	/**
	 * The title of the game window.
	 */
	private static final String TITLE = "Sky Strike Saga";

	/**
	 * Starts the JavaFX application by configuring the primary stage and launching the main menu.
	 *
	 * @param stage the primary {@link Stage} used for displaying the game.
	 * @throws SecurityException        if there are issues with loading resources.
	 * @throws IllegalArgumentException if invalid parameters are passed during stage setup.
	 */
	@Override
	public void start(Stage stage) throws SecurityException, IllegalArgumentException {

		// Set the stage in the StageManager
		StageManager.setStage(stage);
		LevelManager levelManager = new LevelManager(stage);
		StageManager.setLevelManager(levelManager);

		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(GameConfig.SCREEN_HEIGHT);
		stage.setWidth(GameConfig.SCREEN_WIDTH);

		// Play background music and launch the main menu
		SoundManager.getInstance().playBackgroundMusic("/com/example/demo/sound/background2.mp3");
		new MainMenu(stage);
	}

	/**
	 * The main method serves as the entry point for launching the JavaFX application.
	 *
	 * @param args the command-line arguments.
	 */
	public static void main(String[] args) {
		launch();
	}
}

