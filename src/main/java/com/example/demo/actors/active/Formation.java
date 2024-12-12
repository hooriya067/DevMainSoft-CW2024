package com.example.demo.actors.active;

import com.example.demo.actors.active.enemies.EnemyPlaneTypeA;
import com.example.demo.core.GameConfig;

import java.util.ArrayList;
import java.util.List;
/**
 * The {@code Formation} class represents a group of enemy planes that move together
 * in a structured pattern. The formation consists of a leader plane and follower planes,
 * with distinct behaviors depending on the leader's status.
 *
 * <p>
 * If the leader is destroyed, the follower planes diverge, changing their movement pattern.
 * </p>
 */
public class Formation {

    /**
     * List of enemy planes in the formation, including the leader and followers.
     */
    private final List<EnemyPlaneTypeA> formationUnits;

    /**
     * The leader of the formation, which dictates the formation's behavior.
     */
    private final EnemyPlaneTypeA leader;

    /**
     * A flag to indicate whether the leader of the formation has been destroyed.
     */
    private boolean leaderDestroyed = false;

    /**
     * Constructs a {@code Formation} object with a specified list of enemy planes.
     *
     * @param formationUnits the list of enemy planes in the formation.
     * @throws IllegalArgumentException if the formationUnits list is empty.
     */
    public Formation(List<EnemyPlaneTypeA> formationUnits) {
        this.formationUnits = new ArrayList<>(formationUnits);

        if (!formationUnits.isEmpty()) {
            this.leader = formationUnits.get(0);
            leader.setFitHeight(leader.getFitHeight() * 1.5); // Increase leader size for visual distinction
        } else {
            throw new IllegalArgumentException("Formation must have at least one unit!");
        }
    }

    /**
     * Updates the positions of all planes in the formation. The follower planes
     * adjust their positions relative to the leader, maintaining a structured pattern.
     * If the leader is destroyed, the formation behavior transitions to individual movements.
     */
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
        } else {
            onLeaderDestroyed();
        }
    }

    /**
     * Triggers the behavior when the leader of the formation is destroyed.
     * The follower planes begin independent downward movement with random rotations.
     */
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
                double rotationAngle = Math.random() < 0.5 ? 90 : -90; // Random rotation direction
                unit.setRotate(rotationAngle);
            }
        }
    }

    /**
     * Checks whether the leader of the formation has been destroyed.
     *
     * @return {@code true} if the leader is destroyed; {@code false} otherwise.
     */
    public boolean isLeaderDestroyed() {
        return leader != null && leader.isDestroyed();
    }
}
