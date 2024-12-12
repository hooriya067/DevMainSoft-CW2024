/**
 * The {@code LevelViewLevelOne} class extends {@link LevelViewParent} to provide specific
 * UI functionalities and visual elements for Level One of the game.
 *
 * <p>This class introduces a kill counter label that updates in real-time to enhance
 * the player's experience by providing immediate feedback on their progress.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link LevelViewParent}: Provides the foundational UI management for all levels.</li>
 *     <li>{@link LevelParent}: The parent level class to which this UI is associated.</li>
 *     <li>{@link GameConfig}: Provides configuration constants for screen dimensions.</li>
 * </ul>
 */
package com.example.demo.Levels.view;

import com.example.demo.core.GameConfig;
import com.example.demo.Levels.LevelParent;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;

/**
 * Represents the UI elements and functionalities specific to Level One.
 */
public class LevelViewLevelOne extends LevelViewParent {

    /**
     * Label to display the player's kill count.
     */
    private Label killsLabel;

    /**
     * Constructs a LevelViewLevelOne instance with the specified parameters.
     *
     * @param root            the root group for UI elements
     * @param heartsToDisplay the number of hearts to display
     * @param levelParent     reference to the parent level
     */
    public LevelViewLevelOne(Group root, int heartsToDisplay, LevelParent levelParent) {
        super(root, heartsToDisplay, levelParent);
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
        System.out.println("Kill counter label initialized and added to root.");
    }

    /**
     * Updates the kill counter label to reflect the player's current kill count.
     */
    @Override
    public void updateWinningParameter() {
        Platform.runLater(() -> {
            killsLabel.setText("Kills💀: " + getLevelParent().getNumberOfKills());
            killsLabel.toFront();
        });
    }
}
