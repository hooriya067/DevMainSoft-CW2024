package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

public abstract class LevelParent {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;



	private final Group root;
	private final Timeline timeline;
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

	private int currentNumberOfEnemies;

	private MyObserver myobserver;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
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
		initializeTimeline();
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
			timeline.stop(); // Stop the game timeline
			if (myobserver != null) {
				myobserver.onLevelWin("NEXT");}
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
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
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
		spawnCoins();
		updateActors();
		handleCoinCollisions();
		generateEnemyFire();
		updateNumberOfEnemies();
		updateUserShieldPosition();//ONLY FOR POWER UPS
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateWinningParameter();
		updateLevelView();
		checkIfGameOver();
		misc();

	}



	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	protected void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);

		// Ensure the background only responds when not paused
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (GameStateManager.getInstance().isGamePaused()) {
					return; // Do nothing if the game is paused
				}

				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
			}
		});

		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (GameStateManager.getInstance().isGamePaused()) {
					return; // Do nothing if the game is paused
				}

				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stopVerticalMovement();
			}
		});

		root.getChildren().add(background);
	}

	protected void fireProjectile() {
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
		double spawnProbability = 0.01; // frequency
		if (Math.random() < spawnProbability) {
			double randomYPosition = Math.random() * getScreenHeight(); // Random Y position
			Coin coin = new Coin(getScreenWidth(), randomYPosition, this); // Spawn coin
			coins.add(coin); // Add to coin list
			root.getChildren().add(coin);
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


	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

//actors2 should always be the enemy units list (enemyUnits).
//actors1 should be the user projectiles list (userProjectiles)
private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
	boolean isShieldActive = isShieldActive(); // Check if shield is active
	for (ActiveActorDestructible actor : actors2) {
		for (ActiveActorDestructible otherActor : actors1) {
			if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
				// Case 1: UserPlane with Shield
				if ((actor instanceof UserPlane && isShieldActive) || (otherActor instanceof UserPlane && isShieldActive)) {
					System.out.println("Shield absorbed collision! No damage to user.--levelparent handlecollisions");
					// Damage the non-UserPlane entity
					if (!(actor instanceof UserPlane)) {
						actor.takeDamage();
					}
					if (!(otherActor instanceof UserPlane)) {
						otherActor.takeDamage();
					}
				}
				// Case 2: Regular collision (no shield or non-UserPlane actor)
				else {
					actor.takeDamage();
					otherActor.takeDamage();

					// Count kills if an enemy is destroyed
					if (actor.isDestroyed()) {
						incrementKillCount();
					}
				}
			}
		}
	}
}
	private void handleCoinCollisions() {
		List<ActiveActorDestructible> coinsToRemove = new ArrayList<>();
		for (Node node : getRoot().getChildren()) {
			if (node instanceof Coin) {
				Coin coin = (Coin) node;
				if (coin.getBoundsInParent().intersects(getUser().getBoundsInParent())) {
					CoinSystem.getInstance().addCoins(1); // Add coins to system
					coinsToRemove.add(coin); // Mark coin for removal
					System.out.println("Coin collected! Total coins: " + CoinSystem.getInstance().getCoins());
				}
			}
		}
		getRoot().getChildren().removeAll(coinsToRemove); // Remove coins from the scene
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				//user.takeDamage();
				enemy.destroy();
			}
		}
	}



	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}


	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

//	protected void winGame() {
//		timeline.stop();
//		levelView.showWinImage();
//	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	protected void stopLevel() {
		timeline.stop();
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

	protected void setUser(ActiveActorDestructible userPlane) {
		this.userPlane = userPlane;
	}
}

