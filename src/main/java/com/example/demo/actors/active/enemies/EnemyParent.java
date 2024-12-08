package com.example.demo.actors.active.enemies;

import com.example.demo.actors.active.FighterPlane;
import com.example.demo.levels.LevelParent;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.core.GameStateManager;

public abstract class EnemyParent extends FighterPlane implements Enemy {
    protected int health;
    protected LevelParent levelParent;

    public EnemyParent(String imageName, int imageHeight, double initialX, double initialY, int health, LevelParent levelParent) {
        super(imageName, imageHeight, initialX, initialY, health);
        this.health = health;
        this.levelParent = levelParent;
    }

    @Override
    public void takeDamage() {
        health--;
        if (health <= 0) {
            destroy();
        }
    }

    @Override
    public ActiveActor fireProjectile() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return null;
        }
        return fireProjectileWhenActive(); // Delegate to abstract method
    }

    protected abstract ActiveActor fireProjectileWhenActive();

    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }
        updatePositionWhenActive(); // Delegate to abstract method
    }

    protected abstract void updatePositionWhenActive();
}
