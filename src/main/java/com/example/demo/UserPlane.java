package com.example.demo;

import com.example.demo.controller.Controller;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane1.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 70;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8; // Velocity for left and right movement
	private static final double TOOLBAR_HEIGHT = 70;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

	private int velocityMultiplier; // For vertical movement
	private int horizontalVelocityMultiplier; // For horizontal movement

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	@Override
	public void updatePosition() {
		if (isMoving()) {
			// Save the initial position
			double initialTranslateY = getTranslateY();
			double initialTranslateX = getTranslateX();

			// Vertical movement
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newYPosition = getLayoutY() + getTranslateY();

			// Ensure the new Y position respects the toolbar boundary at the top
			double adjustedUpperBound = TOOLBAR_HEIGHT; // New upper bound considering the toolbar
			if (newYPosition < adjustedUpperBound || newYPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}

			// Horizontal movement
			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			double newXPosition = getLayoutX() + getTranslateX();

			// Ensure the new X position stays within screen boundaries
			double screenWidth = getScene().getWidth(); // Assuming the scene's width represents the game screen width
			if (newXPosition < 0 || newXPosition > screenWidth - getFitWidth()) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
	}


	@Override
	public ActiveActorDestructible fireProjectile() {
		double currentXPosition = getLayoutX() + getTranslateX() + getFitWidth() +120 ;
		double currentYPosition = getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
		return new UserProjectile(currentXPosition, currentYPosition);
	}


	// Methods for vertical movement
	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stopVerticalMovement() {
		velocityMultiplier = 0;
	}

	// Methods for horizontal movement
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	public void stopHorizontalMovement() {
		horizontalVelocityMultiplier = 0;
	}

	private boolean isMoving() {
		return velocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}


//
//	public int getNumberOfKills() {
//		return numberOfKills;
//	}
//
//	public void incrementKillCount() {
//		numberOfKills++;
//	}
//
}
