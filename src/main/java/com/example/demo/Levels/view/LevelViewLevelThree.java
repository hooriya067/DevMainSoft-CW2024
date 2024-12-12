/**
 * The {@code LevelViewLevelThree} class extends {@link LevelViewParent} to provide specific
 * UI functionalities and visual elements for Level Three of the game.
 *
 * <p>This class introduces a kill counter label and informational alerts that are
 * unique to Level Three, enhancing the player's experience with updated gameplay mechanics.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link LevelViewParent}: Provides the foundational UI management for all levels.</li>
 *     <li>{@link AlertManager}: Displays alerts to inform the player of in-game events and tips.</li>
 *     <li>{@link LevelParent}: The parent level class to which this UI is associated.</li>
 *     <li>{@link GameConfig}: Provides configuration constants for screen dimensions.</li>
 * </ul>
 */
package com.example.demo.Levels.view;

import com.example.demo.Managers.AlertManager;
import com.example.demo.core.GameConfig;
import com.example.demo.Levels.LevelParent;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Represents the UI elements and functionalities specific to Level Three.
 */
public class LevelViewLevelThree extends LevelViewParent {

    /**
     * Label to display the player's kill count.
     */
    private Label killsLabel;

    /**
     * The width of the screen.
     */
    private final double screenWidth;

    /**
     * The height of the screen.
     */
    private final double screenHeight;

    /**
     * Constructs a LevelViewLevelThree instance with the specified parameters.
     *
     * @param root            the root group for UI elements
     * @param heartsToDisplay the number of hearts to display
     * @param screenWidth     the width of the screen
     * @param screenHeight    the height of the screen
     * @param levelParent     reference to the parent level
     */
    public LevelViewLevelThree(Group root, int heartsToDisplay, double screenWidth, double screenHeight, LevelParent levelParent) {
        super(root, heartsToDisplay, levelParent);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Initializes the kill counter label and adds it to the root group.
     */
    @Override
    protected void initializeWinningParameter() {
        killsLabel = new Label("Kills💀: 0 ");
        killsLabel.setLayoutX((double) GameConfig.SCREEN_WIDTH / 2);
        killsLabel.setLayoutY(30);
        killsLabel.setStyle(
                "-fx-font-size: 25px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: linear-gradient(#5A0000, #ff5500);" +
                        "-fx-effect: dropshadow(gaussian, black, 8, 0.5, 3, 3);"
        );
        getRoot().getChildren().add(killsLabel);
    }

    /**
     * Updates the kill counter label to reflect the player's current kill count.
     */
    @Override
    public void updateWinningParameter() {
        Platform.runLater(() -> {
            killsLabel.setText("Kills: " + getLevelParent().getNumberOfKills());
            killsLabel.toFront();
        });
    }

    /**
     * Displays informational alerts specific to Level Three gameplay mechanics.
     */
    public void infoAlerts() {
        AlertManager.getInstance().showInfoAlert(
                "Left and Right Moves \n allowed now!",
                screenWidth,
                screenHeight
        );
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> AlertManager.getInstance().showInfoAlert(
                "Hit Helicopter Army's leader \n All of them will\n fall apart",
                screenWidth,
                screenHeight
        ));
        pause.play();
    }
}