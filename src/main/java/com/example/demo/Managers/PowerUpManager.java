package com.example.demo.Managers;

import com.example.demo.Levels.LevelParent;
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
            levelParent.getUser().addHealth(1); // Update user health
            levelParent.getLevelView().addHeart(); // Notify LevelViewParent
            return true;
        }
        return false;
    }

    public boolean purchaseShield() {
        if (levelParent.isShieldActive()) {
            return false;
        }
        if (CoinSystemManager.getInstance().subtractCoins(2)) {
            activateShield();
            return true;
        }
        return false;
    }
    private void activateShield() {
          levelParent.getUserShield().showShield(); // Show shield
        levelParent.getLevelView().startShieldTimer(20);
        shieldTimer = new Timeline(
                new KeyFrame(Duration.seconds(20), e -> deactivateShield())
        );
        shieldTimer.setCycleCount(1);
        shieldTimer.play();
    }

    private void deactivateShield() {
              levelParent.getUserShield().hideShield(); // Hide shield
        if (shieldTimer != null) {
            shieldTimer.stop();
            shieldTimer = null;
        }
    }
    public boolean purchaseBullets() {
        int bulletCost = 3; // Cost in coins
        int bulletsToAdd = 10; // Number of bullets to add

        if (CoinSystemManager.getInstance().subtractCoins(bulletCost)) {
            System.out.println("Purchased " + bulletsToAdd + " bullets!");
            BulletSystemManager.getInstance().addBullets(bulletsToAdd); // Update bullet count
            return true;
        }
        System.err.println("Not enough coins to purchase bullets!");
        return false;
    }


}

