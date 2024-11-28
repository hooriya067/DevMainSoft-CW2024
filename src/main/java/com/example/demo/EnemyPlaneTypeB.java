package com.example.demo;

public class EnemyPlaneTypeB extends FighterPlane {

    private static final String IMAGE_NAME = "enemyplaneB.png";
    private static final int IMAGE_HEIGHT = 60;
    private static final double INITIAL_VELOCITY = -2.0;
    private static final int HORIZONTAL_VELOCITY = -2;

    private final LevelParent levelParent;

    public EnemyPlaneTypeB(double initialX, double initialY, LevelParent levelParent) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY, 3);
        setHorizontalVelocity(INITIAL_VELOCITY);
        this.levelParent = levelParent;
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (GameStateManager.isPaused) {
            return null;
        }
        double probability = 0.0009;
        UserPlane userPlane = levelParent.getUser();  // Access the user plane
        return (Math.random() < probability) ? new HomingMissile(getProjectileXPosition(0), getProjectileYPosition(20), userPlane) : null;
    }

    @Override
    public void updatePosition() {
        if (GameStateManager.isPaused) {
            return;  // Skip updating position if paused
        }
        // Heavier movement
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}
