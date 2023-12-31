package com.game.quizService.Controllers;


import com.game.entities.Quiz;
import com.game.entities.QuizMaster;
import com.game.entities.Room;
import com.game.utilities.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/quizControls")
public class QuizController {

    private final MongoService mongoService;

    @Autowired
    public QuizController(MongoService mongoService) {
        this.mongoService = mongoService;
    }



    @PostMapping("/createQuizMaster")
    public String createQuizMaster(@RequestBody QuizMaster quizMaster){
        mongoService.saveData(quizMaster);
        return "New Quiz Master has been added with username!" + quizMaster.getUserName();
    }


    @PostMapping("/createQuiz/{username}")
    public String createQuiz(@RequestBody Quiz quiz, @PathVariable String username){
        QuizMaster quizMaster = quiz.getQuizMaster();

        //check if quizMasterExists
        List<?> quizMasterList = mongoService.findQuizMaster("_id",username);
        if(quizMasterList.isEmpty()){
            return "QuizMaster does not exist";
        }

        //add Quiz object to the DB.
        mongoService.saveData(quiz);
        return "Quiz Created by" + username;
    }


    @GetMapping("/startQuiz/{quizId}/{quizMasterUserName}")
    public ResponseEntity<String> startQuiz(@PathVariable String quizId, @PathVariable String quizMasterUserName){
        //Find the quiz master
        //If the Quiz Master is not found return a Response with an error saying the quiz master does not exist
        //If the Quiz Master exists get the quiz master and create a room with quiz Id and quiz master
        //Send the Room as the body of the request to the next endpoint.

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8081/quizroom/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<?> quizMasterList = mongoService.findQuizMaster("_id",quizMasterUserName);
        if(quizMasterList.isEmpty()){
            String response = restTemplate.postForObject(apiUrl, null, String.class);
            // Process the response as needed
            System.out.println("Response: " + response);
            return ResponseEntity.ok("The quiz master does not exist. The response is: "+response);
        }
        else{
            QuizMaster quizMaster = (QuizMaster) quizMasterList.get(0);
            Room room = new Room(quizId,quizMaster);
            String response = restTemplate.postForObject(apiUrl, room, String.class);
            System.out.println("Response: " + response);
            return ResponseEntity.ok("Started quiz with" + quizId);
        }

    }
}
