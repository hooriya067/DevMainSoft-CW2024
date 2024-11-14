//package com.example.demo;
//
//public abstract class Projectile extends ActiveActorDestructible {
//
//	protected int horizontalVelocity;
//
//	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, int horizontalVelocity) {
//		super(imageName, imageHeight, initialXPos, initialYPos);
//		this.horizontalVelocity = horizontalVelocity;
//	}
//
//	@Override
//	public void updatePosition() {
//		moveHorizontally(horizontalVelocity);
//	}
//
//	@Override
//	public void updateActor() {
//		updatePosition();
//	}
//
//	public void takeDamage() {
//		this.destroy();
//	}
//}

package com.example.demo;

public abstract class Projectile extends ActiveActorDestructible {

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	@Override
	public void takeDamage() {
		this.destroy();
	}

	@Override
	public abstract void updatePosition();

}