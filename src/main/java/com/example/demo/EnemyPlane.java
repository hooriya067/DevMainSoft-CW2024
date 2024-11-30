package com.example.demo;

public class EnemyPlane extends EnemyParent {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final double FIRE_RATE = 0.01;

	public EnemyPlane(double initialXPos, double initialYPos,LevelParent levelParent) {
		super(IMAGE_NAME, 60, initialXPos, initialYPos, 1, levelParent);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public void updatePositionWhenActive() {
		moveHorizontally(-6);
	}

	@Override
	public ActiveActorDestructible fireProjectileWhenActive() {
		if (Math.random() < FIRE_RATE) {
			return new EnemyProjectile(getProjectileXPosition(0), getProjectileYPosition(20));
		}
		return null;
	}
}
