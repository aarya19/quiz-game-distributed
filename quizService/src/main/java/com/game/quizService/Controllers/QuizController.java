package com.game.quizService.Controllers;


import com.game.entities.Quiz;
import com.game.entities.QuizMaster;
import com.game.utilities.MongoService;
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


    @GetMapping("/startQuiz/{quizId}")
    public ResponseEntity<String> startQuiz(@PathVariable String quizId){
        //Fetch the quiz questions
        //send the quiz object to the active-quizes queue
        return ResponseEntity.ok("Started quiz with" + quizId);
    }
}
