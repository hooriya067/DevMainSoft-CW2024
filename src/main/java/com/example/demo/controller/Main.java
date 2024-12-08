package com.example.demo.controller;

import com.example.demo.Managers.SoundManager;
import com.example.demo.core.GameConfig;
import com.example.demo.UI.screens.MainMenu;
import com.example.demo.core.StageManager;
import com.example.demo.levels.LevelManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final String TITLE = "Sky Strike Saga";

	@Override
//	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
//			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	public void start(Stage stage) throws SecurityException, IllegalArgumentException {

		// Set the stage in the StageManager
		StageManager.setStage(stage);
		LevelManager levelManager = new LevelManager(stage);
		StageManager.setLevelManager(levelManager);

		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(GameConfig.SCREEN_HEIGHT);
		stage.setWidth(GameConfig.SCREEN_WIDTH);

		SoundManager.getInstance().playBackgroundMusic("/com/example/demo/sound/background2.mp3");
		new MainMenu(stage);
	}

	public static void main(String[] args) {
		launch();
	}
}
