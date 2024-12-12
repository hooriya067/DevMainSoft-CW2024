/**
 * The {@code AlertManager} class is a Singleton that provides utilities for displaying
 * custom alerts and informational messages within the game. It handles animations,
 * transitions, and dynamic positioning for a polished user experience.
 *
 * <p>This class integrates with the {@link StageManager} to dynamically retrieve
 * the active scene root for displaying alerts. It supports standard alerts as well
 * as informational alerts with animation sequences.</p>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link StageManager}: Retrieves the global stage for positioning alerts.</li>
 *     <li>{@link javafx.animation}: Used for creating animations such as fade and scale transitions.</li>
 *     <li>{@link javafx.scene.control.Label}: Used to display text for alerts.</li>
 *     <li>{@link javafx.scene.Group}: The container to which alert elements are added.</li>
 * </ul>
 */
package com.example.demo.Managers;

import com.example.demo.core.StageManager;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Manages and displays alert messages with animations and dynamic positioning.
 */
public class AlertManager {

    /**
     * Singleton instance of the AlertManager.
     */
    private static AlertManager instance;

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private AlertManager() {}

    /**
     * Retrieves the singleton instance of the AlertManager.
     *
     * @return the singleton instance
     */
    public static AlertManager getInstance() {
        if (instance == null) {
            instance = new AlertManager();
        }
        return instance;
    }

    /**
     * Displays a standard alert message with animations and transitions.
     *
     * @param message the alert message to display
     */
    public void showAlert(String message) {
        Stage stage = StageManager.getStage();
        if (stage.getScene().getRoot() instanceof Group root) {
            Rectangle alertBox = new Rectangle(400, 100, Color.CYAN);
            alertBox.setArcWidth(40);
            alertBox.setArcHeight(50);
            alertBox.setOpacity(0.2);

            Text alertText = new Text(message);
            alertText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: red;");
            alertText.setWrappingWidth(200);
            alertText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

            alertText.setLayoutX(alertBox.getWidth() / 2 - alertText.getLayoutBounds().getWidth() / 2);
            alertText.setLayoutY(alertBox.getHeight() / 2 - alertText.getLayoutBounds().getHeight() / 2);

            Group alertOverlay = new Group(alertBox, alertText);
            root.getChildren().add(alertOverlay);

            double offsetX = 150;
            double offsetY = 120;
            double centerX = stage.getScene().getWidth() / 2 - alertBox.getWidth() / 2 - offsetX;
            double centerY = stage.getScene().getHeight() / 2 - alertBox.getHeight() / 2 - offsetY;
            alertOverlay.setLayoutX(centerX);
            alertOverlay.setLayoutY(centerY);

            ScaleTransition expand = new ScaleTransition(Duration.millis(200), alertOverlay);
            expand.setToX(1.1);
            expand.setToY(1.1);

            ScaleTransition shrink = new ScaleTransition(Duration.millis(200), alertOverlay);
            shrink.setToX(1.0);
            shrink.setToY(1.0);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(1500), alertOverlay);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            SequentialTransition sequence = new SequentialTransition(expand, shrink, fadeOut);
            sequence.setOnFinished(e -> root.getChildren().remove(alertOverlay));
            sequence.play();
        } else {
            System.err.println("Root is not a Group. Unable to display alert.");
        }
    }

    /**
     * Displays an informational alert with animations and a timed sequence.
     *
     * @param message      the informational message to display
     * @param screenWidth  the width of the screen for positioning the alert
     * @param screenHeight the height of the screen for positioning the alert
     */
    public void showInfoAlert(String message, double screenWidth, double screenHeight) {
        Platform.runLater(() -> {
            Stage stage = StageManager.getStage();
            if (stage.getScene().getRoot() instanceof Group root) {
                Label infoLabel = new Label(message);
                infoLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: red;");

                infoLabel.setLayoutX(screenWidth / 2 - 150);
                infoLabel.setLayoutY(screenHeight / 2 - 50);
                root.getChildren().add(infoLabel);

                ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.5), infoLabel);
                scaleUp.setFromX(1.0);
                scaleUp.setFromY(1.0);
                scaleUp.setToX(2.0);
                scaleUp.setToY(2.0);

                ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(0.5), infoLabel);
                scaleDown.setFromX(2.0);
                scaleDown.setFromY(2.0);
                scaleDown.setToX(1.0);
                scaleDown.setToY(1.0);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), infoLabel);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);

                PauseTransition pause = new PauseTransition(Duration.seconds(3));

                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), infoLabel);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event -> root.getChildren().remove(infoLabel));

                fadeIn.setOnFinished(event -> scaleUp.play());
                scaleUp.setOnFinished(event -> scaleDown.play());
                scaleDown.setOnFinished(event -> pause.play());
                pause.setOnFinished(event -> fadeOut.play());

                fadeIn.play();
            } else {
                System.err.println("Root is not a Group. Unable to display informational alert.");
            }
        });
    }
}