package com.game.quizService.Controllers;


import com.game.quizService.Entities.Quiz;
import com.game.quizService.Entities.QuizMaster;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quizControls")
public class QuizController {

    @PostMapping("/createQuizMaster")
    public String createQuizMaster(@RequestBody QuizMaster quizMaster){
        String userId = quizMaster.getUserName();
        String password = quizMaster.getPassword();

        //send both of it to the db and wait for response
        return "Status of the persistence";
    }


    @PostMapping("/createQuiz")
    public String createQuiz(@RequestBody Quiz quiz){
        String quizMaster = quiz.getQuizMaster();
        //check if quizMasterExists

        //Create an object with quizId and questions and add it to the DB.

        //send both of it to the db and wait for response
        return "Quiz Created by" + quizMaster;
    }


    @GetMapping("/startQuiz/{quizId}")
    public ResponseEntity<String> startQuiz(@PathVariable String quizId){
        //Fetch the quiz questions
        //create a thread and send it to the queue
        return ResponseEntity.ok("Started quiz with" + quizId);
    }
}
