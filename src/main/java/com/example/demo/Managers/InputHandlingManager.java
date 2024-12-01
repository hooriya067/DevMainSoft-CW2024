package com.example.demo.Managers;

import com.example.demo.core.GameStateManager;
import com.example.demo.levels.ControllableLevel;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputHandlingManager {
    private final ControllableLevel level; // Dependency on the interface
    public enum MovementMode {
        VERTICAL_ONLY, // UP and DOWN only
        FULL           // UP, DOWN, LEFT, RIGHT
    }

   // private final LevelParent levelParent;
    private MovementMode movementMode;

    public InputHandlingManager(ControllableLevel level, MovementMode movementMode) {
        this.level = level;
       //this.levelParent = levelParent;
        this.movementMode = movementMode;
    }
    public void setMovementMode(MovementMode movementMode) {
        this.movementMode = movementMode;
    }

    public void initialize(Scene scene) {
        scene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
    }

    private void handleKeyPressed(KeyCode keyCode) {
        if (GameStateManager.getInstance().isGamePaused()) {
            return; // Ignore input when the game is paused
        }

        switch (keyCode) {
            case UP -> level.getUser().moveUp();
            case DOWN -> level.getUser().moveDown();
            case SPACE -> level.fireProjectile();
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
    private void handleKeyReleased(KeyCode keyCode) {
        if (GameStateManager.getInstance().isGamePaused()) {
            return; // Ignore input when the game is paused
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


