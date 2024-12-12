/**
 * The {@code CoinSystemManager} class is a Singleton responsible for managing the player's coins.
 * It tracks the total coins, allows for adding and subtracting coins, and notifies listeners about
 * changes to the coin count. This class is vital for ensuring consistency in the game's coin system.
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link java.util.function.Consumer}: Used for notifying listeners about coin count updates.</li>
 *     <li>{@link java.util.ArrayList}: Stores the list of listeners that observe coin count changes.</li>
 * </ul>
 */
package com.example.demo.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Manages the player's coins, including tracking total coins and notifying listeners of updates.
 */
public class CoinSystemManager {

    /**
     * Singleton instance of the CoinSystemManager.
     */
    private static CoinSystemManager instance;

    /**
     * Total number of coins available to the player.
     */
    private int totalCoins;

    /**
     * List of listeners to notify when the coin count changes.
     */
    private final List<Consumer<Integer>> listeners;

    /**
     * Private constructor to enforce Singleton pattern and initialize the system.
     */
    private CoinSystemManager() {
        totalCoins = 0; // Initial number of coins
        listeners = new ArrayList<>(); // Initialize the listeners list
    }

    /**
     * Retrieves the singleton instance of the CoinSystemManager.
     *
     * @return the singleton instance
     */
    public static CoinSystemManager getInstance() {
        if (instance == null) {
            instance = new CoinSystemManager();
        }
        return instance;
    }

    /**
     * Adds a specified number of coins to the player's inventory.
     *
     * @param amount the number of coins to add
     */
    public void addCoins(int amount) {
        totalCoins += amount;
        notifyListeners(); // Notify listeners of the update
    }

    /**
     * Subtracts a specified number of coins from the player's inventory.
     *
     * @param amount the number of coins to subtract
     * @return true if the subtraction was successful, false if not enough coins are available
     */
    public boolean subtractCoins(int amount) {
        if (totalCoins >= amount) {
            totalCoins -= amount;
            notifyListeners(); // Notify listeners of the update
            return true; // Successful transaction
        } else {
            return false; // Not enough coins
        }
    }

    /**
     * Retrieves the total number of coins available.
     *
     * @return the total coin count
     */
    public int getCoins() {
        return totalCoins;
    }

    /**
     * Adds a listener that observes changes to the coin count.
     *
     * @param listener a function to execute when the coin count changes
     */
    public void addListener(Consumer<Integer> listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners of the updated coin count.
     */
    private void notifyListeners() {
        for (Consumer<Integer> listener : listeners) {
            listener.accept(totalCoins);
        }
    }
}