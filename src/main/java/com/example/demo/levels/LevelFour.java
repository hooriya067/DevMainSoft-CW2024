
package com.example.demo.levels;

import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.actors.active.ActiveActorDestructible;
import com.example.demo.actors.collectibles.FlarePowerUp;
import com.example.demo.actors.active.enemies.EnemyParent;
import com.example.demo.actors.active.Factories.EnemyFactory;
import com.example.demo.actors.active.enemies.StealthEnemyPlane;
import com.example.demo.actors.active.Factories.ProjectileFactory;
import com.example.demo.core.GameStateManager;
import com.example.demo.levels.view.LevelView;
import com.example.demo.levels.view.LevelViewLevelFour;

import java.util.ArrayList;
import java.util.List;

public class LevelFour extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/BG4.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE =2;


    private List<StealthEnemyPlane> stealthEnemies;
    private final List<FlarePowerUp> powerUps = new ArrayList<>();





    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.inputHandler.setMovementMode(InputHandlingManager.MovementMode.FULL);

        stealthEnemies = new ArrayList<>();

    }
    @Override
    protected void spawnEnemyUnits() {
        double spawnProbability = 0.15; // Probability for spawning enemies
        if (Math.random() < spawnProbability && getCurrentNumberOfEnemies() < TOTAL_ENEMIES) {
            double randomYPosition = 70 + (Math.random() * (getEnemyMaximumYPosition() - 70));

            EnemyParent newStealthEnemy = EnemyFactory.createEnemy("STEALTH", getScreenWidth(), randomYPosition, this);
            addEnemyUnit(newStealthEnemy);

            // Ensure stealth enemies are added to the dedicated list
            if (newStealthEnemy instanceof StealthEnemyPlane) {
                stealthEnemies.add((StealthEnemyPlane) newStealthEnemy);
            }
        }
    }
    private void spawnBombs() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }
        double spawnProbability = 0.01; // Adjust this value for bomb frequency
        if (Math.random() < spawnProbability) {
            double randomXPosition = Math.random() * getScreenWidth();
            ActiveActorDestructible bomb = ProjectileFactory.createProjectile("BOMB", randomXPosition, 0, null);
            addProjectileToLevel(bomb);
        }
    }

    private void spawnFlarePowerUp() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }
        double spawnProbability = 0.03; // Adjust this value to change the frequency of flare power-ups
        if (Math.random() < spawnProbability) {
            double randomXPosition = Math.random() * getScreenWidth(); // Random X position for the flare
            FlarePowerUp flarePowerUp = new FlarePowerUp(randomXPosition, 0, this); // Flare starts from the top (Y = 0)
            powerUps.add(flarePowerUp);
            getRoot().getChildren().add(flarePowerUp);
        }
    }
    private void handlePowerUpCollisions() {
        // Iterate through all power-ups to check if the user plane collects them
        List<FlarePowerUp> toRemove = new ArrayList<>();
        for (FlarePowerUp powerUp : powerUps) {
            if (powerUp.getBoundsInParent().intersects(getUser().getBoundsInParent())) {
                powerUp.activate(getUser()); // Activate the flare
                toRemove.add(powerUp); // Mark for removal after activation
            }
        }
        // Remove collected power-ups from the list and root node
        powerUps.removeAll(toRemove);
        getRoot().getChildren().removeAll(toRemove);
    }



    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, getScreenWidth(), getScreenHeight(), this);
    }


    @Override
    protected boolean userHasReachedKillTarget() {
        return getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    private void updatePowerUps() {
        for (FlarePowerUp powerUp : powerUps) {
            powerUp.updateActor(); // Ensure that the position is updated
        }
    }
    @Override
    protected void updateSceneFurther() {
        super.updateSceneFurther();
        spawnBombs();
        spawnFlarePowerUp();
        handlePowerUpCollisions();
        updatePowerUps();
    }
    // Method to reveal stealth enemies
    public void revealStealthEnemies(boolean reveal) {
        for (StealthEnemyPlane stealthEnemy : stealthEnemies) {
            if (!stealthEnemy.isDestroyed()) {
                stealthEnemy.setVisible(reveal);
            }
        }
    }
    @Override
    public int calculateOptimalBullets() {
        return KILLS_TO_ADVANCE*3;
    }
   }

