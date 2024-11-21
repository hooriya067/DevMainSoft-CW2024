package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class ResumeButton {
    private static final String RESUME_BUTTON_IMAGE_NAME = "/com/example/demo/images/resume_icon.png";
    private static final int BUTTON_SIZE = 70;

    private final ImageView resumeButtonImage;

    public ResumeButton() {
        // Load the resume button image
        resumeButtonImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(RESUME_BUTTON_IMAGE_NAME)).toExternalForm()));
        resumeButtonImage.setFitWidth(BUTTON_SIZE);
        resumeButtonImage.setFitHeight(BUTTON_SIZE);
        resumeButtonImage.setPreserveRatio(true);
        resumeButtonImage.setPickOnBounds(true);
    }

    public ImageView getResumeButtonImage() {
        return resumeButtonImage;
    }

    public void setOnResume(PauseMenu pauseMenu) {
        resumeButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Remove overlay using the PauseMenu instance
            pauseMenu.removeOverlay();
            // Resume the game using GameStateManager
            GameStateManager.getInstance().resumeGame();
            System.out.println("Game resumed from ResumeButton");
            event.consume();
        });
    }
}