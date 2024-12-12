package com.example.demo.actors.collectibles;

import com.example.demo.actors.active.ActiveActor;
import com.example.demo.core.GameStateManager;
import com.example.demo.Levels.LevelFour;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The {@code FlarePowerUp} class represents a collectible item in the game that reveals hidden enemies temporarily.
 * When activated, it triggers a mechanism in LevelFour to make stealth enemies visible for a limited duration.
 * This class extends {@link ActiveActor} to leverage graphical and positional capabilities.
 */
public class FlarePowerUp extends ActiveActor {

    /**
     * The file name for the flare power-up image.
     */
    private static final String IMAGE_NAME = "flare_powerup.png";

    /**
     * The height of the flare power-up image in pixels.
     */
    private static final int IMAGE_HEIGHT = 40;

    /**
     * The duration (in seconds) for which the flare effect is active.
     */
    private static final int FLARE_DURATION = 15;

    /**
     * A reference to the {@link LevelFour} instance where this power-up is used.
     */
    private final LevelFour levelFour;

    /**
     * Constructs a {@code FlarePowerUp} object at a specified position within a specific level.
     *
     * @param initialX  the initial X-coordinate of the power-up.
     * @param initialY  the initial Y-coordinate of the power-up.
     * @param levelFour the {@link LevelFour} instance where the power-up is used.
     */
    public FlarePowerUp(double initialX, double initialY, LevelFour levelFour) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY);
        this.levelFour = levelFour;
    }

    /**
     * Activates the flare power-up, revealing all stealth enemies in the level for a fixed duration.
     */
    public void activate() {
        levelFour.revealStealthEnemies(true);
        Timeline flareTimeline = new Timeline(
                new KeyFrame(Duration.seconds(FLARE_DURATION),
                        event -> levelFour.revealStealthEnemies(false)));
        flareTimeline.setCycleCount(1);
        flareTimeline.play();
    }

    /**
     * Updates the position of the flare power-up, making it move downwards on the screen.
     * The movement is paused if the game is paused.
     */
    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }
        setTranslateY(getTranslateY() + 2.0);
    }

    /**
     * Updates the actor's state. For the flare power-up, this includes updating its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Override of the {@link ActiveActor#takeDamage()} method. The flare power-up cannot take damage.
     */
    @Override
    public void takeDamage() {}
}
