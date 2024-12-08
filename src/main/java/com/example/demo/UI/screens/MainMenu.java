package com.example.demo.UI.screens;

import com.example.demo.UI.buttons.SoundButton;
import com.example.demo.UI.buttons.QuitButton;
import com.example.demo.UI.buttons.StartGameButton;
import com.example.demo.UI.buttons.CustomizePlaneButton;
import com.example.demo.controller.Controller;
import com.example.demo.core.GameConfig;
import com.example.demo.core.GameStateManager;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMenu {

    private static final double MENU_WIDTH = GameConfig.SCREEN_WIDTH;
    private static final double MENU_HEIGHT = GameConfig.SCREEN_HEIGHT;

    public MainMenu(Stage primaryStage) {
        // Load the background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/background0.png")).toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(MENU_WIDTH);
        backgroundImageView.setFitHeight(MENU_HEIGHT);

        // Set the VBox for the main menu
        VBox menuBox = new VBox(10);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.9); -fx-padding: 20;");
        menuBox.setMaxWidth(600);

        // Add the components
        Title title = new Title();
        StartGameButton startButton = new StartGameButton();
        CustomizePlaneButton customizeButton = new CustomizePlaneButton(); // Use the new button class
        QuitButton quitButton = new QuitButton();
        menuBox.getChildren().addAll(title.getTitleImage(), startButton.getButton(), customizeButton.getButton(), quitButton.getButton());

        menuBox.setLayoutX((MENU_WIDTH - menuBox.getMaxWidth()) / 2 +50);
        menuBox.setLayoutY(MENU_HEIGHT * 0.1);

        // Add the sound button
        SoundButton soundButton = new SoundButton();
        Pane soundIconPane = new Pane();
        soundIconPane.getChildren().add(soundButton.getSoundButtonImage());
        soundButton.getSoundButtonImage().setLayoutX(GameConfig.SCREEN_WIDTH - 120);
        soundButton.getSoundButtonImage().setLayoutY(20);

        // Create a Group for layout
        Group rootLayout = new Group();
        rootLayout.getChildren().addAll(backgroundImageView, menuBox, soundIconPane);

        // Event handler for start button
        startButton.setOnStartGame(() -> {
            try {
                GameStateManager.getInstance().resumeGame();   //explicitly true flag true whenever game starts
                Controller gameController = new Controller(primaryStage);
                gameController.launchGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        customizeButton.setOnCustomize(() -> {
            CustomizationScreen customizationScreen = new CustomizationScreen(primaryStage, () -> {
                new MainMenu(primaryStage);
            });
            primaryStage.getScene().setRoot(customizationScreen);
        });

        quitButton.setOnQuit(primaryStage);

        Scene scene = new Scene(rootLayout, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        rootLayout.requestFocus();
    }
}
