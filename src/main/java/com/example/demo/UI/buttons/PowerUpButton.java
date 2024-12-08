package com.example.demo.UI.buttons;

public class PowerUpButton extends ButtonParent {

    public PowerUpButton() {
        super("/com/example/demo/images/powerup_icon.png", 60, true);
    }

    public void setPosition(double x, double y) {
        getButton().setLayoutX(x);
        getButton().setLayoutY(y);
    }
     public void setOnPowerUp(PauseButtonDisplay.PauseAction action) {
         setOnClick(() -> action.execute());
    }

}
