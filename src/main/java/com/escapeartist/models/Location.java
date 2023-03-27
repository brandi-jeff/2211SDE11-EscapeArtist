package com.escapeartist.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class Location {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("exits")
    private Map<String, Integer> exits;

    @SerializedName("items")
    private List<Item> items;

    @SerializedName("npcs")
    private List<NPC> npcs;

    public void removeNPC(String npcName) {
        npcs.removeIf(npc -> npc.getName().equalsIgnoreCase(npcName));
    }


    public void addItemToLocation(Item item) {
        items.add(item);
    }

    public void removeItem(Item item){
        items.remove(item);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getExits() {
        return exits;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }


}
