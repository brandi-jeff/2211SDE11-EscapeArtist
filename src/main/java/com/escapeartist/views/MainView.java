package com.escapeartist.views;

import com.escapeartist.util.Clear;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MainView {
  private JsonObject menuData;
  private JsonObject basicGameInfo;

  public MainView() {
    loadMenuData();
  }

  private void loadMenuData() {
    Gson gson = new Gson();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("menu_dialogue.json");
    menuData = gson.fromJson(new InputStreamReader(inputStream), JsonObject.class);
    InputStream inputStreamInfo = getClass().getClassLoader().getResourceAsStream("game_play.json");
    basicGameInfo = gson.fromJson(new InputStreamReader(inputStreamInfo), JsonObject.class);
  }

  public void showWelcomeMessage() {
    System.out.println(menuData.get("welcome_message").getAsString());
    JsonArray options = menuData.getAsJsonArray("options");
    for (int i = 0; i < options.size(); i++) {
      System.out.println(options.get(i).getAsString());
    }
  }

  public void showGameInfo() {
    System.out.println(basicGameInfo.get("story").getAsString());
    toContinue();
    Clear.clearConsole();
    formatExplanation();
    System.out.println(basicGameInfo.get("objective").getAsString());
    toContinue();
    Clear.clearConsole();
    System.out.println(basicGameInfo.get("outcome").getAsString());
    toContinue();
    Clear.clearConsole();
  }


  public void formatExplanation() {
    JsonObject exhibits = basicGameInfo.getAsJsonObject("exhibits");
    for (Map.Entry<String, JsonElement> entry : exhibits.entrySet()) {
      JsonObject exhibit = entry.getValue().getAsJsonObject();
      String explanation = exhibit.get("explanation").getAsString();
      String easy = exhibit.get("easy").getAsString();
      String medium = exhibit.get("medium").getAsString();
      String hard = exhibit.get("hard").getAsString();
      explanation = explanation
          .replace("{easy}", easy)
          .replace("{medium}", medium)
          .replace("{hard}", hard);
      System.out.println(explanation);
      toContinue();
    }
  }

  public void toContinue() {
    System.out.println("Press enter to continue...");
    try {
      System.in.read();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getUserInput() {
    System.out.print("> "); // Display the prompt
    Scanner scanner = new Scanner(System.in);
    String userInput = scanner.nextLine().trim().toLowerCase();

    // Split the input into words
    String[] words = userInput.split("\\s+");

    // Get the words to remove from the menu_data JSON object
    JsonArray wordsToRemoveJson = menuData.getAsJsonArray("words_to_remove");
    List<String> wordsToRemove = new ArrayList<>();
    for (int i = 0; i < wordsToRemoveJson.size(); i++) {
      wordsToRemove.add(wordsToRemoveJson.get(i).getAsString());
    }

    // Remove the unwanted words
    StringBuilder cleanedInput = new StringBuilder();
    for (String word : words) {
      if (!wordsToRemove.contains(word)) {
        cleanedInput.append(word).append(" ");
      }
    }

    // Return the cleaned-up string
    return cleanedInput.toString().trim();
  }


  public String isValidInput(String userInput) {
    JsonArray validInputs = menuData.getAsJsonArray("valid_inputs");
    for (int i = 0; i < validInputs.size(); i++) {
      if (userInput.equals(validInputs.get(i).getAsString())) {
        return validInputs.get(i).getAsString();
      }
    }
    return "";
  }

  public void clear() {
    try {
      String os = System.getProperty("os.name");

      if (os.contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }


  public void printMessage(String key) {
    System.out.println(menuData.get(key).getAsString());
  }


}
