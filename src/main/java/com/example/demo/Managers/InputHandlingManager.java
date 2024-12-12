/**
 * The {@code InputHandlingManager} class is responsible for managing user input for controlling gameplay.
 * It processes keyboard events to move the player and fire projectiles, providing flexibility
 * through customizable movement modes.
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link ControllableLevel}: Represents the level with user-controllable elements.</li>
 *     <li>{@link GameStateManager}: Manages the game's paused state to determine input responsiveness.</li>
 *     <li>{@link javafx.scene.Scene}: Used to bind key event listeners for handling input.</li>
 * </ul>
 */
package com.example.demo.Managers;

import com.example.demo.core.GameStateManager;
import com.example.demo.Levels.ControllableLevel;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * Manages user input to control gameplay elements such as movement and firing projectiles.
 */
public class InputHandlingManager {

    /**
     * Enum representing different movement modes.
     */
    public enum MovementMode {
        VERTICAL_ONLY, // UP and DOWN only
        FULL           // UP, DOWN, LEFT, RIGHT
    }

    /**
     * The level associated with this input handler.
     */
    private final ControllableLevel level;

    /**
     * The current movement mode for input handling.
     */
    private MovementMode movementMode;

    /**
     * Constructs an {@code InputHandlingManager} instance with the specified level and movement mode.
     *
     * @param level        the level containing user-controllable elements
     * @param movementMode the initial movement mode for the input handler
     */
    public InputHandlingManager(ControllableLevel level, MovementMode movementMode) {
        this.level = level;
        this.movementMode = movementMode;
    }

    /**
     * Sets the movement mode for input handling.
     *
     * @param movementMode the new movement mode
     */
    public void setMovementMode(MovementMode movementMode) {
        this.movementMode = movementMode;
    }

    /**
     * Initializes input handling by binding key event listeners to the provided scene.
     *
     * @param scene the scene to which input listeners will be added
     */
    public void initialize(Scene scene) {
        scene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
    }

    /**
     * Handles key press events for movement and actions.
     *
     * @param keyCode the key that was pressed
     */
    private void handleKeyPressed(KeyCode keyCode) {
        switch (keyCode) {
            case UP -> level.getUser().moveUp();
            case DOWN -> level.getUser().moveDown();
            case SPACE -> {
                if (!GameStateManager.getInstance().isGamePaused()) {
                    level.fireProjectile();
                }
            }
            case LEFT -> {
                if (movementMode == MovementMode.FULL) {
                    level.getUser().moveLeft();
                }
            }
            case RIGHT -> {
                if (movementMode == MovementMode.FULL) {
                    level.getUser().moveRight();
                }
            }
        }
    }

    /**
     * Handles key release events to stop movement.
     *
     * @param keyCode the key that was released
     */
    private void handleKeyReleased(KeyCode keyCode) {
        if (GameStateManager.getInstance().isPaused) {
            return;
        }

        switch (keyCode) {
            case UP, DOWN -> level.getUser().stopVerticalMovement();
            case LEFT, RIGHT -> {
                if (movementMode == MovementMode.FULL) {
                    level.getUser().stopHorizontalMovement();
                }
            }
        }
    }
}
