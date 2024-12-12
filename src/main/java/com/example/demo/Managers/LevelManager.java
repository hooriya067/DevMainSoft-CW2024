/**
 * The {@code LevelManager} class manages the flow of levels within the game.
 * It handles transitions between levels, starting levels, replaying levels, and displaying the
 * final win screen. This class ensures smooth navigation between levels and integrates with
 * other components like {@link Controller}, {@link LevelParent}, and UI screens.
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link StageManager}: Provides access to the global stage for scene transitions.</li>
 *     <li>{@link Controller}: Observes level transitions and manages game logic at the controller level.</li>
 *     <li>{@link LevelParent}: Serves as the base class for all levels, providing common functionalities.</li>
 *     <li>{@link LevelIntroScreen}: Displays the introductory screen for a level.</li>
 *     <li>{@link LevelCompletedScreen}: Displays the level completion screen with options to proceed or replay.</li>
 *     <li>{@link WinImage}: Displays the final win screen upon completing all levels.</li>
 *     <li>{@link SoundManager}: Manages background music and sound effects for level transitions.</li>
 * </ul>
 */
package com.example.demo.Managers;

import com.example.demo.UI.screens.LevelCompletedScreen;
import com.example.demo.UI.screens.LevelIntroScreen;
import com.example.demo.UI.screens.WinImage;
import com.example.demo.controller.Controller;
import com.example.demo.core.StageManager;
import com.example.demo.Levels.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the flow of levels within the game, including transitions, replays, and level introductions.
 */
public class LevelManager {

    /**
     * The global stage for scene transitions.
     */
    private final Stage stage = StageManager.getStage();

    /**
     * The sequence of levels in the game.
     */
    private final List<String> levelSequence = new ArrayList<>();

    /**
     * The index of the current level in the sequence.
     */
    private int currentLevelIndex = 0;

    /**
     * The current level being played.
     */
    private LevelParent currentLevel;

    /**
     * The name of the current level.
     */
    public String currentLevelName;

    /**
     * Constructs a {@code LevelManager} instance and initializes the level sequence.
     *
     * @param stage the global stage for the game
     */
    public LevelManager(Stage stage) {
        levelSequence.add("LEVEL_ONE");
        levelSequence.add("LEVEL_TWO");
        levelSequence.add("LEVEL_THREE");
        levelSequence.add("LEVEL_FOUR");
        levelSequence.add("LEVEL_FIVE");
    }

    /**
     * Starts the first level in the sequence.
     */
    public void startFirstLevel() {
        if (!levelSequence.isEmpty()) {
            goToLevelIntro(levelSequence.get(currentLevelIndex));
        }
    }

    /**
     * Proceeds to the next level or displays the final win screen if all levels are completed.
     */
    public void goToNextLevel() {
        Scene currentScene = stage.getScene();
        if (currentLevelIndex + 1 < levelSequence.size()) {
            currentLevelIndex++;
            SoundManager.getInstance().stopBackgroundMusic();
            SoundManager.getInstance().playSoundEffect("/com/example/demo/sound/levelcomplete.mp3");
            SoundManager.getInstance().playBackgroundMusic("/com/example/demo/sound/background2.mp3");

            LevelCompletedScreen levelCompletedPage = new LevelCompletedScreen(
                    stage.getWidth(),
                    stage.getHeight(),
                    () -> goToLevelIntro(levelSequence.get(currentLevelIndex)),
                    this::replayCurrentLevel
            );

            Group root = (Group) currentScene.getRoot();
            root.getChildren().add(levelCompletedPage);
        } else {
            showFinalWinScreen();
        }
    }

    /**
     * Displays the level introduction screen for the specified level.
     *
     * @param levelName the name of the level to introduce
     */
    private void goToLevelIntro(String levelName) {
        try {
            currentLevelName = levelName;
            LevelIntroScreen levelIntroScreen = new LevelIntroScreen(levelName, this);
            Scene introScene = levelIntroScreen.getScene();
            stage.setScene(introScene);
            BulletSystemManager.getInstance().setBulletsUsed(0);
        } catch (IllegalArgumentException e) {
            showErrorAlert(e);
        }
    }

    /**
     * Proceeds to the specified level.
     *
     * @param levelName the name of the level to load
     */
    public void proceedToLevel(String levelName) {
        if (!levelName.equals(currentLevelName)) {
            throw new IllegalStateException("Level name mismatch. Expected: " + currentLevelName + ", Got: " + levelName);
        }

        try {
            switch (levelName) {
                case "LEVEL_ONE":
                    currentLevel = new LevelOne(stage.getHeight(), stage.getWidth());
                    break;
                case "LEVEL_TWO":
                    currentLevel = new LevelTwo(stage.getHeight(), stage.getWidth());
                    break;
                case "LEVEL_THREE":
                    currentLevel = new LevelThree(stage.getHeight(), stage.getWidth());
                    break;
                case "LEVEL_FOUR":
                    currentLevel = new LevelFour(stage.getHeight(), stage.getWidth());
                    break;
                case "LEVEL_FIVE":
                    currentLevel = new LevelFive(stage.getHeight(), stage.getWidth());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown level: " + levelName);
            }

            currentLevel.setmyobserver((Controller) stage.getUserData());
            Scene scene = currentLevel.initializeScenario();
            stage.setScene(scene);
            currentLevel.startGame();
        } catch (IllegalArgumentException e) {
            showErrorAlert(e);
        }
    }

    /**
     * Replays the current level.
     */
    public void replayCurrentLevel() {
        if (currentLevelIndex > 0) {
            currentLevelIndex--;
        }
        proceedToLevel(levelSequence.get(currentLevelIndex));
    }

    /**
     * Displays the final win screen upon completing all levels.
     */
    public void showFinalWinScreen() {
        SoundManager.getInstance().stopBackgroundMusic();
        SoundManager.getInstance().playSoundEffect("/com/example/demo/sound/wingame.mp3");
        SoundManager.getInstance().playBackgroundMusic("/com/example/demo/sound/background2.mp3");

        javafx.scene.shape.Rectangle dimBackground = new javafx.scene.shape.Rectangle(stage.getWidth(), stage.getHeight());
        dimBackground.setFill(javafx.scene.paint.Color.BLACK);
        dimBackground.setOpacity(0.5);

        WinImage winImage = new WinImage(stage.getWidth(), stage.getHeight());
        winImage.setVisible(true);

        Group winScreenRoot = new Group(dimBackground, winImage);
        Scene currentScene = stage.getScene();

        if (currentScene.getRoot() instanceof Group) {
            Group root = (Group) currentScene.getRoot();
            root.getChildren().add(winScreenRoot);
        } else {
            Group newRoot = new Group();
            newRoot.getChildren().add(currentScene.getRoot());
            newRoot.getChildren().add(winScreenRoot);
            currentScene.setRoot(newRoot);
        }

        winScreenRoot.toFront();
    }

    /**
     * Displays an error alert for the provided exception.
     *
     * @param e the exception to display
     */
    private void showErrorAlert(Exception e) {
        System.out.println("Error: " + e.getMessage());
    }

    /**
     * Retrieves the name of the current level.
     *
     * @return the name of the current level
     * @throws IllegalStateException if the level index is invalid
     */
    public String getCurrentLevelName() {
        if (currentLevelIndex >= 0 && currentLevelIndex < levelSequence.size()) {
            return levelSequence.get(currentLevelIndex);
        } else {
            throw new IllegalStateException("Invalid level index: " + currentLevelIndex);
        }
    }
}
