package com.game.quizService.Entities;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "quizzes") //edit this to the collection name

public class Quiz {


    String quizMaster;

    @Id
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
