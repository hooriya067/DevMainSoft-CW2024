package com.example.demo;

public class PowerUpManager {
    private static PowerUpManager instance;
    private LevelParent levelParent; // Set when initializing the game

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
        if (levelParent != null && CoinSystem.getInstance().subtractCoins(10)) {
            levelParent.grantExtraLife();
            return true;
        }
        return false;
    }


    public boolean purchaseShield() {
        int cost = 5; // Cost of the shield power-up
        if (levelParent != null && CoinSystem.getInstance().subtractCoins(cost)) {
            levelParent.activateShieldForUser();
            return true;
        }
        return false;
    }

}
