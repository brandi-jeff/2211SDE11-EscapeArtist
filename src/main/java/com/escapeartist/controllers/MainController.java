package com.escapeartist.controllers;

import com.escapeartist.models.*;
import com.escapeartist.util.GsonDeserializer;
import com.escapeartist.views.MainView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MainController {
    // Hold an instance of MainView
    private MainView mainView;
    private GameController gameController;

    // Constructor initializes the MainView instance
    public MainController() {
        mainView = new MainView();

        // Deserialize game data
        GsonDeserializer deserializer = new GsonDeserializer();
        JsonObject gameData = new JsonObject();
        gameData.add("dialogue", new Gson().toJsonTree(deserializer.deserializeGameDialogue()));
        gameData.add("locations", new Gson().toJsonTree(deserializer.deserializeLocations()));
        gameData.add("items", new Gson().toJsonTree(deserializer.deserializeItems()));
        gameData.add("npcs", new Gson().toJsonTree(deserializer.deserializeNPCs()));

        // Initialize game controller with game data
        gameController = new GameController(gameData);
    }


    // Start the game loop, prompting the user for input until a valid input is provided
    public void startMenu() {
        boolean validInput = false;
        mainView.showGameInfo();

        while (!validInput) {
            // Show the welcome message and options
            mainView.showWelcomeMessage();

            String userInput = String.valueOf(mainView.getUserInput());

            // Check if the user input is valid
            String validCommand = mainView.isValidInput(userInput);
            if (!validCommand.isEmpty()) {
                switch (validCommand) {
                    case "new game":
                    case "new":
                        // Start a new game
                        newGame();
                        validInput = true;
                        break;
                    case "quit":
                    case "exit":
                        // Quit the game
                        quitGame();
                        validInput = true;
                        break;
                    default:
                        break;
                }
            } else {
                mainView.clear();
                // Show the invalid option message
                mainView.printMessage("invalid_option");
            }
        }
    }
    private void quitGame() {
        mainView.printMessage("goodbye_message");
        System.exit(0);
    }

    // Starts a new game
    private void newGame() {
        mainView.clear();
        mainView.printMessage("new_game_start");
        gameController.run();
    }
}
