package com.example.demo.UI.screens;

import com.example.demo.core.GameConfig;
import com.example.demo.levels.LevelManager;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LevelIntroScreen {
    private final String levelName;
    private final LevelManager levelManager;

    public LevelIntroScreen(String levelName, LevelManager levelManager) {
        this.levelName = levelName;
        this.levelManager = levelManager;
    }

    public Scene getScene() {
        // Root group
        Group root = new Group();

        // Background gradient (blue sky color)
        Rectangle gradientBackground = new Rectangle(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        gradientBackground.setFill(
                new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#8793eb")),  // Sky Blue
                        new Stop(1, Color.web("#87CEEB"))   // Same Sky Blue
                )
        );

        // Dim Overlay
        Rectangle dimOverlay = new Rectangle(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        dimOverlay.setFill(Color.BLACK);
        dimOverlay.setOpacity(0.3);

        // Quit Button (top-right corner)
        Text quitButton = new Text("Quit");
        quitButton.setFill(Color.RED);
        quitButton.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
        quitButton.setX(GameConfig.SCREEN_WIDTH - 100); // Position in top-right
        quitButton.setY(50); // Slight padding from top
        quitButton.setOnMouseClicked(e -> System.exit(0));

        // Bubble Box for All Text
        StackPane textBubble = new StackPane();
        Rectangle bubbleBackground = new Rectangle(GameConfig.SCREEN_WIDTH - 100, GameConfig.SCREEN_HEIGHT - 200, Color.web("#0000FF", 0.3)); // Larger translucent blue
        bubbleBackground.setArcWidth(50); // Softer rounded corners
        bubbleBackground.setArcHeight(50);
        bubbleBackground.setStroke(Color.WHITE);
        bubbleBackground.setStrokeType(StrokeType.INSIDE);
        bubbleBackground.setStrokeWidth(3); // Slightly thicker stroke

        // Add glowing effect to bubble
        DropShadow bubbleShadow = new DropShadow();
        bubbleShadow.setOffsetX(0);
        bubbleShadow.setOffsetY(0);
        bubbleShadow.setColor(Color.AQUA);
        bubbleShadow.setRadius(15);
        bubbleBackground.setEffect(bubbleShadow);

        // Bubble Content
        VBox bubbleContent = new VBox(20); // Increased spacing between elements
        bubbleContent.setAlignment(Pos.CENTER);

        // Title Label (Level Name)
        Label titleLabel = new Label("Level: " + levelName);
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Objective Label
        Label objectiveLabel = new Label();
        objectiveLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: #FFDB58;");

        // Press Enter Text
        Label pressEnterLabel = new Label("Press ENTER to Continue");
        pressEnterLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        bubbleContent.getChildren().addAll(titleLabel, objectiveLabel, pressEnterLabel);
        textBubble.getChildren().addAll(bubbleBackground, bubbleContent);
        textBubble.setAlignment(Pos.CENTER);

        // Center the bubble
        textBubble.setLayoutX((GameConfig.SCREEN_WIDTH - bubbleBackground.getWidth()) / 2);
        textBubble.setLayoutY(-bubbleBackground.getHeight()); // Position bubble just above the screen



        root.getChildren().addAll(gradientBackground, dimOverlay, quitButton, textBubble);


// Animate the bubble from its starting position to the center
        TranslateTransition dropdown = new TranslateTransition(Duration.seconds(1), textBubble);
        dropdown.setFromY(textBubble.getLayoutY()); // Use its absolute starting position
        dropdown.setToY(GameConfig.SCREEN_HEIGHT / 2 + 250); // Bring it further down


        dropdown.setOnFinished(event -> {
            // Start typing effect after dropdown
            startTypingEffect(objectiveLabel, getLevelObjective(levelName));
        });
        dropdown.play();

        // Handle key press for "Enter"
        Scene scene = new Scene(root, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    levelManager.proceedToLevel(levelName);
                    break;
                default:
                    break;
            }
        });

        return scene;
    }

    private String getLevelObjective(String levelName) {
        switch (levelName) {
            case "LEVEL_ONE":
                return "Destroy the enemy planes to advance in this Battle!!\n" +
                        "Shoot at least 10 planes to proceed to the next level of the Battle.\n" +
                        "BEWARE OF ENEMY FIRES";
            case "LEVEL_TWO":
                return "In this Step, Defeat the Boss plane.\n" +
                        "The boss plane is stronger and bigger!!" +
                        "Boss has 20 lives so beware!!\n" +
                        "BOSS'S SHIELD PROTECTS HIM!!";
            case "LEVEL_THREE":
                return "Two types of enemies are attacking you.\n" +
                        "One comes in groups, and other comes with homing missiles\n" +
                        "HOMING MISSILES FOLLOW & KILL YOU AS THEY TOUCH.\n" +
                        "Kill 10 Enemies before a homing missile finds you!\n" +
                        "LEFT RIGHT movement is allowed from this level";
            case "LEVEL_FOUR":
                return "DARK NIGHT HAS FALLEN!!" +
                        "Enemies are hidden under their sheaths!!\n" +
                        "But don't worry, Flare Power-ups coming\n " +
                        "from the sky can help you see them.\n" +
                        "But BEWARE the flare you catch might\n" +
                        " just be a bomb!";
            case "LEVEL_FIVE":
                return "SHOWDOWNNNNNNNN\n" +
                        "ALL Enemies are attacking youuu\n" +
                        "Meteors are coming downnn\n" +
                        "BEWARE\n";

            default:
                return "Objective: Unknown level.";
        }
    }

    private void startTypingEffect(Label label, String text) {
        Timeline timeline = new Timeline();
        final StringBuilder displayedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(50 * (index + 1)), e -> {
                displayedText.append(text.charAt(index));
                label.setText(displayedText.toString());
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }
}
