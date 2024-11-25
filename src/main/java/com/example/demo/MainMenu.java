package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.example.demo.controller.Controller;

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
        VBox menuBox = new VBox(40);  // Increased spacing to make it visually balanced on larger screen
        menuBox.setAlignment(Pos.CENTER);  // Align elements to the center
        menuBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.7); -fx-padding: 20;");  // Semi-transparent background
        menuBox.setMaxWidth(600);  // Adjusted width for larger screen

        // Add the components
        Title title = new Title();
        StartGameButton startButton = new StartGameButton();
        QuitButton quitButton = new QuitButton();

        menuBox.getChildren().addAll(title.getTitleImage(), startButton.getStartButton(), quitButton.getQuitButtonImage());

        // Set padding for better vertical positioning
        menuBox.setTranslateY(0);  // Remove extra downward translation to keep it aligned properly

        // Create a StackPane for layout and add the background image
        StackPane rootLayout = new StackPane();
        rootLayout.setPrefSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        rootLayout.getChildren().add(backgroundImageView);  // Add background first

        // Add the main menu VBox (the translucent box holding the buttons)
        rootLayout.getChildren().add(menuBox);

        // Add the sound button in a separate pane for precise positioning
        SoundButton soundButton = new SoundButton();
        Pane soundIconPane = new Pane();
        soundIconPane.getChildren().add(soundButton.getSoundButtonImage());
        soundButton.getSoundButtonImage().setLayoutX(GameConfig.SCREEN_WIDTH - 120); // Move it slightly left to keep it within the screen
        soundButton.getSoundButtonImage().setLayoutY(20);
        rootLayout.getChildren().add(soundIconPane);

        // Bring the menuBox and its elements to the front to ensure visibility and clickability
        menuBox.toFront();
        startButton.getStartButton().toFront();  // Ensure the start button is on top of all other elements
        quitButton.getQuitButtonImage().toFront();  // Ensure the quit button is on top of all other elements

        // Add event handler for start button
        startButton.setOnStartGame(() -> {
            try {
                Controller gameController = new Controller(primaryStage);
                gameController.launchGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Add event handler for quit button
        quitButton.setOnQuit(primaryStage);


//        rootLayout.setOnKeyPressed(event -> {
//            switch (event.getCode()) {
//                case ENTER:
//                    try {
//                        Controller gameController = new Controller(primaryStage);
//                        gameController.launchGame();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        });

        // Create the scene with the root layout and set it to the stage
        Scene scene = new Scene(rootLayout, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Request focus so key events are recognized
        rootLayout.requestFocus();
    }
}
