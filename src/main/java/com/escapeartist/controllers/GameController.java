package com.escapeartist.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import com.escapeartist.models.TextParser;

public class GameController {

    private JsonObject gameData;
    private TextParser textParser;

    public GameController() {
        loadGameData();
        textParser = new TextParser(gameData);
    }

    private void loadGameData() {
        Gson gson = new Gson();
        InputStream inputStream = getClass().getClassLoader()
            .getResourceAsStream("game_dialogue.json");
        gameData = gson.fromJson(new InputStreamReader(inputStream), JsonObject.class);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print(gameData.get("command_prompt").getAsString());
            String userInput = scanner.nextLine();
            String cleanedInput = textParser.cleanUserInput(userInput);
            JsonElement inputElement = new Gson().toJsonTree(cleanedInput);

            if (textParser.isQuitCommand(inputElement)) {
                boolean confirmQuit = textParser.getConfirmation(
                    gameData.get("quit_confirm").getAsString());

                if (confirmQuit) {
                    System.out.println(gameData.get("goodbye_message").getAsString());
                    running = false;
                }
            } else if (textParser.isHelpCommand(inputElement)) {
                System.out.println(gameData.get("help_menu").getAsString());

            } else {
                if (!textParser.isValidInput(inputElement)) {
                    System.out.println(gameData.get("invalid_input").getAsString());
                }

                // TODO: Add game logic here
            }
        }
    }
}
