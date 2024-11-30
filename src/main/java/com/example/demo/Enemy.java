package com.example.demo;

public interface Enemy {
    void updatePosition();
    void updateActor();
    ActiveActorDestructible fireProjectile();
    void takeDamage();
    void destroy();
}

