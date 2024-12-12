/**
 * The {@code MyObserver} interface defines a callback mechanism to be implemented by classes
 * that need to observe and respond to level completion events in the game. It provides a single method,
 * {@code onLevelWin}, which is triggered when a level is completed successfully.
 *
 * <p><b>Purpose:</b></p>
 * <ul>
 *     <li>Provides a flexible way to notify observers when the player wins a level.</li>
 *     <li>Decouples the logic of level progression from specific implementations.</li>
 * </ul>
 *
 * <p><b>Connection to {@link Controller}:</b></p>
 * The {@code MyObserver} interface is implemented by the {@link Controller} class.
 * The {@link Controller} listens for level completion events and takes appropriate actions, such as transitioning
 * to the next level or updating the game state. When a level is won, the {@link Controller} handles the callback
 * and determines the next course of action based on the {@code nextLevel} parameter.
 *
 * <p><b>Usage:</b></p>
 * The {@code onLevelWin} method is invoked by game elements such as the {@code LevelParent} class when a level
 * is successfully completed. The {@link Controller}, as an observer, updates the game state, notifies the user,
 * and manages the transition to the next level.
 *
 * <p><b>Implementation Example in {@link Controller}:</b></p>
 * <pre>{@code
 */
package com.example.demo;
/**
 * The {@code MyObserver} interface defines a custom observer mechanism for monitoring and responding to
 * level completion events in the game. Implementations of this interface should handle the actions
 * required when a level is successfully completed, such as transitioning to the next level or
 * displaying a completion screen.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Provides a single method, {@link #onLevelWin(String)}, to handle level completion.</li>
 *     <li>Supports custom implementations for varied behaviors upon level success.</li>
 * </ul>
 */
public interface MyObserver {
    /**
     * Invoked when a level is completed successfully.
     *
     * @param nextLevel the name or identifier of the next level to transition to
     */
    void onLevelWin(String nextLevel);
}

