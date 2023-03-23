package com.escapeartist.controllers;

import com.escapeartist.models.*;
import com.escapeartist.util.Clear;
import com.escapeartist.util.GameMusic;
import com.escapeartist.util.GsonDeserializer;
import com.escapeartist.views.GameView;
import com.escapeartist.util.TextParser;
import com.escapeartist.views.MainView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.gson.reflect.TypeToken;
import com.sun.source.tree.WhileLoopTree;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class GameController {

  private JsonObject gameData;
  private TextParser textParser;
  private Player player;
  private GameView gameView;
  private int currentLocationId;
  private List<Location> locations;
  private GameDialogue gameDialogue;
  private List<Riddle> riddles;
  private List<Trivia> trivias;

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
    riddles = deserializer.deserializeRiddles();
    trivias = deserializer.deserializeTrivia();

    gameData.add("player", new Gson().toJsonTree(player));
    gameData.add("dialogue", new Gson().toJsonTree(gameDialogue));
    gameData.add("locations", new Gson().toJsonTree(locations));
    gameData.add("items", new Gson().toJsonTree(items));
    gameData.add("npcs", new Gson().toJsonTree(npcs));
    gameData.add("riddle", new Gson().toJsonTree(riddles));
    gameData.add("trivia", new Gson().toJsonTree(trivias));

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
      } else if (textParser.isDropCommand(inputElement)) {
        Clear.clearConsole();
        dropItem(userInput, currentLocation);
      } else {
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
      System.out.println(
          gameData.getAsJsonObject("dialogue").get("player_moved_location").getAsString()
              + currentLocationName);
    } else {
      System.out.println(gameDialogue.getInvalidExit());
    }
  }

  public void talkNpc(String userInput, JsonObject gameData) {
    String talkWord = textParser.getSecondWord(userInput);
    List<NPC> npcs = new Gson().fromJson(gameData.getAsJsonArray("npcs"),
        new TypeToken<List<NPC>>() {
        }.getType());

    NPC ghost = null;
    NPC knight = null;

    // Check if the NPC is in the current location before talking to them
    Location currentLocation = getLocationById(currentLocationId);
    if (currentLocation.getNpcs().stream()
        .noneMatch(npc -> npc.getName().equalsIgnoreCase(talkWord))) {
      System.out.println(gameDialogue.getInvalidInput());
      return;
    }

    for (NPC npc : npcs) {
      if (npc.getName().equalsIgnoreCase(talkWord)) {
        System.out.println(npc.getReply());
        toContinue();

        if (npc.getName().equalsIgnoreCase("ghost")) {
          ghost = npc;
        }else if (npc.getName().equalsIgnoreCase("knight")){
          knight = npc;
        }else
        break;
      }
    }

    if (ghost != null) {
      System.out.println(ghost.getGameInvitation());
      System.out.print(gameDialogue.getCommandPrompt());
      Scanner scanner = new Scanner(System.in);
      String choice = scanner.nextLine();

      if (gameDialogue.getValidInputs().get("yes").contains(choice.toLowerCase())) {
        // get a riddle from the game data based on its ID
        int riddleId = 1;
        JsonArray riddlesJsonArray = gameData.getAsJsonArray("riddle");
        Type listType = new TypeToken<List<Riddle>>() {
        }.getType();
        List<Riddle> riddlesList = new Gson().fromJson(riddlesJsonArray, listType);

        Riddle riddle = Riddle.getRiddleById(riddlesList, riddleId);

        // play the riddle mini-game
        playRiddle(riddle.getId());
      } else if (gameDialogue.getValidInputs().get("no").contains(choice.toLowerCase())) {
        System.out.println(ghost.getGoodbyeMessage());
      } else {
        System.out.println(gameDialogue.getInvalidInput());
      }
    }
    //if knight npc is found
    if(knight != null){
      System.out.println(knight.getGameInvitation());
      System.out.print(gameDialogue.getCommandPrompt());
      Scanner scanner = new Scanner(System.in);
      String choice = scanner.next();
      // get the trivia the json file, hardcoding to id 1
      if(gameDialogue.getValidInputs().get("yes").contains((choice.toLowerCase()))){
        int triviaId = 1;
        JsonArray triviasJsonArray = gameData.getAsJsonArray("trivia");
        Type listType = new TypeToken<List<Trivia>>(){}.getType();
        List<Trivia> triviaList = new Gson().fromJson(triviasJsonArray, listType);
        Trivia trivia = Trivia.getTriviaByID(triviaList, triviaId);

        //play the trivia game for the knight
        playTrivia(trivia.getId());
      } else if (gameDialogue.getValidInputs().get("no").contains(choice.toLowerCase())){
        System.out.println(knight.getGoodbyeMessage());
      } else {
        System.out.println(gameDialogue.getInvalidInput());
      }
    }else {
      System.out.println(gameDialogue.getInvalidInput());
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

  public void playRiddle(int riddleId) {
    JsonArray riddlesJsonArray = gameData.getAsJsonArray("riddle");
    Type listType = new TypeToken<List<Riddle>>() {
    }.getType();
    List<Riddle> riddlesList = new Gson().fromJson(riddlesJsonArray, listType);

    Riddle riddle = Riddle.getRiddleById(riddlesList, riddleId);

    if (riddle == null) {
      System.out.println("Invalid riddle ID");
      return;
    }

    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println(riddle.getQuestion());
      System.out.print(gameDialogue.getCommandPrompt());
      String answer = scanner.nextLine().toLowerCase();

      if (answer.equals(riddle.getAnswer())) {
        System.out.println(gameDialogue.getPlayerSolvedRiddle());
        break;
      } else {
        System.out.println(gameDialogue.getPlayerGaveIncorrectAnswer());
      }
    }
  }

  public void playTrivia(int triviaID) {
    JsonArray triviaJsonArray = gameData.getAsJsonArray("trivia");
    Type listType = new TypeToken<List<Trivia>>() {
    }.getType();
    List<Trivia> triviasList = new Gson().fromJson(triviaJsonArray, listType);
    Trivia trivia = Trivia.getTriviaByID(triviasList, triviaID);

    if (trivia == null) {
      System.out.println("Invalid Trivia ID");
      return;
    }
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println(trivia.getQuestion());
      System.out.print(gameDialogue.getCommandPrompt());
      String answer = scanner.nextLine().toLowerCase();

      if (answer.equalsIgnoreCase(trivia.getAnswer())) {
        System.out.println(gameDialogue.getPlayerSolvedTrivia());
        break;
      } else {
        System.out.println(gameDialogue.getPlayerGaveIncorrectAnswer());
      }
    }
  }
}
