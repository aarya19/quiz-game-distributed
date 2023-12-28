package com.game.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
public class GameEvent implements Serializable {

    @Getter
    private String eventType;
    @Getter
    private String quizId;
    @Getter
    private Question question;
    @Getter
    private Map<String, String> response; // playerName: chosenOption

    public GameEvent(String eventType, String quizId) {
        this.eventType = eventType;
        this.quizId = quizId;
//        this.question = new Question();
//        this.response = "";
    }

    public GameEvent(String quizId) {
        this.eventType = "";
        this.quizId = quizId;
//        this.question = "";
//        this.response = "";

    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setResponse(Map<String, String> response) {
        this.response = response;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "GameEvent{" +
                "eventType='" + eventType + '\'' +
                ", quizId='" + quizId + '\'' +
                ", question='" + question + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
