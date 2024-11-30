package com.example.demo;

public class EnemyPlaneTypeA extends EnemyParent {

    private static final String IMAGE_NAME = "enemyplaneA.png";

    public EnemyPlaneTypeA(double initialX, double initialY,LevelParent levelParent) {
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
        return Math.random() < 0.01 ? new EnemyProjectile(getProjectileXPosition(0), getProjectileYPosition(20)) : null;
    }
}
