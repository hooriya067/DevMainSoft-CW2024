package com.example.demo.UI.buttons;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.Objects;

public abstract class ButtonParent {

    private final Button button;

    public ButtonParent(String imagePath, double fitWidth, boolean preserveRatio) {
        this.button = new Button();
        initializeButton(imagePath, fitWidth, preserveRatio);
        addBounceEffect(); // Add bounce effect to the button
    }

    private void initializeButton(String imagePath, double fitWidth, boolean preserveRatio) {
        try {
            // Load the image
            Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(fitWidth);
            imageView.setPreserveRatio(preserveRatio);

            // Set the button's graphic
            button.setGraphic(imageView);

            // Apply default styles
            button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        } catch (Exception e) {
            System.err.println("Error loading button image: " + e.getMessage());
        }
    }

    // Method to add a bounce effect when the button is clicked
    private void addBounceEffect() {
        button.setOnMousePressed(event -> createBounceAnimation(0.9).play()); // Scale down
        button.setOnMouseReleased(event -> createBounceAnimation(1.0).play()); // Scale back to normal
    }

    // Method to create the bounce animation
    private ScaleTransition createBounceAnimation(double scaleValue) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setToX(scaleValue);
        scaleTransition.setToY(scaleValue);
        return scaleTransition;
    }

    // Method to set the click action
    public void setOnClick(Runnable action) {
        button.setOnAction(event -> action.run());
    }

    public Button getButton() {
        return button;
    }
}
