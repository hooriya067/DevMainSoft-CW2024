package com.example.demo.UI.buttons;


public class CustomizePlaneButton extends ButtonParent {

    public CustomizePlaneButton() {
        // Pass the image path and dimensions to the parent class
        super("/com/example/demo/images/customise.png", 300, true);
    }

    public void setOnCustomize(Runnable action) {
        setOnClick(action);
    }
}
