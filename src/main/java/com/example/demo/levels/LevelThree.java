package com.example.demo.levels;

import com.example.demo.Managers.AlertManager;
import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.levels.view.LevelView;
import com.example.demo.levels.view.LevelViewLevelThree;
import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.active.Formation;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.actors.active.enemies.EnemyPlaneTypeA;
import java.util.ArrayList;
import java.util.List;


public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 4;
    private static final int TOOLBAR_HEIGHT = 90;
    private Formation enemyFormation;



    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.inputHandler.setMovementMode(InputHandlingManager.MovementMode.FULL);
        AlertManager.getInstance().showInfoAlert("Left and Right Moves \n  allowed now!",
                screenWidth,
                screenHeight
        );
    }

    @Override
    protected void spawnEnemyUnits() {
        double spawnProbability = 0.09; //probability for individual enemies
        double formationSpawnProbability = 0.009; // Probability for spawning a new formation

        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = TOOLBAR_HEIGHT + (Math.random() * (getEnemyMaximumYPosition() - TOOLBAR_HEIGHT));
            String enemyType = Math.random() < 0.39 ? "TYPEA" : "TYPEB";
            ActiveActorDestructible newEnemy = EnemyFactory.createEnemy(enemyType, getScreenWidth(), randomYPosition, this);
            addEnemyUnit(newEnemy);
        }

        if (enemyFormation == null && Math.random() < formationSpawnProbability) {
            List<EnemyPlaneTypeA> formationUnits = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                double yPosition = TOOLBAR_HEIGHT + 50 + i * 50; // V-shape spacing
                formationUnits.add(new EnemyPlaneTypeA(getScreenWidth(), yPosition, this));
            }
            enemyFormation = new Formation(formationUnits);
            formationUnits.forEach(this::addEnemyUnit);
        }

        if (enemyFormation != null) {
            enemyFormation.updateFormationPosition();

            // Handle leader destruction and reset the formation
            if (enemyFormation.isLeaderDestroyed()) {
                enemyFormation.onLeaderDestroyed();
                enemyFormation = null; // Reset the formation for a new one later
            }
        }
    }
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(),this);
    }
    @Override
    protected void updateSceneFurther() {
            if (enemyFormation != null) {
            enemyFormation.updateFormationPosition();

            if (enemyFormation.isLeaderDestroyed()) {
                enemyFormation.onLeaderDestroyed();
                enemyFormation = null;
            }
        }
    }
    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
    @Override
    public int calculateOptimalBullets() {
        return (KILLS_TO_ADVANCE/2)*2+KILLS_TO_ADVANCE/2;
    }
}