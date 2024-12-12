/**
 * The {@code MainMenu} class represents the main menu screen of the game.
 * It provides navigation options to start the game, customize the plane, view instructions, and quit the application.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Background image with a visually appealing layout.</li>
 *     <li>Title displayed prominently at the top of the menu.</li>
 *     <li>Buttons for game navigation arranged in a grid layout.</li>
 *     <li>Sound toggle button for muting/unmuting game sounds.</li>
 *     <li>Responsive design with elements centered and appropriately spaced.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link StartGameButton}: Button to start the game.</li>
 *     <li>{@link CustomizePlaneButton}: Button to navigate to the plane customization screen.</li>
 *     <li>{@link InstructionButton}: Button to navigate to the instruction screen.</li>
 *     <li>{@link QuitButton}: Button to quit the application.</li>
 *     <li>{@link SoundButton}: Button to toggle sound settings.</li>
 *     <li>{@link CustomizationScreen}: Screen for customizing the player's plane.</li>
 *     <li>{@link InstructionScreen}: Screen displaying game instructions.</li>
 *     <li>{@link Controller}: Handles the initialization and launch of the game.</li>
 *     <li>{@link GameStateManager}: Manages the game's state (paused/resumed).</li>
 * </ul>
 */
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

    /**
     * Constructs a {@code MainMenu} instance and initializes its elements.
     *
     * @param primaryStage the primary stage for the application
     */
    public MainMenu(Stage primaryStage) {
        // Background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/background0.png")).toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(MENU_WIDTH);
        backgroundImageView.setFitHeight(MENU_HEIGHT);

        // Grey box containing menu items
        VBox greyBox = new VBox(20);
        greyBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8); -fx-padding: 20; -fx-border-radius: 10;");
        greyBox.setMaxWidth(600);
        greyBox.setAlignment(Pos.CENTER);

        // Title image
        Title title = new Title();
        ImageView titleImage = title.getTitleImage();
        titleImage.setFitWidth(400);
        titleImage.setPreserveRatio(true);
        greyBox.setSpacing(50);

        // GridPane for menu buttons
        GridPane buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setHgap(0);
        buttonGrid.setVgap(0);

        // Initialize buttons
        StartGameButton startButton = new StartGameButton();
        CustomizePlaneButton customizeButton = new CustomizePlaneButton();
        InstructionButton instructionButton = new InstructionButton();
        QuitButton quitButton = new QuitButton();

        // Arrange buttons in the grid
        buttonGrid.add(startButton.getButton(), 0, 0);
        buttonGrid.add(customizeButton.getButton(), 1, 0);
        buttonGrid.add(instructionButton.getButton(), 0, 1);
        buttonGrid.add(quitButton.getButton(), 1, 1);

        // Align buttons in the grid
        GridPane.setHalignment(startButton.getButton(), HPos.CENTER);
        GridPane.setHalignment(customizeButton.getButton(), HPos.CENTER);
        GridPane.setHalignment(instructionButton.getButton(), HPos.CENTER);
        GridPane.setHalignment(quitButton.getButton(), HPos.CENTER);

        // Add title and buttons to the grey box
        greyBox.getChildren().addAll(titleImage, buttonGrid);

        // Position the grey box
        greyBox.setLayoutX((MENU_WIDTH - greyBox.getMaxWidth()) / 2 - 50);
        greyBox.setLayoutY(MENU_HEIGHT * 0.1);

        // Add sound toggle button
        SoundButton soundButton = new SoundButton();
        Pane soundIconPane = new Pane();
        soundIconPane.getChildren().add(soundButton.getSoundButtonImage());
        soundButton.getSoundButtonImage().setLayoutX(GameConfig.SCREEN_WIDTH - 120);
        soundButton.getSoundButtonImage().setLayoutY(20);

        // Root layout for the main menu
        Group rootLayout = new Group();
        rootLayout.getChildren().addAll(backgroundImageView, greyBox, soundIconPane);

        // Button actions
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

        // Set up the scene and display it
        Scene scene = new Scene(rootLayout, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        rootLayout.requestFocus();
    }
}
