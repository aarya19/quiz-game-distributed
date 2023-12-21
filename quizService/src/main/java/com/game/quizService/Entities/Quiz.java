package com.game.quizService.Entities;

import java.util.ArrayList;

public class Quiz {
    String quizMaster;
    String quizId;
    ArrayList<Question> questions;


    public String getQuizMaster() {
        return quizMaster;
    }

    public void setQuizMaster(String quizMaster) {
        this.quizMaster = quizMaster;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
