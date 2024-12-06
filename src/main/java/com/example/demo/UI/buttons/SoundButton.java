package com.example.demo.UI.buttons;

import com.example.demo.Managers.SoundManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class SoundButton {
    private final ImageView soundButtonImage;
    private boolean isMuted; // Track the current state of sound
    private final Image soundOnImage;
    private final Image soundOffImage;

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

    public ImageView getSoundButtonImage() {
        return soundButtonImage;
    }
    public void setPosition(double x, double y) {
        getSoundButtonImage().setLayoutX(x);
        getSoundButtonImage().setLayoutY(y);
    }
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
