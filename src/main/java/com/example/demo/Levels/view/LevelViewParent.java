package com.example.demo.Levels.view;


import com.example.demo.Managers.BulletSystemManager;
import com.example.demo.Managers.CoinSystemManager;
import com.example.demo.Managers.SoundManager;
import com.example.demo.UI.buttons.PauseButtonDisplay;
import com.example.demo.UI.buttons.PowerUpButton;
import com.example.demo.UI.menu.PauseMenu;
import com.example.demo.UI.menu.PowerUpMenu;
import com.example.demo.UI.screens.GameOverImage;
import com.example.demo.actors.collectibles.HeartDisplay;
import com.example.demo.core.GameConfig;
import com.example.demo.core.StageManager;
import com.example.demo.Levels.LevelParent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class LevelViewParent {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final double PAUSE_BUTTON_X_POSITION =1200;
	private static final double PAUSE_BUTTON_Y_POSITION = 20;
	private static final double COIN_DISPLAY_X_POSITION = 1200;
	private static final double COIN_DISPLAY_Y_POSITION = 90;
	private static final double BULLET_DISPLAY_X_POSITION = 1140;
	private static final double BULLET_DISPLAY_Y_POSITION = GameConfig.SCREEN_HEIGHT - 100;
	private static final double POWER_UP_BUTTON_X_POSITION = 1120;
	private static final double POWER_UP_BUTTON_Y_POSITION = 20;

	private final Group root;
	private final Label coinCountLabel;
	private final PowerUpButton powerUpButton;
	private Label shieldTimerLabel;
	private Timeline shieldTimer;
	private static final double TIMER_X_POSITION = 580;
	private static final double TIMER_Y_POSITION = 100;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final PauseButtonDisplay pauseButtonDisplay;
	private final LevelParent levelParent; // Reference to the parent level
	private Label bulletCountLabel;

	public LevelViewParent(Group root, int heartsToDisplay, double screenWidth, double screenHeight, LevelParent levelParent) {

		this.root=root;
		this.levelParent = levelParent;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.gameOverImage = new GameOverImage(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
		this.pauseButtonDisplay = new PauseButtonDisplay();
		this.pauseButtonDisplay.setPosition(PAUSE_BUTTON_X_POSITION, PAUSE_BUTTON_Y_POSITION);
		// Coin counter label
		this.coinCountLabel = new Label("\uD83D\uDCB0: 0");
		this.coinCountLabel.setLayoutX(COIN_DISPLAY_X_POSITION);
		this.coinCountLabel.setLayoutY(COIN_DISPLAY_Y_POSITION);
		this.coinCountLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: yellow;");

		this.powerUpButton = new PowerUpButton();
		this.powerUpButton.setPosition(POWER_UP_BUTTON_X_POSITION, POWER_UP_BUTTON_Y_POSITION);

		// Initialize shield timer label
		shieldTimerLabel = new Label("");
		shieldTimerLabel.setLayoutX(TIMER_X_POSITION);
		shieldTimerLabel.setLayoutY(TIMER_Y_POSITION);
		shieldTimerLabel.setStyle("-fx-font-size: 20px;-fx-font-weight: bold; -fx-text-fill: cyan;");
		shieldTimerLabel.setVisible(false); // Initially hidden
		root.getChildren().add(shieldTimerLabel);

		bulletCountLabel = new Label("Bullets: 0"); // Initial display
		bulletCountLabel.setLayoutX(BULLET_DISPLAY_X_POSITION);
		bulletCountLabel.setLayoutY(BULLET_DISPLAY_Y_POSITION);
		bulletCountLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: red;");

		addUIElementsToRoot();
	}
	Stage stage = StageManager.getStage();


	private void addUIElementsToRoot() {
		root.getChildren().addAll(
				heartDisplay.getContainer(),
				pauseButtonDisplay.getButton(),
				powerUpButton.getButton(),
				coinCountLabel,
				bulletCountLabel
		);
	}
public void AddUI(){
		initializeWinningParameter();
		showHeartDisplay();
		showPauseButton();
		showLabels();
		showPowerUpButton();
		updateCoinCount(CoinSystemManager.getInstance().getCoins());
		updateBulletCount(BulletSystemManager.getInstance().getBullets());
}
	public void updateBulletCount(int newBulletCount) {
		bulletCountLabel.setText("Bullets: " + newBulletCount);
	}

	public void updateCoinCount(int newCoinCount) {
		coinCountLabel.setText("\uD83D\uDCB0 : " + newCoinCount);
	}

	private void showLabels() {
		coinCountLabel.toFront();
		bulletCountLabel.toFront();
	}
	protected void showPowerUpButton() {
		powerUpButton.getButton().toFront();
		powerUpButton.setOnPowerUp(() -> {
			PowerUpMenu powerupMenu = new PowerUpMenu(stage);
			powerupMenu.displayOverlay();
		});
	}

	protected void showPauseButton() {
		pauseButtonDisplay.getButton().toFront();
		pauseButtonDisplay.setOnPause(() -> {
			PauseMenu pauseMenu = new PauseMenu(stage);
			pauseMenu.displayOverlay();
		});
	}



	protected void showHeartDisplay() {
		heartDisplay.getContainer().toFront();
	}
	protected abstract void initializeWinningParameter();

	public abstract void updateWinningParameter();


	public void showGameOverImage() {
		SoundManager.getInstance().stopBackgroundMusic();
		SoundManager.getInstance().playSoundEffect("/com/example/demo/sound/gameover.mp3");
		SoundManager.getInstance().playBackgroundMusic("/com/example/demo/sound/background2.mp3");
		root.getChildren().add(gameOverImage);
	}

	public void addHeart() {
		heartDisplay.addHeart();
	}

	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public void startShieldTimer(int durationInSeconds) {

		shieldTimerLabel.setVisible(true);
		shieldTimerLabel.toFront();
		shieldTimerLabel.setText("Shield: " + durationInSeconds + "s");
		if (shieldTimer != null) {
			shieldTimer.stop();
		}
		shieldTimer = new Timeline(
				new KeyFrame(Duration.seconds(1), event -> {
					int remainingTime = Integer.parseInt(shieldTimerLabel.getText().replaceAll("[^0-9]", ""));
					if (remainingTime > 1) {
						shieldTimerLabel.setText("Shield: " + (remainingTime - 1) + "s");
					} else {
						stopShieldTimer();
					}
				})
		);
		shieldTimer.setCycleCount(durationInSeconds);
		shieldTimer.play();
	}

	public void stopShieldTimer() {
		if (shieldTimer != null) {
			shieldTimer.stop();
		}
		shieldTimerLabel.setVisible(false);
	}
	public Group getRoot() {
		return root;
	}

	public LevelParent getLevelParent() {
		return levelParent; }

}