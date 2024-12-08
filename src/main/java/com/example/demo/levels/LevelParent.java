package com.example.demo.levels;
import java.util.*;

import com.example.demo.*;
import com.example.demo.Managers.*;
import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.active.Factories.UserPlaneFactory;
import com.example.demo.actors.active.FighterPlane;
import com.example.demo.actors.collectibles.ShieldImage;
import com.example.demo.actors.collectibles.Coin;
import com.example.demo.actors.user.UserPlane;
import com.example.demo.core.GameLoop;
import com.example.demo.core.GameStateManager;
import com.example.demo.core.StageManager;
import com.example.demo.core.Updatable;
import com.example.demo.levels.view.LevelView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;

public abstract class LevelParent implements ControllableLevel, Updatable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private static final int TOOLBAR_HEIGHT = 100;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;



	private final Group root;
	protected final UserPlane user;
	private final Scene scene;
	protected final ImageView background;
	private int numberOfKills;
	private final ShieldImage userShield;

	private final LevelView levelView;
	private final CollisionManager collisionManager;
	protected final InputHandlingManager inputHandler;
	private final GameLoop gameLoop;
	private final ActorManager actorManager;
	private int currentNumberOfEnemies;

	private MyObserver myobserver;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {

		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		UserPlaneFactory.setInitialHealth(playerInitialHealth);
		this.user = UserPlaneFactory.createUserPlane();
		this.actorManager = new ActorManager(root);
		this.collisionManager = new CollisionManager(this, actorManager);
		this.inputHandler = new InputHandlingManager(this, InputHandlingManager.MovementMode.VERTICAL_ONLY);
		this.gameLoop = new GameLoop(MILLISECOND_DELAY);
		gameLoop.addUpdatable(this); // LevelParent
		gameLoop.addUpdatable(actorManager); // ActorManager
		gameLoop.addUpdatable(collisionManager); // CollisionManager
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
		actorManager.addFriendlyUnit(user);
	}
	protected void checkIfGameOver() {
		if (GameStateManager.getInstance().checkGameOver(user,userHasReachedKillTarget())) {
			gameLoop.stop();
			handleGameOver();
		}
	}
	private void handleGameOver() {
		if (user.isDestroyed()) {
			loseGame();
		} else {
			int bulletsUsed = BulletSystemManager.getInstance().getBulletsUsed();
			int optimalBullets = calculateOptimalBullets();
			String levelKey = StageManager.getLevelManager().getCurrentLevelName();
			StarManager.getInstance().calculateLevelStars(levelKey, bulletsUsed, optimalBullets);

			if (myobserver != null) {
				myobserver.onLevelWin("NEXT");
			}
		}
	}
	public abstract int calculateOptimalBullets();


	protected abstract boolean userHasReachedKillTarget();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	@Override
	public Scene initializeScenario() {
		 return initializeScene();
	}
	protected Scene initializeScene() {
		BulletSystemManager.getInstance().addListener(levelView::updateBulletCount);
		CoinSystemManager.getInstance().addListener(levelView::updateCoinCount);
		initializeBackground();
		initializeFriendlyUnits();
		levelView.AddUI();
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
	@Override
	public void update() {
		spawnEnemyUnits();
		spawnCoins();
		generateEnemyFire();
		updateNumberOfEnemies();
		updateUserShieldPosition();
		updateLevelView();
		checkIfGameOver();
		updateSceneFurther();
	}

	protected void updateSceneFurther() {}
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
	protected void fireUserProjectile() {
		if (GameStateManager.getInstance().isGamePaused()) {
			return;
		}
		if (BulletSystemManager.getInstance().getBullets() <= 0) {
			AlertManager.getInstance().showAlert("Bullets are FINISHED!! Collect coins to buy more!");
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
		double spawnProbability = 0.02;
		if (Math.random() < spawnProbability) {
			double randomYPosition = TOOLBAR_HEIGHT + Math.random() * (getScreenHeight() - TOOLBAR_HEIGHT);
			Coin coin = new Coin(getScreenWidth(), randomYPosition, this); // Spawn coin
			actorManager.addCoin(coin);
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
		levelView.updateWinningParameter();
		levelView.removeHearts(user.getHealth());
	}

	protected void loseGame() {
		gameLoop.stop();
		levelView.showGameOverImage();
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
	public UserPlane getUser() {
		return user;
	}
	@Override
	public Group getRoot() {
		return root;
	}
	protected int getCurrentNumberOfEnemies() {
		return actorManager.getEnemyUnits().size();
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	public double getScreenWidth() {
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
	public ShieldImage getUserShield() {
		return userShield;
	}
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies =    actorManager.getEnemyUnits().size();
	}
}

