package com.escapeartist.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Unscramble {

  @SerializedName("name")
  private String name;

  @SerializedName("win")
  private String win;

  @SerializedName("lose")
  private String lose;

  @SerializedName("words")
  private List<String> words;

  @SerializedName("scrambled")
  private String scrambled;

  @SerializedName("answer")
  private String answer;

  @SerializedName("no_answer")
  private String noAnswer;

  @SerializedName("try_again")
  private String tryAgain;

  @SerializedName("no_play_again")
  private String noPlayAgain;

  @SerializedName("give_up")
  private String giveUp;



  List<Character> randList = new ArrayList<>();
  StringBuilder randWord = new StringBuilder();
  String randWordString;
  boolean anotherTry;

public String randomizeWord(List<String> answer) {
  if (anotherTry) {
    randList.clear();
    randWord = new StringBuilder();
    randWordString = "";
  }
  // Need to choose a word to scramble randomly and store
  Random random = new Random();
  //get size of list and randomly choose index, then return word at that index as rand
  String rand = answer.get(random.nextInt(answer.size()));
  // get characters and add to list
  for (char letter : rand.toCharArray()) {
    randList.add(letter);
  }

  for (char letter : randList) {
    randWord.append(letter);
  }
  randWordString = randWord.toString();
  anotherTry = true;
  return randWordString;
}

public String scrambleWord(){
  // Then scramble chosen word and store
  Collections.shuffle(randList);
  StringBuilder scrambled = new StringBuilder();
  for (char letter : randList) {
    scrambled.append(letter);
  }
  return scrambled.toString();
}

public boolean guesses(String guess) {
  if (guess.equals(randWordString)) {
    return true;
  }
  return false;
}

  public String getName() {
    return name;
  }

  public String getWin() {
    return win;
  }

  public String getLose() {
    return lose;
  }

  public String getAnswer() {
    return answer;
  }

  public String getTryAgain() {
    return tryAgain;
  }

  public String getNoPlayAgain() {
    return noPlayAgain;
  }

  public String getNoAnswer() {
    return noAnswer;
  }

  public String getGiveUp() {
    return giveUp;
  }
}


