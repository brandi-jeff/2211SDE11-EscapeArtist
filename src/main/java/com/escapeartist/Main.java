package com.escapeartist;

import com.escapeartist.controllers.MainController;
import com.escapeartist.models.FlashMenuScreen;
import com.escapeartist.util.GameMusic;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    MainController game = new MainController();
    GameMusic music = new GameMusic();
    FlashMenuScreen flashmenu = new FlashMenuScreen(game, music );

  }
}