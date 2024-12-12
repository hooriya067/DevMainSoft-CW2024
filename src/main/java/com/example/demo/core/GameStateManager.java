package com.example.demo.core;

import com.example.demo.actors.user.UserPlane;
/**
 * The {@code GameStateManager} class is a singleton responsible for managing the global game state,
 * such as whether the game is paused and checking for game-over conditions.
 *
 * <p>
 * This class ensures centralized control over the game's state, allowing seamless pausing,
 * resuming, and monitoring of game-over conditions across all game components.
 * </p>
 */
public class GameStateManager {

    /**
     * The singleton instance of the {@code GameStateManager}.
     */
    private static GameStateManager instance;

    /**
     * A flag indicating whether the game is currently paused.
     */
    public boolean isPaused;

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private GameStateManager() {
        this.isPaused = false;
    }

    /**
     * Retrieves the singleton instance of {@code GameStateManager}.
     * If the instance does not exist, it initializes it.
     *
     * @return the singleton instance of {@code GameStateManager}.
     */
    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    /**
     * Pauses the game, setting the {@code isPaused} flag to {@code true}.
     */
    public void pauseGame() {
        isPaused = true;
    }

    /**
     * Resumes the game, setting the {@code isPaused} flag to {@code false}.
     */
    public void resumeGame() {
        isPaused = false;
    }

    /**
     * Checks whether the game is currently paused.
     *
     * @return {@code true} if the game is paused; {@code false} otherwise.
     */
    public boolean isGamePaused() {
        return isPaused;
    }

    /**
     * Checks whether the game is over by evaluating the user's state and kill target condition.
     *
     * @param user      the {@link UserPlane} representing the player.
     * @param killTarget {@code true} if the required kill target has been achieved; {@code false} otherwise.
     * @return {@code true} if the game is over due to the player being destroyed or the kill target being reached.
     */
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
