package com.escapeartist.models;

import com.google.gson.annotations.SerializedName;

public class NPC {

    @SerializedName("interactive")
    private boolean interactive;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("reply")
    private String reply;

    @SerializedName("game_invitation")
    private String gameInvitation;

    @SerializedName("goodbye_message")
    private String goodbyeMessage;

    @SerializedName("goodbye_message_2")
    private String goodbyeMessage2;

    @SerializedName("solved")
    private boolean solved;

    // Getters, setters, and other methods

    public boolean isInteractive() {
        return interactive;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getGameInvitation() {
        return gameInvitation;
    }

    public void setGameInvitation(String gameInvitation) {
        this.gameInvitation = gameInvitation;
    }

    public String getGoodbyeMessage() {
        return goodbyeMessage;
    }

    public void setGoodbyeMessage(String goodbyeMessage) {
        this.goodbyeMessage = goodbyeMessage;
    }

    public String getGoodbyeMessage2() {
        return goodbyeMessage2;
    }

    public void setGoodbyeMessage2(String goodbyeMessage2) {
        this.goodbyeMessage2 = goodbyeMessage2;
    }


    public boolean getSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

}
