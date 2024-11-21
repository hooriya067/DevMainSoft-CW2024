package com.example.demo;

public class GameStateManager {

    private static GameStateManager instance;
    public static boolean isPaused;

    // Private constructor to enforce singleton pattern
    private GameStateManager() {
        this.isPaused = false;
    }

    // Get the single instance of the GameStateManager
    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    // Method to pause the game
    public void pauseGame() {
        this.isPaused = true;
    }

    // Method to resume the game
    public void resumeGame() {
        this.isPaused = false;
    }

    // Check if the game is paused
    public boolean isGamePaused() {
        return isPaused;
    }
}


