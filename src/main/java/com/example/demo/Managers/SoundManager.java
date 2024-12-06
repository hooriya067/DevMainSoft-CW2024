package com.example.demo.Managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static SoundManager instance;

    private final Map<String, MediaPlayer> soundPlayers; // Map to store MediaPlayers for different sounds
    private MediaPlayer backgroundMusicPlayer; // Special player for background music

    private SoundManager() {
        soundPlayers = new HashMap<>();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    // Play background music
    public void playBackgroundMusic(String musicFilePath) {
        stopBackgroundMusic(); // Stop existing background music if any
        try {
            Media music = new Media(getClass().getResource(musicFilePath).toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(music);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop music indefinitely
            backgroundMusicPlayer.setVolume(0.5); // Set initial volume
            backgroundMusicPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Stop background music
    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer = null;
        }
    }

    // Pause background music
    public void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.pause();
        }
    }

    // Resume background music
    public void resumeBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }

    // Play a sound effect
    public void playSoundEffect(String soundFilePath) {
        try {
            Media sound = new Media(getClass().getResource(soundFilePath).toExternalForm());
            MediaPlayer soundPlayer = new MediaPlayer(sound);
            soundPlayer.setVolume(0.7); // Set sound effect volume
            soundPlayer.setOnEndOfMedia(soundPlayer::dispose); // Dispose player after playback
            soundPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading sound effect: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add a reusable sound effect
    public void addSoundEffect(String key, String soundFilePath) {
        try {
            Media sound = new Media(getClass().getResource(soundFilePath).toExternalForm());
            MediaPlayer soundPlayer = new MediaPlayer(sound);
            soundPlayers.put(key, soundPlayer);
        } catch (Exception e) {
            System.err.println("Error loading reusable sound effect: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Play a reusable sound effect
    public void playReusableSoundEffect(String key) {
        MediaPlayer soundPlayer = soundPlayers.get(key);
        if (soundPlayer != null) {
            soundPlayer.stop(); // Ensure it's not playing
            soundPlayer.seek(javafx.util.Duration.ZERO); // Reset playback position
            soundPlayer.play();
        }
    }

    // Set volume for background music
    public void setBackgroundMusicVolume(double volume) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(volume);
        }
    }

    // Set volume for a reusable sound effect
    public void setSoundEffectVolume(String key, double volume) {
        MediaPlayer soundPlayer = soundPlayers.get(key);
        if (soundPlayer != null) {
            soundPlayer.setVolume(volume);
        }
    }

    // Mute/unmute all sounds
    public void toggleMute() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setMute(!backgroundMusicPlayer.isMute());
        }
        for (MediaPlayer soundPlayer : soundPlayers.values()) {
            soundPlayer.setMute(!soundPlayer.isMute());
        }
    }
}
