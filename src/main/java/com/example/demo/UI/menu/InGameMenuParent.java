/**
 * The {@code InGameMenuParent} class provides a base implementation for in-game menu overlays.
 * This class is designed to pause the game and display a customizable menu overlay, offering
 * functionalities like resuming the game with a countdown, adjusting background music volume,
 * and managing the overlay lifecycle.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Displays an overlay menu while pausing the game.</li>
 *     <li>Adjusts background music volume when the overlay is active.</li>
 *     <li>Implements a countdown for resuming the game.</li>
 *     <li>Abstract method for creating custom menu content.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * This class should be extended by specific in-game menu implementations to provide custom menu content.
 * The {@link #createMenuContent()} method must be implemented in subclasses.
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link SoundManager}: Handles sound adjustments.</li>
 *     <li>{@link GameStateManager}: Manages the game's state (paused or active).</li>
 *     <li>{@link GameConfig}: Provides screen dimensions and other game configurations.</li>
 *     <li>{@link StageManager}: Provides access to the application's primary stage.</li>
 * </ul>
 */
package com.example.demo.UI.menu;

import com.example.demo.Managers.SoundManager;
import com.example.demo.core.GameConfig;
import com.example.demo.core.GameStateManager;
import com.example.demo.core.StageManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Abstract base class for in-game menu overlays.
 * Handles overlay display, game state management, and background music adjustments.
 */
public abstract class InGameMenuParent {

    /**
     * The primary stage of the application.
     * Retrieved from {@link StageManager}.
     */
    protected final Stage stage = StageManager.getStage();

    /**
     * The layout container for the overlay.
     */
    private StackPane overlayLayout;

    /**
     * Tracks whether the overlay is currently active.
     */
    private boolean overlayActive = false;

    /**
     * Constructs an {@code InGameMenuParent} with the given stage.
     *
     * @param stage the {@link Stage} used to display the menu overlay
     * @throws IllegalArgumentException if the stage is null
     */
    public InGameMenuParent(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null in InGameMenuParent.");
        }
    }

    /**
     * Displays the overlay menu with the provided content.
     * Pauses the game and dims the background.
     *
     * @param menuContent the {@link VBox} containing the menu's content
     */
    protected void displayOverlay(VBox menuContent) {
        if (GameStateManager.getInstance().isGamePaused()) {
            System.out.println("Game is already paused. Skipping overlay.");
            return;
        }

        SoundManager.getInstance().lowerBackgroundMusicVolume(0.2);
        if (overlayActive) {
            System.err.println(getClass().getSimpleName() + " menu is already active! overlayActive = true");
            return;
        }

        overlayActive = true;
        GameStateManager.getInstance().pauseGame();

        Pane overlayBackground = new Pane();
        overlayBackground.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlayBackground.setPrefSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        overlayLayout = new StackPane();
        overlayLayout.getChildren().addAll(overlayBackground, menuContent);
        StackPane.setAlignment(menuContent, Pos.CENTER);

        if (stage.getScene().getRoot() instanceof Group currentRoot) {
            if (!currentRoot.getChildren().contains(overlayLayout)) {
                currentRoot.getChildren().add(overlayLayout);
            }
        }
    }

    /**
     * Removes the overlay menu and restores the game state.
     */
    protected void removeOverlay() {
        if (overlayLayout == null) return;

        if (overlayLayout.getParent() instanceof Group currentRoot) {
            SoundManager.getInstance().restoreBackgroundMusicVolume(0.5);
            currentRoot.getChildren().remove(overlayLayout);
            overlayActive = false;
        }
    }

    /**
     * Starts a countdown to resume the game, removing the overlay during the countdown.
     */
    protected void startResumeCountdown() {
        removeOverlay();
        Label countdownLabel = new Label("3");
        countdownLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: white; -fx-font-weight: bold;");

        if (stage.getScene().getRoot() instanceof Group root) {
            root.getChildren().add(countdownLabel);

            countdownLabel.setLayoutX(GameConfig.SCREEN_WIDTH / 2 - 20);
            countdownLabel.setLayoutY(GameConfig.SCREEN_HEIGHT / 2 - 50);

            Timeline countdownTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        int currentValue = Integer.parseInt(countdownLabel.getText());
                        if (currentValue > 1) {
                            countdownLabel.setText(String.valueOf(currentValue - 1));
                        } else {
                            GameStateManager.getInstance().resumeGame();
                            root.getChildren().remove(countdownLabel);
                        }
                    })
            );
            countdownTimeline.setCycleCount(3);
            countdownTimeline.play();
        } else {
            System.err.println("Unexpected root type for the scene.");
        }
    }

    /**
     * Creates the menu content for the overlay.
     * This method must be implemented by subclasses to define the specific menu content.
     *
     * @return the {@link VBox} containing the menu's content
     */
    protected abstract VBox createMenuContent();
}