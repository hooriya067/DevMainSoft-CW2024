package com.example.demo.actors.collectibles;

import com.example.demo.levels.LevelParent;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.core.GameStateManager;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Coin extends ActiveActor {

    private static final String IMAGE_NAME = "coin.png";
    private static final int IMAGE_SIZE = 25; // Size of the coin
    private static final double HORIZONTAL_VELOCITY = -9; // Leftward movement
    private final LevelParent level; // Reference to the current level

    public Coin(double initialX, double initialY, LevelParent level) {
        super(IMAGE_NAME, IMAGE_SIZE, initialX, initialY);
        this.level = level;
    }
    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void updatePosition() {
        if (GameStateManager.getInstance().isGamePaused()) {
            return; // Skip updating position if paused
        }
        moveHorizontally(HORIZONTAL_VELOCITY);
        if (getTranslateX() < 0) {
            destroy();
        }
    }
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

    @Override
    public void takeDamage() {
        destroy();
    }
}
