package com.game.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.core.Constants;

import java.io.Serializable;

public class ScoreEvent implements Serializable {
    public ScoreEvent(String playerId, String quizId) {
        this.playerId = playerId;
        this.quizId = quizId;
    }

    public ScoreEvent() {
    }

    // Constructor with JsonCreator and JsonProperty annotations
    @JsonCreator
    public ScoreEvent(
            @JsonProperty("playerId") String playerId,
            @JsonProperty("eventType") String eventType,
            @JsonProperty("quizId") String quizId) {
        this.playerId = playerId;
        this.eventType = eventType;
        this.quizId = quizId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
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

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    private String playerId;
    private String eventType;
    private String quizId;





}
