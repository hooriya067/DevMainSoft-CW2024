package com.example.demo.UI.buttons;

public class InstructionButton extends ButtonParent {

    public InstructionButton() {
        super("/com/example/demo/images/Instructions.png", 300, true);
    }

    public void setOnInstructions(Runnable action) {
        setOnClick(action);
    }
}