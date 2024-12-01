package com.example.demo.core;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLoop {

    private final Timeline timeline;
    private Runnable updateCallback;

    public GameLoop(int millisecondsPerFrame) {
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        setUpdateInterval(millisecondsPerFrame);
    }

    // Set the interval for each frame
    public void setUpdateInterval(int millisecondsPerFrame) {
        timeline.getKeyFrames().clear();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(millisecondsPerFrame), e -> {
            if (updateCallback != null) {
                updateCallback.run();
            }
        });
        timeline.getKeyFrames().add(keyFrame);
    }

    // Set the callback to be invoked on each frame
    public void setUpdateCallback(Runnable updateCallback) {
        this.updateCallback = updateCallback;
    }

    // Start the game loop
    public void start() {
        timeline.play();
    }

    // Stop the game loop
    public void stop() {
        timeline.stop();
    }
    public void pause() {
        timeline.pause();
    }

    public void resume() {
        timeline.play();
    }
    // Check if the game loop is running
    public boolean isRunning() {
        return timeline.getStatus() == Timeline.Status.RUNNING;
    }
}
