//package com.example.demo;
//
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//
//import java.util.Objects;
//
//public class PauseButtonDisplay {
//
//    private static final String PAUSE_BUTTON_IMAGE_NAME = "/com/example/demo/images/pause_icon.png";
//    private static final int PAUSE_BUTTON_SIZE = 50;
//
//    private HBox container;
//    private ImageView pauseButton;
//
//    public PauseButtonDisplay(double xPosition, double yPosition) {
//        initializeContainer(xPosition, yPosition);
//    }
//
//    private void initializeContainer(double xPosition, double yPosition) {
//        container = new HBox();
//        container.setLayoutX(xPosition);
//        container.setLayoutY(yPosition);
//
//        try {
//            pauseButton = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(PAUSE_BUTTON_IMAGE_NAME)).toExternalForm()));
//            pauseButton.setFitHeight(PAUSE_BUTTON_SIZE);
//            pauseButton.setFitWidth(PAUSE_BUTTON_SIZE);
//            pauseButton.setVisible(true);
//            container.getChildren().add(pauseButton);  // Add pause button to container
//
//            // Pause button clicked opens the pause menu and pauses the game.
//            pauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//                System.out.println("Pause button clicked, pausing game and opening pause menu...");
//                GameStateManager.getInstance().pauseGame();
//                Stage stage = (Stage) container.getScene().getWindow();
//                openPauseMenu(stage);
//            });
//
//        } catch (Exception e) {
//            System.out.println("Error loading pause button image: " + e.getMessage());
//        }
//    }
//    // Method to overlay the pause menu without changing the scene
//    private void openPauseMenu(Stage stage) {
//        PauseMenu pauseMenu = new PauseMenu(stage);
//        System.out.println("Displaying pause menu...");
//        pauseMenu.displayOverlay();
//    }
//
//    public HBox getContainer() {
//        return container;
//    }
//}
package com.example.demo;


import javafx.stage.Stage;

;

public class PauseButtonDisplay extends ButtonParent {

    public PauseButtonDisplay() {
        super("/com/example/demo/images/pause_icon.png", 50, true);
    }

    public void setPosition(double x, double y) {
        getButton().setLayoutX(x);
        getButton().setLayoutY(y);
    }
    @FunctionalInterface
    public interface PauseAction {
        void execute(); // Define a single abstract method
    }

    public void setOnPause(PauseAction action) {
        setOnClick(() -> action.execute());
    }

}
