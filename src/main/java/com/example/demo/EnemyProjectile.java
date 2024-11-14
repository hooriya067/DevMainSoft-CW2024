package com.example.demo;

public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -10;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY);
	}
}