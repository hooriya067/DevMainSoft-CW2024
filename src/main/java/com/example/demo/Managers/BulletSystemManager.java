package com.example.demo.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BulletSystemManager {
    private static BulletSystemManager instance; // Singleton instance
    private int totalBullets;
    private final List<Consumer<Integer>> listeners; // List to store listeners

    // Private constructor
    private BulletSystemManager() {
        totalBullets = 5; // Initial bullet count
        listeners = new ArrayList<>(); // Initialize the listeners list
    }

    // Get the singleton instance
    public static BulletSystemManager getInstance() {
        if (instance == null) {
            instance = new BulletSystemManager();
        }
        return instance;
    }

    // Add bullets
    public void addBullets(int amount) {
        totalBullets += amount;
        notifyListeners(); // Notify listeners of the update
    }

    // Subtract bullets
    public boolean subtractBullets(int amount) {
        if (totalBullets >= amount) {
            totalBullets -= amount;
            notifyListeners(); // Notify listeners of the update
            return true; // Successful transaction
        } else {
            return false; // Not enough bullets
        }
    }

    // Get current bullet count
    public int getBullets() {
        return totalBullets;
    }

    // Add a listener
    public void addListener(Consumer<Integer> listener) {
        listeners.add(listener);
    }

    // Notify all listeners of the updated bullet count
    private void notifyListeners() {
        for (Consumer<Integer> listener : listeners) {
            listener.accept(totalBullets);
        }
    }
}

