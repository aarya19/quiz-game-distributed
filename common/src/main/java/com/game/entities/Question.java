package com.game.entities;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "questions")

public class Question {

    @Id
    String id = "";
    String question = "";
    String quizId="";
    Map<String, String> options = new HashMap<>();

    String correctAnswer="";

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuizId() {
        return quizId;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", quizId='" + quizId + '\'' +
                ", options=" + options +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
