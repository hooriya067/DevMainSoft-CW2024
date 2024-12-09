package com.example.demo.UI.menu;

import com.example.demo.Managers.InputHandlingManager;
import com.example.demo.Managers.SoundManager;
import com.example.demo.UI.screens.MainMenu;
import com.example.demo.UI.buttons.MainMenuButton;
import com.example.demo.UI.buttons.QuitButton;
import com.example.demo.UI.buttons.ResumeButton;
import com.example.demo.core.GameStateManager;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PauseMenu extends OnScreenMenu {

    public PauseMenu(Stage stage) {
        super(stage);
    }

    public void displayOverlay() {

        VBox menuContent = createMenuContent();

        super.displayOverlay(menuContent);
    }

    @Override
    protected VBox createMenuContent() {
        VBox menuBox = new VBox(40);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.7); -fx-padding: 20;");
        menuBox.setMaxWidth(400);
        menuBox.setMaxHeight(300);

        MainMenuButton mainMenuButton = new MainMenuButton();
        mainMenuButton.setOnMainMenu(() -> {
            removeOverlay();
            new MainMenu(stage);
        });

        ResumeButton resumeButton = new ResumeButton();
        resumeButton.setOnResume(this::startResumeCountdown);

        QuitButton quitButton = new QuitButton();
        quitButton.setOnClick(stage::close);

        menuBox.getChildren().addAll(mainMenuButton.getButton(), resumeButton.getButton(), quitButton.getButton());
        return menuBox;
    }

}
