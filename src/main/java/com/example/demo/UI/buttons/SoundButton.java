
package com.example.demo.UI.buttons;

import com.example.demo.Managers.SoundManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
/**
 * The {@link  SoundButton} class represents a button for controlling the game's sound.
 * It allows users to toggle sound on and off with a visual representation.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Displays an image representing the current sound state (on/off).</li>
 *     <li>Handles click events to toggle sound state.</li>
 *     <li>Integrates with {@link SoundManager} to control the sound system.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 *     SoundButton soundButton = new SoundButton();
 *     soundButton.setPosition(100, 50); // Position on screen
 *     root.getChildren().add(soundButton.getSoundButtonImage());
 * </pre>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link SoundManager}: Manages the sound system of the game.</li>
 * </ul>
 */

public class SoundButton {

    /**
     * The {@code ImageView} displaying the current state of the sound button (on or off).
     */
    private final ImageView soundButtonImage;

    /**
     * Tracks whether the sound is currently muted.
     */
    private boolean isMuted;

    /**
     * The {@code Image} displayed when the sound is turned on.
     */
    private final Image soundOnImage;

    /**
     * The {@code Image} displayed when the sound is turned off.
     */
    private final Image soundOffImage;

    // Constructor and methods here


    /**
     * Constructs a {@code SoundButton} with default images for sound on and off states.
     */
    public SoundButton() {
        // Load the sound on and off images
        soundOnImage = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/sound_unmute.png")).toExternalForm());
        soundOffImage = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/sound_mute.png")).toExternalForm());

        // Default to sound on
        soundButtonImage = new ImageView(soundOnImage);
        soundButtonImage.setFitWidth(90);
        soundButtonImage.setPreserveRatio(true);

        isMuted = false; // Default state

        // Add click event to toggle sound
        soundButtonImage.setOnMouseClicked(e -> toggleSound());
    }

    /**
     * Returns the {@link ImageView} representing the sound button.
     *
     * @return the {@link ImageView} for this button
     */
    public ImageView getSoundButtonImage() {
        return soundButtonImage;
    }

    /**
     * Sets the position of the sound button on the screen.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public void setPosition(double x, double y) {
        getSoundButtonImage().setLayoutX(x);
        getSoundButtonImage().setLayoutY(y);
    }

    /**
     * Toggles the sound state between muted and unmuted.
     * Updates the button's image and notifies the {@link SoundManager}.
     */
    private void toggleSound() {
        isMuted = !isMuted; // Toggle the muted state

        if (isMuted) {
            soundButtonImage.setImage(soundOffImage); // Update to muted image
        } else {
            soundButtonImage.setImage(soundOnImage); // Update to unmuted image
        }

        // Call the SoundManager to mute or unmute sounds
        SoundManager.getInstance().toggleMute();
    }
}
