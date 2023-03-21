package com.escapeartist.controllers;

import com.escapeartist.models.*;
import com.escapeartist.util.GsonDeserializer;
import com.escapeartist.views.GameView;
import com.escapeartist.util.TextParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Scanner;

public class GameController {
    private JsonObject gameData;
    private TextParser textParser;
    private Player player;
    private GameView gameView;
    private int currentLocationId;
    private List<Location> locations;
    private GameDialogue gameDialogue;

    public GameController(JsonObject gameData) {
        this.gameData = gameData;
    }

    public void loadGameData() {
        textParser = new TextParser(gameData);
        gameView = new GameView(gameData);
        GsonDeserializer deserializer = new GsonDeserializer();
        locations = deserializer.deserializeLocations();
        List<Item> items = deserializer.deserializeItems();
        List<NPC> npcs = deserializer.deserializeNPCs();
        gameDialogue = deserializer.deserializeGameDialogue();
        JsonObject playerJson = deserializer.deserializePlayerJson();
        player = deserializer.deserializePlayer(playerJson); // Deserialize the player using the JsonObject

        gameData.add("player", new Gson().toJsonTree(player));
        gameData.add("dialogue", new Gson().toJsonTree(gameDialogue));
        gameData.add("locations", new Gson().toJsonTree(locations));
        gameData.add("items", new Gson().toJsonTree(items));
        gameData.add("npcs", new Gson().toJsonTree(npcs));

        this.currentLocationId = player.getCurrentLocation();

    }

    public void run() {
        loadGameData();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            Location currentLocation = getLocationById(currentLocationId);
            gameView.displayLocation(new Gson().toJsonTree(currentLocation).getAsJsonObject());
            System.out.print(gameDialogue.getCommandPrompt());
            String userInput = scanner.nextLine();
            String cleanedInput = textParser.cleanUserInput(userInput);
            JsonElement inputElement = new Gson().toJsonTree(cleanedInput);

            if (textParser.isQuitCommand(inputElement)) {
                boolean confirmQuit = textParser.getConfirmation(gameData.getAsJsonObject("dialogue").get("quit_confirm").getAsString());

                if (confirmQuit) {
                    System.out.println(gameData.getAsJsonObject("dialogue").get("goodbye_message").getAsString());
                    running = false;
                }
            } else if (textParser.isHelpCommand(inputElement)) {
                System.out.println(gameData.getAsJsonObject("dialogue").get("help_menu").getAsString());
            } else if (textParser.isGoCommand(inputElement)) {
                moveLocation(userInput, currentLocation);

            } else if (textParser.isLookCommand(inputElement)){
                lookItem(userInput, currentLocation);

            }else {
                if (!textParser.isValidInput(inputElement)) {
                    System.out.println(gameData.getAsJsonObject("dialogue").get("invalid_input").getAsString());
                }
                // TODO: Add game logic here
            }
        }
    }

    private Location getLocationById(int locationId) {
        for (Location location : locations) {
            if (location.getId() == locationId) {
                return location;
            }
        }
        return null;
    }

    public void moveLocation(String userInput, Location currentLocation) {
        String direction = textParser.getSecondWord(userInput); // Assumes the second word is the direction
        Integer newLocationId = currentLocation.getExits().get(direction);
        // Check if the direction is a valid exit from the current location
        if (newLocationId != null) {
            setCurrentLocationId(newLocationId);
            gameView.displayLocation(new Gson().toJsonTree(getLocationById(newLocationId)).getAsJsonObject()); // Update the game view with the new location
        } else {
            System.out.println(gameDialogue.getInvalidExit());
        }
    }

    public void lookItem(String userInput, Location currentLocation){
        String itemWord = textParser.getSecondWord(userInput); //second word will be an item
        List<Item> items = currentLocation.getItems();
        boolean itemFound = false;

        for(Item item : items) {
            if (item.getName().equalsIgnoreCase(itemWord)) {
                System.out.println(item.getDescription());
                itemFound = true;
            }
        }

        if (!itemFound) {
            System.out.println(gameDialogue.getInvalidInput());
        }
    }

    public void setCurrentLocationId(int currentLocationId) {
        this.currentLocationId = currentLocationId;
        gameView.displayLocation(new Gson().toJsonTree(getLocationById(currentLocationId)).getAsJsonObject()); // Update the game view with the new location
    }

//    public void playerStatus() {
//        // TODO: 3/21/23 Refactor this to be able to display the player's status for appropriate ticket'
//        System.out.println("Current HP: " + player.getHp());
//        System.out.println("Attack: " + player.getAttack());
//        System.out.println("Defense: " + player.getDefense());
//        System.out.println("Inventory: " + player.getInventory());
//
//        Item equippedWeapon = player.getEquippedWeapon();
//        if (equippedWeapon != null) {
//            System.out.println("Equipped weapon: " + equippedWeapon.getName());
//        } else {
//            System.out.println("No weapon equipped.");
//        }
//
//        Item equippedArmor = player.getEquippedArmor();
//        if (equippedArmor != null) {
//            System.out.println("Equipped armor: " + equippedArmor.getName());
//        } else {
//            System.out.println("No armor equipped.");
//        }
//    }

}
