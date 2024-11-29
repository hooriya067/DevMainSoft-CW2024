package com.example.demo;
import java.util.*;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;

public abstract class LevelParent {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private static final int TOOLBAR_HEIGHT = 80;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;



	private final Group root;
	protected final UserPlane user;
	private final Scene scene;
	protected final ImageView background;
	private int numberOfKills;
	private final ShieldImage userShield;
	private Timeline shieldTimer; // Timer for shield duration


	private final LevelView levelView;
	private final CollisionManager collisionManager;
	protected final InputHandler inputHandler;
	private final GameLoop gameLoop;
	private final ActorManager actorManager;
	private int currentNumberOfEnemies;

	private MyObserver myobserver;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.user = new UserPlane(playerInitialHealth);
		this.actorManager = new ActorManager(root);
		this.collisionManager = new CollisionManager(this,actorManager);
		this.inputHandler = new InputHandler(this, InputHandler.MovementMode.VERTICAL_ONLY); // Default movement to vertical
		this.gameLoop = new GameLoop(MILLISECOND_DELAY);
		this.gameLoop.setUpdateCallback(this::updateScene);
		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;

		this.userShield = new ShieldImage(0, 0); // Initial position; will follow the user
		root.getChildren().add(userShield); // Add shield image to the scene
		PowerUpManager.getInstance().setLevelParent(this);
	}

	protected void initializeFriendlyUnits() {
		actorManager.addFriendlyUnit(user); // Add user to ActorManager
	}
	protected abstract void initializeWinningParameter();
	protected abstract void updateWinningParameter();

	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			gameLoop.stop(); // Stop the game loop
			if (myobserver != null) {
				myobserver.onLevelWin("NEXT");
			}
		}
	}


	protected abstract boolean userHasReachedKillTarget();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	protected abstract void misc();
	public Scene initializeScene() {
		CoinSystem.getInstance().addListener(levelView::updateCoinCount);
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showPauseButton();
		levelView.showCoinDisplay();
		levelView.showPowerUpButton();
		levelView.updateCoinCount(CoinSystem.getInstance().getCoins());
		inputHandler.initialize(scene);
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		gameLoop.start();
	}

	public void setmyobserver(MyObserver myobserver) {
		this.myobserver = myobserver;
	}

	protected void updateScene() {
		spawnEnemyUnits();
		collisionManager.handleAllCollisions();
		spawnCoins();
		actorManager.handleAllActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		updateUserShieldPosition();
		updateWinningParameter();
		updateLevelView();
		checkIfGameOver();
		misc();

	}

	protected void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		root.getChildren().add(background);
	}
	protected void fireProjectile() {
		if (GameStateManager.isPaused) {
			return;
		}
		ActiveActorDestructible projectile = user.fireProjectile();
		actorManager.addUserProjectile(projectile);
	}


	private void generateEnemyFire() {
		actorManager.getEnemyUnits().forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	protected void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			actorManager.addEnemyProjectile(projectile);
		}
	}

	private void spawnCoins() {
		double spawnProbability = 0.02; // Adjust probability as needed
		if (Math.random() < spawnProbability) {
			double randomYPosition = TOOLBAR_HEIGHT + Math.random() * (getScreenHeight() - TOOLBAR_HEIGHT);
			Coin coin = new Coin(getScreenWidth(), randomYPosition, this); // Spawn coin
			actorManager.addCoin(coin);
		}
	}

	public void grantExtraLife() {
		System.out.println("Health before adding: " + user.getHealth());
		user.addHealth(1); // Increase user's health
		System.out.println("Health after adding: " + user.getHealth());

		levelView.addHeart(); // Add the heart to the UI
		System.out.println("Extra life granted! Remaining coins: " + CoinSystem.getInstance().getCoins());
	}
	public void activateShieldForUser() {
		System.out.println("Shield activated for the user!");

		userShield.showShield(); // Show the shield visually
		shieldTimer = new Timeline(
				new KeyFrame(Duration.seconds(20), event -> deactivateShieldForUser()) // Deactivate after 20 seconds
		);
		shieldTimer.setCycleCount(1);
		shieldTimer.play();
	}
	public void deactivateShieldForUser() {
		System.out.println("Shield deactivated!");
		userShield.hideShield(); // Hide the shield visually
		if (shieldTimer != null) {
			shieldTimer.stop(); // Stop the timer if running
			shieldTimer = null;
		}
	}

	public boolean isShieldActive() {
		return userShield != null && userShield.isVisible();
	}
	private void updateUserShieldPosition() {
		if (userShield.isVisible()) {
			userShield.updatePosition(user.getLayoutX() + user.getTranslateX(), user.getLayoutY() + user.getTranslateY());
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	protected void loseGame() {
		gameLoop.stop();
		levelView.showGameOverImage();
	}
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}
	public void incrementKillCount() {
		numberOfKills++;
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		actorManager.addEnemyUnit(enemy);
	}

	protected void addProjectileToLevel(ActiveActorDestructible projectile) {
		if (projectile != null) {
			actorManager.addEnemyProjectile(projectile);
		}
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return actorManager.getEnemyUnits().size();
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}
	public double getScreenHeight() {
		return screenHeight;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public LevelView getLevelView() {
		return levelView;
	}

	public List<Coin> getCoins() {
		return actorManager.getCoins();
	}
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies =    actorManager.getEnemyUnits().size();
	}

}

