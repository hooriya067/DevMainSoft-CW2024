package com.example.demo.actors.collectibles;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class StarDisplayTest  {

    private StarDisplay starDisplay;
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        }); // Initialize JavaFX toolkit
    }

    @BeforeEach
    void setUp() {
        // Initialize StarDisplay with 3 stars at position (100, 100)
        starDisplay = new StarDisplay(100, 100, 3);
    }

    @Test
    void testContainerInitialization() {
        VBox container = starDisplay.getContainer();

        // Assert: Verify container's position
        assertEquals(100, container.getLayoutX(), "Container X position should be 100.");
        assertEquals(100, container.getLayoutY(), "Container Y position should be 100.");

        // Assert: Verify container structure
        assertEquals(2, container.getChildren().size(), "Container should have two children: a StackPane and a Text.");
        assertTrue(container.getChildren().get(0) instanceof StackPane, "First child should be a StackPane.");
        assertTrue(container.getChildren().get(1) instanceof Text, "Second child should be a Text element.");
    }


    @Test
    void testStarsTextForMultipleStars() {
        VBox container = starDisplay.getContainer();
        Text starText = (Text) container.getChildren().get(1);

        // Assert: Verify the star count text
        assertEquals("3 STARS", starText.getText(), "Text should display '3 STARS' for 3 stars.");
    }

    @Test
    void testStarsTextForSingleStar() {
        // Initialize StarDisplay with 1 star
        starDisplay = new StarDisplay(100, 100, 1);
        VBox container = starDisplay.getContainer();
        Text starText = (Text) container.getChildren().get(1);

        // Assert: Verify the star count text
        assertEquals("1 STAR", starText.getText(), "Text should display '1 STAR' for 1 star.");
    }

}
