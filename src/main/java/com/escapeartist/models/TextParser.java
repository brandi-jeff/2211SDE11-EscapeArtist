package com.escapeartist.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Scanner;

public class TextParser {
  private JsonObject gameData;

  public TextParser(JsonObject gameData) {
    this.gameData = gameData;
  }

  public String cleanUserInput(String userInput) {
    userInput = userInput.trim().toLowerCase();
    JsonArray wordsToRemove = gameData.getAsJsonArray("words_to_remove");

    for (JsonElement wordToRemove : wordsToRemove) {
      String word = wordToRemove.getAsString();
      userInput = userInput.replaceAll("\\b" + word + "\\b", "").trim();
    }

    return userInput;
  }

  public boolean isValidInput(JsonElement inputElement) {
    JsonObject validInputs = gameData.getAsJsonObject("valid_inputs");
    for (String key : validInputs.keySet()) {
      if (validInputs.getAsJsonArray(key).contains(inputElement)) {
        return true;
      }
    }
    return false;
  }

  public boolean isQuitCommand(JsonElement inputElement) {
    JsonObject validInputs = gameData.getAsJsonObject("valid_inputs");
    return validInputs.getAsJsonArray("quit").contains(inputElement);
  }

  public boolean getConfirmation(String prompt) {
    Scanner scanner = new Scanner(System.in);
    JsonObject validInputs = gameData.getAsJsonObject("valid_inputs");

    while (true) {
      System.out.println(prompt);
      System.out.print(gameData.get("command_prompt").getAsString());
      String userInput = scanner.nextLine().trim().toLowerCase();
      JsonElement inputElement = new Gson().toJsonTree(userInput);

      if (validInputs.getAsJsonArray("yes").contains(inputElement)) {
        return true;
      } else if (validInputs.getAsJsonArray("no").contains(inputElement)) {
        return false;
      } else {
        System.out.println(gameData.get("invalid_input").getAsString());
      }
    }
  }

  public boolean isHelpCommand(JsonElement inputElement) {
    JsonObject validInputs = gameData.getAsJsonObject("valid_inputs");
    return validInputs.getAsJsonArray("help").contains(inputElement);
  }
}