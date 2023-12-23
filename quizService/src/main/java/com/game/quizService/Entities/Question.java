package com.game.quizService.Entities;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "questions")

public class Question {

    @Id
    String id;
    String question;
    Map<Character, String> options = new HashMap<Character, String>();

    Character correctAsnwer;
}
