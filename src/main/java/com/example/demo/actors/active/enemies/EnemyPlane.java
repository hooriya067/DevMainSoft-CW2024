package com.example.demo.actors.active.enemies;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.active.ActiveActor;

public class EnemyPlane extends EnemyParent {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final double FIRE_RATE = 0.01;

	public EnemyPlane(double initialXPos, double initialYPos, LevelParent levelParent) {
		super(IMAGE_NAME, 70, initialXPos, initialYPos, 1, levelParent);
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
	public ActiveActor fireProjectileWhenActive() {
		return Math.random() < FIRE_RATE
					? ProjectileFactory.createProjectile("ENEMY_PROJECTILE", getProjectileXPosition(0), getProjectileYPosition(20), levelParent)
					: null;}


}
