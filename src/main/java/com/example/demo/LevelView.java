package com.example.demo;

import com.example.demo.controller.Controller;
import javafx.scene.Group;
import javafx.stage.Stage;

public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final double PAUSE_BUTTON_X_POSITION =1200; // Adjusted position for easier visibility
	private static final double PAUSE_BUTTON_Y_POSITION = 20;

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final PauseButtonDisplay pauseButtonDisplay;

	public LevelView(Group root, int heartsToDisplay, double sceneWidth, double sceneHeight) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(sceneWidth, sceneHeight);
		this.pauseButtonDisplay = new PauseButtonDisplay();
		this.pauseButtonDisplay.setPosition(PAUSE_BUTTON_X_POSITION, PAUSE_BUTTON_Y_POSITION);

		addUIElementsToRoot();
	}


	private void addUIElementsToRoot() {
		root.getChildren().addAll(heartDisplay.getContainer(), pauseButtonDisplay.getButton());
	}


	public void showPauseButton() {
		pauseButtonDisplay.getButton().toFront();

		pauseButtonDisplay.setOnPause(() -> {
			Stage stage = StageManager.getStage();
			PauseMenu pauseMenu = new PauseMenu(stage);
			System.out.println("Pause button clicked!"); // Debug
			pauseMenu.displayOverlay();
		});
	}



	public void showHeartDisplay() {
		heartDisplay.getContainer().toFront();
	}

//	public void showWinImage() {
//		root.getChildren().add(winImage);
//		winImage.showWinImage();
//	}

	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}
}
