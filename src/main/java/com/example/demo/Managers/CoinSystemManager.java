package com.example.demo.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CoinSystemManager {
    private static CoinSystemManager instance; // Singleton instance
    private int totalCoins;
    private final List<Consumer<Integer>> listeners; // List to store listeners

    // Private constructor
    private CoinSystemManager() {
        totalCoins = 0;
        listeners = new ArrayList<>(); // Initialize the listeners list
    }

    // Get the singleton instance
    public static CoinSystemManager getInstance() {
        if (instance == null) {
            instance = new CoinSystemManager();
        }
        return instance;
    }

    // Add coins
    public void addCoins(int amount) {
        totalCoins += amount;
      //  System.out.println("Coins added: " + amount + ". Total coins: " + totalCoins);
        notifyListeners(); // Notify listeners of the update
    }

    // Subtract coins
    public boolean subtractCoins(int amount) {
        if (totalCoins >= amount) {
            totalCoins -= amount;
            System.out.println("Coins subtracted: " + amount + ". Remaining coins: " + totalCoins);
            notifyListeners(); // Notify listeners of the update
            return true; // Successful transaction
        } else {
            System.out.println("Not enough coins to subtract " + amount);
            return false; // Failed transaction
        }
    }

    // Get current coin count
    public int getCoins() {
        return totalCoins;
    }

    // Add a listener
    public void addListener(Consumer<Integer> listener) {
        listeners.add(listener);
    }

    // Notify all listeners of the updated coin count
    private void notifyListeners() {
        for (Consumer<Integer> listener : listeners) {
            listener.accept(totalCoins);
        }
    }
}
