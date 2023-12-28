package com.game.core;

public class Constants {
    public static final String ROOM_TOPIC = "Rooms";
    public static final String ACTIVE_QUESTIONS = "active-questions";
    public static final String QUIZ_EVENTS = "quiz-events";

    public static enum EVENT_TYPE {
        UPDATE_QUESTION("update_question"),
        UPDATE_SCORE("update_score"),
        UPDATE_RESPONSE("update_response"),
        START_QUIZ("start_quiz"),
        END_QUIZ("end_quiz");

        final String label;
        public String event(){
            return label;
        }

        EVENT_TYPE(String label) {
            this.label = label;
        }
    }
}
