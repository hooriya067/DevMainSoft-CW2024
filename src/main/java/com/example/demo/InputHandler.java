package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputHandler {

    public enum MovementMode {
        VERTICAL_ONLY, // UP and DOWN only
        FULL           // UP, DOWN, LEFT, RIGHT
    }

    private final LevelParent levelParent;
    private MovementMode movementMode;

    public InputHandler(LevelParent levelParent, MovementMode movementMode) {
        this.levelParent = levelParent;
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
            case UP -> levelParent.getUser().moveUp();
            case DOWN -> levelParent.getUser().moveDown();
            case SPACE -> levelParent.fireProjectile();
            case LEFT -> {
                if (movementMode == MovementMode.FULL) {
                    levelParent.getUser().moveLeft();
                }
            }
            case RIGHT -> {
                if (movementMode == MovementMode.FULL) {
                    levelParent.getUser().moveRight();
                }
            }
        }
    }

    private void handleKeyReleased(KeyCode keyCode) {
        if (GameStateManager.getInstance().isGamePaused()) {
            return; // Ignore input when the game is paused
        }

        switch (keyCode) {
            case UP, DOWN -> levelParent.getUser().stopVerticalMovement();
            case LEFT, RIGHT -> {
                if (movementMode == MovementMode.FULL) {
                    levelParent.getUser().stopHorizontalMovement();
                }
            }
        }
    }
}


