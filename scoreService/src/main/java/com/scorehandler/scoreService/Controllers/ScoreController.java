package com.scorehandler.scoreService.Controllers;


import com.game.entities.Answer;
import com.game.entities.Question;
import com.game.utilities.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scoreControls")
public class ScoreController {

    private final MongoService mongoService;

    @Autowired
    public ScoreController(MongoService mongoService) {
        this.mongoService = mongoService;
    }

    @PostMapping("/updateScore")
    public String updateScore(@RequestBody Answer playerAnswer){
        String res = "Checking";
        Question question = mongoService.fetchQuestion(playerAnswer.getQuestionId(),playerAnswer.getQuizId());
        if(question.getCorrectAnswer().equalsIgnoreCase(playerAnswer.getAnswer())){
            //update using kSQL the players score to the current score plus 1 and print that it was updated
            res = "SCORED!";
        }
        else{
            res = "No Score :( ";
        }
        return res;
    }
}
