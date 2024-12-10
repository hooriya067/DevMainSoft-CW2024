package com.example.demo.core;
import javafx.animation.Timeline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;



class GameLoopTest {

    private GameLoop gameLoop;

    @BeforeEach
    void setUp() {
        gameLoop = new GameLoop(16);
    }






    @Test
    void testSetUpdateInterval() {
        // Act
        gameLoop.setUpdateInterval(32); // Set to approx. 30 FPS

        // Assert: Verify the timeline has the updated frame duration
        Timeline timeline = getTimeline(gameLoop);
        assertEquals(1, timeline.getKeyFrames().size(), "Timeline should have exactly one keyframe.");
        assertEquals(32, timeline.getKeyFrames().get(0).getTime().toMillis(), "KeyFrame duration should match the set interval.");
    }


    // Helper methods to access internal fields using reflection
    private Timeline getTimeline(GameLoop gameLoop) {
        try {
            var field = GameLoop.class.getDeclaredField("timeline");
            field.setAccessible(true);
            return (Timeline) field.get(gameLoop);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access the timeline field", e);
        }
    }

    private boolean isTimelineRunning(GameLoop gameLoop) {
        return getTimeline(gameLoop).getStatus() == Timeline.Status.RUNNING;
    }
}
