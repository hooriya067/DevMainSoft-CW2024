package com.example.demo.Managers;

import com.example.demo.core.GameStateManager;
import com.example.demo.Levels.ControllableLevel;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.awt.event.FocusEvent;

public class InputHandlingManager {
    private final ControllableLevel level;
    public enum MovementMode {
        VERTICAL_ONLY, // UP and DOWN only
        FULL           // UP, DOWN, LEFT, RIGHT
    }

    private MovementMode movementMode;

    public InputHandlingManager(ControllableLevel level, MovementMode movementMode) {
        this.level = level;
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
        System.out.println("Key pressed: " + keyCode); // Log every key event


        switch (keyCode) {
            case UP -> level.getUser().moveUp();
            case DOWN -> level.getUser().moveDown();
            case SPACE ->{
            if (!GameStateManager.getInstance().isGamePaused())
                {level.fireProjectile();}
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
    private void handleKeyReleased(KeyCode keyCode) {
        if (GameStateManager.getInstance().isPaused) {
            System.out.println("Key event received during pause: " + keyCode);
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


