package com.example.demo.levels;

import com.example.demo.Managers.AlertManager;
import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.levels.view.LevelVeiwLevelFive;
import com.example.demo.levels.view.LevelView;
import javafx.animation.Timeline;

public class LevelFive extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background5.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 8;
    private static final int KILLS_TO_ADVANCE = 2;

 //   private boolean meteorStormActive = true; // Flag to handle meteor storm phase
    private Timeline cloudTimeline;

    public LevelFive(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.inputHandler.setMovementMode(InputHandlingManager.MovementMode.FULL);

        AlertManager.getInstance().showInfoAlert(
                "SHOWDOWNNN!!!",
                screenWidth,
                screenHeight
        );

    }

    @Override
    protected void spawnEnemyUnits() {
//        if (meteorStormActive) {
//            spawnMeteors();
//        }
        double spawnProbability = 0.25; // 25% chance to spawn an enemy per update
        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = 40 + (Math.random() * (getEnemyMaximumYPosition() - 40));
            String enemyType;
            double enemyTypeChance = Math.random();
            if (enemyTypeChance < 0.6) {
                enemyType = "ENEMY1";
            } else if (enemyTypeChance < 0.8) {
                enemyType = "TYPEA";
            } else {
                enemyType = "TYPEB";
            }
            ActiveActorDestructible newEnemy = EnemyFactory.createEnemy(enemyType, getScreenWidth(), randomYPosition, this);
            addEnemyUnit(newEnemy); // Add the enemy to the level
        }
    }
//
//    private void spawnMeteors() {
//        double meteorProbability = 0.3; // 30% chance to spawn a meteor
//        if (Math.random() < meteorProbability) {
//            Meteor meteor = new Meteor(this);
//            addProjectileToLevel(meteor); // Add meteor to the level
//           // System.out.println("Meteor spawned at X: " + meteor.getTranslateX() + ", Y: " + meteor.getTranslateY());
//        }
//    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelVeiwLevelFive(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
    }

    @Override
    public int calculateOptimalBullets() {
        return KILLS_TO_ADVANCE * 2;
    }

    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    @Override
    protected void updateSceneFurther() {
        super.updateSceneFurther();
      //  misc();
        ((LevelVeiwLevelFive) getLevelView()).updateClouds(); // Update clouds via LevelVeiwLevelFive
        stopLevel(); // Stop level if conditions are met
    }

//    protected void misc() {
//        if (getNumberOfKills() > 5) {
//            meteorStormActive = false; // Disable meteor storm after 5 kills
//        }
//    }


    public void stopLevel() {
        if(userHasReachedKillTarget()){
        if (cloudTimeline != null) cloudTimeline.stop();
    }
}}

