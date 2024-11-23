package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;
import com.example.demo.GameConfig;
import com.example.demo.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class  Main extends Application {

	private static final String TITLE = "Sky Battle";
 
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(GameConfig.SCREEN_HEIGHT);
		stage.setWidth(GameConfig.SCREEN_WIDTH);

		new MainMenu(stage);
	}

	public static void main(String[] args) {
		launch();
	}
}
