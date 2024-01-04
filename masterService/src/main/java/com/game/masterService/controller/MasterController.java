package com.game.masterService.controller;


import com.game.core.Constants;
import com.game.entities.*;
import com.game.utilities.RESTClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
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
    @Value("${createQuiz.endpoint.url}")
    private String createQuizEndpoint;
    @Value("${fetchQuizzes.endpoint.url}")
    private String fetchQuizzesEndpoint;
    @Value("${game.start}")
    private String startGameEndpoint;
    @Value("${game.hostUrl.playerService}")
    private String URL_PLAYER_SERVICE;
    @Value("${createQuizRoom.endpoint.url}")
    private String createQuizRoomEndpoint;
    @Value("${joinGame.endpoint.url}")
    private String joinGameEndpoint;
    @Value("${scores.getAllScores}")
    private String getAllScoresEndpoint;

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
    public void sendGameEvents(GameEvent event){
        if (event.getEventType().equals(Constants.EVENT_TYPE.UPDATE_QUESTION.event())){
            template.convertAndSend("/events/question", event.getQuestion());
        }
        else if (event.getEventType().equals(Constants.EVENT_TYPE.END_QUIZ.event())){
            ResponseEntity<String> scores = RESTClient.getMessage(URL_SCORE_SERVICE+getAllScoresEndpoint+event.getQuizId());
            template.convertAndSend("/events/scores", scores.getBody() != null ? scores.getBody() : "");
        }
    }

//    Game service
    @PostMapping("/startgame")
    public ResponseEntity<String> startGame(@RequestParam String quizId){
        return RESTClient.postMessage(URL_GAME_SERVICE+startGameEndpoint+"?quizId="+quizId, quizId);
    }
    @PostMapping("/joinRoom")
    public ResponseEntity<String> joinGame(@RequestBody Player player){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_PLAYER_SERVICE+joinGameEndpoint, player);
        template.convertAndSend("/events/joinedPlayers", player);
        return responseEntity;
    }

    @PostMapping("/createRoom")
    public ResponseEntity<String> createQuizRoom(@RequestBody Room room){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_ROOM_SERVICE+createQuizRoomEndpoint, room);
        return responseEntity;
    }
    //Adding the quizService API connections

    @GetMapping("/fetchQuizzes/{userName}")
    public ResponseEntity<String> createQuizRoom(@PathVariable String userName){
        ResponseEntity<String> responseEntity = RESTClient.getMessage(URL_QUIZ_SERVICE + fetchQuizzesEndpoint + "/" + userName);
        return responseEntity;
    }

}
