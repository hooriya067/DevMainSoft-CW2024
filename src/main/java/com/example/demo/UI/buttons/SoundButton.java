package com.example.demo.UI.buttons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class SoundButton {
    private final ImageView soundButtonImage;

    public SoundButton() {
        // Load the sound button image
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/sound_icon.png")).toExternalForm());
        soundButtonImage = new ImageView(image);
        soundButtonImage.setFitWidth(90);
        soundButtonImage.setPreserveRatio(true);
    }

    public ImageView getSoundButtonImage() {
        return soundButtonImage;
    }
}
