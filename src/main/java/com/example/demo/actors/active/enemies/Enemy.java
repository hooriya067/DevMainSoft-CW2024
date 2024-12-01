package com.example.demo.actors.active.enemies;

import com.example.demo.actors.active.ActiveActorDestructible;

public interface Enemy {
    void updatePosition();
    void updateActor();
    ActiveActorDestructible fireProjectile();
    void takeDamage();
    void destroy();
}

