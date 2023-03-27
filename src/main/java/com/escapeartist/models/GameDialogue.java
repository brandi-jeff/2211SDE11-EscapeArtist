package com.escapeartist.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class GameDialogue {

  @SerializedName("quit_confirm")
  private String quitConfirm;

  @SerializedName("goodbye_message")
  private String goodbyeMessage;

  @SerializedName("command_prompt")
  private String commandPrompt;

  @SerializedName("user_prompt")
  private String userPrompt;

  @SerializedName("invalid_input")
  private String invalidInput;

  @SerializedName("current_location")
  private String currentLocation;

  @SerializedName("invalid_exit")
  private String invalidExit;

  @SerializedName("exits_text")
  private String exitsText;

  @SerializedName("items_text")
  private String itemsText;

  @SerializedName("items_dropped")
  private String itemsDropped;

  @SerializedName("npcs_text")
  private String npcsText;

  @SerializedName("valid_inputs")
  private Map<String, List<String>> validInputs;

  @SerializedName("words_to_remove")
  private List<String> wordsToRemove;

  @SerializedName("help_menu")
  private String helpMenu;

  @SerializedName("player_status_start")
  private String playerStatusStart;

  @SerializedName("player_status_hp")
  private String playerStatusHp;

  @SerializedName("player_status_attack")
  private String playerStatusAttack;

  @SerializedName("player_status_defense")
  private String playerStatusDefense;

  @SerializedName("player_status_inventory")
  private String playerStatusInventory;

  @SerializedName("player_status_weapon_equipped")
  private String playerStatusWeaponEquipped;

  @SerializedName("player_status_weapon_not_equipped")
  private String playerStatusWeaponNotEquipped;

  @SerializedName("player_status_armor_equipped")
  private String playerStatusArmorEquipped;

  @SerializedName("player_status_armor_not_equipped")
  private String playerStatusArmorNotEquipped;

  @SerializedName("player_status_end")
  private String playerStatusEnd;

  @SerializedName("player_picked_up_item")
  private String playerPickedUpItem;

  @SerializedName("player_moved_location")
  private String playerMovedLocation;

  @SerializedName("correct_riddle")
  private String playerSolvedRiddle;

  @SerializedName("correct_trivia")
  private String playerSolvedTrivia;

  @SerializedName("incorrect_answer")
  private String playerGaveIncorrectAnswer;


  @SerializedName("final_incorrect_answer")
  private String finalIncorrectAnswer;

  public String getFinalIncorrectAnswer() {
    return finalIncorrectAnswer;
  }

  public void setFinalIncorrectAnswer(String finalIncorrectAnswer) {
    this.finalIncorrectAnswer = finalIncorrectAnswer;
  }

  public String getPlayerSolvedRiddle() {
    return playerSolvedRiddle;
  }

  public void setPlayerSolvedRiddle(String playerSolvedRiddle) {
    this.playerSolvedRiddle = playerSolvedRiddle;
  }

  public String getPlayerSolvedTrivia() {
    return playerSolvedTrivia;
  }

  public void setPlayerSolvedTrivia(String playerSolvedTrivia) {
    this.playerSolvedTrivia = playerSolvedTrivia;
  }

  public String getPlayerGaveIncorrectAnswer() {
    return playerGaveIncorrectAnswer;
  }

  public void setPlayerGaveIncorrectAnswer(String playerGaveIncorrectAnswer) {
    this.playerGaveIncorrectAnswer = playerGaveIncorrectAnswer;
  }

  @SerializedName("riddle_solved")
  private String riddleSolved;

  @SerializedName("riddle_incorrect")
  private String riddleIncorrect;

  @SerializedName("riddle_not_solved")
  private String riddleNotSolved;

  public String getQuitConfirm() {
    return quitConfirm;
  }

  public String getGoodbyeMessage() {
    return goodbyeMessage;
  }

  public String getPlayerMovedLocation() {
    return playerMovedLocation;
  }

  public void setPlayerMovedLocation(String playerMovedLocation) {
    this.playerMovedLocation = playerMovedLocation;
  }

  public String getCommandPrompt() {
    return commandPrompt;
  }

  public String getInvalidInput() {
    return invalidInput;
  }

  public String getInvalidExit() {
    return invalidExit;
  }

  public String getExitsText() {
    return exitsText;
  }

  public String getItemsText() {
    return itemsText;
  }

  public String getNpcsText() {
    return npcsText;
  }

  public Map<String, List<String>> getValidInputs() {
    return validInputs;
  }

  public List<String> getWordsToRemove() {
    return wordsToRemove;
  }

  public String getHelpMenu() {
    return helpMenu;
  }

  public void setQuitConfirm(String quitConfirm) {
    this.quitConfirm = quitConfirm;
  }

  public void setGoodbyeMessage(String goodbyeMessage) {
    this.goodbyeMessage = goodbyeMessage;
  }

  public void setCommandPrompt(String commandPrompt) {
    this.commandPrompt = commandPrompt;
  }

  public void setInvalidInput(String invalidInput) {
    this.invalidInput = invalidInput;
  }

  public void setInvalidExit(String invalidExit) {
    this.invalidExit = invalidExit;
  }

  public void setExitsText(String exitsText) {
    this.exitsText = exitsText;
  }

  public void setItemsText(String itemsText) {
    this.itemsText = itemsText;
  }

  public void setNpcsText(String npcsText) {
    this.npcsText = npcsText;
  }

  public void setValidInputs(Map<String, List<String>> validInputs) {
    this.validInputs = validInputs;
  }

  public void setWordsToRemove(List<String> wordsToRemove) {
    this.wordsToRemove = wordsToRemove;
  }

  public void setHelpMenu(String helpMenu) {
    this.helpMenu = helpMenu;
  }

  public String getPlayerStatusHp() {
    return playerStatusHp;
  }

  public void setPlayerStatusHp(String playerStatusHp) {
    this.playerStatusHp = playerStatusHp;
  }

  public String getPlayerStatusAttack() {
    return playerStatusAttack;
  }

  public void setPlayerStatusAttack(String playerStatusAttack) {
    this.playerStatusAttack = playerStatusAttack;
  }

  public String getPlayerStatusDefense() {
    return playerStatusDefense;
  }

  public void setPlayerStatusDefense(String playerStatusDefense) {
    this.playerStatusDefense = playerStatusDefense;
  }

  public String getPlayerStatusInventory() {
    return playerStatusInventory;
  }

  public void setPlayerStatusInventory(String playerStatusInventory) {
    this.playerStatusInventory = playerStatusInventory;
  }

  public String getPlayerStatusWeaponEquipped() {
    return playerStatusWeaponEquipped;
  }

  public void setPlayerStatusWeaponEquipped(String playerStatusWeaponEquipped) {
    this.playerStatusWeaponEquipped = playerStatusWeaponEquipped;
  }

  public String getPlayerStatusWeaponNotEquipped() {
    return playerStatusWeaponNotEquipped;
  }

  public void setPlayerStatusWeaponNotEquipped(String playerStatusWeaponNotEquipped) {
    this.playerStatusWeaponNotEquipped = playerStatusWeaponNotEquipped;
  }

  public String getPlayerStatusArmorEquipped() {
    return playerStatusArmorEquipped;
  }

  public void setPlayerStatusArmorEquipped(String playerStatusArmorEquipped) {
    this.playerStatusArmorEquipped = playerStatusArmorEquipped;
  }

  public String getPlayerStatusArmorNotEquipped() {
    return playerStatusArmorNotEquipped;
  }

  public void setPlayerStatusArmorNotEquipped(String playerStatusArmorNotEquipped) {
    this.playerStatusArmorNotEquipped = playerStatusArmorNotEquipped;
  }

  public String getPlayerStatusStart() {
    return playerStatusStart;
  }

  public void setPlayerStatusStart(String playerStatusStart) {
    this.playerStatusStart = playerStatusStart;
  }

  public String getPlayerStatusEnd() {
    return playerStatusEnd;
  }

  public void setPlayerStatusEnd(String playerStatusEnd) {
    this.playerStatusEnd = playerStatusEnd;
  }

  public String getPlayerPickedUpItem() {
    return playerPickedUpItem;
  }

  public void setPlayerPickedUpItem(String playerPickedUpItem) {
    this.playerPickedUpItem = playerPickedUpItem;
  }

  public String getUserPrompt() {
    return userPrompt;
  }

  public void setUserPrompt(String userPrompt) {
    this.userPrompt = userPrompt;
  }

  public String getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(String currentLocation) {
    this.currentLocation = currentLocation;
  }

  public String getItemsDropped() {
    return itemsDropped;
  }

  public void setItemsDropped(String itemsDropped) {
    this.itemsDropped = itemsDropped;
  }

  public String getRiddleSolved() {
    return riddleSolved;
  }

  public void setRiddleSolved(String riddleSolved) {
    this.riddleSolved = riddleSolved;
  }

  public String getRiddleIncorrect() {
    return riddleIncorrect;
  }

  public void setRiddleIncorrect(String riddleIncorrect) {
    this.riddleIncorrect = riddleIncorrect;
  }

  public String getRiddleNotSolved() {
    return riddleNotSolved;
  }

  public void setRiddleNotSolved(String riddleNotSolved) {
    this.riddleNotSolved = riddleNotSolved;
  }

}

