package com.game.masterService.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.common.KafkaProducerConfig;
import com.game.entities.Answer;
import com.game.entities.QuizMaster;
import com.game.utilities.MongoService;
import com.game.utilities.RESTClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/app")
public class MasterController {


    @Value("${addscore.endpoint.url}")
    private String addScoreEndpoint;
    @Value("${quiz.createQuizMaster.url}")
    private String createUserEndpoint;
    @Value("${game.hostUrl.quizService}")
    private String URL_QUIZ_SERVICE;
    @Value("${game.hostUrl.roomService}")
    private String URL_ROOM_SERVICE;
    @Value("${game.hostUrl.gameService}")
    private String URL_GAME_SERVICE;
    @Value("${game.hostUrl.scoreService}")
    private String URL_SCORE_SERVICE;

    @PostMapping("/addScore")
    public void addScore(@RequestBody Answer playerAnswer){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_SCORE_SERVICE + addScoreEndpoint, playerAnswer);
        // Check the response if needed
        System.out.println("Response from external service: " + responseEntity.getBody());

//        RedirectView redirectView = new RedirectView();
//        // Set the URL to redirect to after the POST request
//        redirectView.setUrl("https://your-redirect-url");
//        return redirectView;
    }

    @PostMapping("signup")
    public String createUser(@RequestBody QuizMaster quizMaster){
        ResponseEntity<String> responseEntity = RESTClient.postMessage(URL_QUIZ_SERVICE+createUserEndpoint, quizMaster);
        return responseEntity.getBody();
    }



}
