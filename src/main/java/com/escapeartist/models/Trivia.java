package com.escapeartist.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Trivia {

  @SerializedName("id")
  private int id;

  @SerializedName("question")
  private String question;

  @SerializedName("answer")
  private String answer;


  public static Trivia getTriviaByID(List<Trivia> trivias, int id) {
    for (Trivia trivia : trivias) {
      if (trivia.getId() == id) {
        return trivia;
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

}
