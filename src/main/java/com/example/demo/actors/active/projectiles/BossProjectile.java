package com.example.demo.actors.active.projectiles;

public class BossProjectile extends ProjectileParent {

	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos, HORIZONTAL_VELOCITY);
	}
}
