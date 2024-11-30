package com.example.demo;

public class EnemyPlaneTypeB extends EnemyParent {

    private final LevelParent levelParent;

    public EnemyPlaneTypeB(double initialX, double initialY, LevelParent levelParent) {
        super("enemyplaneB.png", 60, initialX, initialY, 3,levelParent);
        this.levelParent = levelParent;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void updatePositionWhenActive() {
        moveHorizontally(-2);
    }

    @Override
    public ActiveActorDestructible fireProjectileWhenActive() {
        UserPlane userPlane = levelParent.getUser(); // Access UserPlane from LevelParent
        return Math.random() < 0.0009
                ? new HomingMissile(getProjectileXPosition(0), getProjectileYPosition(20), userPlane)
                : null;
    }
}
