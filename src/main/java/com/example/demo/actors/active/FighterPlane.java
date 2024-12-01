package com.example.demo.actors.active;

public abstract class FighterPlane extends ActiveActorDestructible {

	protected int health;
	private double horizontalVelocity; // Add horizontal velocity property

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	// Getter method for horizontal velocity
	public double getHorizontalVelocity() {
		return horizontalVelocity;
	}

	// Setter method for horizontal velocity
	public void setHorizontalVelocity(double horizontalVelocity) {
		this.horizontalVelocity = horizontalVelocity;
	}

	public abstract ActiveActorDestructible fireProjectile();

//		if (GameStateManager.isPaused) {
//			return;
//		}



	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {
		return health == 0;
	}

	public int getHealth() {
		return health;
	}
}