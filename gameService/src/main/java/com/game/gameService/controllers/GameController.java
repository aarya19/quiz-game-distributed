package com.game.gameService.controllers;

import com.game.common.KafkaProducerConfig;
import com.game.entities.Question;
import com.game.entities.Quiz;
import com.game.gameService.service.GameService;
import com.game.utilities.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RestController
@RequestMapping("/game")
public class GameController {

    private final MongoService mongoService;
    private KafkaProducerConfig producer;


    @Autowired
    public GameController(MongoService mongoService, KafkaProducerConfig producer) {
        this.mongoService = mongoService;
        this.producer = producer;
    }

    // start quiz
    @GetMapping("/start/{quizId}")
    public String startGame(@RequestParam String quizId){
        // fetch questions from db
        Quiz quiz = mongoService.fetchQuiz(quizId);
        GameService service = new GameService(this.producer);
        service.startGame(quiz);

        return "Started Game";
    }
}
