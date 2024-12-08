package com.example.demo.actors.active.enemies;

import com.example.demo.actors.active.ActiveActor;

public interface Enemy {
    void updatePosition();
    void updateActor();
    ActiveActor fireProjectile();
    void takeDamage();
    void destroy();
}

