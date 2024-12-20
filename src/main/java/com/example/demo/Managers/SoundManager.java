/**
 * The {@code SoundManager} class is responsible for managing all sound effects and background music in the game.
 * It provides functionality to play, pause, resume, stop, and manage volumes for both background music
 * and sound effects. The class uses the Singleton pattern to ensure a single instance is used throughout the game.
 *
 * <p><b>References:</b></p>
 * <ul>
 *     <li>{@link javafx.scene.media.Media}: Used to load audio files.</li>
 *     <li>{@link javafx.scene.media.MediaPlayer}: Used to play, pause, and control audio playback.</li>
 * </ul>
 */
package com.example.demo.Managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages all sound effects and background music in the game.
 */
public class SoundManager {

    /**
     * Singleton instance of the SoundManager.
     */
    private static SoundManager instance;

    /**
     * A map to store {@link MediaPlayer} instances for different sound effects.
     */
    private final Map<String, MediaPlayer> soundPlayers;

    /**
     * {@link MediaPlayer} instance for background music.
     */
    private MediaPlayer backgroundMusicPlayer;

    /**
     * Private constructor to enforce the Singleton pattern.
     */
    private SoundManager() {
        soundPlayers = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of the SoundManager.
     *
     * @return the singleton instance
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Plays background music.
     *
     * @param musicFilePath the file path of the music file to play
     */
    public void playBackgroundMusic(String musicFilePath) {
        stopBackgroundMusic();
        try {
            Media music = new Media(getClass().getResource(musicFilePath).toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(music);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusicPlayer.setVolume(0.5);
            backgroundMusicPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing background music.Ensures it’s completely cleared.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.dispose(); // Ensure it’s completely cleared
            backgroundMusicPlayer = null;
        }
    }

    /**
     * Plays a sound effect.
     *
     * @param soundFilePath the file path of the sound effect to play
     */
    public void playSoundEffect(String soundFilePath) {
        try {
            Media sound = new Media(getClass().getResource(soundFilePath).toExternalForm());
            MediaPlayer soundPlayer = new MediaPlayer(sound);
            soundPlayer.setVolume(0.7);
            soundPlayer.setOnEndOfMedia(soundPlayer::dispose);
            soundPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading sound effect: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Temporarily lowers the background music volume.
     *
     * @param reducedVolume the reduced volume level (0.0 to 1.0)
     */
    public void lowerBackgroundMusicVolume(double reducedVolume) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(reducedVolume);
        }
    }

    /**
     * Restores the original background music volume.
     *
     * @param originalVolume the original volume level (0.0 to 1.0)
     */
    public void restoreBackgroundMusicVolume(double originalVolume) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(originalVolume);
        }
    }

    /**
     * Toggles mute for all sounds, including background music and sound effects.
     */
    public void toggleMute() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setMute(!backgroundMusicPlayer.isMute());
        }
        for (MediaPlayer soundPlayer : soundPlayers.values()) {
            soundPlayer.setMute(!soundPlayer.isMute());
        }
    }
}
