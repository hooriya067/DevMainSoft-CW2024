package com.example.demo.core;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
/**
 * The {@code GameLoop} class manages the main game loop, providing a framework for updating game components
 * at a consistent frame rate. It utilizes JavaFX's {@link Timeline} for timed updates.
 *
 * <p>
 * The game loop facilitates the separation of update logic for various game elements, ensuring smooth and efficient
 * execution of game updates.
 * </p>
 */
public class GameLoop {

    /**
     * A list of objects implementing the {@link Updatable} interface, which will be updated each frame.
     */
    private final List<Updatable> updatables = new ArrayList<>();

    /**
     * The {@link Timeline} used to schedule periodic updates to game components.
     */
    private final Timeline timeline;

    /**
     * Constructs a {@code GameLoop} with the specified frame interval.
     *
     * @param millisecondsPerFrame the duration of each frame in milliseconds.
     */
    public GameLoop(int millisecondsPerFrame) {
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        setUpdateInterval(millisecondsPerFrame);
    }

    /**
     * Adds an {@link Updatable} object to the list of components updated each frame.
     *
     * @param updatable the object to be added to the update loop.
     */
    public void addUpdatable(Updatable updatable) {
        updatables.add(updatable);
    }

    /**
     * Sets the frame interval for the game loop, adjusting the update frequency.
     *
     * @param millisecondsPerFrame the duration of each frame in milliseconds.
     */
    public void setUpdateInterval(int millisecondsPerFrame) {
        timeline.getKeyFrames().clear();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(millisecondsPerFrame), e -> updateAll());
        timeline.getKeyFrames().add(keyFrame);
    }

    /**
     * Updates all {@link Updatable} objects registered in the loop.
     */
    private void updateAll() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }

    /**
     * Starts the game loop, resuming periodic updates.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Stops the game loop, halting periodic updates.
     */
    public void stop() {
        timeline.stop();
    }
}

