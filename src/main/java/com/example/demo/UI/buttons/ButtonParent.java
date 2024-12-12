/**
 * The {@code ButtonParent} class provides a base implementation for creating customized buttons
 * with visual effects such as hover highlights and bounce animations on click.
 * This class simplifies the creation of buttons with consistent styling and behavior.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Hover effect with brightness adjustment.</li>
 *     <li>Bounce animation when the button is pressed and released.</li>
 *     <li>Support for setting an image as the button's graphic.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link javafx.animation.ScaleTransition}: Used for creating bounce animations.</li>
 *     <li>{@link javafx.scene.image.Image}: Represents the image used for the button's graphic.</li>
 *     <li>{@link javafx.scene.image.ImageView}: Displays the image on the button.</li>
 *     <li>{@link javafx.scene.effect.ColorAdjust}: Applies hover effects to the button.</li>
 * </ul>
 */
package com.example.demo.UI.buttons;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;

import java.util.Objects;

/**
 * Abstract base class for creating custom buttons with enhanced visual effects.
 */
public abstract class ButtonParent {

    /**
     * The underlying {@link Button} instance.
     */
    private final Button button;

    /**
     * Constructs a new {@code ButtonParent} with the specified image and styling options.
     *
     * @param imagePath      the file path of the image to use for the button's graphic
     * @param fitWidth       the width to fit the button's graphic
     * @param preserveRatio  whether to preserve the aspect ratio of the image
     */
    public ButtonParent(String imagePath, double fitWidth, boolean preserveRatio) {
        this.button = new Button();
        initializeButton(imagePath, fitWidth, preserveRatio);
        addBounceEffect();
        addHoverEffect();
    }

    /**
     * Initializes the button with an image and default styling.
     *
     * @param imagePath      the file path of the image to use for the button's graphic
     * @param fitWidth       the width to fit the button's graphic
     * @param preserveRatio  whether to preserve the aspect ratio of the image
     */
    private void initializeButton(String imagePath, double fitWidth, boolean preserveRatio) {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(fitWidth);
            imageView.setPreserveRatio(preserveRatio);

            button.setGraphic(imageView);
            button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        } catch (Exception e) {
            System.err.println("Error loading button image: " + e.getMessage());
        }
    }

    /**
     * Adds a bounce effect to the button when it is pressed and released.
     */
    private void addBounceEffect() {
        button.setOnMousePressed(event -> createBounceAnimation(0.60).play());
        button.setOnMouseReleased(event -> createBounceAnimation(1.0).play());
    }

    /**
     * Creates a bounce animation for the button.
     *
     * @param scaleValue the scale value for the animation
     * @return the {@link ScaleTransition} instance representing the animation
     */
    private ScaleTransition createBounceAnimation(double scaleValue) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
        scaleTransition.setToX(scaleValue);
        scaleTransition.setToY(scaleValue);
        return scaleTransition;
    }

    /**
     * Adds a hover effect to the button, applying a brightness adjustment.
     */
    private void addHoverEffect() {
        ColorAdjust hoverEffect = new ColorAdjust();
        hoverEffect.setBrightness(0.2);

        button.setOnMouseEntered(event -> button.setEffect(hoverEffect));
        button.setOnMouseExited(event -> button.setEffect(null));
    }

    /**
     * Sets the action to be executed when the button is clicked.
     *
     * @param action the action to execute on button click
     */
    public void setOnClick(Runnable action) {
        button.setOnAction(event -> action.run());
    }

    /**
     * Retrieves the underlying {@link Button} instance.
     *
     * @return the button instance
     */
    public Button getButton() {
        return button;
    }
}
