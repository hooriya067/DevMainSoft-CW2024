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

    // Constructor to initialize stage reference
    public PauseMenu(Stage stage) {
        this.stage = stage;
    }

    public void displayOverlay() {


        // Notify the GameStateManager to pause the game
        GameStateManager.getInstance().pauseGame();

        // Create overlay UI (Translucent background and menu box)
        Pane overlayBackground = new Pane();
        overlayBackground.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlayBackground.setPrefSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        VBox menuBox = new VBox(40);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.7); -fx-padding: 20;");
        menuBox.setMaxWidth(400);
        menuBox.setMaxHeight(300);

        // Main Menu, Resume, and Quit Buttons
        MainMenuButton mainMenuButton = new MainMenuButton();
        mainMenuButton.setOnMainMenu(() -> {
          //  removeOverlay();  // Remove the pause menu
            stage.setHeight(GameConfig.SCREEN_HEIGHT);
            stage.setWidth(GameConfig.SCREEN_WIDTH);

            new MainMenu(stage);

        });

        System.out.println("debug: adding a resume button...");
        ResumeButton resumeButton = new ResumeButton();
        resumeButton.setOnResume(this);  // Pass this PauseMenu instance to ResumeButton for removal

        QuitButton quitButton = new QuitButton();
        quitButton.setOnQuit(stage);

        menuBox.getChildren().addAll(mainMenuButton.getMainMenuButtonImage(), resumeButton.getResumeButtonImage(), quitButton.getQuitButtonImage());

        // Create the overlay layout that includes the background and menu box
        overlayLayout = new StackPane();
        overlayLayout.getChildren().addAll(overlayBackground, menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER);

        // Add the overlay to the root
        if (stage.getScene().getRoot() instanceof StackPane) {
            StackPane currentRoot = (StackPane) stage.getScene().getRoot();
            currentRoot.getChildren().add(overlayLayout);
        } else if (stage.getScene().getRoot() instanceof Group) {
            Group currentRoot = (Group) stage.getScene().getRoot();
            currentRoot.getChildren().add(overlayLayout); // Add to group without changing the root
        } else {
            // If the root is neither StackPane nor Group, wrap the existing root in a new StackPane
            System.out.println("Wrapping root in a new StackPane");
            Pane oldRoot = (Pane) stage.getScene().getRoot();
            StackPane newRoot = new StackPane();
            newRoot.getChildren().add(oldRoot);
            newRoot.getChildren().add(overlayLayout);
            stage.getScene().setRoot(newRoot);
        }
    }

    public void removeOverlay() {
        if (overlayLayout != null && overlayLayout.getParent() instanceof StackPane) {
            StackPane currentRoot = (StackPane) overlayLayout.getParent();
            // Option 1: Remove the entire overlay layout (background + menu)
            currentRoot.getChildren().removeAll(overlayLayout);
            // currentRoot.getChildren().remove(menuBox);  // This will remove just the menu box

            System.out.println("Pause menu overlay removed.");
        }
    }

}
