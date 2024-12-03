package com.example.demo.levels;

import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.levels.view.LevelView;
import com.example.demo.levels.view.LevelViewLevelThree;
import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.active.Formation;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.actors.active.enemies.EnemyPlaneTypeA;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;


public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 2;
    private static final int TOOLBAR_HEIGHT = 90;

    private Label killsLabel;


    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.inputHandler.setMovementMode(InputHandlingManager.MovementMode.FULL);

    }
    @Override
    protected void spawnEnemyUnits() {
        double spawnProbability = 0.1;

        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = TOOLBAR_HEIGHT + (Math.random() * (getEnemyMaximumYPosition() - TOOLBAR_HEIGHT));

            String enemyType = Math.random() < 0.4 ? "TYPEA" : "TYPEB";

            // Use the factory to create the enemy
            ActiveActorDestructible newEnemy = EnemyFactory.createEnemy(
                    enemyType,
                    getScreenWidth(),
                    randomYPosition,
                    this
            );

            addEnemyUnit(newEnemy); // Add the enemy to the level
        }

        if (getCurrentNumberOfEnemies() == 0) {
            // Create a formation of EnemyPlaneTypeA, ensuring no unit goes above the toolbar height
            List<EnemyPlaneTypeA> formationUnits = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                double yPosition = TOOLBAR_HEIGHT + 50 + i * 50; // Start below the toolbar and space units
                formationUnits.add(new EnemyPlaneTypeA(getScreenWidth(), yPosition, this));
            }

            Formation formation = new Formation(formationUnits);
            formationUnits.forEach(this::addEnemyUnit);

            // If the leader is destroyed, scatter the formation
            if (formation.isLeaderDestroyed()) {
                formation.onLeaderDestroyed();
            }
        }
    }



    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(),this);
    }

    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
    @Override
    public int calculateOptimalBullets() {
        return (KILLS_TO_ADVANCE/2)*2+KILLS_TO_ADVANCE/2;
    }
    @Override
    protected void misc() {}
}