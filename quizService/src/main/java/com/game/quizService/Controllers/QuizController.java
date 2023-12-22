package com.game.quizService.Controllers;


import com.game.quizService.Entities.Quiz;
import com.game.quizService.Entities.QuizMaster;
import com.game.quizService.Utilities.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        String quizMaster = quiz.getQuizMaster();
        //check if quizMasterExists

        List<?> quizMasterList = mongoService.findQuizMaster("_id",username);
        if(quizMasterList.isEmpty()){
            return "QuizMaster does not exist";
        }

        //Create an object with quizId and questions and add it to the DB.
        mongoService.saveData(quiz);
        //send both of it to the db and wait for response
        return "Quiz Created by" + quizMaster;
    }


    @GetMapping("/startQuiz/{quizId}")
    public ResponseEntity<String> startQuiz(@PathVariable String quizId){
        //Fetch the quiz questions
        //send the quiz object to the active-quizes queue
        return ResponseEntity.ok("Started quiz with" + quizId);
    }
}
