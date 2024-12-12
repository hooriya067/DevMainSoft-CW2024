package com.example.demo.Levels;

import com.example.demo.Managers.LevelManager;
import com.example.demo.actors.collectibles.Coin;
import com.example.demo.actors.user.UserPlane;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.List;

/**
 * The {@code ControllableLevel} interface defines the contract for levels in the game that require user
 * interaction and control. It provides methods for initializing, starting the game, and accessing key components.
 * This interface ensures consistency across different level implementations.This Interface is derived out of {@link LevelParent}
 * It helps decouple Level Parent and helps other managers to still have access to level parent Methods and resources without Endangering Levels Parents encapsulation
 */
public interface ControllableLevel {

    /**
     * Fires a projectile from the user plane.
     */
    void fireProjectile();

    /**
     * Initializes the level scenario by setting up the scene and game components.
     *
     * @return the {@link Scene} representing the initialized level.
     */
    Scene initializeScenario();

    /**
     * Starts the game logic for the level.
     */
    void startGame();

    /**
     * Retrieves the root {@link Group} that contains all the visual elements of the level.
     *
     * @return the root group of the level.
     */
    Group getRoot();

    /**
     * Retrieves the {@link UserPlane} controlled by the player in the level.
     *
     * @return the player's user plane.
     */
    UserPlane getUser();

    /**
     * Retrieves the width of the screen for the level.
     *
     * @return the screen width as a {@code double}.
     */
    double getScreenWidth();

    /**
     * Checks whether the shield is active in the level.
     *
     * @return {@code true} if the shield is active; {@code false} otherwise.
     */
    boolean isShieldActive();

    /**
     * Increments the kill count for the level.
     */
    void incrementKillCount();

    /**
     * Retrieves the list of {@link Coin} objects currently active in the level.
     *
     * @return a {@link List} of active coins.
     */
    List<Coin> getCoins();
}

