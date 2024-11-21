package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class MainMenuButton {
    private static final String MAIN_MENU_BUTTON_IMAGE_NAME = "/com/example/demo/images/mainmenu_icon.png";
    private static final int BUTTON_SIZE = 70;

    private final ImageView mainMenuButtonImage;

    public MainMenuButton() {
        // Load the main menu button image
        mainMenuButtonImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(MAIN_MENU_BUTTON_IMAGE_NAME)).toExternalForm()));
        mainMenuButtonImage.setFitWidth(BUTTON_SIZE);
        mainMenuButtonImage.setFitHeight(BUTTON_SIZE);
        mainMenuButtonImage.setPreserveRatio(true);
        mainMenuButtonImage.setPickOnBounds(true);
    }

    public ImageView getMainMenuButtonImage() {
        return mainMenuButtonImage;
    }

    public void setOnMainMenu(Runnable action) {
        mainMenuButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            action.run();
//            event.consume();

        });

    }
}