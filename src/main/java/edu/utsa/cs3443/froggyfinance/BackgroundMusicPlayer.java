package edu.utsa.cs3443.froggyfinance;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * The BackgroundMusicPlayer class is a utility for playing looping background music
 * in a Java application using the Java Sound API
 *
 * @author erikamey
 */
public class BackgroundMusicPlayer {
    private static Clip clip;

    /**
     * Loads and plays the specified audio file in a continuous loop.
     * If a clip is already playing, it will be stopped and replaced.
     *
     * @param fileName the name of the audio file to play
     */
    public static void playLoop(String fileName) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            URL soundURL = BackgroundMusicPlayer.class.getResource("/sounds/" + fileName);
            if (soundURL == null) {
                System.out.println("Sound not found: " + fileName);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();


        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops and closes the currently playing background music, if any
     */
    public static void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
