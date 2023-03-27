package com.escapeartist.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.escapeartist.models.GameDialogue;
import com.escapeartist.models.Trivia;
import com.escapeartist.util.GsonDeserializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {

    private GameDialogue gameDialogue;
    private GsonDeserializer deserializer;
    private JsonObject gameData;
    private List<Trivia> trivias;
    private GameController gameController;
    private ByteArrayOutputStream outContent;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        deserializer = new GsonDeserializer();
        gameDialogue = deserializer.deserializeGameDialogue();
        trivias = deserializer.deserializeTrivia();
        gameData = new JsonObject();
        gameData.add("trivia", new Gson().toJsonTree(trivias));
        gameController = new GameController(gameData);
        gameController.loadGameData();
    }

    @Test
    public void finalIncorrectTriviaAnswerTest() {
        String thirdIncorrectAnswer = "You're all out of guesses!. Better luck next time!";
        assertEquals(thirdIncorrectAnswer, gameDialogue.getFinalIncorrectAnswer());
    }

    @Test
    public void playerGaveIncorrectAnswerTest() {
        String playerGaveIncorrectAnswer = "Incorrect answer. Please try again";
        assertEquals(playerGaveIncorrectAnswer, gameDialogue.getPlayerGaveIncorrectAnswer());
    }

    @Test
    public void triviaJsonArrayTest() {
        JsonArray triviaJsonArray = gameData.getAsJsonArray("trivia");
        assertNotNull(triviaJsonArray);
        assertTrue(triviaJsonArray.isJsonArray());
        assertFalse(triviaJsonArray.isJsonObject());
        assertFalse(triviaJsonArray.toString().isEmpty());
    }

    @Test
    void PlayRiddleWithCorrectAnswerTest() {

        // Set the input to the correct answer
        ByteArrayInputStream in = new ByteArrayInputStream("coin\n".getBytes());
        System.setIn(in);

        // Test playRiddle with correct answer
        assertTrue(gameController.playRiddle(1));

        // Reset the input and output streams
        System.setIn(originalIn);
        System.setOut(System.out);
    }
}