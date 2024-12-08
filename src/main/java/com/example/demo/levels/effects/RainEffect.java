package com.example.demo.levels.effects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class RainEffect {

    private final Pane container; // Pane to hold all raindrops
    private final double screenWidth;
    private final double screenHeight;
    private final List<Line> raindrops = new ArrayList<>();
    private Timeline rainTimeline;

    public RainEffect(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.container = new Pane();
        initializeRain();
    }

    private void initializeRain() {
        rainTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> spawnRaindrop()));
        rainTimeline.setCycleCount(Timeline.INDEFINITE);
        rainTimeline.play();
    }

    private void spawnRaindrop() {
        Line raindrop = new Line();
        raindrop.setStartX(Math.random() * screenWidth); // Random X position
        raindrop.setStartY(0); // Start at the top
        raindrop.setEndX(raindrop.getStartX());
        raindrop.setEndY(10); // Small vertical line
        raindrop.setStroke(Color.LIGHTBLUE);
        raindrop.setStrokeWidth(2);

        container.getChildren().add(raindrop);
        raindrops.add(raindrop);

        // Animate raindrop falling
        Timeline fallTimeline = new Timeline(new KeyFrame(Duration.millis(30), ev -> {
            raindrop.setStartY(raindrop.getStartY() + 5);
            raindrop.setEndY(raindrop.getEndY() + 5);

            // Remove raindrop if it goes off the screen
            if (raindrop.getStartY() > screenHeight) {
                container.getChildren().remove(raindrop);
                raindrops.remove(raindrop);
            }
        }));
        fallTimeline.setCycleCount(60); // Controls fall duration
        fallTimeline.play();
    }

    public Pane getContainer() {
        return container;
    }

    public void stopRain() {
        if (rainTimeline != null) {
            rainTimeline.stop();
        }
    }
}
