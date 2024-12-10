package com.example.demo.UI.buttons;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class ButtonParentTest  {

    private ButtonParent testButton;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @BeforeEach
    void setup() {
        testButton = new ButtonParent("/com/example/demo/images/dummy.jpg", 100, true) {};
    }

    @Test
    void testInitializeButton() {
        // Retrieve the button and its graphic
        Button button = testButton.getButton();
        assertNotNull(button, "Button should be initialized");
        assertTrue(button.getStyle().contains("-fx-background-color: transparent"), "Button should have transparent background");
        assertTrue(button.getStyle().contains("-fx-cursor: hand"), "Button should have hand cursor style");

        // Verify the graphic is an ImageView
        assertTrue(button.getGraphic() instanceof ImageView, "Button's graphic should be an ImageView");

        // Verify the ImageView properties
        ImageView imageView = (ImageView) button.getGraphic();
        assertEquals(100, imageView.getFitWidth(), "ImageView's width should match the specified fitWidth");
        assertTrue(imageView.isPreserveRatio(), "ImageView should preserve aspect ratio");
    }

    @Test
    void testGetButton() {
        // Verify the getButton method returns the same instance
        Button button = testButton.getButton();
        assertNotNull(button, "Button should not be null");
        assertSame(testButton.getButton(), button, "getButton should return the same button instance");
    }

    @Test
    void testSetOnClick() {
        // Arrange: Create a mock Runnable
        Runnable mockAction = () -> System.out.println("Button clicked!");
        testButton.setOnClick(mockAction);

        // Act: Simulate a button click
        Button button = testButton.getButton();
        button.fire();

        // Assert: Verify the action is executed (check the console for "Button clicked!")
        assertDoesNotThrow(button::fire, "Clicking the button should not throw an exception");
    }
}
