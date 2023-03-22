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
}
