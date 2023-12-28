package com.game.entities;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "questions")

public class Question {

    @Id
    String id = "";
    String questionStr = "";
    String quizId="";
    Map<String, String> options = new HashMap<>();

    String correctAnswer="";

    public String getId() {
        return id;
    }

    public String getQuestionStr() {
        return questionStr;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", question='" + questionStr + '\'' +
                ", quizId='" + quizId + '\'' +
                ", options=" + options +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
