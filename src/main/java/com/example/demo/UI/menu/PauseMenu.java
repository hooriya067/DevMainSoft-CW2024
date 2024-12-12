/**
 * The {@code PauseMenu} class represents the in-game pause menu.
 * It allows the player to navigate back to the main menu, resume the game, or quit the application.
 * This class extends {@link InGameMenuParent} to leverage core overlay functionalities.
 *
 * <p><b>Features:</b></p>
 * <ul>
 *     <li>Displays a menu overlay with options to resume, quit, or navigate to the main menu.</li>
 *     <li>Leverages buttons such as {@link MainMenuButton}, {@link ResumeButton}, and {@link QuitButton} for user actions.</li>
 *     <li>Pauses the game and dims the background when displayed.</li>
 * </ul>
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link InGameMenuParent}: Base class providing core overlay functionalities.</li>
 *     <li>{@link MainMenu}: Represents the main menu screen of the game.</li>
 *     <li>{@link MainMenuButton}: Button for navigating to the main menu.</li>
 *     <li>{@link ResumeButton}: Button for resuming the game.</li>
 *     <li>{@link QuitButton}: Button for quitting the application.</li>
 * </ul>
 */
package com.example.demo.UI.menu;

import com.example.demo.UI.screens.MainMenu;
import com.example.demo.UI.buttons.MainMenuButton;
import com.example.demo.UI.buttons.QuitButton;
import com.example.demo.UI.buttons.ResumeButton;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PauseMenu extends InGameMenuParent {

    /**
     * Constructs a {@code PauseMenu} with the specified stage.
     *
     * @param stage the {@link Stage} on which the pause menu is displayed
     */
    public PauseMenu(Stage stage) {
        super(stage);
    }

    /**
     * Displays the pause menu overlay.
     * Pauses the game and shows menu options to resume, quit, or navigate to the main menu.
     */
    public void displayOverlay() {
        VBox menuContent = createMenuContent();
        super.displayOverlay(menuContent);
    }

    /**
     * Creates the content of the pause menu.
     * Includes buttons for resuming the game, navigating to the main menu, and quitting the application.
     *
     * @return a {@link VBox} containing the menu's content
     */
    @Override
    protected VBox createMenuContent() {
        VBox menuBox = new VBox(40);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.7); -fx-padding: 20;");
        menuBox.setMaxWidth(400);
        menuBox.setMaxHeight(300);

        // Main menu button
        MainMenuButton mainMenuButton = new MainMenuButton();
        mainMenuButton.setOnMainMenu(() -> {
            removeOverlay();
            new MainMenu(stage); // Navigate to main menu
        });

        // Resume button
        ResumeButton resumeButton = new ResumeButton();
        resumeButton.setOnResume(this::startResumeCountdown);

        // Quit button
        QuitButton quitButton = new QuitButton();
        quitButton.setOnClick(stage::close);

        // Add buttons to the menu box
        menuBox.getChildren().addAll(mainMenuButton.getButton(), resumeButton.getButton(), quitButton.getButton());
        return menuBox;
    }
}
