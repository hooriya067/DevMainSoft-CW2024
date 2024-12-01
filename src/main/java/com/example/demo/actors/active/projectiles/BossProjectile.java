package com.example.demo.actors.active.projectiles;

public class BossProjectile extends Projectile {

	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos, HORIZONTAL_VELOCITY);
	}
}

//ADDITIONS:-
//
//		- `updatePosition()`: Moves the projectile horizontally by a constant velocity (`HORIZONTAL_VELOCITY`), effectively updating its X position.
//		- `updateActor()` : Calls `updatePosition()`, which means it also updates the position of the projectile.
//	                     	This method is typically used as part of the game loop to refresh the state of the actor every frame.