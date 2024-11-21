package com.example.demo;
import com.example.demo.controller.Controller;


public abstract class Projectile extends ActiveActorDestructible {

	private final int horizontalVelocity;

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, int horizontalVelocity) {
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
