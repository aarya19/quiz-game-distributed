package com.game.masterService.controller;


import com.game.core.Constants;
import com.game.entities.Answer;
import com.game.entities.GameEvent;
import com.game.entities.Quiz;
import com.game.entities.QuizMaster;
import com.game.utilities.RESTClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

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
    @Value("${game.start}")
    private String startGameEndpoint;

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
    public String createUser(@RequestBody QuizMaster quizMaster){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_QUIZ_SERVICE+createUserEndpoint, quizMaster);
        return responseEntity.getBody();
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody QuizMaster quizMaster){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_QUIZ_SERVICE+signInEndpoint, quizMaster);
        return responseEntity.getBody();
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

//    Game service
    @PostMapping("/start")
    public ResponseEntity<String> startGame(@RequestParam String quizId){
        return RESTClient.postMessage(URL_GAME_SERVICE+startGameEndpoint+"?quizId="+quizId, quizId);
    }

}
