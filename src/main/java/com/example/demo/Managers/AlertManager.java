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

public class AlertManager {

    private static AlertManager instance; // Singleton instance

    private AlertManager() {} // Private constructor for Singleton

    public static AlertManager getInstance() {
        if (instance == null) {
            instance = new AlertManager();
        }
        return instance;
    }

    public void showAlert(String message) {
        // Retrieve the current root dynamically
        Stage stage = StageManager.getStage(); // Assuming StageManager manages the global stage
        if (stage.getScene().getRoot() instanceof Group root) {
            // Create a semi-transparent background
            Rectangle alertBox = new Rectangle(400, 100, Color.CYAN);
            alertBox.setArcWidth(40);
            alertBox.setArcHeight(50);
            alertBox.setOpacity(0.2); // Slight transparency

            // Create a message text
            Text alertText = new Text(message);
            alertText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: red;");
            alertText.setWrappingWidth(200); // Prevent text from exceeding box width
            alertText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            alertText.setBoundsType(javafx.scene.text.TextBoundsType.VISUAL);

            // Center text inside the alert box
            alertText.setLayoutX(alertBox.getWidth() / 2 - alertText.getLayoutBounds().getWidth() / 2);
            alertText.setLayoutY(alertBox.getHeight() / 2 - alertText.getLayoutBounds().getHeight() / 2);

            // Create a container for the alert
            Group alertOverlay = new Group();
            alertOverlay.getChildren().addAll(alertBox, alertText);

            // Add alert to the root
            root.getChildren().add(alertOverlay);

            // Position the alert slightly higher and to the left
            double offsetX = 150; // Move left
            double offsetY = 120; // Move up
            double centerX = stage.getScene().getWidth() / 2 - alertBox.getWidth() / 2 - offsetX;
            double centerY = stage.getScene().getHeight() / 2 - alertBox.getHeight() / 2 - offsetY;
            alertOverlay.setLayoutX(centerX);
            alertOverlay.setLayoutY(centerY);

            // Add animations: expand slightly, then fade out
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
            sequence.setOnFinished(e -> root.getChildren().remove(alertOverlay)); // Remove after fade out
            sequence.play();
        } else {
            System.err.println("Root is not a Group. Unable to display alert.");
        }
    }
    // New method for informational alerts with animations
    public void showInfoAlert(String message, double screenWidth, double screenHeight) {
        Platform.runLater(() -> {
            Stage stage = StageManager.getStage();
            if (stage.getScene().getRoot() instanceof Group root) {
                Label infoLabel = new Label(message);
                infoLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: red;");

                // Position the label dynamically at the center of the screen
                infoLabel.setLayoutX(screenWidth / 2 - 150); // Adjust X to center
                infoLabel.setLayoutY(screenHeight / 2 - 50); // Adjust Y to center
                root.getChildren().add(infoLabel);

                // Scale Transition for the "pop-up" effect
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

                // Fade Transition for smooth fade-in and fade-out
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), infoLabel);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);

                PauseTransition pause = new PauseTransition(Duration.seconds(3)); // Pause before fade out

                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), infoLabel);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                // Remove label after the animation sequence
                fadeOut.setOnFinished(event -> root.getChildren().remove(infoLabel));

                // Play animations sequentially
                fadeIn.setOnFinished(event -> scaleUp.play());
                scaleUp.setOnFinished(event -> scaleDown.play());
                scaleDown.setOnFinished(event -> pause.play());
                pause.setOnFinished(event -> fadeOut.play());

                fadeIn.play(); // Start animation sequence

            } else {
                System.err.println("Root is not a Group. Unable to display informational alert.");
            }
        });

}}
