package com.example.demo;

public abstract class Projectile extends ActiveActorDestructible {

	protected int horizontalVelocity;

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, int horizontalVelocity) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.horizontalVelocity = horizontalVelocity;
	}

	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	public void takeDamage() {
		this.destroy();
	}
}

