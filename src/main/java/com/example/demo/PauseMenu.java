package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.stage.Stage;

public class PauseMenu {
    private final Stage stage;
    private StackPane overlayLayout;
    private boolean overlayActive = false;

    public PauseMenu(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null in PauseMenu.");
        }
        this.stage = stage;
    }

    public void displayOverlay() {
        System.out.println("Pause button clicked! now we in displayyyyylayover"); // Debug

        if (overlayActive) {
            System.err.println("Pause menu is already active! overlayActive = true");
            return;
        }

        overlayActive = true;
        System.out.println("overlayActive set to true.");

        GameStateManager.getInstance().pauseGame();
        System.out.println("Game paused!");

        // Create overlay
        Pane overlayBackground = new Pane();
        overlayBackground.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlayBackground.setPrefSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        VBox menuBox = new VBox(40);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.7); -fx-padding: 20;");
        menuBox.setMaxWidth(400);
        menuBox.setMaxHeight(300);

        MainMenuButton mainMenuButton = new MainMenuButton();
        mainMenuButton.setOnMainMenu(() -> {
            System.out.println("Main menu button clicked!");
            removeOverlay();
            new MainMenu(stage);
        });

        ResumeButton resumeButton = new ResumeButton();
        resumeButton.setOnResume(() -> {
            System.out.println("Resume button clicked!");
            removeOverlay();
            GameStateManager.getInstance().resumeGame();
        });

        QuitButton quitButton = new QuitButton();
        quitButton.setOnClick(() -> {
            System.out.println("Quit button clicked!");
            stage.close();
        });

        menuBox.getChildren().addAll(mainMenuButton.getButton(), resumeButton.getButton(), quitButton.getButton());

        overlayLayout = new StackPane();
        overlayLayout.getChildren().addAll(overlayBackground, menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER);

        if (stage.getScene().getRoot() instanceof Group) {
            System.out.println("Root is a Group. Adding overlay directly.");
            Group currentRoot = (Group) stage.getScene().getRoot();
            if (!currentRoot.getChildren().contains(overlayLayout)) {
                currentRoot.getChildren().add(overlayLayout);
                System.out.println("Overlay added to Group.");
            } else {
                System.err.println("Overlay is already in the Group.");
            }
        } else {
            System.err.println("Unexpected root type: " + stage.getScene().getRoot().getClass().getName());
        }
    }
    public void removeOverlay() {
        if (overlayLayout == null) {
            System.err.println("Cannot remove overlay: overlayLayout is null!");
            return;
        }

        if (overlayLayout.getParent() instanceof Group) {
            Group currentRoot = (Group) overlayLayout.getParent();
            currentRoot.getChildren().remove(overlayLayout);
            overlayActive = false;
            System.out.println("Overlay removed from Group. overlayActive set to false.");
        } else {
            System.err.println("Cannot remove overlay: Unexpected parent type: " + overlayLayout.getParent());
        }
    }


}
