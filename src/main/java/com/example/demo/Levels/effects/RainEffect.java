package com.example.demo.Levels.effects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


/**
 * The {@code RainEffect} class creates a visual rain effect for game levels by animating raindrops falling from
 * the top of the screen. It dynamically spawns, animates, and removes raindrops as they leave the screen.
 *
 * <p>
 * This class is useful for enhancing the atmospheric aesthetics of the game, particularly in levels requiring
 * environmental effects like rain.
 * </p>
 */
public class RainEffect {

    /**
     * The container that holds all the raindrops.
     */
    private final Pane container;

    /**
     * The width of the screen, used to calculate raindrop spawning positions.
     */
    private final double screenWidth;

    /**
     * The height of the screen, used to determine when raindrops should be removed.
     */
    private final double screenHeight;

    /**
     * A list to keep track of all active raindrops.
     */
    private final List<Line> raindrops = new ArrayList<>();

    /**
     * The timeline for continuously spawning raindrops.
     */
    private Timeline rainTimeline;

    /**
     * Constructs a {@code RainEffect} object with the specified screen dimensions.
     *
     * @param screenWidth  the width of the screen for the rain effect.
     * @param screenHeight the height of the screen for the rain effect.
     */
    public RainEffect(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.container = new Pane();
        initializeRain();
    }

    /**
     * Initializes the rain effect by starting a timeline that spawns raindrops at regular intervals.
     */
    private void initializeRain() {
        rainTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> spawnRaindrop()));
        rainTimeline.setCycleCount(Timeline.INDEFINITE);
        rainTimeline.play();
    }

    /**
     * Spawns a single raindrop and animates it falling down the screen. Raindrops are removed once
     * they leave the screen.
     */
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

    /**
     * Retrieves the {@link Pane} container holding the rain effect.
     *
     * @return the container with all active raindrops.
     */
    public Pane getContainer() {
        return container;
    }

    /**
     * Stops the rain effect by halting the spawning timeline.
     */
    public void stopRain() {
        if (rainTimeline != null) {
            rainTimeline.stop();
        }
    }
}
