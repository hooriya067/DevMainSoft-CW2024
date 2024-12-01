package com.example.demo.UI.buttons;

public class StartGameButton extends ButtonParent {

    public StartGameButton() {
        super("/com/example/demo/images/start_icon.png", 300, true);
    }

    public void setOnStartGame(Runnable action) {
        setOnClick(action);
    }
}
