package com.example.demo;

public class PowerUpButton extends ButtonParent {

    public PowerUpButton() {
        super("/com/example/demo/images/powerup_icon.png", 50, true);
    }

    public void setPosition(double x, double y) {
        getButton().setLayoutX(x);
        getButton().setLayoutY(y);
    }
    @FunctionalInterface
    public interface PauseAction {
        void execute(); // Define a single abstract method
    }

    public void setOnPowerUp(PauseButtonDisplay.PauseAction action) {
        setOnClick(() -> action.execute());
    }

}
