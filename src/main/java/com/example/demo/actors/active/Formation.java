package com.example.demo.actors.active;

import com.example.demo.actors.active.enemies.EnemyPlaneTypeA;
import com.example.demo.core.GameConfig;

import java.util.ArrayList;
import java.util.List;

public class Formation {
    private final List<EnemyPlaneTypeA> formationUnits;
    private final EnemyPlaneTypeA leader;
    private boolean leaderDestroyed = false;

    public Formation(List<EnemyPlaneTypeA> formationUnits) {
        this.formationUnits = new ArrayList<>(formationUnits);

        if (!formationUnits.isEmpty()) {
            this.leader = formationUnits.get(0);
            leader.setFitHeight(leader.getFitHeight() * 1.5); // Increase leader size
        } else {
            throw new IllegalArgumentException("Formation must have at least one unit!");
        }
    }

    public void updateFormationPosition() {
        if (!leaderDestroyed && leader != null && !leader.isDestroyed()) {
            double leaderX = leader.getTranslateX();
            double leaderY = leader.getTranslateY();

            for (int i = 1; i < formationUnits.size(); i++) {
                EnemyPlaneTypeA unit = formationUnits.get(i);
                if (!unit.isDestroyed()) {
                    double offsetX = (i % 2 == 0) ? -60 * (i / 2) : 60 * (i / 2); // Alternating horizontal offset
                    double offsetY = 50 * (i / 2); // Vertical spacing increases with each row
                    unit.setTranslateX(leaderX + offsetX);
                    unit.setTranslateY(leaderY + offsetY);
                }
            }
        } else{onLeaderDestroyed();}
    }
    public void onLeaderDestroyed() {
        leaderDestroyed = true;
        for (EnemyPlaneTypeA unit : formationUnits) {
            if (!unit.isDestroyed()) {
                unit.setHorizontalVelocity(0); // Stop horizontal movement
                unit.setOnUpdate(() -> {
                    unit.moveVertically(7); // Move downward
                    if (unit.getTranslateY() > GameConfig.SCREEN_HEIGHT) {
                        unit.destroy();
                    }
                });
                double rotationAngle = Math.random() < 0.5 ? 90 : -90;
                unit.setRotate(rotationAngle);
            }
        }
    }
    public boolean isLeaderDestroyed() {
        return leader != null && leader.isDestroyed();
    }

}
