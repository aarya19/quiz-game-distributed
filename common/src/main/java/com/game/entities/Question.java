package com.game.entities;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "questions")

public class Question {

    @Id
    String id;
    String question;
    int quizId;
    Map<Character, String> options = new HashMap<>();

    Character correctAsnwer;

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuizId() {
        return quizId;
    }

    public Map<Character, String> getOptions() {
        return options;
    }

    public Character getCorrectAsnwer() {
        return correctAsnwer;
    }
}
