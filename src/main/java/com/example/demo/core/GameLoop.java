package com.example.demo.core;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameLoop {

    private final List<Updatable> updatables = new ArrayList<>();
    private final Timeline timeline;

    public GameLoop(int millisecondsPerFrame) {
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        setUpdateInterval(millisecondsPerFrame);
    }

    public void addUpdatable(Updatable updatable) {
        updatables.add(updatable);
    }

    public void setUpdateInterval(int millisecondsPerFrame) {
        timeline.getKeyFrames().clear();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(millisecondsPerFrame), e -> updateAll());
        timeline.getKeyFrames().add(keyFrame);
    }

    private void updateAll() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    //    public void removeUpdatable(Updatable updatable) {
//        updatables.remove(updatable);
//    }
//
//    public void clearUpdatables() {
//        updatables.clear();
//    }

//
//    public void pause() {
//        timeline.pause();
//    }
//
//    public void resume() {
//        timeline.play();
//    }
//
//    public boolean isRunning() {
//        return timeline.getStatus() == Timeline.Status.RUNNING;
//    }
}
