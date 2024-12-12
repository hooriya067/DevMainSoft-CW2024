/**
 * The {@code BulletSystemManager} class is a Singleton responsible for managing the player's bullets.
 * It tracks the total bullets, bullets used, and allows listeners to react to bullet changes.
 * This class is essential for ensuring the gameplay mechanics of bullet usage and inventory are consistent.
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link java.util.function.Consumer}: Used for notifying listeners about bullet count updates.</li>
 *     <li>{@link java.util.ArrayList}: Stores the list of listeners that observe bullet count changes.</li>
 * </ul>
 */
package com.example.demo.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Manages the player's bullets, including tracking total bullets, bullets used,
 * and notifying listeners of updates.
 */
public class BulletSystemManager {

    /**
     * Singleton instance of the BulletSystemManager.
     */
    private static BulletSystemManager instance;

    /**
     * Total number of bullets available to the player.
     */
    private int totalBullets;

    /**
     * List of listeners to notify when the bullet count changes.
     */
    private final List<Consumer<Integer>> listeners;

    /**
     * The total number of bullets used by the player.
     */
    private int bulletsUsed;

    /**
     * Private constructor to enforce Singleton pattern and initialize the system.
     */
    private BulletSystemManager() {
        totalBullets = 120; // Initial number of bullets
        listeners = new ArrayList<>(); // Initialize the listeners list
    }

    /**
     * Retrieves the singleton instance of the BulletSystemManager.
     *
     * @return the singleton instance
     */
    public static BulletSystemManager getInstance() {
        if (instance == null) {
            instance = new BulletSystemManager();
        }
        return instance;
    }

    /**
     * Adds a specified number of bullets to the player's inventory.
     *
     * @param amount the number of bullets to add
     */
    public void addBullets(int amount) {
        totalBullets += amount;
        notifyListeners(); // Notify listeners of the update
    }

    /**
     * Subtracts a specified number of bullets from the player's inventory.
     *
     * @param amount the number of bullets to subtract
     * @return true if the subtraction was successful, false if not enough bullets are available
     */
    public boolean subtractBullets(int amount) {
        if (totalBullets >= amount) {
            totalBullets -= amount;
            notifyListeners(); // Notify listeners of the update
            return true; // Successful transaction
        } else {
            return false; // Not enough bullets
        }
    }

    /**
     * Sets the total number of bullets available.
     *
     * @param totalBullets the new total bullet count
     */
    public void setBullets(int totalBullets) {
        this.totalBullets = totalBullets;
    }

    /**
     * Retrieves the total number of bullets available.
     *
     * @return the total bullet count
     */
    public int getBullets() {
        return totalBullets;
    }

    /**
     * Sets the total number of bullets used by the player.
     *
     * @param bulletsUsed the total number of bullets used
     */
    public void setBulletsUsed(int bulletsUsed) {
        this.bulletsUsed = bulletsUsed;
    }

    /**
     * Increments the count of bullets used by the player by one.
     */
    public void incrementBulletsUsed() {
        bulletsUsed++;
    }

    /**
     * Retrieves the total number of bullets used by the player.
     *
     * @return the total number of bullets used
     */
    public int getBulletsUsed() {
        return bulletsUsed;
    }

    /**
     * Adds a listener that observes changes to the bullet count.
     *
     * @param listener a function to execute when the bullet count changes
     */
    public void addListener(Consumer<Integer> listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners of the updated bullet count.
     */
    private void notifyListeners() {
        for (Consumer<Integer> listener : listeners) {
            listener.accept(totalBullets);
        }
    }
}
