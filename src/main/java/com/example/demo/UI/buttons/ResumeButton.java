package com.example.demo.UI.buttons;

public class ResumeButton extends ButtonParent {

    public ResumeButton() {
        super("/com/example/demo/images/resume_icon.png", 70, true); // Initialize with image path and size
    }

    public void setOnResume(Runnable action) {
        setOnClick(action); // Pass the Runnable directly
    }

}
