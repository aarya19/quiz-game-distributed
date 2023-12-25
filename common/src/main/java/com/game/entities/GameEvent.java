package com.game.entities;

import java.util.Map;


public class GameEvent {
    public static enum EVENT_TYPE {
        UPDATE_QUESTION("update_question"),
        UPDATE_SCORE("update_score"),
        START_QUIZ("start_quiz"),
        END_QUIZ("end_quiz");

        final String label;

        EVENT_TYPE(String label) {
            this.label = label;
        }
    }

    private String eventType;
    private int quizId;
    private Question question;
    private Map<Player, Character> response;

    public GameEvent(String eventType, int quizId) {
        this.eventType = eventType;
        this.quizId = quizId;
        this.question = null;
        this.response = null;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setResponse(Map<Player, Character> response) {
        this.response = response;
    }
}
