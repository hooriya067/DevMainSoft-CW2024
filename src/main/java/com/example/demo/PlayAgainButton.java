package com.example.demo;

public class PlayAgainButton extends ButtonParent {

    private static final String IMAGE_NAME = "/com/example/demo/images/playagain.png";
    private static final double BUTTON_WIDTH = 300;

    public PlayAgainButton() {
        super(IMAGE_NAME, BUTTON_WIDTH, true); // Call ButtonParent constructor with image details
    }

    // Method to set the action for Play Again
    public void setOnPlayAgain(Runnable action) {
        setOnClick(action); // Use ButtonParent's setOnClick to define behavior
    }
}


