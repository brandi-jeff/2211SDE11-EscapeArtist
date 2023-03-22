package com.escapeartist.util;

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
    JsonArray wordsToRemove = gameData.getAsJsonObject("dialogue").getAsJsonArray("words_to_remove");

    if (wordsToRemove != null) {
      for (JsonElement wordToRemove : wordsToRemove) {
        String word = wordToRemove.getAsString();
        userInput = userInput.replaceAll("\\b" + word + "\\b", "").trim();
      }
    }

    return userInput;
  }


  public boolean isValidInput(JsonElement inputElement) {
    JsonObject validInputs = gameData.getAsJsonObject("dialogue").getAsJsonObject("valid_inputs");
    for (String key : validInputs.keySet()) {
      if (validInputs.getAsJsonArray(key).contains(inputElement)) {
        return true;
      }
    }
    return false;
  }

  public boolean isQuitCommand(JsonElement inputElement) {
    if (inputElement == null) {
      return false;
    }

    JsonObject dialogue = gameData.getAsJsonObject("dialogue");
    if (dialogue == null) {
      return false;
    }

    JsonObject validInputs = dialogue.getAsJsonObject("valid_inputs");
    if (validInputs == null) {
      return false;
    }

    return validInputs.getAsJsonArray("quit").contains(inputElement);
  }



  public boolean getConfirmation(String prompt) {
    Scanner scanner = new Scanner(System.in);
    JsonObject validInputs = gameData.getAsJsonObject("dialogue").getAsJsonObject("valid_inputs");

    while (true) {
      System.out.println(prompt);
      System.out.print(gameData.getAsJsonObject("dialogue").get("command_prompt").getAsString());
      String userInput = scanner.nextLine().trim().toLowerCase();
      JsonElement inputElement = new Gson().toJsonTree(userInput);

      if (validInputs.getAsJsonArray("yes").contains(inputElement)) {
        return true;
      } else if (validInputs.getAsJsonArray("no").contains(inputElement)) {
        return false;
      } else {
        System.out.println(gameData.getAsJsonObject("dialogue").get("invalid_input").getAsString());
      }
    }
  }

  public boolean isHelpCommand(JsonElement inputElement) {
    JsonObject validInputs = gameData.getAsJsonObject("dialogue").getAsJsonObject("valid_inputs");
    return validInputs.getAsJsonArray("help").contains(inputElement);
  }

  public boolean isGoCommand(JsonElement inputElement) {
    if (inputElement == null) {
      return false;
    }

    JsonObject dialogue = gameData.getAsJsonObject("dialogue");
    if (dialogue == null) {
      return false;
    }

    JsonObject validInputs = dialogue.getAsJsonObject("valid_inputs");
    if (validInputs == null) {
      return false;
    }

    JsonArray goCommands = validInputs.getAsJsonArray("go");
    for (JsonElement word : goCommands) {
      if (inputElement.getAsString().startsWith(word.getAsString())) {
        return true;
      }
    }
    return false;
  }


  public boolean isLookCommand(JsonElement inputElement){
    if (inputElement == null) {
      return false;
    }

    JsonObject dialogue = gameData.getAsJsonObject("dialogue");
    if (dialogue == null) {
      return false;
    }

    JsonObject validInputs = dialogue.getAsJsonObject("valid_inputs");
    if (validInputs == null) {
      return false;
    }
    JsonArray lookCommands = validInputs.getAsJsonArray("look");
    for(JsonElement word : lookCommands){
      if(inputElement.getAsString().startsWith(word.getAsString())){
        return true;
      }
    }
    return false;
  }

  public boolean isTalkCommand(JsonElement inputElement){
    if (inputElement == null) {
      return false;
    }

    JsonObject dialogue = gameData.getAsJsonObject("dialogue");
    if (dialogue == null) {
      return false;
    }

    JsonObject validInputs = dialogue.getAsJsonObject("valid_inputs");
    if (validInputs == null) {
      return false;
    }
    JsonArray lookCommands = validInputs.getAsJsonArray("talk");
    for(JsonElement word : lookCommands){
      if(inputElement.getAsString().startsWith(word.getAsString())){
        return true;
      }
    }
    return false;
  }

  public boolean isGetCommand(JsonElement inputElement){
    if(inputElement == null){
      return false;
    }
    JsonObject dialogue = gameData.getAsJsonObject("dialogue");
    if(dialogue == null){
      return false;
    }
    JsonObject validInputs = dialogue.getAsJsonObject("valid_inputs");
    if(validInputs == null){
      return false;
    }
    JsonArray getCommands = validInputs.getAsJsonArray("get");
    for(JsonElement word : getCommands){
      if(inputElement.getAsString().startsWith(word.getAsString())){
        return true;
      }
    }
    return false;
  }

  public boolean isDropCommand(JsonElement inputElement){
    if (inputElement == null) {
      return false;
    }

    JsonObject dialogue = gameData.getAsJsonObject("dialogue");
    if (dialogue == null) {
      return false;
    }

    JsonObject validInputs = dialogue.getAsJsonObject("valid_inputs");
    if (validInputs == null) {
      return false;
    }
    JsonArray lookCommands = validInputs.getAsJsonArray("drop");
    for(JsonElement word : lookCommands){
      if(inputElement.getAsString().startsWith(word.getAsString())){
        return true;
      }
    }
    return false;
  }

  public String getSecondWord(String userInput) {
    String[] words = userInput.split(" ");
    if (words.length > 1) {
      return words[1];
    }
    return "";
  }
}