package com.example.demo.UI.buttons;

import javafx.stage.Stage;
public class QuitButton extends ButtonParent {

    public QuitButton() {
        super("/com/example/demo/images/quit_icon.png", 300, true);
    }

    public void setOnQuit(Stage stage) {
        setOnClick(stage::close);
    }

}
