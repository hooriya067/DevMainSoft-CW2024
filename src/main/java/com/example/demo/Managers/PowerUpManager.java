/**
 * The {@code PowerUpManager} class is responsible for managing power-up purchases and activations
 * within the game. It allows the player to purchase additional lives, shields, and bullets,
 * and ensures proper integration with the {@link LevelParent} and coin/bullet systems.
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link LevelParent}: Provides level-specific functionality such as user shield management.</li>
 *     <li>{@link CoinSystemManager}: Manages the coin balance for power-up purchases.</li>
 *     <li>{@link BulletSystemManager}: Handles bullet inventory when bullets are purchased.</li>
 *     <li>{@link javafx.animation.Timeline}: Used to manage shield duration with a timer.</li>
 * </ul>
 */
package com.example.demo.Managers;

import com.example.demo.Levels.LevelParent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Manages power-up functionality, including purchases and activations.
 */
public class PowerUpManager {

    /**
     * Singleton instance of the PowerUpManager.
     */
    private static PowerUpManager instance;

    /**
     * The {@link LevelParent} associated with this power-up manager.
     */
    private LevelParent levelParent;

    /**
     * Timer for managing the duration of the shield power-up.
     */
    private Timeline shieldTimer;

    /**
     * Private constructor to enforce the Singleton pattern.
     */
    private PowerUpManager() {}

    /**
     * Retrieves the singleton instance of the PowerUpManager.
     *
     * @return the singleton instance
     */
    public static PowerUpManager getInstance() {
        if (instance == null) {
            instance = new PowerUpManager();
        }
        return instance;
    }

    /**
     * Sets the {@link LevelParent} for this power-up manager.
     *
     * @param levelParent the current level
     */
    public void setLevelParent(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    /**
     * Purchases an extra life for the user.
     *
     * @return true if the purchase was successful, false otherwise
     */
    public boolean purchaseExtraLife() {
        if (CoinSystemManager.getInstance().subtractCoins(15)) {
            levelParent.getUser().addHealth(1);
            levelParent.getLevelView().addHeart();
            return true;
        }
        return false;
    }

    /**
     * Purchases a shield for the user.
     *
     * @return true if the purchase was successful, false otherwise
     */
    public boolean purchaseShield() {
        if (levelParent.isShieldActive()) {
            return false;
        }
        if (CoinSystemManager.getInstance().subtractCoins(10)) {
            activateShield();
            return true;
        }
        return false;
    }

    /**
     * Activates the shield power-up, making the user invulnerable for a limited duration.
     */
    private void activateShield() {
        levelParent.getUserShield().showShield();
        levelParent.getLevelView().startShieldTimer(20);
        shieldTimer = new Timeline(
                new KeyFrame(Duration.seconds(20), e -> deactivateShield())
        );
        shieldTimer.setCycleCount(1);
        shieldTimer.play();
    }

    /**
     * Deactivates the shield power-up, removing the user's invulnerability.
     */
    private void deactivateShield() {
        levelParent.getUserShield().hideShield();
        if (shieldTimer != null) {
            shieldTimer.stop();
            shieldTimer = null;
        }
    }

    /**
     * Purchases additional bullets for the user.
     *
     * @return true if the purchase was successful, false otherwise
     */
    public boolean purchaseBullets() {
        int bulletCost = 10;
        int bulletsToAdd = 10;

        if (CoinSystemManager.getInstance().subtractCoins(bulletCost)) {
            System.out.println("Purchased " + bulletsToAdd + " bullets!");
            BulletSystemManager.getInstance().addBullets(bulletsToAdd);
            return true;
        }
        System.err.println("Not enough coins to purchase bullets!");
        return false;
    }
}
