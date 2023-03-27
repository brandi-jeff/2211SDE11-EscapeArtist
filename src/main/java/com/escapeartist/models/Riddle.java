package com.escapeartist.models;

import com.escapeartist.util.GsonDeserializer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Riddle {
    @SerializedName("id")
    private int id;

    @SerializedName("question")
    private String question;

    @SerializedName("answer")
    private String answer;

    public static Riddle getRiddleById(List<Riddle> riddles, int id) {
        for (Riddle riddle : riddles) {
            if (riddle.getId() == id) {
                return riddle;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

