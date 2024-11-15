package com.example.demo.controller;

import javafx.stage.Stage;
import com.example.demo.MyObserver;
import com.example.demo.LevelManager;

public class Controller implements MyObserver { // Implementing MyObserver to get notified when the player wins a level

	private final Stage stage;
	private final LevelManager levelManager;

	public Controller(Stage stage) {
		this.stage = stage;
		this.stage.setUserData(this); // Store reference to controller in stage for LevelManager
		this.levelManager = new LevelManager(stage);
	}

	public void launchGame() {
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

//package com.example.demo.controller;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//import javafx.stage.Stage;
//import com.example.demo.LevelParent;
//import com.example.demo.MyObserver;
//
//
//public class Controller implements MyObserver { //Implementing MyObserver to get notified when the player wins a level
//
//	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
//	private final Stage stage;
//
//	private LevelParent currentLevel;
//
//	public Controller(Stage stage) {
//		this.stage = stage;
//	}
//
//	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
//			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
//
//		stage.show();
//		goToLevel(LEVEL_ONE_CLASS_NAME);
//	}
//
//	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
//			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Class<?> myClass = Class.forName(className);
//		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
//		currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
//		currentLevel.setmyobserver(this);
//		Scene scene = currentLevel.initializeScene();
//		stage.setScene(scene);
//		currentLevel.startGame();
//	}
//
//	@Override
//	public void onLevelWin(String nextLevel) {//decides which level should come next and calls goToLevel() to go to that level.
//		try {
//			goToLevel(nextLevel);
//		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
//				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setContentText(e.getClass().toString());
//			alert.show();
//		}
//	}
//}
