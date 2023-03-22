package com.escapeartist.views;

import java.util.List;
import java.util.Map;

import com.escapeartist.models.Item;
import com.escapeartist.models.Location;
import com.escapeartist.models.NPC;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class GameView {
    private JsonObject gameData;

    public GameView(JsonObject gameData) {
        this.gameData = gameData;
    }

    public void displayLocation(JsonElement location) {
        JsonObject locationObject = location.getAsJsonObject();
        System.out.println(gameData.getAsJsonObject("dialogue").get("current_location").getAsString() + locationObject.get("name").getAsString());
        System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_end").getAsString());
        System.out.println(locationObject.get("description").getAsString());
        System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_end").getAsString());
        System.out.print(gameData.getAsJsonObject("dialogue").get("exits_text").getAsString());
        locationObject.get("exits").getAsJsonObject().entrySet().forEach(entry -> System.out.print(entry.getKey() + ", "));
        System.out.println();

        JsonArray itemsArray = new JsonArray();
        if (locationObject.has("items")) {
            itemsArray = locationObject.get("items").getAsJsonArray();
        }
        String itemsText = gameData.getAsJsonObject("dialogue").get("items_text").getAsString();
        itemsText += getJsonArray(itemsArray, "name");
        System.out.println(itemsText);

        JsonArray npcsArray = new JsonArray();
        if (locationObject.has("npcs")) {
            npcsArray = locationObject.get("npcs").getAsJsonArray();
        }
        String npcsText = gameData.getAsJsonObject("dialogue").get("npcs_text").getAsString();
        npcsText += getJsonArray(npcsArray, "name");
        System.out.println(npcsText);
        System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_end").getAsString());
    }



    private String getJsonArray(JsonArray jsonArray, String fieldName) {
        StringBuilder stringArray = new StringBuilder();

        for (int i = 0; i < jsonArray.size(); i ++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            stringArray.append(jsonObject.get(fieldName).getAsString());
            if(i < jsonArray.size() - 1 ){
                stringArray.append(", ");
            }
        }
        return stringArray.toString();
    }


}
