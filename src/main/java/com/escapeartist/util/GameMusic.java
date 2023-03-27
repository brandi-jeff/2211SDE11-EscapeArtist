package com.escapeartist.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameMusic {

  private Clip clip;

  public void playMusic() {
    try {
      InputStream audioFile = GameMusic.class.getResourceAsStream("/game_music.wav");
      InputStream bufferedStream = new BufferedInputStream(audioFile);
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (Exception ex) {
      System.out.println("Error playing sound.");
      ex.printStackTrace();
      // TODO: 3/24/23 background sound using Loop continously, adding volume options
    }
  }
  public void stopMusic() {
    if (clip != null && clip.isRunning()) {
      clip.stop();
    }
  }

}

