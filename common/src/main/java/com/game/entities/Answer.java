package com.game.entities;

public class Answer {
    public Answer() {

    }

    public Answer(String playerId, String quizId, String questionId, String answer, Question question) {
        this.playerId = playerId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.question = question;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    String playerId;
    String quizId;
    String questionId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    String answer;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    Question question;


}
