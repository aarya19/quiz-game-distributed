package com.game.quizService.Entities;

import java.util.HashMap;
import java.util.Map;

public class Question {
    String question;
    Map<Character, String> options = new HashMap<Character, String>();

    Character correctAsnwer;
}
