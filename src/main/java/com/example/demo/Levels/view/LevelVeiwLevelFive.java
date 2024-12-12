/**
 * The {@code LevelVeiwLevelFive} class extends {@link LevelViewParent} to provide specific
 * UI functionalities and visual elements for Level Five of the game.
 *
 * <p>This class introduces animated cloud effects, a kill counter label, and dynamic
 * updates to UI elements to enhance the player's experience during gameplay.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link LevelViewParent}: Provides the foundational UI management for all levels.</li>
 *     <li>{@link LevelParent}: The parent level class to which this UI is associated.</li>
 *     <li>{@link GameConfig}: Provides configuration constants for screen dimensions.</li>
 *     <li>{@link Cloud}: Represents the visual components for cloud animations.</li>
 * </ul>
 */
package com.example.demo.Levels.view;

import com.example.demo.core.GameConfig;
import com.example.demo.Levels.LevelParent;
import com.example.demo.Levels.effects.Cloud;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;

/**
 * Represents the UI elements and functionalities specific to Level Five.
 */
public class LevelVeiwLevelFive extends LevelViewParent {

    /**
     * Label to display the player's kill count.
     */
    private Label killsLabel;

    /**
     * Animated cloud effect 1.
     */
    private final Cloud cloud1;

    /**
     * Animated cloud effect 2.
     */
    private final Cloud cloud2;

    /**
     * Constructs a LevelVeiwLevelFive instance with the specified parameters.
     *
     * @param root            the root group for UI elements
     * @param heartsToDisplay the number of hearts to display
     * @param screenWidth     the width of the screen
     * @param screenHeight    the height of the screen
     * @param levelParent     reference to the parent level
     */
    public LevelVeiwLevelFive(Group root, int heartsToDisplay, double screenWidth, double screenHeight, LevelParent levelParent) {
        super(root, heartsToDisplay, levelParent);

        double buffer = 10;
        cloud1 = new Cloud(screenWidth - 800, 0, false, buffer, 400);
        cloud2 = new Cloud(screenWidth - 500, 0, true, buffer, 400);

        // Add cloud containers to the root group
        getRoot().getChildren().addAll(cloud1.getContainer(), cloud2.getContainer());
    }

    /**
     * Updates the positions of the clouds to create a dynamic background effect.
     */
    public void updateClouds() {
        double deltaX = 2;
        cloud1.move(deltaX, GameConfig.SCREEN_WIDTH);
        cloud2.move(deltaX, GameConfig.SCREEN_WIDTH);
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
     * Adds UI elements to the screen, including clouds and the toolbar elements.
     */
    @Override
    public void AddUI() {
        super.AddUI();
        showClouds();
        showToolBarElements();
    }

    /**
     * Brings the clouds to the front and ensures they are displayed correctly.
     */
    protected void showClouds() {
        cloud1.getContainer().toFront();
        cloud2.getContainer().toFront();
    }
}
