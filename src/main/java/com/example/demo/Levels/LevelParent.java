package com.example.demo.Levels;

import com.example.demo.*;
import com.example.demo.Managers.*;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.UserPlaneFactory;
import com.example.demo.actors.active.FighterPlane;
import com.example.demo.actors.collectibles.ShieldImage;
import com.example.demo.actors.collectibles.Coin;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.core.GameLoop;
import com.example.demo.core.GameStateManager;
import com.example.demo.core.StageManager;
import com.example.demo.core.Updatable;
import com.example.demo.Levels.view.LevelViewParent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;

import java.util.List;
import java.util.Objects;

/**
 * The {@code LevelParent} class serves as the base class for all game levels,
 * providing shared functionalities such as user plane management, collision handling,
 * game loop integration, and enemy spawning.
 *
 * <p>
 * This class implements {@link ControllableLevel} to standardize level behaviors and
 * {@link Updatable} to integrate with the {@link GameLoop}.
 * </p>
 *
 * <h2>Key Features</h2>
 * <ul>
 *     <li>Centralized management of the game loop and collision handling via {@link GameLoop} and {@link CollisionManager}.</li>
 *     <li>Dynamic background and UI initialization through {@link LevelViewParent}.</li>
 *     <li>Enhanced encapsulation of actor behaviors through {@link ActorManager}.</li>
 *     <li>New shield mechanics added via {@link ShieldImage}.</li>
 *     <li> 'Observable' removed and functionality replaced by custom-made MyObserver.</li>
 * </ul>
 *
 * <h3>References to Older Code</h3>
 * <ul>
 *     <li><b>Collision Handling:</b> Previously scattered collision logic was centralized in {@link CollisionManager}.</li>
 *     <li><b>Actor Management:</b> Previously managed independently in each level, now unified under {@link ActorManager}.</li>
 *     <li><b>Input Handling:</b> Enhanced via {@link InputHandlingManager}, supporting dynamic movement modes.</li>
 *     <li><b>Game Loop:</b> Replaced older manual `Timeline` handling with a structured {@link GameLoop} for better modularity.</li>
 * </ul>
 */
public abstract class LevelParent implements ControllableLevel, Updatable {

	/**
	 * Adjustment value for the screen height in pixels.
	 * Used to calculate the maximum Y-coordinate for spawning enemies and other elements.
	 */
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

	/**
	 * Delay in milliseconds for each game loop frame.
	 * This value controls the update interval for the game logic.
	 */
	private static final int MILLISECOND_DELAY = 50;

	/**
	 * Height of the toolbar area in pixels.
	 * Reserved for displaying UI components like health bars and score counters.
	 */
	private static final int TOOLBAR_HEIGHT = 100;

	/**
	 * The height of the game screen in pixels.
	 * Specifies the vertical resolution for the current level.
	 */
	private final double screenHeight;

	/**
	 * The width of the game screen in pixels.
	 * Specifies the horizontal resolution for the current level.
	 */
	private final double screenWidth;

	/**
	 * The maximum Y-coordinate for spawning enemies in the level.
	 * Calculated based on the screen height and the {@code SCREEN_HEIGHT_ADJUSTMENT}.
	 */
	private final double enemyMaximumYPosition;

	/**
	 * The root container for all elements in the level's scene.
	 * Acts as the main group for adding UI components, game objects, and background elements.
	 */
	private final Group root;

	/**
	 * The user-controlled plane instance.
	 * Represents the player's main character or unit in the game.
	 */
	protected final UserPlane user;

	/**
	 * The scene object for the current level.
	 * Manages the graphical interface and interactions for the level.
	 */
	private final Scene scene;

	/**
	 * The background image for the level.
	 * Provides the visual backdrop for the current gameplay environment.
	 */
	protected final ImageView background;

	/**
	 * The number of enemies killed by the player in the level.
	 * Tracks progress towards the kill target required to complete the level.
	 */
	private int numberOfKills;

	/**
	 * The shield image representing the player's active shield.
	 * Displays a visual effect when the shield is active.
	 */
	private final ShieldImage userShield;

	/**
	 * The view object for the current level.
	 * Manages UI components and overlays specific to the level.
	 */
	private final LevelViewParent levelView;

	/**
	 * The input handler for managing player controls.
	 * Processes user inputs like movement and firing projectiles.
	 */
	protected final InputHandlingManager inputHandler;

	/**
	 * The game loop responsible for updating game state at regular intervals.
	 * Manages timing and frame updates for the level.
	 */
	private final GameLoop gameLoop;

	/**
	 * The actor manager for handling game objects.
	 * Centralizes the creation, updating, and removal of actors in the level.
	 */
	private final ActorManager actorManager;

