package com.example.demo;

import java.util.List;

public class Formation {
    private List<EnemyPlaneTypeA> formationUnits;
    private EnemyPlaneTypeA leader;

    public Formation(List<EnemyPlaneTypeA> formationUnits) {
        this.formationUnits = formationUnits;


        if (!formationUnits.isEmpty()) {
            this.leader = formationUnits.get(0); // Set the first plane as the leader
            // Make the leader bigger
            leader.setFitHeight(leader.getFitHeight() * 1.5); // Increase the size by 1.5x
        }
    }

    public void updateFormationPosition() {
        if (leader != null && !leader.isDestroyed()) {
            double leaderX = leader.getTranslateX();
            double leaderY = leader.getTranslateY();

            // All other units follow the leader in a V-shape
            for (int i = 1; i < formationUnits.size(); i++) {
                EnemyPlaneTypeA unit = formationUnits.get(i);
                if (!unit.isDestroyed()) {
                    double offsetX = (i % 2 == 0) ? -50 : 50; // Adjust for V-shape formation
                    double offsetY = 50 * (i / 2);
                    unit.setTranslateX(leaderX + offsetX);
                    unit.setTranslateY(leaderY + offsetY);
                }
            }
        }
    }

    public void onLeaderDestroyed() {
        // Scatter formation when the leader is destroyed
        for (EnemyPlaneTypeA unit : formationUnits) {
            if (!unit.isDestroyed()) {
                unit.setHorizontalVelocity(unit.getHorizontalVelocity() - 2); // Change direction and speed
            }
        }
    }

    public boolean isLeaderDestroyed() {
        return leader != null && leader.isDestroyed();
    }
}

