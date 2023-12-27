package com.game.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "quiz_master") //edit this to the collection name

public class QuizMaster {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Id
    String userName;
    String name;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuizMaster(String userName, String name, String password) {
        this.userName = userName;
        this.name = name;
        this.password = password;
    }

    public QuizMaster() {
    }
}
