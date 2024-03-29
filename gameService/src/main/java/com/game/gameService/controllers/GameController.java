package com.game.gameService.controllers;

import com.game.common.KafkaProducerConfig;
import com.game.common.KafkaTopicConfig;
import com.game.core.Constants;
import com.game.entities.GameEvent;
import com.game.entities.Question;
import com.game.entities.Quiz;
import com.game.gameService.service.GameService;
import com.game.utilities.MongoService;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.game.core.Constants.QUIZ_EVENTS;


@RestController
@RequestMapping("/game")
public class GameController {

    private final MongoService mongoService;
    private KafkaProducerConfig producer;
    @Value("${game.question.timeout}")
    private long gameTimeout;


    @Autowired
    public GameController(MongoService mongoService, KafkaProducerConfig producer) {
        this.mongoService = mongoService;
        this.producer = producer;

    }

    // start quiz
    @PostMapping("/start")
    public String startGame(@RequestParam String quizId){
        KafkaTopicConfig topicConfig = new KafkaTopicConfig();
        NewTopic topic = topicConfig.createTopic(QUIZ_EVENTS);
        producer.setTopic(topic);
        GameEvent event = new GameEvent(Constants.EVENT_TYPE.START_QUIZ.event(),quizId);
        producer.sendMessage(event);
        return "Started Game";
    }

    // Use the kafka
    @KafkaListener(topics = QUIZ_EVENTS, groupId = "quiz_events")
    public void startGame(GameEvent event){
        if (event.getEventType().equals(Constants.EVENT_TYPE.START_QUIZ.event())){
            Quiz quiz = mongoService.fetchQuiz(event.getQuizId());
            GameService service = new GameService(this.producer);
            service.setGameTimeout(gameTimeout);
            service.startGame(quiz);
        }
        if (event.getEventType().equals(Constants.EVENT_TYPE.UPDATE_RESPONSE.event())){

            GameService service = new GameService(this.producer);
            service.updateResponse(event);
        }
    }

    // TO-DO: Remove this and create an equivalent test case in a test suite
    @PostMapping("/test/updateResponse")
    public void updateResponse(@RequestBody GameEvent event){
        KafkaTopicConfig topicConfig = new KafkaTopicConfig();
        NewTopic topic = topicConfig.createTopic(QUIZ_EVENTS);
        producer.setTopic(topic);
        event.setEventType(Constants.EVENT_TYPE.UPDATE_SCORE.event());
        producer.sendMessage(event);
    }

//    @KafkaListener(topics = QUIZ_EVENTS, groupId = "response")
//    public void updateResponse(GameEvent event){
//        if (event.getEventType().equals(Constants.EVENT_TYPE.UPDATE_RESPONSE.event())){
//
//            GameService service = new GameService(this.producer);
//
//            service.updateResponse(event);
//        }
//    }
}
