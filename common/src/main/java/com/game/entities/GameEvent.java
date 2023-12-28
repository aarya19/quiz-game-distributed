package com.game.entities;

import java.util.Map;


public class GameEvent {

    private String eventType;
    private String quizId;
    private Question question;
    private Map<Player, Character> response;

    public GameEvent(String eventType, String quizId) {
        this.eventType = eventType;
        this.quizId = quizId;
        this.question = null;
        this.response = null;
    }

    public GameEvent(String quizId) {
        this.quizId = quizId;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setResponse(Map<Player, Character> response) {
        this.response = response;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getQuizId() {
        return quizId;
    }

    public Question getQuestion() {
        return question;
    }

    public Map<Player, Character> getResponse() {
        return response;
    }
}
