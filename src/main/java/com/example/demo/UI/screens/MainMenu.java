package com.example.demo.UI.screens;

import com.example.demo.UI.buttons.*;
import com.example.demo.controller.Controller;
import com.example.demo.core.GameConfig;
import com.example.demo.core.GameStateManager;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMenu {

    private static final double MENU_WIDTH = GameConfig.SCREEN_WIDTH;
    private static final double MENU_HEIGHT = GameConfig.SCREEN_HEIGHT;

    public MainMenu(Stage primaryStage) {

        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/background0.png")).toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(MENU_WIDTH);
        backgroundImageView.setFitHeight(MENU_HEIGHT);

        // Create grey box for menu
        VBox greyBox = new VBox(20);
        greyBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8); -fx-padding: 20; -fx-border-radius: 10;");
        greyBox.setMaxWidth(600);
        greyBox.setAlignment(Pos.CENTER);

        // Title image
        Title title = new Title();
        ImageView titleImage = title.getTitleImage();
        titleImage.setFitWidth(400); // Adjust title size
        titleImage.setPreserveRatio(true);
        greyBox.setSpacing(50);



        // GridPane for buttons
        GridPane buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setHgap(0);
        buttonGrid.setVgap(0);

        // Buttons
        StartGameButton startButton = new StartGameButton();
        CustomizePlaneButton customizeButton = new CustomizePlaneButton();
        InstructionButton instructionButton = new InstructionButton();
        QuitButton quitButton = new QuitButton();

        // Add buttons to grid (row, column)
        buttonGrid.add(startButton.getButton(), 0, 0);
        buttonGrid.add(customizeButton.getButton(), 1, 0);
        buttonGrid.add(instructionButton.getButton(), 0, 1);
        buttonGrid.add(quitButton.getButton(), 1, 1);

        // Align buttons in the grid
        GridPane.setHalignment(startButton.getButton(), HPos.CENTER);
        GridPane.setHalignment(customizeButton.getButton(), HPos.CENTER);
        GridPane.setHalignment(instructionButton.getButton(), HPos.CENTER);
        GridPane.setHalignment(quitButton.getButton(), HPos.CENTER);

        // Add title and buttons to grey box
        greyBox.getChildren().addAll(titleImage, buttonGrid);

        // Center grey box
        greyBox.setLayoutX((MENU_WIDTH - greyBox.getMaxWidth()) / 2 -50);
        greyBox.setLayoutY(MENU_HEIGHT * 0.1);


        // Add the sound button
        SoundButton soundButton = new SoundButton();
        Pane soundIconPane = new Pane();
        soundIconPane.getChildren().add(soundButton.getSoundButtonImage());
        soundButton.getSoundButtonImage().setLayoutX(GameConfig.SCREEN_WIDTH - 120);
        soundButton.getSoundButtonImage().setLayoutY(20);

        Group rootLayout = new Group();
        rootLayout.getChildren().addAll(backgroundImageView, greyBox, soundIconPane);

        startButton.setOnStartGame(() -> {
            try {
                GameStateManager.getInstance().resumeGame();
                Controller gameController = new Controller(primaryStage);
                gameController.launchGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        customizeButton.setOnCustomize(() -> {
            CustomizationScreen customizationScreen = new CustomizationScreen(primaryStage, () -> new MainMenu(primaryStage));
            primaryStage.getScene().setRoot(customizationScreen);
        });

        instructionButton.setOnInstructions(() -> {
            InstructionScreen instructionScreen = new InstructionScreen(primaryStage, () -> new MainMenu(primaryStage));
            primaryStage.getScene().setRoot(instructionScreen);
        });

        quitButton.setOnQuit(primaryStage);

        Scene scene = new Scene(rootLayout, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        rootLayout.requestFocus();
    }
}
