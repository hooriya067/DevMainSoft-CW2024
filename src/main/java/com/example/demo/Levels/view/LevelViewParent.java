/**
 * The {@code LevelViewParent} class serves as the base class for managing the UI and visual elements
 * of a game level. It includes support for heart displays, coin counters, bullet counters,
 * power-up buttons, pause functionality, and shield timers.
 *
 * <p>This class has been extensively modified to include additional features and functionalities
 * for improved gameplay experience.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link LevelParent}: Provides the core level functionality this class interacts with.</li>
 *     <li>{@link HeartDisplay}: Manages the display and updates of player health as hearts.</li>
 *     <li>{@link PauseButtonDisplay}: Displays the pause button and handles its interaction.</li>
 *     <li>{@link PowerUpButton}: Manages the display and interaction of the power-up button.</li>
 *     <li>{@link CoinSystemManager}: Updates and tracks the coin count displayed to the player.</li>
 *     <li>{@link BulletSystemManager}: Tracks and updates the bullet count for the player.</li>
 *     <li>{@link GameOverImage}: Displays the game over image when the player loses.</li>
 *     <li>{@link StageManager}: Provides access to the primary stage for UI overlays like pause or power-up menus.</li>
 * </ul>
 */
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

	/**
	 * X-position for the heart display.
	 */
	private static final double HEART_DISPLAY_X_POSITION = 5;

	/**
	 * Y-position for the heart display.
	 */
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	/**
	 * X-position for the pause button.
	 */
	private static final double PAUSE_BUTTON_X_POSITION = 1200;

	/**
	 * Y-position for the pause button.
	 */
	private static final double PAUSE_BUTTON_Y_POSITION = 20;

	/**
	 * X-position for the coin counter.
	 */
	private static final double COIN_DISPLAY_X_POSITION = 890;

	/**
	 * Y-position for the coin counter.
	 */
	private static final double COIN_DISPLAY_Y_POSITION = 30;

	/**
	 * X-position for the bullet counter.
	 */
	private static final double BULLET_DISPLAY_X_POSITION = 370;

	/**
	 * Y-position for the bullet counter.
	 */
	private static final double BULLET_DISPLAY_Y_POSITION = 30;

	/**
	 * X-position for the power-up button.
	 */
	private static final double POWER_UP_BUTTON_X_POSITION = 1120;

	/**
	 * Y-position for the power-up button.
	 */
	private static final double POWER_UP_BUTTON_Y_POSITION = 20;

	/**
	 * Root group for adding UI elements.
	 */
	private final Group root;

	/**
	 * Label displaying the coin count.
	 */
	private final Label coinCountLabel;

	/**
	 * Button for triggering power-ups.
	 */
	private final PowerUpButton powerUpButton;

	/**
	 * Label for displaying the shield timer.
	 */
	private final Label shieldTimerLabel;

	/**
	 * Timeline for managing shield timer countdown.
	 */
	private Timeline shieldTimer;

	/**
	 * X-position for the shield timer label.
	 */
	private static final double TIMER_X_POSITION = 580;

	/**
	 * Y-position for the shield timer label.
	 */
	private static final double TIMER_Y_POSITION = 100;

	/**
	 * Image displayed when the game is over.
	 */
	private final GameOverImage gameOverImage;

	/**
	 * Display for player's health as hearts.
	 */
	private final HeartDisplay heartDisplay;

	/**
	 * Button for pausing the game.
	 */
	private final PauseButtonDisplay pauseButtonDisplay;

	/**
	 * Reference to the parent level for accessing shared data and functionality.
	 */
	private final LevelParent levelParent;

	/**
	 * Label displaying the bullet count.
	 */
	private final Label bulletCountLabel;

	/**
	 * Constructs a LevelViewParent instance with the specified root group, number of hearts, and level parent.
	 *
	 * @param root            the root group for adding UI elements
	 * @param heartsToDisplay the number of hearts to display initially
	 * @param levelParent     reference to the parent level
	 */
	public LevelViewParent(Group root, int heartsToDisplay, LevelParent levelParent) {
		this.root = root;
		this.levelParent = levelParent;

		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.gameOverImage = new GameOverImage(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
		this.pauseButtonDisplay = new PauseButtonDisplay();
		this.pauseButtonDisplay.setPosition(PAUSE_BUTTON_X_POSITION, PAUSE_BUTTON_Y_POSITION);

		// Coin counter label
		this.coinCountLabel = new Label("Coins💰: 0");
		this.coinCountLabel.setLayoutX(COIN_DISPLAY_X_POSITION);
		this.coinCountLabel.setLayoutY(COIN_DISPLAY_Y_POSITION);
		this.coinCountLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(#ffd400, #ff5500); -fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);");

		this.powerUpButton = new PowerUpButton();
		this.powerUpButton.setPosition(POWER_UP_BUTTON_X_POSITION, POWER_UP_BUTTON_Y_POSITION);

		// Initialize shield timer label
		shieldTimerLabel = new Label("");
		shieldTimerLabel.setLayoutX(TIMER_X_POSITION);
		shieldTimerLabel.setLayoutY(TIMER_Y_POSITION);
		shieldTimerLabel.setStyle("-fx-font-size: 20px;-fx-font-weight: bold; -fx-text-fill: cyan;");
		shieldTimerLabel.setVisible(false); // Initially hidden
		root.getChildren().add(shieldTimerLabel);

		bulletCountLabel = new Label("Bullets💥: 0");
		bulletCountLabel.setLayoutX(BULLET_DISPLAY_X_POSITION);
		bulletCountLabel.setLayoutY(BULLET_DISPLAY_Y_POSITION);
		bulletCountLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(#ffd400, #ff5500); -fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);");

		addUIElementsToRoot();
	}

	/**
	 * Adds all UI elements to the root group.
	 */
	protected void addUIElementsToRoot() {
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
		showToolBarElements();
		showPowerUpButton();
		updateCoinCount(CoinSystemManager.getInstance().getCoins());
		updateBulletCount(BulletSystemManager.getInstance().getBullets());
	}
	/**
	 * Updates the bullet count display.
	 *
	 * @param newBulletCount the new bullet count to display
	 */
	public void updateBulletCount(int newBulletCount) {
		bulletCountLabel.setText("Bullets🔫: " + newBulletCount);
	}
	protected void showHeartDisplay() {
		heartDisplay.getContainer().toFront();
	}
	/**
	 * Updates the coin count display.
	 *
	 * @param newCoinCount the new coin count to display
	 */
	public void updateCoinCount(int newCoinCount) {
		coinCountLabel.setText("Coins💰 : " + newCoinCount);
	}

	/**
	 * Ensures all toolbar elements are displayed on top.
	 */
	protected void showToolBarElements() {
		coinCountLabel.toFront();
		bulletCountLabel.toFront();
		heartDisplay.getContainer().toFront();
		powerUpButton.getButton().toFront();
		pauseButtonDisplay.getButton().toFront();
		if (shieldTimerLabel != null) shieldTimerLabel.toFront();
	}

	/**
	 * Sets up the power-up button and its functionality.
	 */
	protected void showPowerUpButton() {
		powerUpButton.setOnPowerUp(() -> {
			PowerUpMenu powerupMenu = new PowerUpMenu(StageManager.getStage());
			powerupMenu.displayOverlay();
		});
	}

	/**
	 * Sets up the pause button and its functionality.
	 */
	protected void showPauseButton() {
		pauseButtonDisplay.setOnPause(() -> {
			PauseMenu pauseMenu = new PauseMenu(StageManager.getStage());
			pauseMenu.displayOverlay();
		});
	}

	/**
	 * Starts the shield timer for the specified duration.
	 *
	 * @param durationInSeconds the duration of the shield timer in seconds
	 */
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

	/**
	 * Stops the shield timer and hides the label.
	 */
	public void stopShieldTimer() {
		if (shieldTimer != null) {
			shieldTimer.stop();
		}
		shieldTimerLabel.setVisible(false);
	}

	/**
	 * Adds a heart to the heart display.
	 */
	public void addHeart() {
		heartDisplay.addHeart();
	}

	/**
	 * Removes hearts from the display until the specified number remains.
	 *
	 * @param heartsRemaining the number of hearts to remain visible
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Displays the game over image and plays associated sound effects.
	 */
	public void showGameOverImage() {
		SoundManager.getInstance().stopBackgroundMusic();
		SoundManager.getInstance().playSoundEffect("/com/example/demo/sound/gameover.mp3");
		SoundManager.getInstance().playBackgroundMusic("/com/example/demo/sound/background2.mp3");
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Provides access to the root group.
	 *
	 * @return the root group
	 */
	public Group getRoot() {
		return root;
	}

	/**
	 * Provides access to the parent level.
	 *
	 * @return the parent level
	 */
	public LevelParent getLevelParent() {
		return levelParent;
	}

	/**
	 * Abstract method to initialize the parameters for winning the level.
	 */
	protected abstract void initializeWinningParameter();

	/**
	 * Abstract method to update the parameters for winning the level during gameplay.
	 */
	public abstract void updateWinningParameter();
}
