//package com.example.demo;
//
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Stage;
//
//import java.util.Objects;
//
//public class QuitButton {
//    private final ImageView quitButtonImage;
//
//    public QuitButton() {
//        // Load the quit button image
//        Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/quit_icon.png")).toExternalForm());
//        quitButtonImage = new ImageView(image);
//        quitButtonImage.setFitWidth(300);
//        quitButtonImage.setPreserveRatio(true);
//        quitButtonImage.setPickOnBounds(true);  // Allow clicks in transparent areas
//    }
//
//    public ImageView getQuitButtonImage() {
//        return quitButtonImage;
//    }
//
//    public void setOnQuit(Stage stage) {
//        quitButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            stage.close();  // Close the application
//            event.consume();  // Consume the event to prevent propagation
//        });
//    }
//}
package com.example.demo;

import javafx.stage.Stage;
public class QuitButton extends ButtonParent {

    public QuitButton() {
        super("/com/example/demo/images/quit_icon.png", 300, true);
    }

    public void setOnQuit(Stage stage) {
        setOnClick(stage::close);
    }

}
