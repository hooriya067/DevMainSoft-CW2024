package com.example.demo.actors.active.enemies;

import com.example.demo.levels.LevelParent;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.active.ActiveActorDestructible;

public class EnemyPlaneTypeA extends EnemyParent {

    private static final String IMAGE_NAME = "enemyplaneA.png";
    private static final double FIRE_RATE = 0.01;
    public EnemyPlaneTypeA(double initialX, double initialY, LevelParent levelParent) {
        super(IMAGE_NAME, 60, initialX, initialY, 2, levelParent);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void updatePositionWhenActive() {
        moveHorizontally(-3);
    }

    @Override
    public ActiveActorDestructible fireProjectileWhenActive() {
        return Math.random() < FIRE_RATE
                ? ProjectileFactory.createProjectile("ENEMY_PROJECTILE_LEVEL_THREE", getProjectileXPosition(0), getProjectileYPosition(20), levelParent)
                : null;}
}
