package com.escapeartist.util;

import com.escapeartist.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class GsonDeserializer {

    public GameDialogue deserializeGameDialogue() {
        Reader reader = new InputStreamReader(
            GsonDeserializer.class.getClassLoader().getResourceAsStream("game_dialogue.json"));
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(reader, GameDialogue.class);
    }

    public static List<Location> deserializeLocations() {
        try (Reader reader = new InputStreamReader(
            GsonDeserializer.class.getClassLoader().getResourceAsStream("locations.json"))) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray locationsArray = jsonObject.getAsJsonArray("locations");
            Type listType = new TypeToken<List<Location>>() {
            }.getType();
            return gson.fromJson(locationsArray, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Item> deserializeItems() {
        try (Reader reader = new InputStreamReader(
            GsonDeserializer.class.getClassLoader().getResourceAsStream("items.json"))) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray itemsArray = jsonObject.getAsJsonArray("item");
            Type listType = new TypeToken<List<Item>>() {
            }.getType();
            return gson.fromJson(itemsArray, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<NPC> deserializeNPCs() {
        try (Reader reader = new InputStreamReader(
            GsonDeserializer.class.getClassLoader().getResourceAsStream("npcs.json"))) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray npcsArray = jsonObject.getAsJsonArray("npcs");
            Type listType = new TypeToken<List<NPC>>() {
            }.getType();
            return gson.fromJson(npcsArray, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // New method to deserialize the player JSON
    public JsonObject deserializePlayerJson() {
        try (Reader reader = new InputStreamReader(
            GsonDeserializer.class.getClassLoader().getResourceAsStream("player.json"))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // New method to convert the JsonObject into a Player object
    public Player deserializePlayer(JsonObject playerJson) {
        Gson gson = new Gson();
        return gson.fromJson(playerJson, Player.class);
    }

    public List<Riddle> deserializeRiddles() {
        try (Reader reader = new InputStreamReader(
            GsonDeserializer.class.getClassLoader().getResourceAsStream("riddle.json"))) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray riddlesArray = jsonObject.getAsJsonArray("riddles");
            Type listType = new TypeToken<List<Riddle>>() {
            }.getType();
            return gson.fromJson(riddlesArray, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Unscramble deserializeUnscramble() {
        Reader reader = new InputStreamReader(GsonDeserializer.class.getClassLoader().getResourceAsStream("unscramble.json"));
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(reader, Unscramble.class);
    }
    public List<Trivia> deserializeTrivia() {
        try (Reader reader = new InputStreamReader(
            GsonDeserializer.class.getClassLoader().getResourceAsStream("trivia.json"))) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray triviaArray = jsonObject.getAsJsonArray("trivias");
            Type listType = new TypeToken<List<Trivia>>(){
            }.getType();
            return gson.fromJson(triviaArray, listType);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}



