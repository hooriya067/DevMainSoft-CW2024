package com.example.demo.actors.active.projectiles;

import com.example.demo.actors.active.ActiveActor;

public class StubProjectile extends ActiveActor {
    public StubProjectile() {
        super("dummy.jpg", 10, 0, 0);
    }

    @Override
    public void updatePosition() {}
    @Override
    public void updateActor() {}
}

