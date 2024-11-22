package com.example.demo;

import com.example.demo.controller.Controller;

import java.util.*;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .002;
	private static final int IMAGE_HEIGHT = 50;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 10;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int MAX_FRAMES_WITH_SHIELD = 150; //500 for testing
	private static final int TOOLBAR_HEIGHT = 70;
	private static final int BOTTOM_BUFFER = 70;  // Buffer to ensure boss stays within bounds

	private final int screenHeight;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	public Boss(int screenHeight) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.screenHeight = screenHeight;
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	public boolean getIsShielded() {
		return isShielded;
	}

	@Override
	public void updatePosition() {
		if (GameStateManager.isPaused) {
			return;  // Skip updating position if paused
		}
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < getUpperBound() || currentPosition > getLowerBound()) {
			setTranslateY(initialTranslateY); // Reset to previous position if out of bounds
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			if (shieldExhausted()) deactivateShield();
		} else if (shieldShouldBeActivated()) {
			activateShield();
		}
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
		framesWithShieldActivated = 0;
		System.out.println("Shield Activated");
	}

	private void deactivateShield() {
		isShielded = false;
		System.out.println("Shield Deactivated");
	}

	private int getUpperBound() {
		return TOOLBAR_HEIGHT; // Top of the screen plus the height of the toolbar
	}


	private int getLowerBound() {
		return screenHeight - IMAGE_HEIGHT - BOTTOM_BUFFER; // Bottom of the screen accounting for Boss height and a buffer
	}}
