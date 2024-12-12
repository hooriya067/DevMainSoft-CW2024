package com.example.demo.actors.collectibles;

import com.example.demo.Levels.LevelParent;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.core.GameStateManager;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The {@code Coin} class represents a collectible coin in the game.
 * Coins can be collected by the player to increase their score or enable certain game functionalities.
 * This class extends {@link ActiveActor} to incorporate graphical and movement behavior.
 */
public class Coin extends ActiveActor {

    /**
     * The file name for the coin image resource.
     */
    private static final String IMAGE_NAME = "coin.png";

    /**
     * The size of the coin image in pixels.
     */
    private static final int IMAGE_SIZE = 25;

    /**
     * The horizontal velocity of the coin's movement.
     */
    private static final double HORIZONTAL_VELOCITY = -9.0;

    /**
     * A reference to the current level where the coin exists.
     */
    private final LevelParent level;

    /**
     * Constructs a {@code Coin} object at a specified position within a specific level.
     *
     * @param initialX the initial X-coordinate of the coin.
     * @param initialY the initial Y-coordinate of the coin.
     * @param level    the {@link LevelParent} instance where the coin is present.
     */
    public Coin(double initialX, double initialY, LevelParent level) {
        super(IMAGE_NAME, IMAGE_SIZE, initialX, initialY);
        this.level = level;
    }

    /**
     * Updates the state of the coin by updating its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Updates the position of the coin, moving it horizontally.
     * If the coin moves off-screen, it is destroyed.
     */
    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return;
        }
        moveHorizontally(HORIZONTAL_VELOCITY);
        if (getTranslateX() < 0) {
            destroy();
        }
    }

    /**
     * Displays a "+1" visual effect when the coin is collected by the player.
     *
     * @param root the root {@link Group} where the effect will be displayed.
     * @param x    the X-coordinate for the effect.
     * @param y    the Y-coordinate for the effect.
     */
    public void showPlusOneEffect(Group root, double x, double y) {
        Text plusOneText = new Text("+1");
        plusOneText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: orange;");
        plusOneText.setLayoutX(x);
        plusOneText.setLayoutY(y);
        root.getChildren().add(plusOneText);

        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(1.5), plusOneText);
        moveUp.setByY(-50);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), plusOneText);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        moveUp.setOnFinished(event -> root.getChildren().remove(plusOneText));
        fadeOut.setOnFinished(event -> root.getChildren().remove(plusOneText));
        moveUp.play();
        fadeOut.play();
    }
}
