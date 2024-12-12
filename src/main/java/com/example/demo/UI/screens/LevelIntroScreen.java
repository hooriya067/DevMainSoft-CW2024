
package com.example.demo.UI.screens;

import com.example.demo.Levels.LevelParent;
import com.example.demo.core.GameConfig;
import com.example.demo.Managers.LevelManager;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import java.util.Objects;

/**
 * The {@link  LevelIntroScreen} class provides an introductory screen for each game level.
 * It includes visual animations, a background gradient, level objectives, and a prompt to start the level.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Animated entry of a text bubble displaying the level name and objectives.</li>
 *     <li>Customizable objectives for different levels.</li>
 *     <li>Interactive elements such as a quit button and a prompt to start the level.</li>
 *     <li>Typing effect for displaying level objectives.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link GameConfig}: Provides game-wide configurations like screen width and height.</li>
 *     <li>{@link LevelManager}: Manages level progression and transitions.</li>
 * </ul>
 */
public class LevelIntroScreen {
    /**
     * The name of the level being introduced.
     * This value is used to display the level name on the introduction screen.
     */
    private final String levelName;

    /**
     * The manager responsible for handling level transitions and gameplay flow.
     * It coordinates actions like starting the level or returning to the menu.
     */
    private final LevelManager levelManager;

    /**
     * Constructs a {@code LevelIntroScreen}.
     *
     * @param levelName     the name of the level to be displayed on the intro screen
     * @param levelManager  the manager responsible for controlling level transitions and interactions
     */

    public LevelIntroScreen(String levelName, LevelManager levelManager) {
        this.levelName = levelName;
        this.levelManager = levelManager;
    }

    /**
     * Creates and returns the {@link Scene} for the level intro screen.
     *
     * @return the level intro {@code Scene}
     */
    public Scene getScene() {
        Group root = new Group();

        // Blurred Background Image
        ImageView backgroundImage = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource("/com/example/demo/images/background0.png")).toExternalForm()
        ));
        backgroundImage.setFitWidth(GameConfig.SCREEN_WIDTH);
        backgroundImage.setFitHeight(GameConfig.SCREEN_HEIGHT);
        backgroundImage.setPreserveRatio(false);
        BoxBlur blur = new BoxBlur();
        blur.setWidth(15);
        blur.setHeight(15);
        blur.setIterations(3);
        backgroundImage.setEffect(blur);
        root.getChildren().add(backgroundImage);

        // Quit button
        Text quitButton = new Text("Quit");
        quitButton.setFill(Color.RED);
        quitButton.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
        quitButton.setX(GameConfig.SCREEN_WIDTH - 100);
        quitButton.setY(50);
        quitButton.setOnMouseClicked(e -> System.exit(0));

        // Text bubble
        StackPane textBubble = new StackPane();
        Rectangle bubbleBackground = new Rectangle(GameConfig.SCREEN_WIDTH - 100, GameConfig.SCREEN_HEIGHT - 200, Color.web("#0000FF", 0.3));
        bubbleBackground.setArcWidth(50);
        bubbleBackground.setArcHeight(50);
        bubbleBackground.setStroke(Color.WHITE);
        bubbleBackground.setStrokeType(StrokeType.INSIDE);
        bubbleBackground.setStrokeWidth(3);
        bubbleBackground.setEffect(new DropShadow(15, Color.GOLD));

        VBox bubbleContent = new VBox(20);
        bubbleContent.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Level: " + levelName);
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label objectiveLabel = new Label();
        objectiveLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: #FFDB58;");
        Label pressEnterLabel = new Label("Press ENTER to Continue");
        pressEnterLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        bubbleContent.getChildren().addAll(titleLabel, objectiveLabel, pressEnterLabel);
        textBubble.getChildren().addAll(bubbleBackground, bubbleContent);
        textBubble.setAlignment(Pos.CENTER);
        textBubble.setLayoutX((GameConfig.SCREEN_WIDTH - bubbleBackground.getWidth()) / 2);
        textBubble.setLayoutY(-bubbleBackground.getHeight());

        root.getChildren().addAll(quitButton, textBubble);

        // Animate the bubble
        TranslateTransition dropdown = new TranslateTransition(Duration.seconds(1), textBubble);
        dropdown.setFromY(textBubble.getLayoutY());
        dropdown.setToY(GameConfig.SCREEN_HEIGHT / 2 + 250);
        dropdown.setOnFinished(event -> startTypingEffect(objectiveLabel, getLevelObjective(levelName)));
        dropdown.play();

        Scene scene = new Scene(root, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ENTER) {
                startLevel();
            }
        });

        return scene;
    }
    /**
     * Proceeds to start the level by calling the {@link LevelManager}.
     */
    public void startLevel() {
        levelManager.proceedToLevel(levelName);
    }

    /**
     * Retrieves the objective text for the given level name.
     *
     * @param levelName the name of the level
     * @return the objective text for the level
     */
    private String getLevelObjective(String levelName) {
        switch (levelName) {
            case "LEVEL_ONE":
                return "TARGET\uD83C\uDFAF\uD83C\uDFAF:10\n Destroy the enemy planes to advance in this Battle!!\n" +
                        "Shoot the planes to proceed to the next level of the Battle.\n" +
                        "BEWARE OF ENEMY FIRES";
            case "LEVEL_TWO":
                return "TARGET\uD83C\uDFAF\uD83C\uDFAF:15 \n In this Step, Defeat the Boss plane.\n" +
                        "The boss plane is stronger and bigger!!\n" +
                        "Boss has High life so beware!!\n" +
                        "BOSS'S SHIELD PROTECTS HIM!!";
            case "LEVEL_THREE":
                return "\uD83C\uDFAF\uD83C\uDFAF:10\n Two types of enemies are attacking you.\n" +
                        "One comes in groups, and other comes with homing missiles\n" +
                        "HOMING MISSILES FOLLOW WHEREVER U GO.\n" +
                        "If you kill Helicopter Leader,a lot of them will fall apart!\n" +
                        "LEFT RIGHT movement is allowed from this level" +
                        "LIFE:- Heli enemy:- 2 ,  Bigger Enemy:- 3";
            case "LEVEL_FOUR":
                return "TARGET\uD83C\uDFAF\uD83C\uDFAF:15\n DARK NIGHT HAS FALLEN!!\n" +
                        "Enemies are hidden under their sheaths!!\n" +
                        "But don't worry, Flare Power-ups coming\n" +
                        "from the sky can help you see them.\n" +
                        "But BEWARE the flare you catch might\n" +
                        " just be a bomb!";
            case "LEVEL_FIVE":
                return "TARGET\uD83C\uDFAF\uD83C\uDFAF:15\n SHOWDOWNNNNNNNN\n" +
                        "ALL Enemies are attacking youuu\n" +
                        "Meteors are coming downnn\n" +
                        "BEWARE\n";
            default:
                return "Objective: Unknown level.";
        }
    }



    /**
     * Starts a typing effect to display the level objective text.
     *
     * @param label the label to display the text
     * @param text  the text to display
     */
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