	/**
	 * The initial health of the player's plane.
	 * Specifies the starting health for the user-controlled plane.
	 */
	private int playerInitialHealth;

	/**
	 * An observer instance for monitoring level events.
	 * Allows external components to respond to changes in the level's state.
	 */
	private MyObserver myObserver;

	/**
	 * Constructs a {@code LevelParent} with the specified background, screen dimensions, and initial player health.
	 *
	 * @param backgroundImageName the name of the background image.
	 * @param screenHeight        the height of the screen.
	 * @param screenWidth         the width of the screen.
	 * @param playerInitialHealth the initial health of the player.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.playerInitialHealth = playerInitialHealth;
		this.scene = new Scene(root, screenWidth, screenHeight);
		UserPlaneFactory.initializeUserPlane();
		this.user = UserPlaneFactory.getUserPlane();
		this.actorManager = new ActorManager(root);
		CollisionManager collisionManager = new CollisionManager(this, actorManager);
		this.inputHandler = new InputHandlingManager(this, InputHandlingManager.MovementMode.VERTICAL_ONLY);
		this.gameLoop = new GameLoop(MILLISECOND_DELAY);

		// Add updatables to the game loop
		gameLoop.addUpdatable(this); // LevelParent
		gameLoop.addUpdatable(actorManager); // ActorManager
		gameLoop.addUpdatable(collisionManager); // CollisionManager

		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.userShield = new ShieldImage(0, 0); // Initial position; will follow the user
		root.getChildren().add(userShield); // Add shield image to the scene
		PowerUpManager.getInstance().setLevelParent(this);
	}

	/**
	 * Initializes friendly units, specifically the user plane.
	 */
	protected void initializeFriendlyUnits() {
		actorManager.addFriendlyUnit(user);
	}

	/**
	 * Checks if the game is over by evaluating player status and kill targets.
	 * Stops the game loop if the game ends.
	 */
	protected void checkIfGameOver() {
		if (GameStateManager.getInstance().checkGameOver(user, userHasReachedKillTarget())) {
			gameLoop.stop();
			handleGameOver();
		}
	}

	/**
	 * Handles game over scenarios by showing either the game over screen or
	 * advancing to the next level if the user wins.
	 */
	private void handleGameOver() {
		if (user.isDestroyed()) {
			loseGame();
		} else {
			int bulletsUsed = BulletSystemManager.getInstance().getBulletsUsed();
			int optimalBullets = calculateOptimalBullets();
			String levelKey = StageManager.getLevelManager().getCurrentLevelName();
			StarManager.getInstance().calculateLevelStars(levelKey, bulletsUsed, optimalBullets);

			if (myObserver != null) {
				myObserver.onLevelWin("NEXT");
			}
		}
	}

	/**
	 * Calculates the optimal number of bullets needed for the level.
	 *
	 * @return the recommended bullet count.
	 */
	public abstract int calculateOptimalBullets();

	/**
	 * Determines if the user has met the kill target to advance to the next level.
	 *
	 * @return {@code true} if the kill target is met, {@code false} otherwise.
	 */
	protected abstract boolean userHasReachedKillTarget();

	/**
	 * Spawns enemy units dynamically. Implementation varies for each level.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Instantiates the custom view for the level.
	 *
	 * @return the {@link LevelViewParent} for the level.
	 */
	protected abstract LevelViewParent instantiateLevelView();

	/**
	 * Initializes the background and user interface elements for the level.
	 *
	 * @return the scene representing the level.
	 */
	@Override
	public Scene initializeScenario() {
		BulletSystemManager.getInstance().addListener(levelView::updateBulletCount);
		CoinSystemManager.getInstance().addListener(levelView::updateCoinCount);
		initializeBackground();
		initializeFriendlyUnits();
		levelView.AddUI();
		inputHandler.initialize(scene);
		return scene;
	}

	/**
	 * Starts the game loop and sets the focus on the background.
	 */
	public void startGame() {
		background.requestFocus();
		gameLoop.start();
	}

	/**
	 * Sets the observer for level events.
	 *
	 * @param myObserver the observer to notify.
	 */
	public void setmyobserver(MyObserver myObserver) {
		this.myObserver = myObserver;
	}

	@Override
	public void update() {
		spawnEnemyUnits();
		spawnCoins();
		generateEnemyFire();
		updateUserShieldPosition();
		updateLevelView();
		checkIfGameOver();
		updateSceneFurther();
	}

	/**
	 * Additional scene updates to be implemented in specific levels.
	 */
	protected void updateSceneFurther() {
	}

