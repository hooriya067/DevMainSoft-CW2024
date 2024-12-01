package com.example.demo.UI.buttons;

public class MainMenuButton extends ButtonParent {

    public MainMenuButton() {
        super("/com/example/demo/images/mainmenu_icon.png", 70, true); // Initialize with image path and size
    }

    public void setOnMainMenu(Runnable action) {
        setOnClick(action); // Use ButtonParent's setOnClick to define behavior
    }
}
