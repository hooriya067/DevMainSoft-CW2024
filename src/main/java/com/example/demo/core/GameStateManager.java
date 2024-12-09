package com.example.demo.core;

import com.example.demo.actors.user.UserPlane;

public class GameStateManager {

    private static GameStateManager instance;
    public boolean isPaused;

    private GameStateManager() {
        this.isPaused = false;
    }

    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
    }

    public boolean isGamePaused() {
        return isPaused;
    }

    public boolean checkGameOver(UserPlane user, boolean killTarget) {
        if (user.isDestroyed()) {
            System.out.println("User is destroyed. Game Over!");
            return true;
        }
        if (killTarget) {
            System.out.println("Kill target reached. Level completed!");
            return true;
        }
        return false;
    }
}
