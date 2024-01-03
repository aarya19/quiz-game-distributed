package com.game.quizService.Controllers;


import com.game.entities.Quiz;
import com.game.entities.QuizMaster;
import com.game.utilities.MongoService;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.serializer.Serializer;
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

    @PostMapping("/signin")
    public Response signIn(@RequestBody QuizMaster user){
        QuizMaster quizMaster = mongoService.findQuizMaster("_id",user.getUserName());
        if(quizMaster == null || !quizMaster.getPassword().equals(user.getPassword())){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }

        return Response.status(Response.Status.OK).entity(quizMaster).build();
    }


    @PostMapping("/createQuiz/{username}")
    public String createQuiz(@RequestBody Quiz quiz, @PathVariable String username){
        //check if quizMasterExists
        QuizMaster quizMaster = mongoService.findQuizMaster("_id",username);
        if(quizMaster == null){
            return "QuizMaster does not exist";
        }

        //add Quiz object to the DB.
        mongoService.saveData(quiz);
        return "Quiz Created by" + quizMaster.getName();
    }


    @GetMapping("/startQuiz/{quizId}")
    public ResponseEntity<String> startQuiz(@PathVariable String quizId){
        //Fetch the quiz questions
        //send the quiz object to the active-quizes queue
        return ResponseEntity.ok("Started quiz with" + quizId);
    }
}
