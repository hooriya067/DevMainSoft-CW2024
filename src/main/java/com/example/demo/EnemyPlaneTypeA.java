package com.example.demo;

public class EnemyPlaneTypeA extends FighterPlane {

    private static final String IMAGE_NAME = "enemyplaneA.png";
    private static final int IMAGE_HEIGHT = 60;
    private static final double INITIAL_VELOCITY = -3.0;

    private final LevelParent levelParent;

    public EnemyPlaneTypeA(double initialX, double initialY, LevelParent levelParent) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY, 2);
        setHorizontalVelocity(INITIAL_VELOCITY);
        this.levelParent = levelParent;
    }
    @Override
    public ActiveActorDestructible fireProjectile() {
        double probability = 0.01;
        return (Math.random() < probability) ? new EnemyProjectileLevelThree(getProjectileXPosition(0), getProjectileYPosition(20)) : null;
    }


    @Override
    public void updatePosition() {
        if (GameStateManager.isPaused) {
            return;  // Skip updating position if paused
        }
        // Basic horizontal movement
        moveHorizontally(getHorizontalVelocity());
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}
