package com.example.demo.Managers;

import com.example.demo.levels.LevelParent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PowerUpManager {
    private static PowerUpManager instance;
    private LevelParent levelParent; // Set during initialization
    private Timeline shieldTimer; // Shield timer

    private PowerUpManager() {}

    public static PowerUpManager getInstance() {
        if (instance == null) {
            instance = new PowerUpManager();
        }
        return instance;
    }

    public void setLevelParent(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    public boolean purchaseExtraLife() {
        if (CoinSystemManager.getInstance().subtractCoins(2)) {
            System.out.println("Extra life granted!");
            levelParent.getUser().addHealth(1); // Update user health
            levelParent.getLevelView().addHeart(); // Notify LevelView
            return true;
        }
        return false;
    }

    public boolean purchaseShield() {
        if (levelParent.isShieldActive()) {
            System.err.println("Shield already active!");
            return false;
        }
        if (CoinSystemManager.getInstance().subtractCoins(2)) {
            activateShield();
            return true;
        }
        return false;
    }

    private void activateShield() {
      //  System.out.println("Shield activated!");

        levelParent.getUserShield().showShield(); // Show shield
        levelParent.getLevelView().startShieldTimer(20);
        shieldTimer = new Timeline(
                new KeyFrame(Duration.seconds(20), e -> deactivateShield())
        );
        shieldTimer.setCycleCount(1);
        shieldTimer.play();
    }

    private void deactivateShield() {
        System.out.println("Shield deactivated!");

        levelParent.getUserShield().hideShield(); // Hide shield
        if (shieldTimer != null) {
            shieldTimer.stop();
            shieldTimer = null;
        }
    }
}

