package com.game.masterService.controller;


import com.game.core.Constants;
import com.game.entities.*;
import com.game.utilities.RESTClient;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import static com.game.core.Constants.QUIZ_EVENTS;

@RestController
@RequestMapping("/app")
public class MasterController {

    @Autowired
    SimpMessagingTemplate template;


    @Value("${addscore.endpoint.url}")
    private String addScoreEndpoint;
    @Value("${quiz.createQuizMaster.url}")
    private String createUserEndpoint;
    @Value("${quiz.signin.url}")
    private String signInEndpoint;
    @Value("${game.hostUrl.quizService}")
    private String URL_QUIZ_SERVICE;
    @Value("${game.hostUrl.roomService}")
    private String URL_ROOM_SERVICE;
    @Value("${game.hostUrl.gameService}")
    private String URL_GAME_SERVICE;
    @Value("${game.hostUrl.scoreService}")
    private String URL_SCORE_SERVICE;
    @Value("${createQuizMaster.endpoint.url}")
    private String createQuizMasterEndpoint;
    @Value("${game.hostUrl.playerService}")
    private String URL_PLAYER_SERVICE;
    @Value("${createQuizRoom.endpoint.url}")
    private String createQuizRoomEndpoint;
    @Value("${createQuizRoom.endpoint.url}")
    private String createQuizEndpoint;
    @Value("${joinGame.endpoint.url}")
    private String joinGameEndpoint;

    @PostMapping("/addScore")
    public void addScore(@RequestBody Answer playerAnswer){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_SCORE_SERVICE + addScoreEndpoint, playerAnswer);
        // Check the response if needed
        System.out.println("Response from external service: " + responseEntity.getBody());

    }


    @PostMapping("/createQuizMaster")
    public String createQuizMaster(@RequestBody QuizMaster quizMaster){
        ResponseEntity result = RESTClient.postMessage(URL_QUIZ_SERVICE + createQuizMasterEndpoint, quizMaster);
        // Check the response if needed
        System.out.println("Response from external service: " + result.getBody());
        return result.getBody().toString();
    }


    @PostMapping("/createQuiz/{userName}")
    public String createQuiz(@RequestBody Quiz quiz, @PathVariable String userName){
        ResponseEntity result = RESTClient.postMessage(URL_QUIZ_SERVICE + createQuizEndpoint + "/" + userName, quiz);
        // Check the response if needed
        System.out.println("Response from external service: " + result.getBody());
        return result.getBody().toString();
    }




    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody QuizMaster quizMaster){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_QUIZ_SERVICE+createUserEndpoint, quizMaster);

        return responseEntity;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody QuizMaster quizMaster){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_QUIZ_SERVICE+signInEndpoint, quizMaster);
        return responseEntity;
    }

//    @PostMapping("/updateQuestion")
//    @SendTo("/events/question")
//    public Question updateQuestion(Question question){
//        return question;
//    }
    @KafkaListener(topics = QUIZ_EVENTS, groupId = "quiz_events_questions")
    public void startGame(GameEvent event){
        if (event.getEventType().equals(Constants.EVENT_TYPE.UPDATE_QUESTION.event())){
            template.convertAndSend("/events/question", event.getQuestion());
        }
    }

    @PostMapping("/join")
    public String joinGame(@RequestBody Player player){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_PLAYER_SERVICE+joinGameEndpoint, player);
        return responseEntity.getBody();
    }

    @PostMapping("/create")
    public String createQuizRoom(@RequestBody Room room){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_ROOM_SERVICE+createQuizRoomEndpoint, room);
        return responseEntity.getBody();
    }
    //Adding the quizService API connections



}
