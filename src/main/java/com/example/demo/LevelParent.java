package com.example.demo;
import java.util.*;
import java.util.stream.Collectors;
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
	protected ActiveActorDestructible userPlane;
	private final List<ActiveActorDestructible> friendlyUnits;
	protected final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	protected final List<ActiveActorDestructible> enemyProjectiles;
	private ShieldImage userShield;
	private Timeline shieldTimer; // Timer for shield duration


	private final LevelView levelView;
	private final CollisionManager collisionManager;
	protected final InputHandler inputHandler;
	private final GameLoop gameLoop;


	private int currentNumberOfEnemies;

	private MyObserver myobserver;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.user = new UserPlane(playerInitialHealth);
		this.collisionManager = new CollisionManager(this);
		this.inputHandler = new InputHandler(this, InputHandler.MovementMode.VERTICAL_ONLY); // Default movement to vertical
		this.gameLoop = new GameLoop(MILLISECOND_DELAY);
		this.gameLoop.setUpdateCallback(this::updateScene);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		friendlyUnits.add(user);
		this.userShield = new ShieldImage(0, 0); // Initial position; will follow the user
		root.getChildren().add(userShield); // Add shield image to the sce
		PowerUpManager.getInstance().setLevelParent(this);
	}

	protected abstract void initializeFriendlyUnits();
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
		inputHandler.initialize(scene); // Initialize input handling
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		gameLoop.start();
	}

	public void goToNextLevel(String levelName) {//Notifies the observer (the Controller) when a level is won via the MyObserver interface.
		if (myobserver != null) {
			myobserver.onLevelWin(levelName);
		}
	}

	public void setmyobserver(MyObserver myobserver) {
		this.myobserver = myobserver;
	}

	protected void updateScene() {
		spawnEnemyUnits();
		collisionManager.handleAllCollisions();
		spawnCoins();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		updateUserShieldPosition();//ONLY FOR POWER UPS
		removeAllDestroyedActors();
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
			return;  // Skip updating position if paused
		}
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	protected void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}
	private final List<Coin> coins = new ArrayList<>();

	private void spawnCoins() {
		double spawnProbability = 0.01; // Adjust spawn frequency
		if (Math.random() < spawnProbability) {
			// Ensure coins are not spawned in the toolbar area
			double randomYPosition = TOOLBAR_HEIGHT + Math.random() * (getScreenHeight() - TOOLBAR_HEIGHT);
			Coin coin = new Coin(getScreenWidth(), randomYPosition, this); // Spawn coin
			root.getChildren().add(coin); // Add coin to the scene
		}
	}

	public void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
		root.getChildren().stream()
				.filter(node -> node instanceof Coin)
				.map(node -> (Coin) node)
				.forEach(Coin::updateActor); // Ensure coins are updated
	}


	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		coins.removeIf(Coin::isDestroyed); // Clean up destroyed coins
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
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

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}
	protected void addProjectileToLevel(ActiveActorDestructible projectile) {
		if (projectile != null) {
			enemyProjectiles.add(projectile); // Add to enemy projectiles
			getRoot().getChildren().add(projectile);
			System.out.println("Projectile added to level at X: " + projectile.getTranslateX() + ", Y: " + projectile.getTranslateY());
		}
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
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}
//new


	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

	public LevelView getLevelView() {
		return levelView;
	}
	public List<ActiveActorDestructible> getFriendlyUnits() {
		return friendlyUnits;
	}

	public List<ActiveActorDestructible> getEnemyUnits() {
		return enemyUnits;
	}

	public List<ActiveActorDestructible> getUserProjectiles() {
		return userProjectiles;
	}

	public List<ActiveActorDestructible> getEnemyProjectiles() {
		return enemyProjectiles;
	}


	public List<Coin> getCoins() {
		return coins;
	}

}

