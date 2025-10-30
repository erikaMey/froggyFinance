package edu.utsa.cs3443.froggyfinance;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * The SoundPlayer class provides a utility method to play short audio clips
 *
 * @author erikamey
 */
public class SoundPlayer {
    /**
     * Plays a sound from the sounds resource folder
     *
     * @param fileName The name of the sound file to play
     */
    public static void playSound(String fileName) {
        try {
            URL soundURL = SoundPlayer.class.getResource("/sounds/" + fileName);
            if (soundURL == null) {
                System.out.println("Sound not found: " + fileName);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

