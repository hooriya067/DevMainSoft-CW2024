package com.example.demo.actors.collectibles;

import com.example.demo.actors.active.ActiveActor;
import com.example.demo.core.GameStateManager;
import com.example.demo.Levels.LevelFour;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class FlarePowerUp extends ActiveActor {

    private static final String IMAGE_NAME = "flare_powerup.png";
    private static final int IMAGE_HEIGHT = 40;
    private static final int FLARE_DURATION = 15; // Duration of flare in seconds
    private final LevelFour levelFour;

    public FlarePowerUp(double initialX, double initialY, LevelFour levelFour) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY);
        this.levelFour = levelFour;
    }

    public void activate() {
        // Activate flare to reveal hidden enemies for a duration
        levelFour.revealStealthEnemies(true);
        Timeline flareTimeline = new Timeline(new KeyFrame(Duration.seconds(FLARE_DURATION), event -> levelFour.revealStealthEnemies(false)));
        flareTimeline.setCycleCount(1);
        flareTimeline.play();
    }

    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;  // Skip updating position if paused
        }
        // Move down the screen slowly
        setTranslateY(getTranslateY() + 2.0);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void takeDamage() {}

}
