package com.escapeartist.util;

import com.escapeartist.models.GameDialogue;
import com.escapeartist.models.Player;
import com.escapeartist.models.Riddle;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class GsonDeserializerTest {

    private static final String PLAYER_FILE = "player.json";

    @Test
    public void testDeserializeGameDialogue() throws IOException {
        GsonDeserializer gsonDeserializer = new GsonDeserializer();
        GameDialogue dialogue = gsonDeserializer.deserializeGameDialogue();

        assertEquals("Are you sure you want to quit? (yes/no)", dialogue.getQuitConfirm());
        assertEquals("Thanks for playing! Goodbye!", dialogue.getGoodbyeMessage());
        assertEquals("What would you like to do? \n> ", dialogue.getCommandPrompt());
        assertEquals("> ", dialogue.getUserPrompt());
        assertEquals("Invalid command. Please try again.\nType help for more information.", dialogue.getInvalidInput());
        assertEquals("There is no exit in that direction.", dialogue.getInvalidExit());
        assertEquals("Current location: ", dialogue.getCurrentLocation());
        assertEquals("Exits: ", dialogue.getExitsText());
        assertEquals("Items: ", dialogue.getItemsText());
        assertEquals("Item not found.\n", dialogue.getItemsDropped());
        assertEquals("NPCs: ", dialogue.getNpcsText());

        Map<String, List<String>> validInputs = dialogue.getValidInputs();
        assertEquals(Arrays.asList("yes", "y"), validInputs.get("yes"));
        assertEquals(Arrays.asList("no", "n"), validInputs.get("no"));
        assertEquals(Arrays.asList("quit", "exit", "q"), validInputs.get("quit"));
        assertEquals(Arrays.asList("help", "h", "?"), validInputs.get("help"));
        assertEquals(Arrays.asList("get", "take", "grab", "pickup"), validInputs.get("get"));
        assertEquals(Arrays.asList("talk", "speak", "chat", "interact"), validInputs.get("talk"));
        assertEquals(Arrays.asList("go", "move"), validInputs.get("go"));
        assertEquals(Arrays.asList("look", "inspect", "view"), validInputs.get("look"));
        assertEquals(Arrays.asList("use", "consume"), validInputs.get("use"));
        assertEquals(Arrays.asList("equip", "arm"), validInputs.get("equip"));
        assertEquals(Arrays.asList("drop", "discard"), validInputs.get("drop"));

        List<String> wordsToRemove = dialogue.getWordsToRemove();
        assertEquals("the", wordsToRemove.get(0));
        assertEquals("of", wordsToRemove.get(1));
        assertEquals("a", wordsToRemove.get(2));
        assertEquals("to", wordsToRemove.get(3));
        assertEquals("and", wordsToRemove.get(4));
        assertEquals("an", wordsToRemove.get(5));

        assertEquals("Acceptable commands\n'go': used to move to another location\n'get': used to pickup an item\n'talk: allows you to interact\n'look': allows you to look around a room\n'use': allows you to use the items in your inventory\n'drop': allows you to drop an item from your inventory\n'equip': allows you to equip yourself with the equipment in your inventory\n'quit':  allows you to exit the game\n'new': allows you to start a new game\n'help': brings you to this menu\nAcceptable directions to use with 'go':\n'north'\n'east'\n'south'\n'west'", dialogue.getHelpMenu());
    }

    @Test
    public void testDeserializeRiddles() throws IOException {
        GsonDeserializer gsonDeserializer = new GsonDeserializer();
        List<Riddle> riddles = gsonDeserializer.deserializeRiddles();

        // Check that we have the correct number of riddles
        assertEquals(1, riddles.size());

        // Check that the riddle has the correct properties
        Riddle riddle = riddles.get(0);
        assertEquals(1, riddle.getId());
        assertEquals("What has a head and a tail but no body?", riddle.getQuestion());
        assertEquals("coin", riddle.getAnswer());
    }

    @Test
    public void testDeserializePlayer() throws IOException {
        GsonDeserializer gsonDeserializer = new GsonDeserializer();

        // Deserialize player JSON into a JsonObject
        JsonObject playerJson = gsonDeserializer.deserializePlayerJson();

        // Deserialize the JsonObject into a Player object
        Player player = gsonDeserializer.deserializePlayer(playerJson);

        assertEquals(100, player.getHp());
        assertEquals(10, player.getAttack());
        assertEquals(5, player.getDefense());
        assertEquals(1, player.getCurrentLocation());
        assertNull(player.getEquippedArmor());
        assertNull(player.getEquippedWeapon());
        assertTrue(player.getInventory().isEmpty());
    }


}