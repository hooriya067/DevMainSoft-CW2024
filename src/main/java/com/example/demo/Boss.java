package com.example.demo;

import java.util.*;
import java.util.function.Consumer;




public class Boss extends EnemyParent {
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final String IMAGE_NAME = "bossplane.png";
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .002;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int MAX_FRAMES_WITH_SHIELD = 150;
	private static final int IMAGE_HEIGHT = 50;
	private static final int TOOLBAR_HEIGHT = 90;

	private final int screenHeight;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private Consumer<Integer> healthChangeListener; // Listener for health changes
	public Boss(double initialX, double initialY, int screenHeight,LevelParent levelParent) {
		super(IMAGE_NAME, 50, initialX, initialY, 10, levelParent);
		this.screenHeight = screenHeight;
		this.movePattern = new ArrayList<>();
		this.isShielded = false;
		initializeMovePattern();
	}
	public int getHealth() {
		return health;
	}
	@Override
	public void takeDamage() {
		if (!isShieldActive()) {
			super.takeDamage();
			if (healthChangeListener != null) {
				healthChangeListener.accept(getHealth());
			}
		}
	}
	public boolean isShieldActive() {
		return isShielded;
	}
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public void updatePositionWhenActive() {
		double initialTranslateY = getTranslateY();

		moveVertically(getNextMove());

		double currentPosition = getLayoutY() + getTranslateY();

		if (currentPosition < TOOLBAR_HEIGHT || currentPosition > getLowerBound()) {
			setTranslateY(initialTranslateY); // Reset to the previous position if out of bounds
		}
	}

	private int getLowerBound() {
		return (int) (levelParent.getScreenHeight() - IMAGE_HEIGHT - 70);
	}


	@Override
	public ActiveActorDestructible fireProjectileWhenActive() {{
			return Math.random() < BOSS_FIRE_RATE
					? ProjectileFactory.createProjectile("BOSS_PROJECTILE",0, getProjectileInitialPosition(), levelParent)
					: null;
		}
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			if (framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD) deactivateShield();
		} else if (Math.random() < BOSS_SHIELD_PROBABILITY) activateShield();
	}

	private void activateShield() {
		isShielded = true;
		framesWithShieldActivated = 0;
		System.out.println("Boss Shield Activated");
	}

	private void deactivateShield() {
		isShielded = false;
		System.out.println("Boss Shield Deactivated");
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(8);
			movePattern.add(-8);
			movePattern.add(0);
		}
		Collections.shuffle(movePattern);
	}

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



