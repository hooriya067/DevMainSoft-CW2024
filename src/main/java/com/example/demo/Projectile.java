package com.example.demo;


public class Projectile extends ActiveActorDestructible {
	private final double horizontalVelocity;  // Change from int to double

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity) {  // Change the parameter type to double
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.horizontalVelocity = horizontalVelocity;
	}


	@Override
	public void takeDamage() {
		this.destroy();
	}

	public void updatePosition() {
		if (GameStateManager.isPaused) {
			return;  // Skip updating position if paused
		}
		moveHorizontally(horizontalVelocity);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}

