package com.example.demo.core;

import com.example.demo.Managers.LevelManager;
import javafx.stage.Stage;

/**
 * The {@link StageManager} class provides a centralized mechanism for managing the global {@link Stage}
 * instance and the {@link LevelManager} in the application. It ensures a single source of truth for accessing
 * and modifying the stage and level manager throughout the game.
 *
 * <p>
 * This utility class simplifies the process of sharing the {@link Stage} and {@link LevelManager}
 * instances between different components, ensuring consistency and avoiding redundant instantiations.
 * </p>
 */
public class StageManager {

    /**
     * The global {@link Stage} instance used by the application.
     */
    private static Stage currentStage;

    /**
     * The global {@link LevelManager} instance used to manage game levels.
     */
    private static LevelManager levelManager;

    /**
     * Sets the global {@link Stage} instance.
     *
     * @param stage the {@link Stage} instance to be set.
     */
    public static void setStage(Stage stage) {
        currentStage = stage;
    }

    /**
     * Retrieves the global {@link Stage} instance.
     *
     * @return the global {@link Stage} instance.
     * @throws IllegalStateException if the {@link Stage} has not been set.
     */
    public static Stage getStage() {
        if (currentStage == null) {
            throw new IllegalStateException("Stage has not been set!");
        }
        return currentStage;
    }

    /**
     * Sets the global {@link LevelManager} instance.
     *
     * @param manager the {@link LevelManager} instance to be set.
     */
    public static void setLevelManager(LevelManager manager) {
        levelManager = manager;
    }

    /**
     * Retrieves the global {@link LevelManager} instance.
     *
     * @return the global {@link LevelManager} instance.
     * @throws IllegalStateException if the {@link LevelManager} has not been set.
     */
    public static LevelManager getLevelManager() {
        if (levelManager == null) {
            throw new IllegalStateException("LevelManager has not been set!");
        }
        return levelManager;
    }
}
