package com.game.entities;

import java.util.ArrayList;

import com.game.entities.Question;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "quizzes") //edit this to the collection name

public class Quiz {


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    String userName;

    public String getQuizTtitle() {
        return quizTtitle;
    }

    public void setQuizTtitle(String quizTtitle) {
        this.quizTtitle = quizTtitle;
    }

    String quizTtitle;

    @Id
    String quizId;
    ArrayList<Question> questions;





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