	/**
	 * Initializes the background with proper dimensions and focus traversal.
	 */
	protected void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		root.getChildren().add(background);
	}

	@Override
	public void fireProjectile() {
		fireUserProjectile();
	}

	/**
	 * Handles user projectile firing and manages bullet availability.
	 */
	protected void fireUserProjectile() {
		if (GameStateManager.getInstance().isGamePaused()) {
			return;
		}
		if (BulletSystemManager.getInstance().getBullets() <= 0) {
			AlertManager.getInstance().showAlert("Bullets are FINISHED!! Collect coins to buy more!");
			return;
		}
		ActiveActor projectile = user.fireProjectile();
		actorManager.addUserProjectile(projectile);
	}

	/**
	 * Spawns enemy projectiles dynamically based on enemy behaviors.
	 */
	private void generateEnemyFire() {
		actorManager.getEnemyUnits().forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns a projectile from an enemy unit.
	 *
	 * @param projectile the projectile to add.
	 */
	protected void spawnEnemyProjectile(ActiveActor projectile) {
		if (projectile != null) {
			actorManager.addEnemyProjectile(projectile);
		}
	}

	/**
	 * Spawns coins randomly across the level.
	 */
	private void spawnCoins() {
		double spawnProbability = 0.02;
		if (Math.random() < spawnProbability) {
			double randomYPosition = TOOLBAR_HEIGHT + Math.random() * (getScreenHeight() - TOOLBAR_HEIGHT);
			Coin coin = new Coin(getScreenWidth(), randomYPosition, this); // Spawn coin
			actorManager.addCoin(coin);
		}
	}

	/**
	 * Updates the position of the user's shield.
	 */
	private void updateUserShieldPosition() {
		if (userShield.isVisible()) {
			userShield.updatePosition(user.getLayoutX() + user.getTranslateX(), user.getLayoutY() + user.getTranslateY());
		}
	}

	/**
	 * Updates the user interface, including winning parameters and health display.
	 */
	private void updateLevelView() {
		levelView.updateWinningParameter();
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Handles game-over scenarios by showing the game-over screen.
	 */
	protected void loseGame() {
		gameLoop.stop();
		levelView.showGameOverImage();
	}

	/**
	 * Increments the kill count for the level.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Adds an enemy unit to the level.
	 *
	 * @param enemy the enemy to add.
	 */
	protected void addEnemyUnit(ActiveActor enemy) {
		actorManager.addEnemyUnit(enemy);
	}

	/**
	 * Adds a projectile to the level.
	 *
	 * @param projectile the projectile to add.
	 */
	protected void addProjectileToLevel(ActiveActor projectile) {
		if (projectile != null) {
			actorManager.addEnemyProjectile(projectile);
		}
	}

	// Getters for core level properties

	/**
	 * Retrieves the user-controlled plane in the level.
	 *
	 * @return the {@link UserPlane} controlled by the player.
	 */
	public UserPlane getUser() {
		return user;
	}

	/**
	 * Retrieves the root {@link Group} node for the level.
	 *
	 * @return the root {@link Group} containing all UI and game elements.
	 */
	@Override
	public Group getRoot() {
		return root;
	}

	/**
	 * Retrieves the current number of active enemy units in the level.
	 *
	 * @return the count of active enemies.
	 */
	protected int getCurrentNumberOfEnemies() {
		return actorManager.getEnemyUnits().size();
	}

	/**
	 * Retrieves the maximum Y-position for enemy movement in the level.
	 *
	 * @return the maximum allowable Y-coordinate for enemies.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Retrieves the width of the game screen.
	 *
	 * @return the screen width in pixels.
	 */
	public double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Retrieves the height of the game screen.
	 *
	 * @return the screen height in pixels.
	 */
	public double getScreenHeight() {
		return screenHeight;
	}

	/**
	 * Retrieves the number of kills made by the player in the level.
	 *
	 * @return the number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Retrieves the {@link LevelViewParent} object associated with the level.
	 *
	 * @return the level's {@link LevelViewParent}, managing its UI elements.
	 */
	public LevelViewParent getLevelView() {
		return levelView;
	}

	/**
	 * Checks if the shield is currently active for the user-controlled plane.
	 *
	 * @return {@code true} if the shield is active; {@code false} otherwise.
	 */
	public boolean isShieldActive() {
		return userShield != null && userShield.isVisible();
	}

	/**
	 * Retrieves a list of collectible coins present in the level.
	 *
	 * @return a {@link List} of {@link Coin} objects available in the level.
	 */
	public List<Coin> getCoins() {
		return actorManager.getCoins();
	}

	/**
	 * Retrieves the {@link ShieldImage} object associated with the user-controlled plane.
	 *
	 * @return the {@link ShieldImage} object representing the player's shield.
	 */
	public ShieldImage getUserShield() {
		return userShield;
	}


}