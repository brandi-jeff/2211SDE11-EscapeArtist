package com.escapeartist.models;

import com.escapeartist.controllers.MainController;
import com.escapeartist.util.Clear;
import com.escapeartist.util.GameMusic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FlashMenuScreen {

  private GameMusic gameMusic;

  public FlashMenuScreen(MainController game, GameMusic gameMusic) throws IOException{
    this.gameMusic = gameMusic;
    startGame(game);
  }

  public void startGame(MainController game) throws IOException {
//    new Thread (()->gameMusic.playMusic()).start();
    gameMusic.playMusic();
    readAsciiFile();
    String message = "Press any key to continue...";
    int length = message.length() + 4;
    String border = "+".repeat(length);

    System.out.println(border);
    System.out.println("| " + message + " |");
    System.out.println(border);
    System.in.read(); // Wait for user to press any key
    Clear.clearConsole();
    game.startMenu();
  }

  public void readAsciiFile() {
    String fileName = "title_screen.txt";

    //noinspection ConstantConditions
    try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
