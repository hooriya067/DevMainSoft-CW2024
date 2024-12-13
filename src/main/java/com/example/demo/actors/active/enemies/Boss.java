package com.example.demo.actors.active.enemies;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.active.ActiveActor;

import java.util.*;
import java.util.function.Consumer;

/**
 * The {@code Boss} class represents a powerful enemy in the game with advanced behaviors such as movement patterns,
 * shield activation, and firing projectiles. It extends {@link EnemyParent} to inherit common enemy functionalities
 * and adds unique capabilities like a shield and a customizable move pattern.
 */
public class Boss extends EnemyParent {

	/**
	 * Offset for the Y-coordinate of fired projectiles.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;

	/**
	 * The image name representing the boss.
	 */
	private static final String IMAGE_NAME = "bossplane.png";

	/**
	 * Probability for the boss to fire a projectile in each frame.
	 */
	private static final double BOSS_FIRE_RATE = 0.04;

	/**
	 * Probability for the boss to activate its shield in each frame.
	 */
	private static final double BOSS_SHIELD_PROBABILITY = 0.002;

	/**
	 * Number of movements in a single cycle of the movement pattern.
	 */
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;

	/**
	 * Maximum number of consecutive frames the boss can move in the same direction.
	 */
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;

	/**
	 * Maximum number of frames the boss can have its shield activated.
	 */
	private static final int MAX_FRAMES_WITH_SHIELD = 150;

	/**
	 * Height of the boss image.
	 */
	private static final int IMAGE_HEIGHT = 50;

	/**
	 * Height of the toolbar in the game.
	 */
	private static final int TOOLBAR_HEIGHT = 90;

	/**
	 * The height of the game screen.
	 */
	private final int screenHeight;

	/**
	 * The list representing the movement pattern of the boss.
	 */
	private final List<Integer> movePattern;

	/**
	 * Flag indicating whether the boss's shield is currently active.
	 */
	private boolean isShielded;

	/**
	 * Counter for consecutive moves in the same direction.
	 */
	private int consecutiveMovesInSameDirection;

	/**
	 * Index of the current move in the movement pattern.
	 */
	private int indexOfCurrentMove;

	/**
	 * Counter for frames with the shield activated.
	 */
	private int framesWithShieldActivated;

	/**
	 * Listener for health change events.
	 */
	private Consumer<Integer> healthChangeListener;

	/**
	 * Constructs a {@code Boss} object with specified position, screen height, and a reference to the level it belongs to.
	 *
	 * @param initialX      the initial X-coordinate of the boss.
	 * @param initialY      the initial Y-coordinate of the boss.
	 * @param screenHeight  the height of the game screen.
	 * @param levelParent   the parent level to which the boss belongs.
	 */
	public Boss(double initialX, double initialY, int screenHeight, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY, 3, levelParent);
		this.screenHeight = screenHeight;
		this.movePattern = new ArrayList<>();
		this.isShielded = false;
		initializeMovePattern();
	}

	/**
	 * Retrieves the current health of the boss.
	 *
	 * @return the current health value.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Reduces the boss's health if the shield is inactive. Triggers a health change listener if set.
	 */
	@Override
	public void takeDamage() {
		if (!isShieldActive()) {
			super.takeDamage();
			if (healthChangeListener != null) {
				healthChangeListener.accept(getHealth());
			}
		}
	}

	/**
	 * Checks whether the boss's shield is currently active.
	 *
	 * @return {@code true} if the shield is active; {@code false} otherwise.
	 */
	public boolean isShieldActive() {
		return isShielded;
	}

	/**
	 * Updates the boss's behavior, including movement and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Updates the boss's position based on its movement pattern and ensures it stays within bounds.
	 */
	@Override
	public void updatePositionWhenActive() {
		double initialTranslateY = getTranslateY();

		moveVertically(getNextMove());

		double currentPosition = getLayoutY() + getTranslateY();

		if (currentPosition < TOOLBAR_HEIGHT || currentPosition > getLowerBound()) {
			setTranslateY(initialTranslateY); // Reset to the previous position if out of bounds
		}
	}

	/**
	 * Retrieves the lower boundary for the boss's movement.
	 *
	 * @return the lower boundary as an integer.
	 */
	private int getLowerBound() {
		return (int) (levelParent.getScreenHeight() - IMAGE_HEIGHT - 70);
	}

	/**
	 * Fires a projectile based on the boss's fire rate.
	 *
	 * @return a new {@link ActiveActor} projectile if the fire condition is met; {@code null} otherwise.
	 */
	@Override
	public ActiveActor fireProjectileWhenActive() {
		return Math.random() < BOSS_FIRE_RATE
				? ProjectileFactory.createProjectile("BOSS_PROJECTILE", 0, getProjectileInitialPosition(), levelParent)
				: null;
	}

	/**
	 * Calculates the initial Y-coordinate for the projectile based on the boss's current position and offset.
	 *
	 * @return the Y-coordinate for the projectile's starting position.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Updates the shield status of the boss. Handles the activation and deactivation of the shield
	 * based on predefined probabilities and time constraints.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			if (framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD) deactivateShield();
		} else if (Math.random() < BOSS_SHIELD_PROBABILITY) activateShield();
	}

	/**
	 * Activates the shield for the boss, preventing damage. Logs the shield activation for debugging.
	 */
	private void activateShield() {
		isShielded = true;
		framesWithShieldActivated = 0;
	}

	/**
	 * Deactivates the boss's shield and resets the activation counter. Logs the shield deactivation for debugging.
	 */
	private void deactivateShield() {
		isShielded = false;
	}

	/**
	 * Initializes the movement pattern for the boss. The pattern consists of alternating vertical movements
	 * and is randomized to create unpredictable movement behavior.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(8);
			movePattern.add(-8);
			movePattern.add(0);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Retrieves the next move in the boss's movement pattern. Shuffles the pattern after a defined number
	 * of consecutive moves in the same direction to maintain unpredictability.
	 *
	 * @return the next movement increment for the boss.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection >= MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}
}
