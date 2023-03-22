package com.escapeartist.controllers;

import com.escapeartist.models.*;
import com.escapeartist.util.Clear;
import com.escapeartist.util.GsonDeserializer;
import com.escapeartist.views.GameView;
import com.escapeartist.util.TextParser;
import com.escapeartist.views.MainView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.gson.reflect.TypeToken;
import java.io.IOException;
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
    player = deserializer.deserializePlayer(
        playerJson); // Deserialize the player using the JsonObject

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
      player.playerStatus(gameData);
      Location currentLocation = getLocationById(currentLocationId);
      gameView.displayLocation(new Gson().toJsonTree(currentLocation).getAsJsonObject());
      System.out.print(gameDialogue.getCommandPrompt());
      String userInput = scanner.nextLine();
      String cleanedInput = textParser.cleanUserInput(userInput);
      JsonElement inputElement = new Gson().toJsonTree(cleanedInput);

      if (textParser.isQuitCommand(inputElement)) {
        Clear.clearConsole();
        boolean confirmQuit = textParser.getConfirmation(
            gameData.getAsJsonObject("dialogue").get("quit_confirm").getAsString());

        if (confirmQuit) {
          System.out.println(
              gameData.getAsJsonObject("dialogue").get("goodbye_message").getAsString());
          running = false;
        }
      } else if (textParser.isHelpCommand(inputElement)) {
        Clear.clearConsole();
        System.out.println(gameData.getAsJsonObject("dialogue").get("help_menu").getAsString());
      } else if (textParser.isGoCommand(inputElement)) {
        Clear.clearConsole();
        moveLocation(userInput, currentLocation);

      } else if (textParser.isLookCommand(inputElement)) {
        Clear.clearConsole();
        String secondWord = textParser.getSecondWord(userInput);
        if (currentLocation.getNpcs().stream()
            .anyMatch(npc -> npc.getName().equalsIgnoreCase(secondWord))) {
          lookNpc(userInput, gameData);
        } else if (currentLocation.getItems().stream()
            .anyMatch(item -> item.getName().equalsIgnoreCase(secondWord))) {
          lookItem(userInput, gameData);
        } else {
          System.out.println(gameDialogue.getInvalidInput());
        }
      } else if (textParser.isTalkCommand(inputElement)) {
        Clear.clearConsole();
        talkNpc(userInput, gameData);
      } else if (textParser.isGetCommand(inputElement)) {
        Clear.clearConsole();
        getItem(userInput, gameData, currentLocation);
      }else if (textParser.isDropCommand(inputElement)) {
        Clear.clearConsole();
        dropItem(userInput, currentLocation);
      }
      else {
        if (!textParser.isValidInput(inputElement)) {
          Clear.clearConsole();
          System.out.println(
              gameData.getAsJsonObject("dialogue").get("invalid_input").getAsString());
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
    String direction = textParser.getSecondWord(
        userInput); // Assumes the second word is the direction
    Integer newLocationId = currentLocation.getExits().get(direction);
    // Check if the direction is a valid exit from the current location
    if (newLocationId != null) {
      setCurrentLocationId(newLocationId); // Update the game view with the new location
      String currentLocationName = getLocationById(currentLocationId).getName();
      System.out.println(gameData.getAsJsonObject("dialogue").get("player_moved_location").getAsString() + currentLocationName);
    } else {
      System.out.println(gameDialogue.getInvalidExit());
    }
  }

  public void talkNpc(String userInput, JsonObject gameData) {
    String talkWord = textParser.getSecondWord(userInput);
    List<NPC> npcs = new Gson().fromJson(gameData.getAsJsonArray("npcs"),
        new TypeToken<List<NPC>>() {
        }.getType());
    for (NPC npc : npcs) {
      if (npc.getName().equalsIgnoreCase(talkWord)) {
        System.out.println(npc.getReply());
        toContinue();
        // ask if want to play... add method to go into mini game
      }
    }
  }

  public void lookItem(String userInput, JsonObject gameData) {
    String itemWord = textParser.getSecondWord(userInput);
    List<Item> items = new Gson().fromJson(gameData.getAsJsonArray("items"),
        new TypeToken<List<Item>>() {
        }.getType());
    boolean itemFound = false;

    for (Item item : items) {
      if (item.getName().equalsIgnoreCase(itemWord)) {
        itemFound = true;
        System.out.println(item.getDescription());
      }
    }
    if (!itemFound) {
      System.out.println(gameDialogue.getInvalidInput());
    }
  }

  public void lookNpc(String userInput, JsonObject gameData) {
    String npcWord = textParser.getSecondWord(userInput);
    List<NPC> npcs = new Gson().fromJson(gameData.getAsJsonArray("npcs"),
        new TypeToken<List<NPC>>() {
        }.getType());
    boolean npcFound = false;

    for (NPC npc : npcs) {
      if (npc.getName().equalsIgnoreCase(npcWord)) {
        npcFound = true;
        System.out.println(npc.getDescription());
      }
    }
    if (!npcFound) {
      System.out.println(gameDialogue.getInvalidInput());
    }
  }

  public void getItem(String userInput, JsonObject gameData, Location currentLocation) {
    String itemWord = textParser.getSecondWord(userInput);
    List<Item> itemsLocation = currentLocation.getItems();
    boolean itemFound = false;

    for (Item item : itemsLocation) {
      if (item.getName().equalsIgnoreCase(itemWord)) {
        itemFound = true;
        Item itemToAdd = new Item(item.getId(), item.getName(), item.getDescription());
        player.addItem(itemToAdd);
        currentLocation.removeItem(item);
        System.out.println(
            gameData.getAsJsonObject("dialogue").get("player_picked_up_item").getAsString());
        break;
      }
    }
    if (!itemFound) {
      System.out.println(gameDialogue.getInvalidInput());
    }
  }

  public void dropItem(String userInput, Location currentLocation) {
    String dropWord = textParser.getSecondWord(userInput);
    List<Item> inventory = player.getInventory();
    Item itemToRemove = null;

    for (Item item : inventory) {
      if (item.getName().equalsIgnoreCase(dropWord)) {
        itemToRemove = item;
        break;
      }
    }
    if (itemToRemove == null) {
      System.out.print(gameData.getAsJsonObject("dialogue").get("items_dropped").getAsString());
      toContinue();
    } else {
      player.dropItem(itemToRemove);
      currentLocation.addItemToLocation(itemToRemove);

    }
  }

  public void setCurrentLocationId(int currentLocationId) {
    this.currentLocationId = currentLocationId;
  }

  public void toContinue() {
    System.out.println("Press enter to continue...");
    try {
      System.in.read();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
