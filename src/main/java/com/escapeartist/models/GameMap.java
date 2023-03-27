package com.escapeartist.models;

import com.escapeartist.util.Clear;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameMap {

  public void readMap(){
    readAsciiFile();
    Clear.clearConsole();
  }

  public void readAsciiFile() {
    String fileName = "game_map.txt";

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
