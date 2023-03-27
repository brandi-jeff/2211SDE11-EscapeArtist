package com.escapeartist.models;

import static org.junit.jupiter.api.Assertions.*;

import com.escapeartist.controllers.GameController;
import com.escapeartist.util.GsonDeserializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UnscrambleTest {
  private static Unscramble unscrambled;
  private final String answer = "silent";

  @BeforeAll
  public static void setUp() {
    unscrambled = new Unscramble();
  }

  @Test
  void testGuessedCorrectly() {
    String correctGuess = "silent";
    unscrambled.guesses(correctGuess);
    assertEquals(answer, correctGuess);
  }

  @Test
  void testGuessedIncorrectly() {
    String incorrectGuess = "listen";
    unscrambled.guesses(incorrectGuess);
    assertNotEquals(answer, incorrectGuess);
  }

}