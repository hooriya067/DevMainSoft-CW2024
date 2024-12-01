package com.example.demo.UI.screens;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Title {
    private final ImageView titleImage;

    public Title() {
        // Load the title image
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/title1.png")).toExternalForm());
        titleImage = new ImageView(image);
        titleImage.setFitWidth(650);
        titleImage.setPreserveRatio(true);
    }

    public ImageView getTitleImage() {
        return titleImage;
    }
}
