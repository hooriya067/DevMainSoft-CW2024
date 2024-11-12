package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.WinImage; //class import
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Group;
import javafx.stage.Stage;
import com.example.demo.LevelParent; //class import
// managing the flow of your game, including switching between different levels and showing specific screens
public class Controller implements Observer {             //observing game flow

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private static final String LEVEL_TWO_CLASS_NAME = "com.example.demo.LevelTwo";
	private final Stage stage; //Stage:represents the main game window.

	public Controller(Stage stage) {      //to manage scenes and screen changes.
		this.stage = stage;
	}

	public void launchGame() {
		stage.show();
		try {
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			showErrorAlert(e);
		}
	}

	private void goToLevel(String className) {
		try {
			if (className.equals("com.example.demo.WinScreen")) {
				showWinScreen();
				return;
			}
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();
		} catch (ClassNotFoundException e) {
			showErrorAlert(new Exception("Level class not found: " + className));
		} catch (NoSuchMethodException e) {
			showErrorAlert(new Exception("Constructor not found in class: " + className));
		} catch (Exception e) {
			showErrorAlert(e);
		}
	}


	private void showWinScreen() {
		WinImage winImage = new WinImage(stage.getWidth() / 2 - 300, stage.getHeight() / 2 - 250);
		Button nextButton = new Button("Next Level");
		nextButton.setLayoutX(stage.getWidth() / 2 - 50);
		nextButton.setLayoutY(stage.getHeight() / 2 + 300);
		nextButton.setOnAction(e -> {
			try {
				goToLevel(LEVEL_TWO_CLASS_NAME);
			} catch (Exception ex) {
				showErrorAlert(ex);
			}
		});

		Group root = new Group(winImage, nextButton);
		Scene winScene = new Scene(root, stage.getWidth(), stage.getHeight());
		stage.setScene(winScene);
		winImage.showWinImage();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (Exception e) {
			showErrorAlert(e);
		}
	}

	private void showErrorAlert(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(e.getMessage());
		alert.show();
	}
}
