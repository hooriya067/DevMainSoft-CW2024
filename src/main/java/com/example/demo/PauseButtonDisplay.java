package com.example.demo;




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
