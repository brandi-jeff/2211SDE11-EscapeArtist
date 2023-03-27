package com.escapeartist.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.stream.Collectors;

public class Player {

    @SerializedName("hp")
    private int hp;

    @SerializedName("attack")
    private int attack;

    @SerializedName("defense")
    private int defense;

    @SerializedName("inventory")
    private List<Item> inventory;

    @SerializedName("name")
    private String name;

    @SerializedName("currentLocation")
    private int currentLocation;

    @SerializedName("equippedArmor")
    private Item equippedArmor;

    @SerializedName("equippedWeapon")
    private Item equippedWeapon;

    public Player(int hp, int attack, int defense, List<Item> inventory, int currentLocation, Item equippedArmor, Item equippedWeapon) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.inventory = inventory;
        this.currentLocation = currentLocation;
        this.equippedArmor = equippedArmor;
        this.equippedWeapon = equippedWeapon;
    }

    public void playerStatus(JsonObject gameData) {
        System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_start").getAsString());
        System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_hp").getAsString() + getHp());
        System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_attack").getAsString() + getAttack());
        System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_defense").getAsString() + getDefense());
        List<String> inventoryNames = inventory.stream().map(Item::getName).collect(Collectors.toList());
        String inventoryString = String.join(", ", inventoryNames);
        System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_inventory").getAsString() + inventoryString);

        if (equippedWeapon != null) {
            System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_weapon_equipped").getAsString() + getEquippedWeapon().getName());
        } else {
            System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_weapon_not_equipped").getAsString());
        }

        if (equippedArmor != null) {
            System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_armor_equipped").getAsString() + getEquippedArmor().getName());
        } else {
            System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_armor_not_equipped").getAsString());
        }
        System.out.println(gameData.getAsJsonObject("dialogue").get("player_status_end").getAsString());
    }


    public void addItem(Item item){
        inventory.add(item);
    }


    public void dropItem(Item item) {
        inventory.remove(item);
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(int currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Item getEquippedArmor() {
        return equippedArmor;
    }

    public void setEquippedArmor(Item equippedArmor) {
        this.equippedArmor = equippedArmor;
    }

    public Item getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Item equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public String getName() {
        return name;
    }
}

