package com.example.demo.controller;

import com.example.demo.core.GameConfig;
import com.example.demo.core.StageManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.MyObserver;
import com.example.demo.levels.LevelManager;

public class Controller implements MyObserver { // Implementing MyObserver to get notified when the player wins a level

	Stage stage = StageManager.getStage(); // Get the Stage from the StageManager
	private final LevelManager levelManager;

	public Controller(Stage stage) {
		this.stage.setUserData(this); // Store reference to controller in stage for LevelManager
		this.levelManager = new LevelManager(stage);
	}

	public void launchGame() {
		Group root = new Group();
		Scene scene = new Scene(root, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
		stage.setScene(scene);
		stage.show();
		levelManager.startFirstLevel();
	}

	@Override
	public void onLevelWin(String nextLevel) {
		if ("NEXT".equals(nextLevel)) {
			levelManager.goToNextLevel();
		}
}
}
