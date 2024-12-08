package com.example.demo.actors.active.enemies;

import com.example.demo.levels.LevelParent;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.actors.active.ActiveActor;

public class EnemyPlaneTypeA extends EnemyParent {

    private static final String IMAGE_NAME = "enemyplaneA.png";
    private static final double FIRE_RATE = 0.005;
    private Runnable onUpdate;
    public EnemyPlaneTypeA(double initialX, double initialY, LevelParent levelParent) {
        super(IMAGE_NAME, 60, initialX, initialY, 2, levelParent);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    public void setOnUpdate(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }

    @Override
    public void updatePositionWhenActive() {
        if (onUpdate==null){

        moveHorizontally(-5);
        double verticalMove = Math.sin(System.currentTimeMillis() / 500.0) * 2; // Oscillate
        moveVertically(verticalMove);}

        if (onUpdate != null) {
            onUpdate.run();
        }
    }

    @Override
    public ActiveActor fireProjectileWhenActive() {
        return Math.random() < FIRE_RATE
                ? ProjectileFactory.createProjectile("ENEMY_PROJECTILE_LEVEL_THREE", getProjectileXPosition(0), getProjectileYPosition(20), levelParent)
                : null;}

}
